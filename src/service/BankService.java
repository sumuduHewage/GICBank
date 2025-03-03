package service;

import model.BankAccount;

public interface BankService {

    /**
     * processes a banking transaction.
     *
     * @param date          Transaction date in YYYYMMDD format
     * @param accountNumber Account number
     * @param type          Transaction type ("D" for deposit, "W" for withdrawal)
     * @param amount        Transaction amount
     */
    void processTransaction(String date, String accountNumber, String type, double amount);

    /**
     * define interest rate for a given account.
     *
     * @param accountNumber Account number
     * @param interestRate  Interest rate percentage
     */
    void defineInterestRate(String accountNumber, double interestRate);

    /**
     * prints statements for all accounts, including transactions and interest earned.
     */
    void printStatements();

    /**
     * fetch an account by its number.
     *
     * @param accountNumber Account number
     * @return BankAccount object if found, otherwise null
     */
    BankAccount getAccount(String accountNumber);
}
