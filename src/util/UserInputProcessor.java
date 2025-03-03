package util;

import service.BankService;
import java.util.Scanner;
import java.util.regex.Pattern;

import static util.BankTermConstants.*;

public class UserInputProcessor {
    private final BankService bankService;
    private final Scanner scanner;

    private static final Pattern DATE_PATTERN = Pattern.compile("^\\d{4}(0[1-9]|1[0-2])(0[1-9]|[12]\\d|3[01])$");
    private static final Pattern AMOUNT_PATTERN = Pattern.compile("^-?\\d+(\\.\\d{1,2})?$");

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

            if (!isValidDate(date) || !isValidType(type) || !isValidAmount(amountStr)) {
                continue;
            }

            double amount = Double.parseDouble(amountStr);
            bankService.processTransaction(date, accountNumber, type, amount);
            System.out.println(TRANSACTION_RECORDED);
        }
    }

    public void handleInterestRuleInput() {
        System.out.println(INTEREST_PROMPT);
        while (true) {
            System.out.print("> ");
            String input = scanner.nextLine().trim();
            if (input.isEmpty()) return;

            String[] parts = input.split(" ");
            if (parts.length == 2) {
                bankService.defineInterestRate(parts[0], Double.parseDouble(parts[1]));
                System.out.println(INTEREST_DEFINED);
            } else {
                System.out.println(INVALID_FORMAT);
            }
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
