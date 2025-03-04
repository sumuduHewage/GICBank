package com.bank.service;

import com.bank.model.BankAccount;
import com.bank.model.InterestRule;
import com.bank.model.Transaction;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class BankServiceImpl implements BankService {
    private final Map<String, BankAccount> accounts;
    private final Map<String, InterestRule> interestRules;
    private final Map<String, Integer> transactionCounters;

    public BankServiceImpl() {
        this.accounts = new HashMap<>();
        this.interestRules = new HashMap<>();
        this.transactionCounters = new HashMap<>();
    }

    @Override
    public void processTransaction(String date, String accountNumber, String type, double amount) {
        accounts.putIfAbsent(accountNumber, new BankAccount(accountNumber));
        BankAccount account = accounts.get(accountNumber);

        type = type.toUpperCase();

        if (amount <= 0) {
            System.out.println("Transaction amount must be greater than zero.");
            return;
        }

        if (!type.equals("D") && !type.equals("W")) {
            System.out.println("Invalid transaction type! Use 'D' for deposit or 'W' for withdrawal.");
            return;
        }

        if (type.equals("W") && account.getBalance() < amount) {
            System.out.println("Insufficient balance! Withdrawal cannot be processed.");
            return;
        }

        // generate unique transaction ID
        String transactionId = generateTransactionId(date);
        Transaction transaction = new Transaction(date, transactionId, type, amount);
        account.addTransaction(transaction);

        System.out.println("Transaction recorded successfully.");
        printTransactionAccountStatement(accountNumber);
    }

    private String generateTransactionId(String date) {
        int count = transactionCounters.getOrDefault(date, 0) + 1;
        transactionCounters.put(date, count);
        return date + "-" + String.format("%02d", count);
    }

    @Override
    public void defineInterestRule(String date, String ruleId, double interestRate) {
        // stores only the latest rule for each date
        interestRules.put(date, new InterestRule(date, ruleId, interestRate));
        System.out.println("Interest rule set successfully.");
    }

    public void printTransactionAccountStatement(String accountNumber) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.println("Account not found.");
            return;
        }

        BankAccount account = accounts.get(accountNumber);
        List<Transaction> transactions = account.getTransactions();

        System.out.println("\nAccount: " + accountNumber);
        System.out.println("| Date     | Txn Id      |Type| Amount |");

        for (Transaction txn : transactions) {
            System.out.printf("| %s | %s | %s | %7.2f |\n", txn.getDate(), txn.getTransactionId(), txn.getType(), txn.getAmount());
        }
    }

    public void printInterestRules() {
        System.out.println("\nInterest rules:");
        System.out.println("| Date     | RuleId  | Rate (%) |");
        for (InterestRule rule : getInterestRules()) {
            System.out.printf("| %s | %s | %7.2f |\n", rule.getDate(), rule.getRuleId(), rule.getInterestRate());
        }
    }

    @Override
    public List<InterestRule> getInterestRules() {
        List<InterestRule> sortedRules = new ArrayList<>(interestRules.values());
        sortedRules.sort(Comparator.comparing(InterestRule::getDate)); // sort by date
        return sortedRules;
    }

    @Override
    public void printAccountStatement(String accountNumber, String yearMonth) {
        if (!accounts.containsKey(accountNumber)) {
            System.out.println("Account not found.");
            return;
        }

        BankAccount account = accounts.get(accountNumber);
        List<Transaction> monthlyTransactions = getTransactionsForMonth(account, yearMonth);

        if (monthlyTransactions.isEmpty()) {
            System.out.println("No transactions found for this account in the specified month.");
            return;
        }

        System.out.println("\nAccount: " + accountNumber);
        System.out.println("| Date     | Txn Id      | Type | Amount | Balance |");

        double balance = 0.0;
        for (Transaction txn : monthlyTransactions) {
            balance += txn.getType().equals("D") ? txn.getAmount() : -txn.getAmount();
            System.out.printf("| %s | %s | %s | %7.2f | %7.2f |\n", txn.getDate(), txn.getTransactionId(), txn.getType(), txn.getAmount(), balance);
        }

        double totalInterest = calculateInterest(accountNumber, yearMonth, monthlyTransactions);
        System.out.printf("| %s |              | I    | %7.2f | %7.2f |\n", yearMonth + "30", totalInterest, balance + totalInterest);
    }

    private double calculateInterest(String accountNumber, String yearMonth, List<Transaction> transactions) {
        List<InterestRule> rules = getInterestRules();
        double totalInterest = 0.0;
        double lastBalance = 0.0;
        String lastDate = yearMonth + "01";

        for (Transaction txn : transactions) {
            double numDays = getDaysBetween(lastDate, txn.getDate());
            InterestRule applicableRule = findApplicableRule(rules, lastDate);
            double dailyInterest = (lastBalance * applicableRule.getInterestRate() * numDays) / 365.0;
            totalInterest += dailyInterest;

            lastBalance += txn.getType().equals("D") ? txn.getAmount() : -txn.getAmount();
            lastDate = txn.getDate();
        }

        return Math.round(totalInterest * 100.0) / 100.0;
    }

    private long getDaysBetween(String startDate, String endDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        LocalDate start = LocalDate.parse(startDate, formatter);
        LocalDate end = LocalDate.parse(endDate, formatter);

        return ChronoUnit.DAYS.between(start, end);
    }

    private InterestRule findApplicableRule(List<InterestRule> rules, String date) {
        InterestRule lastRule = new InterestRule("00000000", "DEFAULT", 0.0);
        for (InterestRule rule : rules) {
            if (rule.getDate().compareTo(date) <= 0) {
                lastRule = rule;
            } else {
                break;
            }
        }
        return lastRule;
    }


    private List<Transaction> getTransactionsForMonth(BankAccount account, String yearMonth) {
        List<Transaction> filteredTxns = new ArrayList<>();
        for (Transaction txn : account.getTransactions()) {
            if (txn.getDate().startsWith(yearMonth)) {
                filteredTxns.add(txn);
            }
        }
        return filteredTxns;
    }


    @Override
    public BankAccount getAccount(String accountNumber) {
        return accounts.get(accountNumber);
    }
}
