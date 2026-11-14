
import java.awt.*;
import java.awt.event.*;

public class FeeWindow extends Frame {

    TextField regNo = new TextField(20);
    TextField amount = new TextField(20);

    Button pay = new Button("Pay");
    Button clear = new Button("Clear");
    Button receipt = new Button("Receipt");

    Label status = new Label("Enter fee payment details.");

    FeeService service = new FeeService();

    String lastReceipt = "";

    public FeeWindow() {
        super("Student Fee Payment");

        setLayout(new GridLayout(5, 2, 10, 10));

        add(new Label("Register Number:"));
        add(regNo);

        add(new Label("Fee Amount:"));
        add(amount);

        add(pay);
        add(clear);

        add(receipt);
        add(status);

        pay.addActionListener(e -> {
            lastReceipt = service.validateAndPay(
                    regNo.getText(),
                    amount.getText()
            );

            status.setText(lastReceipt.split("\n")[0]);

            if (lastReceipt.startsWith("Payment successful")) {
                showMessage(lastReceipt);
            } else {
                showMessage(lastReceipt);
            }
        });

        clear.addActionListener(e -> {
            regNo.setText("");
            amount.setText("");
            status.setText("Fields cleared.");
            lastReceipt = "";
        });

        receipt.addActionListener(e -> {
            if (lastReceipt.startsWith("Payment successful")) {
                showMessage(lastReceipt);
            } else {
                showMessage("No successful payment receipt available.");
            }
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        setSize(500, 250);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void showMessage(String message) {
        Dialog dialog = new Dialog(this, "Payment Information", true);
        dialog.setLayout(new FlowLayout());

        Label label = new Label(message);
        Button ok = new Button("OK");

        ok.addActionListener(e -> dialog.dispose());

        dialog.add(label);
        dialog.add(ok);

        dialog.setSize(350, 150);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    public static void main(String[] args) {
        new FeeWindow();
    }
}
