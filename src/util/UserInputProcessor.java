package util;

import service.BankService;

import java.util.Scanner;
import java.util.regex.Pattern;

import static util.BankTermConstants.*;

public class UserInputProcessor {
    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^-?\\d+(\\.\\d{1,2})?$");
    private final BankService bankService;
    private final Scanner scanner;

    public UserInputProcessor(BankService bankService, Scanner scanner) {
        this.bankService = bankService;
        this.scanner = scanner;
    }

    public void handleTransactionInput() {
        System.out.println(TRANSACTION_PROMPT);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split(" ");
            if (!isValidTransactionFormat(parts)) {
                System.out.println(INVALID_FORMAT);
                continue;
            }

            String date = parts[0];
            String accountNumber = parts[1];
            String type = parts[2];
            String amountStr = parts[3];

            if (!DateValidator.isValidDate(date)) {
                System.out.println(INVALID_DATE);
                continue;
            }

            if (!isValidType(type) || !isValidAmount(amountStr)) {
                continue;
            }

            try {
                double amount = Double.parseDouble(amountStr);
                bankService.processTransaction(date, accountNumber, type, amount);
                // prompt user for next action
                askForNextAction();
            } catch (NumberFormatException e) {
                System.out.println("Invalid amount! Must be a valid number with up to 2 decimal places.");
            }
        }
    }

    public void handleInterestRuleInput() {
        System.out.println(INTEREST_PROMPT);

        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split(" ");
            if (parts.length != 3) {
                System.out.println("Invalid format! Enter data as: YYYYMMDD RuleId Rate(%)");
                continue;
            }

            String date = parts[0];
            String ruleId = parts[1];
            String rateStr = parts[2];

            if (!DateValidator.isValidDate(date)) {
                System.out.println("Invalid date format! Please enter in YYYYMMDD format.");
                continue;
            }

            try {
                double interestRate = Double.parseDouble(rateStr);
                if (interestRate <= 0 || interestRate >= 100) {
                    System.out.println("Invalid interest rate! Must be between 0 and 100.");
                    continue;
                }

                bankService.defineInterestRule(date, ruleId, interestRate);
                bankService.printInterestRules();
                // prompt user for next action
                askForNextAction();
            } catch (NumberFormatException e) {
                System.out.println("Invalid number format! Please enter a valid interest rate.");
            }
        }
    }

    private void askForNextAction() {
        while (true) {
            System.out.println(MENU_IS_THERE_ANYTHING);
            System.out.println(MENU_OPTION_TRANSACTION);
            System.out.println(MENU_OPTION_INTEREST);
            System.out.println(MENU_OPTION_PRINT);
            System.out.println(MENU_OPTION_QUIT);
            System.out.print(MENU_OPTION_TERMINATE);

            String choice = scanner.nextLine().trim().toUpperCase();

            switch (choice) {
                case TRANSACTION:
                    handleTransactionInput();
                    return;
                case INTEREST:
                    handleInterestRuleInput();
                    return;
                case PRINT:
                    handlePrintStatementInput();
                    return;
                case QUIT:
                    System.out.println(GOODBYE_MESSAGE);
                    System.exit(0);
                default:
                    System.out.println(INVALID_CHOICE);
            }
        }
    }

    public void handlePrintStatementInput() {
        System.out.println("Please enter account and month to generate the statement <Account> <Year><Month> (or enter blank to go back to main menu):");
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split(" ");
            if (parts.length != 2 || !parts[1].matches("^\\d{6}$")) {
                System.out.println("Invalid format! Use <Account> <Year><Month> (ex: AC001 202503).");
                continue;
            }

            String accountNumber = parts[0];
            String yearMonth = parts[1];

            bankService.printAccountStatement(accountNumber, yearMonth);
            askForNextAction();
        }
    }

    private boolean isValidTransactionFormat(String[] parts) {
        return parts.length == 4;
    }

    /**
     * check the date format (YYYYMMDD).
     *
     * @param date The date string to validate.
     * @return true if the date is in valid format, false otherwise.
     */
    private boolean isValidDate(String date) {
        if (!DATE_PATTERN.matcher(date).matches()) {
            System.out.println(INVALID_DATE);
            return false;
        }
        return true;
    }

    /**
     * check the transaction type (must be 'D' or 'W').
     *
     * @param type The transaction type.
     * @return true if type is valid, false otherwise.
     */
    private boolean isValidType(String type) {
        if (!type.equals(DEPOSIT) && !type.equals(WITHDRAWAL)) {
            System.out.println(INVALID_TRANSACTION_TYPE);
            return false;
        }
        return true;
    }

    /**
     * check the amount format (should be a valid decimal number)
     *
     * @param amountStr The amount string to validate.
     * @return true if the amount is valid, false otherwise.
     */
    private boolean isValidAmount(String amountStr) {
        if (!AMOUNT_PATTERN.matcher(amountStr).matches()) {
            System.out.println(INVALID_AMOUNT);
            return false;
        }
        return true;
    }
}
