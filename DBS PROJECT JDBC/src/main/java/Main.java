public class Main {
    public static void main(String[] args) {

        // ── Step 1: Set up the database ──────────────────────────────────────
        System.out.println("╔══════════════════════════════════════╗");
        System.out.println("║   Kidney Transplant Management System ║");
        System.out.println("╚══════════════════════════════════════╝");
        System.out.println();
        System.out.println(">>> Initializing database from 'Project DBMS.sql'...");

        try {
            DbmsProjectRunner.runSqlScript("Project DBMS.sql");
            System.out.println(">>> Database ready.\n");
        } catch (Exception e) {
            System.out.println(">>> Warning: SQL script issue — " + e.getMessage());
            System.out.println(">>> Continuing (DB may already be set up).\n");
        }

        // ── Step 2: Print all reports in terminal ────────────────────────────
        System.out.println(">>> Running all reports...\n");

        System.out.println(ReportsDAO.getAvailableBedsReport());
        System.out.println(ReportsDAO.getLegalClearanceReport());
        System.out.println(ReportsDAO.getDoctorAssignmentsReport());
        System.out.println(ReportsDAO.getPostOpFollowUpsReport());
        System.out.println(ReportsDAO.getSummaryStatsReport());
        System.out.println(ReportsDAO.getHlaMatchesReport());

        // ── Step 3: Launch GUI ───────────────────────────────────────────────
        System.out.println(">>> Launching GUI...");
        OrganMatchApp.main(args);
    }
}