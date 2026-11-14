package ui;

import dao.EventDAO;
import dao.RegistrationDAO;
import model.Event;
import model.RegistrationRecord;
import service.RegistrationService;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;

public class EventRegistrationApp extends JFrame {

    private JTextField studentIdField;
    private JTextField studentNameField;
    private JTextField emailField;
    private JTextField searchField;

    private JComboBox<Event> eventComboBox;

    private JTable registrationTable;
    private DefaultTableModel tableModel;

    private JButton registerButton;
    private JButton searchButton;
    private JButton previousButton;
    private JButton nextButton;

    private JLabel statusLabel;
    private JLabel pageLabel;

    private final EventDAO eventDAO = new EventDAO();
    private final RegistrationDAO registrationDAO = new RegistrationDAO();
    private final RegistrationService registrationService =
            new RegistrationService();

    private int currentPage = 0;
    private final int pageSize = 10;
    private int totalRecords = 0;

    public EventRegistrationApp() {

        setTitle("College Event Registration System");
        setSize(1000, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        createMenu();
        createRegistrationPanel();
        createTablePanel();
        createBottomPanel();

        loadEvents();
        loadRegistrations();
    }

    private void createMenu() {

        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");

        JMenuItem exportItem = new JMenuItem("Export Registrations");
        JMenuItem exitItem = new JMenuItem("Exit");

        exportItem.addActionListener(e -> exportRegistrations());
        exitItem.addActionListener(e -> dispose());

        fileMenu.add(exportItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        menuBar.add(fileMenu);
        setJMenuBar(menuBar);
    }

    private void createRegistrationPanel() {

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createTitledBorder(
                "Student Event Registration"));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));

        studentIdField = new JTextField();
        studentNameField = new JTextField();
        emailField = new JTextField();
        eventComboBox = new JComboBox<>();

        formPanel.add(new JLabel("Student ID:"));
        formPanel.add(studentIdField);

        formPanel.add(new JLabel("Student Name:"));
        formPanel.add(studentNameField);

        formPanel.add(new JLabel("Email Address:"));
        formPanel.add(emailField);

        formPanel.add(new JLabel("Select Event:"));
        formPanel.add(eventComboBox);

        registerButton = new JButton("Register Student");
        registerButton.addActionListener(e -> registerStudent());

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(mainPanel, BorderLayout.NORTH);
    }

    private void createTablePanel() {

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder(
                "Registered Students"));

        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(25);
        searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            currentPage = 0;
            loadRegistrations();
        });

        searchField.addActionListener(e -> {
            currentPage = 0;
            loadRegistrations();
        });

        searchPanel.add(new JLabel("Search:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);

        tableModel = new DefaultTableModel(
                new String[]{
                    "Registration ID",
                    "Student ID",
                    "Student Name",
                    "Email",
                    "Event",
                    "Registration Date"
                }, 0) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        registrationTable = new JTable(tableModel);
        registrationTable.setRowHeight(25);
        registrationTable.setAutoCreateRowSorter(true);

        JScrollPane scrollPane = new JScrollPane(registrationTable);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);

        add(panel, BorderLayout.CENTER);
    }

    private void createBottomPanel() {

        JPanel bottomPanel = new JPanel(new BorderLayout());

        statusLabel = new JLabel("Ready");
        pageLabel = new JLabel("Page 1");

        previousButton = new JButton("Previous");
        nextButton = new JButton("Next");

        previousButton.addActionListener(e -> {
            if (currentPage > 0) {
                currentPage--;
                loadRegistrations();
            }
        });

        nextButton.addActionListener(e -> {
            if ((currentPage + 1) * pageSize < totalRecords) {
                currentPage++;
                loadRegistrations();
            }
        });

        JPanel navigationPanel = new JPanel();
        navigationPanel.add(previousButton);
        navigationPanel.add(pageLabel);
        navigationPanel.add(nextButton);

        bottomPanel.add(statusLabel, BorderLayout.WEST);
        bottomPanel.add(navigationPanel, BorderLayout.EAST);

        add(bottomPanel, BorderLayout.SOUTH);
    }

    private void loadEvents() {

        registerButton.setEnabled(false);

        SwingWorker<List<Event>, Void> worker =
                new SwingWorker<>() {

            @Override
            protected List<Event> doInBackground() throws Exception {
                return eventDAO.getAllEvents();
            }

            @Override
            protected void done() {
                try {
                    List<Event> events = get();

                    eventComboBox.removeAllItems();

                    for (Event event : events) {
                        eventComboBox.addItem(event);
                    }

                    registerButton.setEnabled(true);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this,
                            "Unable to load events: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    private void registerStudent() {

        String studentId = studentIdField.getText();
        String studentName = studentNameField.getText();
        String email = emailField.getText();

        Event selectedEvent =
                (Event) eventComboBox.getSelectedItem();

        if (selectedEvent == null) {
            JOptionPane.showMessageDialog(
                    this, "Please select an event.");
            return;
        }

        int eventId = selectedEvent.getEventId();

        registerButton.setEnabled(false);
        statusLabel.setText("Processing registration...");

        SwingWorker<String, Void> worker =
                new SwingWorker<>() {

            @Override
            protected String doInBackground() {
                return registrationService.registerStudent(
                        studentId,
                        studentName,
                        email,
                        eventId);
            }

            @Override
            protected void done() {
                try {
                    String result = get();

                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this, result);

                    if (result.equals("Registration successful!")) {
                        studentIdField.setText("");
                        studentNameField.setText("");
                        emailField.setText("");

                        currentPage = 0;
                        loadEvents();
                        loadRegistrations();
                    }

                    statusLabel.setText(result);

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this,
                            "Registration failed: " + e.getMessage());
                } finally {
                    registerButton.setEnabled(true);
                }
            }
        };

        worker.execute();
    }

    private void loadRegistrations() {

        String keyword = searchField.getText().trim();

        previousButton.setEnabled(false);
        nextButton.setEnabled(false);
        statusLabel.setText("Loading registrations...");

        SwingWorker<List<RegistrationRecord>, Void> worker =
                new SwingWorker<>() {

            @Override
            protected List<RegistrationRecord> doInBackground()
                    throws Exception {

                totalRecords =
                        registrationDAO.getTotalRegistrations(keyword);

                int offset = currentPage * pageSize;

                return registrationDAO.getRegistrations(
                        keyword, pageSize, offset);
            }

            @Override
            protected void done() {
                try {
                    List<RegistrationRecord> records = get();

                    tableModel.setRowCount(0);

                    for (RegistrationRecord record : records) {
                        tableModel.addRow(new Object[]{
                            record.getRegistrationId(),
                            record.getStudentId(),
                            record.getStudentName(),
                            record.getEmail(),
                            record.getEventName(),
                            record.getRegistrationDate()
                        });
                    }

                    pageLabel.setText(
                            "Page " + (currentPage + 1));

                    previousButton.setEnabled(currentPage > 0);

                    nextButton.setEnabled(
                            (currentPage + 1) * pageSize < totalRecords);

                    statusLabel.setText(
                            "Total registrations: " + totalRecords);

                } catch (Exception e) {
                    statusLabel.setText("Unable to load registrations.");
                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this,
                            "Error: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    private void exportRegistrations() {

        JFileChooser chooser = new JFileChooser();
        chooser.setDialogTitle("Save Registration List");

        chooser.setSelectedFile(
                new File("Event_Registrations.csv"));

        int choice = chooser.showSaveDialog(this);

        if (choice != JFileChooser.APPROVE_OPTION) {
            return;
        }

        File file = chooser.getSelectedFile();
        String keyword = searchField.getText().trim();

        statusLabel.setText("Exporting registrations...");

        SwingWorker<Void, Void> worker = new SwingWorker<>() {

            @Override
            protected Void doInBackground() throws Exception {

                try (PrintWriter writer = new PrintWriter(file)) {

                    writer.println(
                            "Registration ID,Student ID,Student Name,Email,Event,Registration Date");

                    int offset = 0;
                    int limit = 500;

                    while (true) {

                        List<RegistrationRecord> records =
                                registrationDAO.getRegistrations(
                                        keyword, limit, offset);

                        if (records.isEmpty()) {
                            break;
                        }

                        for (RegistrationRecord record : records) {

                            writer.printf(
                                    "%d,\"%s\",\"%s\",\"%s\",\"%s\",\"%s\"%n",
                                    record.getRegistrationId(),
                                    csv(record.getStudentId()),
                                    csv(record.getStudentName()),
                                    csv(record.getEmail()),
                                    csv(record.getEventName()),
                                    record.getRegistrationDate()
                            );
                        }

                        offset += limit;

                        if (records.size() < limit) {
                            break;
                        }
                    }
                }

                return null;
            }

            @Override
            protected void done() {
                try {
                    get();

                    statusLabel.setText("Export completed.");

                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this,
                            "Registration list exported successfully!");

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(
                            EventRegistrationApp.this,
                            "Export failed: " + e.getMessage());
                }
            }
        };

        worker.execute();
    }

    private String csv(String value) {
        return value == null ? "" : value.replace("\"", "\"\"");
    }

    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            EventRegistrationApp app =
                    new EventRegistrationApp();

            app.setVisible(true);
        });
    }
}