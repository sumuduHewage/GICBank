package util;

public class BankTermConstants {
    public static final String MENU_WELCOME = "\nWelcome to AwesomeGIC Bank! What would you like to do?";
    public static final String MENU_IS_THERE_ANYTHING = "\nIs there anything else you'd like to do?";
    public static final String MENU_OPTION_TRANSACTION = "[T] Input transactions";
    public static final String MENU_OPTION_INTEREST = "[I] Define interest rules";
    public static final String MENU_OPTION_PRINT= "[P] Print statement";
    public static final String MENU_OPTION_QUIT= "[Q] Quit";
    public static final String MENU_OPTION_TERMINATE = "> ";
    public static final String TRANSACTION_PROMPT = "Please enter transaction details in <Date> <Account> <Type> <Amount> format \n (or enter blank to go back to main menu):";
    public static final String INTEREST_PROMPT = "Please enter interest rule details in <Date> <RuleId> <Rate in %> format (or enter blank to go back to main menu):";
    public static final String INVALID_FORMAT = "Invalid format. Try again.";
    public static final String TRANSACTION_RECORDED = "Transaction recorded.";
    public static final String INTEREST_DEFINED = "Interest rate defined.";
    public static final String ACCOUNT_NOT_FOUND = "Account does not exist.";
    public static final String DEPOSIT = "D";
    public static final String WITHDRAWAL = "W";
    public static final String TRANSACTION = "T";
    public static final String INTEREST = "I";
    public static final String PRINT = "P";
    public static final String QUIT = "Q";
    public static final String STATEMENT_HEADER = "\nAccount: ";
    public static final String BALANCE_LABEL = "Balance: ";
    public static final String INTEREST_EARNED_LABEL = "Interest Earned: ";
    public static final String TRANSACTIONS_LABEL = "Transactions:";
    public static final String GOODBYE_MESSAGE = "Thank you for banking with AwesomeGIC Bank.\n Have a nice day!";
    public static final String INVALID_CHOICE = "Invalid choice. Please try again.";
    public static final String INVALID_DATE = "Invalid date format! Please enter in YYYYMMDD format.";
    public static final String INVALID_TRANSACTION_TYPE= "Invalid transaction type! Use 'D' for deposit or 'W' for withdrawal.";
    public static final String INVALID_AMOUNT= "Invalid amount! Please enter a valid number (e.g., 100.00, -50.50).";

}
