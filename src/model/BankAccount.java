package model;

import java.util.ArrayList;
import java.util.List;

import static util.BankTermConstants.DEPOSIT;
import static util.BankTermConstants.WITHDRAWAL;

public class BankAccount {
    private final String accountNumber;
    private double balance;
    private final List<Transaction> transactions;
    private double interestRate;

    public BankAccount(String accountNumber) {
        this.accountNumber = accountNumber;
        this.balance = 0.0;
        this.transactions = new ArrayList<>();
        this.interestRate = 0.0;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }

    public void addTransaction(Transaction transaction) {
        transactions.add(transaction);
        if (transaction.getType().equals(DEPOSIT)) {
            balance += transaction.getAmount();
        } else if (transaction.getType().equals(WITHDRAWAL)) {
            balance -= transaction.getAmount();
        }
    }

    public List<Transaction> getTransactions() {
        return transactions;
    }

    public void setInterestRate(double interestRate) {
        this.interestRate = interestRate;
    }

    public double calculateInterest() {
        return balance * (interestRate / 100);
    }
}
