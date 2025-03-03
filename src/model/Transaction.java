package model;

public class Transaction {
    private final String date;
    private final String transactionId;
    private final String type;
    private final double amount;

    public Transaction(String date, String transactionId, String type, double amount) {
        this.date = date;
        this.transactionId = transactionId;
        this.type = type;
        this.amount = amount;
    }

    public String getDate() {
        return date;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public String getType() {
        return type;
    }

    public double getAmount() {
        return amount;
    }
}
