import com.google.gson.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;

import java.io.*;
import java.nio.file.Files;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.HashSet;
import java.util.Set;


public class Main extends JFrame {

    // =========================================================
    // COLORS
    // =========================================================

    private final Color SIDEBAR_COLOR =
            new Color(24, 31, 42);

    private final Color BACKGROUND_COLOR =
            new Color(245, 247, 250);

    private final Color CARD_COLOR =
            Color.WHITE;

    private final Color PRIMARY_COLOR =
            new Color(37, 99, 235);

    private final Color TEXT_COLOR =
            new Color(31, 41, 55);

    private final Color MUTED_COLOR =
            new Color(107, 114, 128);

    private final Color BORDER_COLOR =
            new Color(229, 231, 235);


    // =========================================================
    // PYTHON PATHS
    // =========================================================

    private static final String PYTHON_PATH =
            "C:\\Users\\DELL\\PyCharmMiscProject\\.venv\\Scripts\\python.exe";


    private static final String ENGINE_FOLDER =
            "E:\\k24SW009\\python project\\Portfilo project\\" +
                    "smart data analyze project\\java swing gui\\untitled\\" +
                    "Python Data Engine";


    private static final String PYTHON_SCRIPT =
            ENGINE_FOLDER + "\\data-engine.py";


    // =========================================================
    // MAIN COMPONENTS
    // =========================================================

    private JPanel contentPanel;

    private JLabel pageTitle;
    private JLabel fileLabel;

    private JTable previewTable;


    // =========================================================
    // DASHBOARD VALUES
    // =========================================================

    private JLabel rowsValue;
    private JLabel columnsValue;
    private JLabel missingValue;
    private JLabel duplicateValue;


    // =========================================================
    // SELECTED CSV
    // =========================================================

    private File selectedCSVFile;


    // =========================================================
    // GSON
    // =========================================================

    private final Gson gson =
            new GsonBuilder()
                    .setPrettyPrinting()
                    .create();


    // =========================================================
    // CONSTRUCTOR
    // =========================================================

    public Main() {

        setTitle("SmartData Analyzer");

        setSize(1200, 750);

        setMinimumSize(
                new Dimension(1000, 650)
        );

        setDefaultCloseOperation(
                JFrame.EXIT_ON_CLOSE
        );

        setLocationRelativeTo(null);


        JPanel root =
                new JPanel(
                        new BorderLayout()
                );

        root.setBackground(
                BACKGROUND_COLOR
        );


        // SIDEBAR

        root.add(
                createSidebar(),
                BorderLayout.WEST
        );


        // MAIN AREA

        JPanel mainArea =
                new JPanel(
                        new BorderLayout()
                );

        mainArea.setBackground(
                BACKGROUND_COLOR
        );


        mainArea.add(
                createTopBar(),
                BorderLayout.NORTH
        );


        contentPanel =
                new JPanel(
                        new BorderLayout()
                );

        contentPanel.setBackground(
                BACKGROUND_COLOR
        );

        contentPanel.setBorder(
                new EmptyBorder(
                        20,
                        25,
                        20,
                        25
                )
        );


        mainArea.add(
                contentPanel,
                BorderLayout.CENTER
        );


        root.add(
                mainArea,
                BorderLayout.CENTER
        );


        setContentPane(root);


        showDashboard();
    }


    // =========================================================
    // SIDEBAR
    // =========================================================

    private JPanel createSidebar() {

        JPanel sidebar =
                new JPanel(
                        new BorderLayout()
                );


        sidebar.setPreferredSize(
                new Dimension(
                        230,
                        0
                )
        );


        sidebar.setBackground(
                SIDEBAR_COLOR
        );


        // =====================================================
        // LOGO
        // =====================================================

        JPanel logoPanel =
                new JPanel();


        logoPanel.setOpaque(false);


        logoPanel.setLayout(
                new BoxLayout(
                        logoPanel,
                        BoxLayout.Y_AXIS
                )
        );


        logoPanel.setBorder(
                new EmptyBorder(
                        25,
                        20,
                        20,
                        20
                )
        );


        JLabel logo =
                new JLabel(
                        "SmartData"
                );


        logo.setForeground(
                Color.WHITE
        );


        logo.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );


        JLabel subtitle =
                new JLabel(
                        "ANALYZER"
                );


        subtitle.setForeground(
                new Color(
                        147,
                        197,
                        253
                )
        );


        subtitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        12
                )
        );


        logoPanel.add(logo);

        logoPanel.add(
                Box.createVerticalStrut(2)
        );

        logoPanel.add(subtitle);


        sidebar.add(
                logoPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // NAVIGATION
        // =====================================================

        JPanel navPanel =
                new JPanel();


        navPanel.setOpaque(false);


        navPanel.setLayout(
                new BoxLayout(
                        navPanel,
                        BoxLayout.Y_AXIS
                )
        );


        navPanel.setBorder(
                new EmptyBorder(
                        15,
                        10,
                        10,
                        10
                )
        );


        JButton dashboardBtn =
                createNavButton(
                        "Dashboard"
                );


        JButton uploadBtn =
                createNavButton(
                        "Upload Dataset"
                );


        JButton cleaningBtn =
                createNavButton(
                        "Data Cleaning"
                );


        JButton statisticsBtn =
                createNavButton(
                        "Statistics"
                );


        JButton chartsBtn =
                createNavButton(
                        "Charts"
                );


        JButton reportBtn =
                createNavButton(
                        "Generate Report"
                );


        JButton settingsBtn =
                createNavButton(
                        "Settings"
                );


        // LISTENERS

        dashboardBtn.addActionListener(
                e -> showDashboard()
        );


        uploadBtn.addActionListener(
                e -> uploadCSV()
        );


        cleaningBtn.addActionListener(
                e -> runDataCleaning()
        );


        statisticsBtn.addActionListener(
                e -> runStatistics()
        );


        chartsBtn.addActionListener(
                e -> runCharts()
        );


        reportBtn.addActionListener(
                e -> runReport()
        );


        settingsBtn.addActionListener(
                e -> showMessage(
                        "Settings",
                        "Settings module is reserved for future improvements."
                )
        );


        navPanel.add(dashboardBtn);
        navPanel.add(uploadBtn);
        navPanel.add(cleaningBtn);
        navPanel.add(statisticsBtn);
        navPanel.add(chartsBtn);
        navPanel.add(reportBtn);
        navPanel.add(settingsBtn);


        sidebar.add(
                navPanel,
                BorderLayout.CENTER
        );


        // VERSION

        JLabel version =
                new JLabel(
                        "Version 1.0"
                );


        version.setForeground(
                new Color(
                        156,
                        163,
                        175
                )
        );


        version.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        12
                )
        );


        version.setBorder(
                new EmptyBorder(
                        15,
                        20,
                        20,
                        20
                )
        );


        sidebar.add(
                version,
                BorderLayout.SOUTH
        );


        return sidebar;
    }


    // =========================================================
    // NAVIGATION BUTTON
    // =========================================================

    private JButton createNavButton(
            String text
    ) {

        JButton button =
                new JButton(text);


        button.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        45
                )
        );


        button.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );


        button.setHorizontalAlignment(
                SwingConstants.LEFT
        );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        14
                )
        );


        button.setForeground(
                new Color(
                        229,
                        231,
                        235
                )
        );


        button.setBackground(
                SIDEBAR_COLOR
        );


        button.setBorder(
                new EmptyBorder(
                        10,
                        15,
                        10,
                        10
                )
        );


        button.setFocusPainted(false);

        button.setBorderPainted(false);

        button.setContentAreaFilled(false);


        button.addMouseListener(

                new java.awt.event.MouseAdapter() {

                    @Override

                    public void mouseEntered(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setOpaque(true);

                        button.setBackground(
                                new Color(
                                        55,
                                        65,
                                        81
                                )
                        );
                    }


                    @Override

                    public void mouseExited(
                            java.awt.event.MouseEvent e
                    ) {

                        button.setOpaque(false);

                        button.setBackground(
                                SIDEBAR_COLOR
                        );
                    }
                }
        );


        return button;
    }


    // =========================================================
    // TOP BAR
    // =========================================================

    private JPanel createTopBar() {

        JPanel topBar =
                new JPanel(
                        new BorderLayout()
                );


        topBar.setBackground(
                Color.WHITE
        );


        topBar.setBorder(
                new EmptyBorder(
                        18,
                        25,
                        18,
                        25
                )
        );


        pageTitle =
                new JLabel(
                        "Dashboard"
                );


        pageTitle.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        24
                )
        );


        pageTitle.setForeground(
                TEXT_COLOR
        );


        fileLabel =
                new JLabel(
                        "No dataset loaded"
                );


        fileLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );


        fileLabel.setForeground(
                MUTED_COLOR
        );


        JPanel left =
                new JPanel();


        left.setOpaque(false);


        left.setLayout(
                new BoxLayout(
                        left,
                        BoxLayout.Y_AXIS
                )
        );


        left.add(pageTitle);

        left.add(
                Box.createVerticalStrut(4)
        );

        left.add(fileLabel);


        JButton uploadButton =
                new JButton(
                        "Upload CSV"
                );


        stylePrimaryButton(
                uploadButton
        );


        uploadButton.addActionListener(
                this::handleUpload
        );


        topBar.add(
                left,
                BorderLayout.WEST
        );


        topBar.add(
                uploadButton,
                BorderLayout.EAST
        );


        return topBar;
    }


    // =========================================================
    // DASHBOARD
    // =========================================================

    private void showDashboard() {

        pageTitle.setText("Dashboard");

        contentPanel.removeAll();

        JPanel dashboard = new JPanel();
        dashboard.setLayout(new BoxLayout(
                dashboard,
                BoxLayout.Y_AXIS
        ));

        dashboard.setBackground(BACKGROUND_COLOR);

        // =====================================================
        // KPI CARDS
        // =====================================================

        JPanel cardsPanel = new JPanel(
                new GridLayout(1, 4, 15, 0)
        );

        cardsPanel.setOpaque(false);
        cardsPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        110
                )
        );

        rowsValue = createValueLabel("0");
        columnsValue = createValueLabel("0");
        missingValue = createValueLabel("0");
        duplicateValue = createValueLabel("0");

        cardsPanel.add(
                createCard(
                        "Total Rows",
                        rowsValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Columns",
                        columnsValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Missing Values",
                        missingValue
                )
        );

        cardsPanel.add(
                createCard(
                        "Duplicate Rows",
                        duplicateValue
                )
        );

        dashboard.add(cardsPanel);

        dashboard.add(
                Box.createVerticalStrut(20)
        );

        // =====================================================
        // DATASET OVERVIEW + QUICK ACTIONS
        // =====================================================

        JPanel bottomPanel = new JPanel(
                new BorderLayout(
                        15,
                        15
                )
        );

        bottomPanel.setOpaque(false);
        bottomPanel.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        350
                )
        );

        // DATASET OVERVIEW

        JPanel overviewPanel = createWhitePanel(
                "Dataset Overview"
        );

        String[] columns = {
                "Column",
                "Data Type",
                "Missing"
        };

        DefaultTableModel model =
                new DefaultTableModel(
                        columns,
                        0
                );

        previewTable = new JTable(model);

        previewTable.setRowHeight(30);

        JScrollPane tableScroll =
                new JScrollPane(
                        previewTable
                );

        overviewPanel.add(
                tableScroll,
                BorderLayout.CENTER
        );

        bottomPanel.add(
                overviewPanel,
                BorderLayout.CENTER
        );

        // =====================================================
        // QUICK ACTIONS
        // =====================================================

        JPanel actionPanel = createWhitePanel(
                "Quick Actions"
        );

        actionPanel.setPreferredSize(
                new Dimension(
                        240,
                        0
                )
        );

        actionPanel.setLayout(
                new BoxLayout(
                        actionPanel,
                        BoxLayout.Y_AXIS
                )
        );

        JButton upload =
                new JButton(
                        "Upload CSV File"
                );

        stylePrimaryButton(upload);

        upload.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        upload.addActionListener(
                this::handleUpload
        );


        JButton cleaning =
                new JButton(
                        "Run Data Cleaning"
                );

        styleSecondaryButton(cleaning);

        cleaning.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        cleaning.addActionListener(
                e -> runDataCleaning()
        );


        JButton stats =
                new JButton(
                        "View Statistics"
                );

        styleSecondaryButton(stats);

        stats.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        stats.addActionListener(
                e -> runStatistics()
        );


        JButton charts =
                new JButton(
                        "Generate Charts"
                );

        styleSecondaryButton(charts);

        charts.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        charts.addActionListener(
                e -> runCharts()
        );

        JButton analyticsDashboard =
                new JButton(
                        "Open Analytics Dashboard"
                );

        styleSecondaryButton(analyticsDashboard);

        analyticsDashboard.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        analyticsDashboard.addActionListener(
                e -> generateDashboardCharts()
        );

        JButton report =
                new JButton(
                        "Generate Report"
                );

        styleSecondaryButton(report);

        report.setAlignmentX(
                Component.LEFT_ALIGNMENT
        );

        report.addActionListener(
                e -> runReport()
        );


        actionPanel.add(upload);

        actionPanel.add(
                Box.createVerticalStrut(10)
        );

        actionPanel.add(cleaning);

        actionPanel.add(
                Box.createVerticalStrut(10)
        );

        actionPanel.add(stats);

        actionPanel.add(
                Box.createVerticalStrut(10)
        );

        actionPanel.add(charts);

        actionPanel.add(
                Box.createVerticalStrut(10)
        );

        actionPanel.add(analyticsDashboard);

        actionPanel.add(
                Box.createVerticalStrut(10)
        );

        actionPanel.add(report);

        bottomPanel.add(
                actionPanel,
                BorderLayout.EAST
        );

        dashboard.add(bottomPanel);

        JScrollPane scrollPane =
                new JScrollPane(
                        dashboard
                );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();
        contentPanel.repaint();
    }

    // =========================================================
    // GENERATE COMBINED ANALYTICS DASHBOARD (ALL CHARTS TOGETHER)
    // =========================================================

    private void generateDashboardCharts() {

        try {

            String result =
                    runPythonEngine(
                            "charts"
                    );

            JsonObject json =
                    getJsonObject(
                            result
                    );

            validatePythonResponse(
                    json
            );

            updateDashboardFromPython(
                    json
            );

            if (
                    !json.has(
                            "chart_files"
                    )
            ) {

                throw new Exception(
                        "No chart files received from Python."
                );
            }

            JsonArray charts =
                    json.getAsJsonArray(
                            "chart_files"
                    );

            showGraphDashboard(
                    charts
            );

        }
        catch (Exception e) {

            e.printStackTrace();

            JOptionPane.showMessageDialog(
                    this,
                    "Dashboard Chart Error:\n\n"
                            + e.getMessage(),
                    "Chart Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void showGraphDashboard(
            JsonArray charts
    ) {

        pageTitle.setText(
                "Analytics Dashboard"
        );

        contentPanel.removeAll();

        JPanel page =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );

        page.setBackground(
                BACKGROUND_COLOR
        );

        // =====================================================
        // TITLE
        // =====================================================

        JLabel heading =
                new JLabel(
                        "Dataset Analytics Dashboard"
                );

        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        22
                )
        );

        heading.setForeground(
                TEXT_COLOR
        );

        page.add(
                heading,
                BorderLayout.NORTH
        );

        // =====================================================
        // GRAPH GRID (ALL CHARTS COMBINED)
        // =====================================================

        JPanel graphGrid =
                new JPanel(
                        new GridLayout(
                                0,
                                2,
                                20,
                                20
                        )
                );

        graphGrid.setBackground(
                BACKGROUND_COLOR
        );

        for (
                JsonElement element :
                charts
        ) {

            JsonObject chart =
                    element.getAsJsonObject();

            String title =
                    getValue(
                            chart,
                            "title"
                    );

            String path =
                    getValue(
                            chart,
                            "path"
                    );

            JPanel card =
                    createDashboardChartCard(
                            title,
                            path
                    );

            graphGrid.add(
                    card
            );
        }

        JScrollPane scrollPane =
                new JScrollPane(
                        graphGrid
                );

        scrollPane.setBorder(null);

        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);

        page.add(
                scrollPane,
                BorderLayout.CENTER
        );

        // =====================================================
        // BACK BUTTON
        // =====================================================

        JButton backButton =
                new JButton(
                        "Back to Dashboard"
                );

        styleSecondaryButton(
                backButton
        );

        backButton.addActionListener(
                e -> showDashboard()
        );

        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        bottom.setOpaque(false);

        bottom.add(
                backButton
        );


        page.add(
                bottom,
                BorderLayout.SOUTH
        );

        contentPanel.add(
                page,
                BorderLayout.CENTER
        );

        contentPanel.revalidate();
        contentPanel.repaint();

    }

    private JPanel createDashboardChartCard(
            String title,
            String chartPath
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );

        card.setBackground(
                Color.WHITE
        );

        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );

        // TITLE

        JLabel titleLabel =
                new JLabel(
                        title
                );

        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        15
                )
        );

        titleLabel.setForeground(
                TEXT_COLOR
        );

        card.add(
                titleLabel,
                BorderLayout.NORTH
        );

        // IMAGE

        File imageFile =
                new File(
                        chartPath
                );

        if (
                imageFile.exists()
        ) {

            ImageIcon icon =
                    new ImageIcon(
                            chartPath
                    );

            Image image =
                    icon.getImage()
                            .getScaledInstance(
                                    430,
                                    280,
                                    Image.SCALE_SMOOTH
                            );

            JLabel imageLabel =
                    new JLabel(
                            new ImageIcon(
                                    image
                            )
                    );

            imageLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(
                    imageLabel,
                    BorderLayout.CENTER
            );

        }
        else {

            JLabel error =
                    new JLabel(
                            "Chart not found"
                    );

            error.setHorizontalAlignment(
                    SwingConstants.CENTER
            );

            card.add(
                    error,
                    BorderLayout.CENTER
            );
        }

        return card;
    }

    // =========================================================
    // CSV UPLOAD
    // =========================================================

    private void handleUpload(
            ActionEvent e
    ) {

        uploadCSV();
    }


    private void uploadCSV() {

        JFileChooser chooser =
                new JFileChooser();


        chooser.setDialogTitle(
                "Select CSV Dataset"
        );


        int result =
                chooser.showOpenDialog(
                        this
                );


        if (
                result !=
                        JFileChooser.APPROVE_OPTION
        ) {

            return;
        }


        File file =
                chooser.getSelectedFile();


        if (
                !file.getName()
                        .toLowerCase()
                        .endsWith(".csv")
        ) {

            JOptionPane.showMessageDialog(
                    this,
                    "Please select a valid CSV file.",
                    "Invalid File",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }


        selectedCSVFile = file;


        fileLabel.setText(
                "Dataset: " +
                        file.getName()
        );


        try {

            loadCSVPreview(file);

            JOptionPane.showMessageDialog(
                    this,
                    "Dataset uploaded successfully!",
                    "Success",
                    JOptionPane.INFORMATION_MESSAGE
            );

        }
        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Unable to read CSV file.\n\n"
                            + e.getMessage(),
                    "Upload Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // LOAD CSV PREVIEW
    // =========================================================

    private void loadCSVPreview(
            File file
    ) throws IOException {

        List<String> lines =
                Files.readAllLines(
                        file.toPath()
                );


        if (lines.isEmpty()) {

            throw new IOException(
                    "The CSV file is empty."
            );
        }


        String headerLine =
                lines.get(0);


        String[] headers =
                headerLine.split(
                        ",",
                        -1
                );


        int totalColumns =
                headers.length;


        int totalRows =
                Math.max(
                        0,
                        lines.size() - 1
                );


        DefaultTableModel model =
                new DefaultTableModel();


        for (
                String header :
                headers
        ) {

            model.addColumn(
                    header.trim()
            );
        }


        int maxRows =
                Math.min(
                        lines.size(),
                        11
                );


        for (
                int i = 1;
                i < maxRows;
                i++
        ) {

            String[] data =
                    lines.get(i)
                            .split(
                                    ",",
                                    -1
                            );


            String[] fixedData =
                    new String[
                            totalColumns
                            ];


            for (
                    int j = 0;
                    j < totalColumns;
                    j++
            ) {

                if (
                        j < data.length
                ) {

                    fixedData[j] =
                            data[j].trim();

                }
                else {

                    fixedData[j] = "";
                }
            }


            model.addRow(
                    fixedData
            );
        }


        previewTable.setModel(model);


        rowsValue.setText(
                String.valueOf(totalRows)
        );


        columnsValue.setText(
                String.valueOf(totalColumns)
        );


        int missing =
                calculateMissingValues(
                        lines,
                        totalColumns
                );


        int duplicates =
                calculateDuplicateRows(
                        lines
                );


        missingValue.setText(
                String.valueOf(missing)
        );


        duplicateValue.setText(
                String.valueOf(duplicates)
        );
    }


    // =========================================================
    // MISSING VALUES
    // =========================================================

    private int calculateMissingValues(
            List<String> lines,
            int totalColumns
    ) {

        int missingCount = 0;


        for (
                int i = 1;
                i < lines.size();
                i++
        ) {

            String[] data =
                    lines.get(i)
                            .split(
                                    ",",
                                    -1
                            );


            for (
                    int j = 0;
                    j < totalColumns;
                    j++
            ) {

                String value = "";


                if (
                        j < data.length
                ) {

                    value =
                            data[j].trim();
                }


                if (
                        value.isEmpty()
                ) {

                    missingCount++;
                }
            }
        }


        return missingCount;
    }


    // =========================================================
    // DUPLICATE ROWS
    // =========================================================

    private int calculateDuplicateRows(
            List<String> lines
    ) {

        Set<String> uniqueRows =
                new HashSet<>();

        int duplicates = 0;


        for (
                int i = 1;
                i < lines.size();
                i++
        ) {

            String row =
                    lines.get(i).trim();


            if (
                    !uniqueRows.add(row)
            ) {

                duplicates++;
            }
        }


        return duplicates;
    }


    // =========================================================
    // TEST PYTHON
    // =========================================================

    private void connectPython() {

        try {

            File python =
                    new File(PYTHON_PATH);


            if (!python.exists()) {

                throw new Exception(
                        "Python interpreter not found:\n"
                                + PYTHON_PATH
                );
            }


            ProcessBuilder processBuilder =
                    new ProcessBuilder(
                            PYTHON_PATH,
                            "--version"
                    );


            processBuilder.redirectErrorStream(
                    true
            );


            Process process =
                    processBuilder.start();


            BufferedReader reader =
                    new BufferedReader(
                            new InputStreamReader(
                                    process.getInputStream()
                            )
                    );


            StringBuilder output =
                    new StringBuilder();


            String line;


            while (
                    (line = reader.readLine())
                            != null
            ) {

                output.append(line)
                        .append("\n");
            }


            int exitCode =
                    process.waitFor();


            if (exitCode == 0) {

                JOptionPane.showMessageDialog(
                        this,
                        "Python Connected Successfully!\n\n"
                                + output,
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                );

            }
            else {

                throw new Exception(
                        output.toString()
                );
            }

        }
        catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Python Connection Error:\n\n"
                            + e.getMessage(),
                    "Python Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // COMMON PYTHON ENGINE
    // =========================================================

    private String runPythonEngine(
            String action
    ) throws Exception {

        if (selectedCSVFile == null) {

            throw new Exception(
                    "Please upload a CSV file first."
            );
        }


        File pythonFile =
                new File(PYTHON_PATH);


        if (!pythonFile.exists()) {

            throw new Exception(
                    "Python interpreter not found:\n"
                            + PYTHON_PATH
            );
        }


        File scriptFile =
                new File(PYTHON_SCRIPT);


        if (!scriptFile.exists()) {

            throw new Exception(
                    "Python Data Engine not found:\n"
                            + PYTHON_SCRIPT
            );
        }


        System.out.println(
                "================================="
        );


        System.out.println(
                "SMART DATA ANALYZER"
        );


        System.out.println(
                "Action: " + action
        );


        System.out.println(
                "CSV: "
                        + selectedCSVFile
                        .getAbsolutePath()
        );


        System.out.println(
                "================================="
        );


        ProcessBuilder processBuilder =
                new ProcessBuilder(
                        PYTHON_PATH,
                        PYTHON_SCRIPT,
                        selectedCSVFile
                                .getAbsolutePath(),
                        action
                );


        processBuilder.directory(
                new File(
                        ENGINE_FOLDER
                )
        );


        processBuilder.redirectErrorStream(
                true
        );


        Process process =
                processBuilder.start();


        BufferedReader reader =
                new BufferedReader(
                        new InputStreamReader(
                                process.getInputStream()
                        )
                );


        StringBuilder output =
                new StringBuilder();


        String line;


        while (
                (line = reader.readLine())
                        != null
        ) {

            System.out.println(
                    "PYTHON: " + line
            );


            output.append(line)
                    .append("\n");
        }


        int exitCode =
                process.waitFor();


        String result =
                output.toString()
                        .trim();


        System.out.println(
                "EXIT CODE: "
                        + exitCode
        );


        if (exitCode != 0) {

            throw new Exception(
                    result
            );
        }


        return result;
    }


    // =========================================================
    // PARSE PYTHON JSON
    // =========================================================

    private JsonObject getJsonObject(
            String json
    ) throws Exception {

        JsonElement element =
                JsonParser.parseString(
                        json.trim()
                );


        if (
                !element.isJsonObject()
        ) {

            throw new Exception(
                    "Invalid JSON received from Python."
            );
        }


        return element.getAsJsonObject();
    }


    // =========================================================
    // CHECK SUCCESS
    // =========================================================

    private void validatePythonResponse(
            JsonObject json
    ) throws Exception {

        if (
                json.has("success")
                        &&
                        !json.get("success")
                                .getAsBoolean()
        ) {

            String error =
                    json.has("error")
                            ? json.get("error")
                            .getAsString()
                            : "Unknown Python error";


            throw new Exception(error);
        }
    }


    // =========================================================
    // DATA CLEANING
    // =========================================================

    private void runDataCleaning() {

        try {

            String result =
                    runPythonEngine(
                            "clean"
                    );


            JsonObject json =
                    getJsonObject(result);


            validatePythonResponse(json);


            updateDashboardFromPython(json);


            showDataCleaningPage(json);

        }
        catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Cleaning Error:\n\n"
                            + e.getMessage(),
                    "Cleaning Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // UPDATE DASHBOARD
    // =========================================================

    private void updateDashboardFromPython(
            JsonObject json
    ) {

        if (
                rowsValue != null
                        &&
                        json.has("rows")
        ) {

            rowsValue.setText(
                    json.get("rows")
                            .getAsString()
            );
        }


        if (
                columnsValue != null
                        &&
                        json.has("columns")
        ) {

            columnsValue.setText(
                    json.get("columns")
                            .getAsString()
            );
        }


        if (
                duplicateValue != null
                        &&
                        json.has("duplicate_rows")
        ) {

            duplicateValue.setText(
                    json.get("duplicate_rows")
                            .getAsString()
            );
        }


        if (
                missingValue != null
                        &&
                        json.has(
                                "missing_value_total_before"
                        )
        ) {

            missingValue.setText(
                    json.get(
                                    "missing_value_total_before"
                            )
                            .getAsString()
            );
        }
    }


    // =========================================================
    // DATA CLEANING PAGE
    // =========================================================

    private void showDataCleaningPage(
            JsonObject json
    ) {

        pageTitle.setText(
                "Data Cleaning Results"
        );


        contentPanel.removeAll();


        JPanel page =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );


        page.setBackground(
                BACKGROUND_COLOR
        );


        // =====================================================
        // KPI
        // =====================================================

        JPanel cardsPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                4,
                                15,
                                0
                        )
                );


        cardsPanel.setOpaque(false);


        cardsPanel.add(
                createCard(
                        "Original Rows",
                        createValueLabel(
                                getValue(
                                        json,
                                        "original_rows"
                                )
                        )
                )
        );


        cardsPanel.add(
                createCard(
                        "Cleaned Rows",
                        createValueLabel(
                                getValue(
                                        json,
                                        "cleaned_rows"
                                )
                        )
                )
        );


        cardsPanel.add(
                createCard(
                        "Duplicates Removed",
                        createValueLabel(
                                getValue(
                                        json,
                                        "duplicates_removed"
                                )
                        )
                )
        );


        cardsPanel.add(
                createCard(
                        "Missing Values",
                        createValueLabel(
                                getValue(
                                        json,
                                        "missing_value_total_before"
                                )
                        )
                )
        );


        page.add(
                cardsPanel,
                BorderLayout.NORTH
        );


        // =====================================================
        // CENTER
        // =====================================================

        JPanel centerPanel =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );


        centerPanel.setOpaque(false);


        // MISSING VALUES

        JPanel missingPanel =
                createWhitePanel(
                        "Missing Values Before Cleaning"
                );


        DefaultTableModel missingModel =
                new DefaultTableModel(
                        new String[]{
                                "Column",
                                "Missing Count"
                        },
                        0
                );


        if (
                json.has(
                        "missing_values_before"
                )
        ) {

            JsonObject missing =
                    json.getAsJsonObject(
                            "missing_values_before"
                    );


            for (
                    Map.Entry<String, JsonElement>
                            entry :
                    missing.entrySet()
            ) {

                missingModel.addRow(
                        new Object[]{
                                entry.getKey(),
                                entry.getValue()
                                        .getAsString()
                        }
                );
            }
        }


        JTable missingTable =
                new JTable(
                        missingModel
                );


        missingTable.setRowHeight(28);


        missingPanel.add(
                new JScrollPane(
                        missingTable
                ),
                BorderLayout.CENTER
        );


        // ACTIONS

        JPanel actionsPanel =
                createWhitePanel(
                        "Cleaning Actions"
                );


        DefaultListModel<String>
                actionsModel =
                new DefaultListModel<>();


        if (
                json.has(
                        "cleaning_actions"
                )
        ) {

            JsonArray actions =
                    json.getAsJsonArray(
                            "cleaning_actions"
                    );


            for (
                    JsonElement element :
                    actions
            ) {

                JsonObject action =
                        element.getAsJsonObject();


                StringBuilder text =
                        new StringBuilder();


                if (
                        action.has("action")
                ) {

                    text.append(
                            action.get("action")
                                    .getAsString()
                    );
                }


                if (
                        action.has("column")
                ) {

                    text.append(
                            " — "
                    );

                    text.append(
                            action.get("column")
                                    .getAsString()
                    );
                }


                if (
                        action.has("columns")
                ) {

                    text.append(
                            " — "
                    );

                    text.append(
                            action.get("columns")
                                    .getAsString()
                    );
                }


                if (
                        action.has("count")
                ) {

                    text.append(
                            " ("
                    );

                    text.append(
                            action.get("count")
                                    .getAsString()
                    );

                    text.append(
                            ")"
                    );
                }


                actionsModel.addElement(
                        text.toString()
                );
            }
        }


        if (
                actionsModel.isEmpty()
        ) {

            actionsModel.addElement(
                    "No cleaning actions were required."
            );
        }


        JList<String> actionsList =
                new JList<>(
                        actionsModel
                );


        actionsPanel.add(
                new JScrollPane(
                        actionsList
                ),
                BorderLayout.CENTER
        );


        centerPanel.add(
                missingPanel
        );


        centerPanel.add(
                actionsPanel
        );


        page.add(
                centerPanel,
                BorderLayout.CENTER
        );


        JButton backButton =
                new JButton(
                        "Back to Dashboard"
                );


        styleSecondaryButton(
                backButton
        );


        backButton.addActionListener(
                e -> showDashboard()
        );


        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        bottom.setOpaque(false);


        bottom.add(backButton);


        page.add(
                bottom,
                BorderLayout.SOUTH
        );


        contentPanel.add(
                page,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // =========================================================
    // STATISTICS
    // =========================================================

    private void runStatistics() {

        try {

            String result =
                    runPythonEngine(
                            "stats"
                    );


            JsonObject json =
                    getJsonObject(result);


            validatePythonResponse(json);


            updateDashboardFromPython(json);


            showStatisticsPage(json);

        }
        catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Statistics Error:\n\n"
                            + e.getMessage(),
                    "Statistics Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // STATISTICS PAGE
    // =========================================================

    private void showStatisticsPage(
            JsonObject json
    ) {

        pageTitle.setText(
                "Statistics"
        );


        contentPanel.removeAll();


        JPanel page =
                new JPanel(
                        new BorderLayout(
                                0,
                                20
                        )
                );


        page.setBackground(
                BACKGROUND_COLOR
        );


        // KPI

        JPanel cards =
                new JPanel(
                        new GridLayout(
                                1,
                                2,
                                15,
                                0
                        )
                );


        cards.setOpaque(false);


        cards.add(
                createCard(
                        "Total Rows",
                        createValueLabel(
                                getValue(
                                        json,
                                        "row_count"
                                )
                        )
                )
        );


        cards.add(
                createCard(
                        "Total Columns",
                        createValueLabel(
                                getValue(
                                        json,
                                        "column_count"
                                )
                        )
                )
        );


        page.add(
                cards,
                BorderLayout.NORTH
        );


        // TABS

        JTabbedPane tabs =
                new JTabbedPane();


        // =====================================================
        // NUMERIC
        // =====================================================

        String[] numericColumns = {
                "Column",
                "Count",
                "Mean",
                "Median",
                "Std",
                "Min",
                "Q1",
                "Q3",
                "Max"
        };


        DefaultTableModel numericModel =
                new DefaultTableModel(
                        numericColumns,
                        0
                );


        if (
                json.has(
                        "numeric_summary"
                )
        ) {

            JsonObject numeric =
                    json.getAsJsonObject(
                            "numeric_summary"
                    );


            for (
                    Map.Entry<String, JsonElement>
                            entry :
                    numeric.entrySet()
            ) {

                JsonObject stats =
                        entry.getValue()
                                .getAsJsonObject();


                numericModel.addRow(
                        new Object[]{

                                entry.getKey(),

                                getValue(
                                        stats,
                                        "count"
                                ),

                                getValue(
                                        stats,
                                        "mean"
                                ),

                                getValue(
                                        stats,
                                        "median"
                                ),

                                getValue(
                                        stats,
                                        "std"
                                ),

                                getValue(
                                        stats,
                                        "min"
                                ),

                                getValue(
                                        stats,
                                        "q1"
                                ),

                                getValue(
                                        stats,
                                        "q3"
                                ),

                                getValue(
                                        stats,
                                        "max"
                                )
                        }
                );
            }
        }


        JTable numericTable =
                new JTable(
                        numericModel
                );


        numericTable.setRowHeight(28);


        tabs.addTab(
                "Numeric Statistics",
                new JScrollPane(
                        numericTable
                )
        );


        // =====================================================
        // CATEGORICAL
        // =====================================================

        String[] categoricalColumns = {
                "Column",
                "Unique Count",
                "Top Value",
                "Frequency",
                "Non Null"
        };


        DefaultTableModel categoricalModel =
                new DefaultTableModel(
                        categoricalColumns,
                        0
                );


        if (
                json.has(
                        "categorical_summary"
                )
        ) {

            JsonObject categorical =
                    json.getAsJsonObject(
                            "categorical_summary"
                    );


            for (
                    Map.Entry<String, JsonElement>
                            entry :
                    categorical.entrySet()
            ) {

                JsonObject stats =
                        entry.getValue()
                                .getAsJsonObject();


                categoricalModel.addRow(
                        new Object[]{

                                entry.getKey(),

                                getValue(
                                        stats,
                                        "unique_count"
                                ),

                                getValue(
                                        stats,
                                        "top_value"
                                ),

                                getValue(
                                        stats,
                                        "top_frequency"
                                ),

                                getValue(
                                        stats,
                                        "non_null_count"
                                )
                        }
                );
            }
        }


        JTable categoricalTable =
                new JTable(
                        categoricalModel
                );


        categoricalTable.setRowHeight(28);


        tabs.addTab(
                "Categorical Statistics",
                new JScrollPane(
                        categoricalTable
                )
        );


        // =====================================================
        // CORRELATIONS
        // =====================================================

        String[] correlationColumns = {
                "Column A",
                "Column B",
                "Correlation"
        };


        DefaultTableModel correlationModel =
                new DefaultTableModel(
                        correlationColumns,
                        0
                );


        if (
                json.has(
                        "top_correlations"
                )
        ) {

            JsonArray correlations =
                    json.getAsJsonArray(
                            "top_correlations"
                    );


            for (
                    JsonElement element :
                    correlations
            ) {

                JsonObject correlation =
                        element.getAsJsonObject();


                correlationModel.addRow(
                        new Object[]{

                                getValue(
                                        correlation,
                                        "column_a"
                                ),

                                getValue(
                                        correlation,
                                        "column_b"
                                ),

                                getValue(
                                        correlation,
                                        "correlation"
                                )
                        }
                );
            }
        }


        JTable correlationTable =
                new JTable(
                        correlationModel
                );


        correlationTable.setRowHeight(28);


        tabs.addTab(
                "Top Correlations",
                new JScrollPane(
                        correlationTable
                )
        );


        page.add(
                tabs,
                BorderLayout.CENTER
        );


        JButton back =
                new JButton(
                        "Back to Dashboard"
                );


        styleSecondaryButton(back);


        back.addActionListener(
                e -> showDashboard()
        );


        JPanel bottom =
                new JPanel(
                        new FlowLayout(
                                FlowLayout.LEFT
                        )
                );


        bottom.setOpaque(false);

        bottom.add(back);


        page.add(
                bottom,
                BorderLayout.SOUTH
        );


        contentPanel.add(
                page,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // =========================================================
    // CHARTS
    // =========================================================

    private void runCharts() {

        try {

            String result =
                    runPythonEngine(
                            "charts"
                    );


            JsonObject json =
                    getJsonObject(result);


            validatePythonResponse(json);


            updateDashboardFromPython(json);


            showChartsPage(json);

        }
        catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Charts Error:\n\n"
                            + e.getMessage(),
                    "Charts Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // CHARTS PAGE
    // =========================================================

    private void showChartsPage(
            JsonObject json
    ) {

        pageTitle.setText(
                "Data Visualization"
        );


        contentPanel.removeAll();


        JPanel chartsContainer =
                new JPanel();


        chartsContainer.setBackground(
                BACKGROUND_COLOR
        );


        chartsContainer.setLayout(
                new BoxLayout(
                        chartsContainer,
                        BoxLayout.Y_AXIS
                )
        );


        if (
                !json.has(
                        "chart_files"
                )
        ) {

            JLabel label =
                    new JLabel(
                            "No charts were generated."
                    );


            chartsContainer.add(label);
        }
        else {

            JsonArray charts =
                    json.getAsJsonArray(
                            "chart_files"
                    );


            for (
                    JsonElement element :
                    charts
            ) {

                JsonObject chart =
                        element.getAsJsonObject();


                String title =
                        getValue(
                                chart,
                                "title"
                        );


                String path =
                        getValue(
                                chart,
                                "path"
                        );


                JPanel chartCard =
                        createChartCard(
                                title,
                                path
                        );


                chartsContainer.add(
                        chartCard
                );


                chartsContainer.add(
                        Box.createVerticalStrut(
                                20
                        )
                );
            }
        }


        JScrollPane scrollPane =
                new JScrollPane(
                        chartsContainer
                );


        scrollPane.getVerticalScrollBar()
                .setUnitIncrement(16);


        contentPanel.add(
                scrollPane,
                BorderLayout.CENTER
        );


        contentPanel.revalidate();

        contentPanel.repaint();
    }


    // =========================================================
    // CREATE CHART CARD
    // =========================================================

    private JPanel createChartCard(
            String title,
            String chartPath
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        card.setBackground(
                Color.WHITE
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        card.setMaximumSize(
                new Dimension(
                        Integer.MAX_VALUE,
                        500
                )
        );


        JLabel titleLabel =
                new JLabel(
                        title
                );


        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );


        File imageFile =
                new File(
                        chartPath
                );


        if (
                imageFile.exists()
        ) {

            ImageIcon icon =
                    new ImageIcon(
                            chartPath
                    );


            Image image =
                    icon.getImage()
                            .getScaledInstance(
                                    650,
                                    400,
                                    Image.SCALE_SMOOTH
                            );


            JLabel imageLabel =
                    new JLabel(
                            new ImageIcon(image)
                    );


            imageLabel.setHorizontalAlignment(
                    SwingConstants.CENTER
            );


            card.add(
                    imageLabel,
                    BorderLayout.CENTER
            );

        }
        else {

            JLabel error =
                    new JLabel(
                            "Chart file not found: "
                                    + chartPath
                    );


            card.add(
                    error,
                    BorderLayout.CENTER
            );
        }


        return card;
    }


    // =========================================================
    // REPORT
    // =========================================================

    private void runReport() {

        try {

            String result =
                    runPythonEngine(
                            "report"
                    );


            JsonObject json =
                    getJsonObject(result);


            validatePythonResponse(json);


            updateDashboardFromPython(json);


            if (
                    !json.has(
                            "report_file"
                    )
            ) {

                throw new Exception(
                        "Python did not return the report file path."
                );
            }


            String reportPath =
                    json.get(
                                    "report_file"
                            )
                            .getAsString();


            File reportFile =
                    new File(
                            reportPath
                    );


            if (
                    !reportFile.exists()
            ) {

                throw new Exception(
                        "Report file not found:\n"
                                + reportPath
                );
            }


            int choice =
                    JOptionPane.showConfirmDialog(
                            this,
                            "Report generated successfully!\n\n"
                                    + reportPath
                                    + "\n\nOpen report now?",
                            "Report Generated",
                            JOptionPane.YES_NO_OPTION,
                            JOptionPane.INFORMATION_MESSAGE
                    );


            if (
                    choice ==
                            JOptionPane.YES_OPTION
            ) {

                if (
                        Desktop.isDesktopSupported()
                ) {

                    Desktop.getDesktop()
                            .browse(
                                    reportFile
                                            .toURI()
                            );
                }
            }

        }
        catch (Exception e) {

            e.printStackTrace();


            JOptionPane.showMessageDialog(
                    this,
                    "Report Error:\n\n"
                            + e.getMessage(),
                    "Report Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }


    // =========================================================
    // JSON VALUE HELPER
    // =========================================================

    private String getValue(
            JsonObject json,
            String key
    ) {

        if (
                json == null
                        ||
                        !json.has(key)
                        ||
                        json.get(key).isJsonNull()
        ) {

            return "0";
        }


        return json.get(key)
                .getAsString();
    }


    // =========================================================
    // CREATE VALUE LABEL
    // =========================================================

    private JLabel createValueLabel(
            String value
    ) {

        JLabel label =
                new JLabel(value);


        label.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        28
                )
        );


        label.setForeground(
                TEXT_COLOR
        );


        return label;
    }


    // =========================================================
    // CREATE CARD
    // =========================================================

    private JPanel createCard(
            String title,
            JLabel valueLabel
    ) {

        JPanel card =
                new JPanel(
                        new BorderLayout()
                );


        card.setBackground(
                CARD_COLOR
        );


        card.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        new EmptyBorder(
                                18,
                                18,
                                18,
                                18
                        )
                )
        );


        JLabel titleLabel =
                new JLabel(title);


        titleLabel.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );


        titleLabel.setForeground(
                MUTED_COLOR
        );


        card.add(
                titleLabel,
                BorderLayout.NORTH
        );


        card.add(
                valueLabel,
                BorderLayout.CENTER
        );


        return card;
    }


    // =========================================================
    // WHITE PANEL
    // =========================================================

    private JPanel createWhitePanel(
            String title
    ) {

        JPanel panel =
                new JPanel(
                        new BorderLayout(
                                10,
                                10
                        )
                );


        panel.setBackground(
                Color.WHITE
        );


        panel.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                BORDER_COLOR
                        ),
                        new EmptyBorder(
                                15,
                                15,
                                15,
                                15
                        )
                )
        );


        JLabel heading =
                new JLabel(title);


        heading.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        16
                )
        );


        heading.setForeground(
                TEXT_COLOR
        );


        panel.add(
                heading,
                BorderLayout.NORTH
        );


        return panel;
    }


    // =========================================================
    // PRIMARY BUTTON
    // =========================================================

    private void stylePrimaryButton(
            JButton button
    ) {

        button.setBackground(
                PRIMARY_COLOR
        );


        button.setForeground(
                Color.WHITE
        );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.BOLD,
                        13
                )
        );


        button.setFocusPainted(false);


        button.setBorder(
                new EmptyBorder(
                        10,
                        18,
                        10,
                        18
                )
        );
    }


    // =========================================================
    // SECONDARY BUTTON
    // =========================================================

    private void styleSecondaryButton(
            JButton button
    ) {

        button.setBackground(
                Color.WHITE
        );


        button.setForeground(
                TEXT_COLOR
        );


        button.setFont(
                new Font(
                        "SansSerif",
                        Font.PLAIN,
                        13
                )
        );


        button.setFocusPainted(false);


        button.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(
                                        209,
                                        213,
                                        219
                                )
                        ),
                        new EmptyBorder(
                                9,
                                15,
                                9,
                                15
                        )
                )
        );
    }


    // =========================================================
    // MESSAGE
    // =========================================================

    private void showMessage(
            String title,
            String message
    ) {

        JOptionPane.showMessageDialog(
                this,
                message,
                title,
                JOptionPane.INFORMATION_MESSAGE
        );
    }


    // =========================================================
    // MAIN
    // =========================================================

    public static void main(
            String[] args
    ) {

        SwingUtilities.invokeLater(
                () -> {

                    try {

                        UIManager.setLookAndFeel(
                                UIManager
                                        .getSystemLookAndFeelClassName()
                        );

                    }
                    catch (
                            Exception ignored
                    ) {
                    }


                    Main app =
                            new Main();


                    app.setVisible(
                            true
                    );
                }
        );
    }
}