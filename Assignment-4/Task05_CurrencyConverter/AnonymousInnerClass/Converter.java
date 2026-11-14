import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Converter extends JFrame {

    JTextField amountField;
    JComboBox<String> currencyBox;
    JButton convertButton;

    public Converter() {
        setTitle("Currency Converter - Anonymous Inner Class");
        setSize(400, 250);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Enter Amount:"));
        amountField = new JTextField();
        add(amountField);

        add(new JLabel("Convert To:"));
        currencyBox = new JComboBox<>(new String[]{"USD", "EUR", "GBP"});
        add(currencyBox);

        convertButton = new JButton("Convert");
        add(convertButton);

        add(new JLabel(""));

        convertButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        amountField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                convertCurrency();
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void convertCurrency() {
        try {
            double amount = Double.parseDouble(amountField.getText());
            double rate = 1.0;

            String currency = (String) currencyBox.getSelectedItem();

            switch (currency) {
                case "USD": rate = 0.012; break;
                case "EUR": rate = 0.011; break;
                case "GBP": rate = 0.0095; break;
            }

            double result = amount * rate;

            JOptionPane.showMessageDialog(this,
                    String.format("%.2f %s", result, currency),
                    "Conversion Result",
                    JOptionPane.INFORMATION_MESSAGE);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                    "Please enter a valid amount.",
                    "Invalid Input",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new Converter();
    }
}