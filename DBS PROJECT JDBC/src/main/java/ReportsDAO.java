import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class ReportsDAO {

    // --- AVAILABLE BEDS REPORT ------------------------------------------------
    public static void viewAvailableBeds() {
        System.out.print(getAvailableBedsReport());
    }

    public static String getAvailableBedsReport() {
        String sql = """
                SELECT r.room_name, r.type AS room_type, b.bed_name, b.status
                FROM Room r
                JOIN Bed b ON r.room_id = b.room_id
                WHERE b.status = 'Available'
                """;

        StringBuilder out = new StringBuilder();
        out.append("\n==============================================\n");
        out.append("           AVAILABLE BEDS REPORT             \n");
        out.append("==============================================\n");
        out.append(String.format("%-12s %-18s %-12s %-10s%n",
                "Room", "Type", "Bed", "Status"));
        out.append("----------------------------------------------\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.append(String.format("%-12s %-18s %-12s %-10s%n",
                        rs.getString("room_name"),
                        rs.getString("room_type"),
                        rs.getString("bed_name"),
                        rs.getString("status")));
            }
            if (!found) {
                out.append("No beds currently available.\n");
            }
            out.append("==============================================\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // --- LEGAL CLEARANCE REPORT ----------------------------------------------
    public static void viewLegalClearance() {
        System.out.print(getLegalClearanceReport());
    }

    public static String getLegalClearanceReport() {
        String sql = """
                SELECT d.name AS Donor, r.name AS Recipient,
                       lc.committee_officer, lc.status AS Clearance_Status,
                       ga.grant_date
                FROM Legal_Clearance lc
                JOIN Donor           d  ON lc.d_id = d.d_id
                JOIN Recipient       r  ON lc.r_id = r.r_id
                JOIN Grant_Approval  ga ON d.d_id = ga.d_id AND r.r_id = ga.r_id
                """;

        StringBuilder out = new StringBuilder();
        out.append("\n==================================================================\n");
        out.append("                  LEGAL CLEARANCE REPORT                        \n");
        out.append("==================================================================\n");
        out.append(String.format("%-16s %-16s %-18s %-12s %-12s%n",
                "Donor", "Recipient", "Officer", "Clearance", "Grant Date"));
        out.append("------------------------------------------------------------------\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.append(String.format("%-16s %-16s %-18s %-12s %-12s%n",
                        rs.getString("Donor"),
                        rs.getString("Recipient"),
                        rs.getString("committee_officer"),
                        rs.getString("Clearance_Status"),
                        rs.getString("grant_date")));
            }
            if (!found) {
                out.append("No clearance data found.\n");
            }
            out.append("==================================================================\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // --- DOCTOR ASSIGNMENT REPORT --------------------------------------------
    public static void viewDoctorAssignments() {
        System.out.print(getDoctorAssignmentsReport());
    }

    public static String getDoctorAssignmentsReport() {
        String sql = """
                SELECT doc.name AS Doctor, doc.specialization,
                       b.bed_name, r.room_name, da.shift
                FROM Doc_Assignment da
                JOIN Doctor doc ON da.doc_id = doc.doc_id
                JOIN Bed    b   ON da.bed_id  = b.bed_id
                JOIN Room   r   ON b.room_id  = r.room_id
                """;

        StringBuilder out = new StringBuilder();
        out.append("\n==============================================================\n");
        out.append("                DOCTOR ASSIGNMENT REPORT                     \n");
        out.append("==============================================================\n");
        out.append(String.format("%-18s %-22s %-10s %-12s %-10s%n",
                "Doctor", "Specialization", "Bed", "Room", "Shift"));
        out.append("--------------------------------------------------------------\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.append(String.format("%-18s %-22s %-10s %-12s %-10s%n",
                        rs.getString("Doctor"),
                        rs.getString("specialization"),
                        rs.getString("bed_name"),
                        rs.getString("room_name"),
                        rs.getString("shift")));
            }
            if (!found) {
                out.append("No doctor assignments found.\n");
            }
            out.append("==============================================================\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // --- POST-OP FOLLOWUP REPORT ---------------------------------------------
    public static void viewPostOpFollowUps() {
        System.out.print(getPostOpFollowUpsReport());
    }

    public static String getPostOpFollowUpsReport() {
        String sql = """
                SELECT r.name AS Recipient, doc.name AS Doctor,
                       pf.visit_date, pf.creatinine_level, pf.status
                FROM Post_Op_FollowUp pf
                JOIN Recipient r  ON pf.r_id   = r.r_id
                JOIN Doctor    doc ON pf.doc_id = doc.doc_id
                ORDER BY pf.visit_date
                """;

        StringBuilder out = new StringBuilder();
        out.append("\n==========================================================\n");
        out.append("               POST-OP FOLLOW-UP REPORT                  \n");
        out.append("==========================================================\n");
        out.append(String.format("%-18s %-18s %-12s %-12s %-12s%n",
                "Recipient", "Doctor", "Visit Date", "Creatinine", "Status"));
        out.append("----------------------------------------------------------\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.append(String.format("%-18s %-18s %-12s %-12.2f %-12s%n",
                        rs.getString("Recipient"),
                        rs.getString("Doctor"),
                        rs.getString("visit_date"),
                        rs.getDouble("creatinine_level"),
                        rs.getString("status")));
            }
            if (!found) {
                out.append("No follow-up records found.\n");
            }
            out.append("==========================================================\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // --- SUMMARY STATS --------------------------------------------------------
    public static void viewSummaryStats() {
        System.out.print(getSummaryStatsReport());
    }

    public static String getSummaryStatsReport() {
        StringBuilder out = new StringBuilder();
        out.append("\n+------------------------------+\n");
        out.append("|     SYSTEM SUMMARY STATS     |\n");
        out.append("+------------------------------+\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement()) {

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
                        out.append(String.format("|  %-22s : %-4d |%n", labels[i], rs.getInt(1)));
                    }
                }
            }

            out.append("+------------------------------+\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }

    // --- HLA MATCHES ----------------------------------------------------------
    public static String getHlaMatchesReport() {
        String sql = """
                SELECT d.name AS donor_name,
                       r.name AS recipient_name,
                       do.name AS donor_organ,
                       ro.name AS recipient_need,
                       (CASE WHEN rht.hla_a1 = dht.hla_a1 THEN 1 ELSE 0 END +
                        CASE WHEN rht.hla_a2 = dht.hla_a2 THEN 1 ELSE 0 END +
                        CASE WHEN rht.hla_b1 = dht.hla_b1 THEN 1 ELSE 0 END +
                        CASE WHEN rht.hla_b2 = dht.hla_b2 THEN 1 ELSE 0 END +
                        CASE WHEN rht.hla_dr1 = dht.hla_dr1 THEN 1 ELSE 0 END +
                        CASE WHEN rht.hla_dr2 = dht.hla_dr2 THEN 1 ELSE 0 END) AS match_count
                FROM Recipient_HLA_Test rht
                JOIN Recipient_Organ ro ON rht.ro_id = ro.ro_id
                JOIN Recipient r ON ro.r_id = r.r_id
                JOIN Donor_HLA_Test dht
                JOIN Donor_Organ do ON dht.od_id = do.od_id
                JOIN Donor d ON do.d_id = d.d_id
                WHERE do.status = 'Available'
                ORDER BY match_count DESC
                LIMIT 5
                """;

        StringBuilder out = new StringBuilder();
        out.append("\n==============================================================\n");
        out.append("                 TOP HLA MATCHES (TOP 5)                     \n");
        out.append("==============================================================\n");
        out.append(String.format("%-16s %-16s %-16s %-18s %-8s%n",
                "Donor", "Recipient", "Donor Organ", "Recipient Need", "HLA"));
        out.append("--------------------------------------------------------------\n");

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            boolean found = false;
            while (rs.next()) {
                found = true;
                out.append(String.format("%-16s %-16s %-16s %-18s %-8s%n",
                        rs.getString("donor_name"),
                        rs.getString("recipient_name"),
                        rs.getString("donor_organ"),
                        rs.getString("recipient_need"),
                        rs.getInt("match_count") + "/6"));
            }
            if (!found) {
                out.append("No HLA matches found.\n");
            }
            out.append("==============================================================\n");
        } catch (SQLException e) {
            out.append("Error: ").append(e.getMessage()).append("\n");
        }

        return out.toString();
    }
}
