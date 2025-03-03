import service.BankService;
import service.BankServiceImpl;
import util.UserInputProcessor;

import java.util.Scanner;

import static util.BankTermConstants.*;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        BankService bankService = new BankServiceImpl();
        UserInputProcessor inputHandler = new UserInputProcessor(bankService, scanner);

        while (true) {
            System.out.println(MENU_WELCOME);
            System.out.println(MENU_OPTION_TRANSACTION);
            System.out.println(MENU_OPTION_INTEREST);
            System.out.println(MENU_OPTION_PRINT);
            System.out.println(MENU_OPTION_QUIT);
            System.out.print(MENU_OPTION_TERMINATE);

            String choice = scanner.nextLine().trim().toUpperCase();
            switch (choice) {
                case TRANSACTION:
                    inputHandler.handleTransactionInput();
                    break;
                case INTEREST:
                    inputHandler.handleInterestRuleInput();
                    break;
                case PRINT:
                    inputHandler.handlePrintStatementInput();
                    break;
                case QUIT:
                    System.out.println(GOODBYE_MESSAGE);
                    return;
                default:
                    System.out.println(INVALID_CHOICE);
            }
        }
    }
}
