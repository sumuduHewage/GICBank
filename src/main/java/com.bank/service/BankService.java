package com.bank.service;


import com.bank.model.BankAccount;
import com.bank.model.InterestRule;

import java.util.List;

public interface BankService {

    /**
     * processes a banking transaction.
     *
     * @param date          transaction date in YYYYMMDD format
     * @param accountNumber account number
     * @param type          transaction type ("D" for deposit, "W" for withdrawal)
     * @param amount        transaction amount
     */
    void processTransaction(String date, String accountNumber, String type, double amount);

    /**
     * define interest rate for a given account.
     *
     * @param date         transaction date in YYYYMMDD format
     * @param ruleId       account number
     * @param interestRate interest rate percentage
     */
    void defineInterestRule(String date, String ruleId, double interestRate);

    /**
     * prints interest rules
     */
    void printInterestRules();

    /**
     * fetch an account by its number.
     *
     * @param accountNumber Account number
     * @return BankAccount object if found, otherwise null
     */
    BankAccount getAccount(String accountNumber);

    List<InterestRule> getInterestRules();

    /**
     * prints account statements for all accounts
     */
    void printAccountStatement(String accountNumber, String yearMonth);


}
