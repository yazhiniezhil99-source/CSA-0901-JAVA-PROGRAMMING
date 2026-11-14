
public class FeeService {

    public String validateAndPay(String regNo, String amountText) {
        if (regNo == null || regNo.trim().isEmpty()) {
            return "Register number cannot be empty.";
        }

        if (amountText == null || amountText.trim().isEmpty()) {
            return "Amount cannot be empty.";
        }

        try {
            double amount = Double.parseDouble(amountText);

            if (amount <= 0) {
                return "Amount must be greater than zero.";
            }

            return "Payment successful!\nRegister Number: "
                    + regNo + "\nAmount: Rs. " + amount;

        } catch (NumberFormatException e) {
            return "Please enter a valid amount.";
        }
    }
}
