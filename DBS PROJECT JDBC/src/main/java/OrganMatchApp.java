import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.function.Supplier;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class OrganMatchApp {
    private static final Color BG_DARK = new Color(5, 5, 5);
    private static final Color BG_PANEL = new Color(7, 7, 7);
    private static final Color BG_GLASS = new Color(12, 12, 12);
    private static final Color TEXT_MAIN = new Color(248, 248, 248);
    private static final Color TEXT_MUTED = new Color(161, 161, 170);
    private static final Color ACCENT = new Color(255, 45, 45);
    private static final Color ACCENT_DEEP = new Color(179, 0, 0);
    private static final Color BORDER = new Color(40, 40, 40);

    private static final Font FONT_TITLE = new Font("SansSerif", Font.BOLD, 20);
    private static final Font FONT_LABEL = new Font("SansSerif", Font.PLAIN, 13);
    private static final Font FONT_MONO = new Font("Monospaced", Font.PLAIN, 12);

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new OrganMatchApp().show());
    }

    private void show() {
        JFrame frame = new JFrame("OrganMatch AI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setMinimumSize(new Dimension(980, 680));
        frame.setLocationRelativeTo(null);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(BG_DARK);
        root.setBorder(BorderFactory.createEmptyBorder(16, 20, 20, 20));

        root.add(buildHeader(), BorderLayout.NORTH);
        root.add(buildTabs(), BorderLayout.CENTER);

        frame.setContentPane(root);
        frame.pack();
        frame.setVisible(true);
    }

    private JComponent buildHeader() {
        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(BG_PANEL);
        header.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(16, 20, 16, 20)));

        JLabel title = new JLabel("OrganMatch AI");
        title.setFont(FONT_TITLE);
        title.setForeground(TEXT_MAIN);

        JLabel subtitle = new JLabel("Transplant matching and operational reports");
        subtitle.setFont(FONT_LABEL);
        subtitle.setForeground(TEXT_MUTED);

        JPanel textPanel = new JPanel();
        textPanel.setBackground(BG_PANEL);
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        textPanel.add(title);
        textPanel.add(Box.createVerticalStrut(4));
        textPanel.add(subtitle);

        JLabel status = new JLabel("Connected", SwingConstants.RIGHT);
        status.setFont(FONT_LABEL);
        status.setForeground(ACCENT);

        header.add(textPanel, BorderLayout.WEST);
        header.add(status, BorderLayout.EAST);
        return header;
    }

    private JComponent buildTabs() {
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(BG_DARK);
        tabs.setForeground(TEXT_MAIN);

        tabs.addTab("Reports", buildReportsTab());
        tabs.addTab("SQL Runner", buildSqlRunnerTab());
        return tabs;
    }

    private JComponent buildReportsTab() {
        JTextArea output = buildOutputArea();
        JLabel status = buildStatusLabel("Ready");

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(BG_PANEL);
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));

        buttonPanel.add(buildSectionLabel("Reports"));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(buildActionButton("Available Beds", () -> ReportsDAO.getAvailableBedsReport(), output, status));
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(buildActionButton("Legal Clearance", () -> ReportsDAO.getLegalClearanceReport(), output, status));
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(buildActionButton("Doctor Assignments", () -> ReportsDAO.getDoctorAssignmentsReport(), output, status));
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(buildActionButton("Post-Op Follow-Ups", () -> ReportsDAO.getPostOpFollowUpsReport(), output, status));
        buttonPanel.add(Box.createVerticalStrut(8));
        buttonPanel.add(buildActionButton("Summary Stats", () -> ReportsDAO.getSummaryStatsReport(), output, status));

        buttonPanel.add(Box.createVerticalStrut(18));
        buttonPanel.add(buildSectionLabel("Matching"));
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(buildActionButton("Top HLA Matches", () -> ReportsDAO.getHlaMatchesReport(), output, status));

        buttonPanel.add(Box.createVerticalGlue());

        JButton clear = new JButton("Clear Output");
        styleSecondaryButton(clear);
        clear.addActionListener(event -> output.setText(""));
        buttonPanel.add(clear);

        JScrollPane outputPane = new JScrollPane(output);
        outputPane.setBorder(BorderFactory.createLineBorder(BORDER));
        outputPane.getViewport().setBackground(BG_GLASS);

        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(BG_PANEL);
        rightPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        rightPanel.add(outputPane, BorderLayout.CENTER);
        rightPanel.add(status, BorderLayout.SOUTH);

        JSplitPane split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, buttonPanel, rightPanel);
        split.setDividerLocation(280);
        split.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        split.setResizeWeight(0);
        return split;
    }

    private JComponent buildSqlRunnerTab() {
        JTextArea output = buildOutputArea();
        JLabel status = buildStatusLabel("Ready");

        JTextField pathField = new JTextField("Project DBMS.sql");
        pathField.setBackground(BG_GLASS);
        pathField.setForeground(TEXT_MAIN);
        pathField.setCaretColor(TEXT_MAIN);
        pathField.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 10, 8, 10)));

        JButton runButton = new JButton("Run SQL Script");
        stylePrimaryButton(runButton);
        runButton.addActionListener(event -> runInBackground(() -> {
            try {
                String result = DbmsProjectRunner.runSqlScript(pathField.getText().trim());
                SwingUtilities.invokeLater(() -> {
                    output.setText(result);
                    status.setText("Completed");
                });
            } catch (Exception e) {
                SwingUtilities.invokeLater(() -> {
                    output.setText("Error: " + e.getMessage());
                    status.setText("Failed");
                });
            }
        }, status, "Running SQL script..."));

        JPanel top = new JPanel(new BorderLayout(12, 0));
        top.setBackground(BG_PANEL);
        top.setBorder(BorderFactory.createEmptyBorder(16, 16, 12, 16));
        top.add(buildSectionLabel("SQL File"), BorderLayout.WEST);
        top.add(pathField, BorderLayout.CENTER);
        top.add(runButton, BorderLayout.EAST);

        JScrollPane outputPane = new JScrollPane(output);
        outputPane.setBorder(BorderFactory.createLineBorder(BORDER));
        outputPane.getViewport().setBackground(BG_GLASS);

        JPanel body = new JPanel(new BorderLayout());
        body.setBackground(BG_PANEL);
        body.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(16, 16, 16, 16)));
        body.add(outputPane, BorderLayout.CENTER);
        body.add(status, BorderLayout.SOUTH);

        JPanel container = new JPanel(new BorderLayout());
        container.setBackground(BG_DARK);
        container.add(top, BorderLayout.NORTH);
        container.add(body, BorderLayout.CENTER);
        container.setBorder(BorderFactory.createEmptyBorder(12, 0, 0, 0));
        return container;
    }

    private JTextArea buildOutputArea() {
        JTextArea area = new JTextArea();
        area.setEditable(false);
        area.setBackground(BG_GLASS);
        area.setForeground(TEXT_MAIN);
        area.setCaretColor(TEXT_MAIN);
        area.setFont(FONT_MONO);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
        return area;
    }

    private JLabel buildStatusLabel(String text) {
        JLabel status = new JLabel(text);
        status.setFont(FONT_LABEL);
        status.setForeground(TEXT_MUTED);
        status.setBorder(BorderFactory.createEmptyBorder(10, 4, 0, 4));
        return status;
    }

    private JLabel buildSectionLabel(String text) {
        JLabel label = new JLabel(text);
        label.setFont(FONT_LABEL.deriveFont(Font.BOLD));
        label.setForeground(TEXT_MAIN);
        return label;
    }

    private JButton buildActionButton(String label, Supplier<String> reportSupplier, JTextArea output, JLabel status) {
        JButton button = new JButton(label);
        stylePrimaryButton(button);
        button.addActionListener(event -> runInBackground(() -> {
            String result = reportSupplier.get();
            SwingUtilities.invokeLater(() -> {
                output.setText(result);
                status.setText("Completed");
            });
        }, status, "Running report..."));
        return button;
    }

    private void stylePrimaryButton(JButton button) {
        button.setBackground(ACCENT);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setFont(FONT_LABEL.deriveFont(Font.BOLD));
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(ACCENT_DEEP),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }

    private void styleSecondaryButton(JButton button) {
        button.setBackground(BG_GLASS);
        button.setForeground(TEXT_MAIN);
        button.setFocusPainted(false);
        button.setFont(FONT_LABEL);
        button.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(BORDER),
                BorderFactory.createEmptyBorder(8, 14, 8, 14)));
    }

    private void runInBackground(Runnable task, JLabel status, String runningText) {
        status.setText(runningText);
        SwingWorker<Void, Void> worker = new SwingWorker<>() {
            @Override
            protected Void doInBackground() {
                task.run();
                return null;
            }

            @Override
            protected void done() {
                if (status.getText().equals(runningText)) {
                    status.setText("Ready");
                }
            }
        };
        worker.execute();
    }
}
