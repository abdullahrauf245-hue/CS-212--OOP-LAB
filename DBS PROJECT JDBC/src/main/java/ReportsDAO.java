import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportsDAO {

    // --- AVAILABLE BEDS REPORT ------------------------------------------------
    public static void viewAvailableBeds() {
        String sql = """
                SELECT r.room_name, r.type AS room_type, b.bed_name, b.status
                FROM Room r
                JOIN Bed b ON r.room_id = b.room_id
                WHERE b.status = 'Available'
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n==============================================");
            System.out.println("           AVAILABLE BEDS REPORT             ");
            System.out.println("==============================================");
            System.out.printf("%-12s %-18s %-12s %-10s%n",
                    "Room", "Type", "Bed", "Status");
            System.out.println("----------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-12s %-18s %-12s %-10s%n",
                        rs.getString("room_name"),
                        rs.getString("room_type"),
                        rs.getString("bed_name"),
                        rs.getString("status"));
            }
            if (!found) {
                System.out.println("No beds currently available.");
            }
            System.out.println("==============================================");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- LEGAL CLEARANCE REPORT ----------------------------------------------
    public static void viewLegalClearance() {
        String sql = """
                SELECT d.name AS Donor, r.name AS Recipient,
                       lc.committee_officer, lc.status AS Clearance_Status,
                       ga.grant_date
                FROM Legal_Clearance lc
                JOIN Donor           d  ON lc.d_id = d.d_id
                JOIN Recipient       r  ON lc.r_id = r.r_id
                JOIN Grant_Approval  ga ON d.d_id = ga.d_id AND r.r_id = ga.r_id
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n==================================================================");
            System.out.println("                  LEGAL CLEARANCE REPORT                        ");
            System.out.println("==================================================================");
            System.out.printf("%-16s %-16s %-18s %-12s %-12s%n",
                    "Donor", "Recipient", "Officer", "Clearance", "Grant Date");
            System.out.println("------------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-16s %-16s %-18s %-12s %-12s%n",
                        rs.getString("Donor"),
                        rs.getString("Recipient"),
                        rs.getString("committee_officer"),
                        rs.getString("Clearance_Status"),
                        rs.getString("grant_date"));
            }
            if (!found) {
                System.out.println("No clearance data found.");
            }
            System.out.println("==================================================================");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- DOCTOR ASSIGNMENT REPORT --------------------------------------------
    public static void viewDoctorAssignments() {
        String sql = """
                SELECT doc.name AS Doctor, doc.specialization,
                       b.bed_name, r.room_name, da.shift
                FROM Doc_Assignment da
                JOIN Doctor doc ON da.doc_id = doc.doc_id
                JOIN Bed    b   ON da.bed_id  = b.bed_id
                JOIN Room   r   ON b.room_id  = r.room_id
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n==============================================================");
            System.out.println("                DOCTOR ASSIGNMENT REPORT                     ");
            System.out.println("==============================================================");
            System.out.printf("%-18s %-22s %-10s %-12s %-10s%n",
                    "Doctor", "Specialization", "Bed", "Room", "Shift");
            System.out.println("--------------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-18s %-22s %-10s %-12s %-10s%n",
                        rs.getString("Doctor"),
                        rs.getString("specialization"),
                        rs.getString("bed_name"),
                        rs.getString("room_name"),
                        rs.getString("shift"));
            }
            if (!found) {
                System.out.println("No doctor assignments found.");
            }
            System.out.println("==============================================================");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- POST-OP FOLLOWUP REPORT ---------------------------------------------
    public static void viewPostOpFollowUps() {
        String sql = """
                SELECT r.name AS Recipient, doc.name AS Doctor,
                       pf.visit_date, pf.creatinine_level, pf.status
                FROM Post_Op_FollowUp pf
                JOIN Recipient r  ON pf.r_id   = r.r_id
                JOIN Doctor    doc ON pf.doc_id = doc.doc_id
                ORDER BY pf.visit_date
                """;

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            System.out.println("\n==========================================================");
            System.out.println("               POST-OP FOLLOW-UP REPORT                  ");
            System.out.println("==========================================================");
            System.out.printf("%-18s %-18s %-12s %-12s %-12s%n",
                    "Recipient", "Doctor", "Visit Date", "Creatinine", "Status");
            System.out.println("----------------------------------------------------------");

            boolean found = false;
            while (rs.next()) {
                found = true;
                System.out.printf("%-18s %-18s %-12s %-12.2f %-12s%n",
                        rs.getString("Recipient"),
                        rs.getString("Doctor"),
                        rs.getString("visit_date"),
                        rs.getDouble("creatinine_level"),
                        rs.getString("status"));
            }
            if (!found) {
                System.out.println("No follow-up records found.");
            }
            System.out.println("==========================================================");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // --- SUMMARY STATS --------------------------------------------------------
    public static void viewSummaryStats() {
        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

            System.out.println("\n+------------------------------+");
            System.out.println("|     SYSTEM SUMMARY STATS     |");
            System.out.println("+------------------------------+");

            String[] queries = {
                "SELECT COUNT(*) FROM Donor",
                "SELECT COUNT(*) FROM Recipient",
                "SELECT COUNT(*) FROM Transplant",
                "SELECT COUNT(*) FROM Waiting_List WHERE status = 'Active'"
            };
            String[] labels = {
                "Total Donors",
                "Total Recipients",
                "Total Transplants",
                "Active on Waiting List"
            };

            for (int i = 0; i < queries.length; i++) {
                try (ResultSet rs = st.executeQuery(queries[i])) {
                    if (rs.next()) {
                        System.out.printf("|  %-22s : %-4d |%n", labels[i], rs.getInt(1));
                    }
                }
            }

            System.out.println("+------------------------------+");

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
