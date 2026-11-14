
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class LibrarySearch extends JFrame {

    JTextField searchField;
    JComboBox<String> categoryBox;
    JButton searchButton, prevButton, nextButton, reserveButton;
    JTextArea resultsArea;
    JLabel statusLabel;

    public LibrarySearch() {

        setTitle("Campus Library Search");
        setLayout(new BorderLayout(10, 10));

        // Title
        JLabel title = new JLabel("CAMPUS LIBRARY SEARCH", JLabel.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 22));
        add(title, BorderLayout.NORTH);

        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        searchField = new JTextField(20);

        String[] categories = {
            "All", "Computer Science", "Mathematics",
            "Physics", "Electronics"
        };

        categoryBox = new JComboBox<>(categories);
        searchButton = new JButton("Search");

        searchPanel.add(new JLabel("Search Book:"));
        searchPanel.add(searchField);
        searchPanel.add(new JLabel("Category:"));
        searchPanel.add(categoryBox);
        searchPanel.add(searchButton);

        // Results Area
        resultsArea = new JTextArea();
        resultsArea.setEditable(false);
        resultsArea.setFont(new Font("Arial", Font.PLAIN, 15));

        JScrollPane scrollPane = new JScrollPane(resultsArea);
        add(scrollPane, BorderLayout.CENTER);

        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout());

        JPanel buttonPanel = new JPanel(new FlowLayout());

        prevButton = new JButton("Prev");
        nextButton = new JButton("Next");
        reserveButton = new JButton("Reserve");

        buttonPanel.add(prevButton);
        buttonPanel.add(nextButton);
        buttonPanel.add(reserveButton);

        statusLabel = new JLabel("Enter a book name and click Search.");

        bottomPanel.add(buttonPanel, BorderLayout.NORTH);
        bottomPanel.add(statusLabel, BorderLayout.SOUTH);

        // Main layout
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(title, BorderLayout.NORTH);
        topPanel.add(searchPanel, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(bottomPanel, BorderLayout.SOUTH);

        // Search Action
        searchButton.addActionListener(e -> {
            String book = searchField.getText().trim();

            if (book.isEmpty()) {
                statusLabel.setText("Please enter a book name.");
            } else {
                resultsArea.setText(
                    "Search Results:\n\n" +
                    "1. " + book + " - Available\n" +
                    "2. Introduction to " + book + " - Available\n" +
                    "3. Advanced " + book + " - Not Available"
                );

                statusLabel.setText("Search completed.");
            }
        });

        prevButton.addActionListener(e ->
            statusLabel.setText("Previous page selected.")
        );

        nextButton.addActionListener(e ->
            statusLabel.setText("Next page selected.")
        );

        reserveButton.addActionListener(e ->
            statusLabel.setText("Select a book to reserve.")
        );

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(750, 500);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new LibrarySearch());
    }
}
