package service;

import model.BankAccount;
import model.Transaction;

import java.util.HashMap;
import java.util.Map;

import static util.BankTermConstants.*;

public class BankServiceImpl implements BankService {
    private final Map<String, BankAccount> accounts;

    public BankServiceImpl() {
        this.accounts = new HashMap<>();
    }

    @Override
    public void processTransaction(String date, String accountNumber, String type, double amount) {
        accounts.putIfAbsent(accountNumber, new BankAccount(accountNumber));
        BankAccount account = accounts.get(accountNumber);
        account.addTransaction(new Transaction(date, type, amount));
    }

    @Override
    public void defineInterestRate(String accountNumber, double interestRate) {
        if (accounts.containsKey(accountNumber)) {
            accounts.get(accountNumber).setInterestRate(interestRate);
        } else {
            System.out.println(ACCOUNT_NOT_FOUND);
        }
    }

    @Override
    public void printStatements() {
        for (BankAccount account : accounts.values()) {
            System.out.println(STATEMENT_HEADER + account.getAccountNumber());
            System.out.println(BALANCE_LABEL + account.getBalance());
            System.out.println(INTEREST_EARNED_LABEL + account.calculateInterest());
            System.out.println(TRANSACTIONS_LABEL);
            for (Transaction transaction : account.getTransactions()) {
                System.out.println(transaction.getDate() + " " + transaction.getType() + " " + transaction.getAmount());
            }
        }
    }

    @Override
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}
