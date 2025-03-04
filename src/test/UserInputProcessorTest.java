import com.bank.model.BankAccount;
import com.bank.service.BankService;
import com.bank.service.BankServiceImpl;
import com.bank.util.UserInputProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserInputProcessorTest {

    @Mock
    private BankService bankService;

    private UserInputProcessor userInputProcessor;
    private Scanner scanner;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userInputProcessor = new UserInputProcessor(bankService, new Scanner(System.in));
    }

    @Test
    void testHandleTransactionInput_InvalidDateFormat() {
        String input = "20231515 AC001 D 100.50\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleTransactionInput();

        verify(bankService, never()).processTransaction(any(), any(), any(), anyDouble());
    }

    @Test
    void testHandleTransactionInput_InvalidTransactionType() {
        String input = "20231015 AC001 X 100.50\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleTransactionInput();

        verify(bankService, never()).processTransaction(any(), any(), any(), anyDouble());
    }

    @Test
    void testHandleTransactionInput_InvalidAmountFormat() {
        String input = "20231015 AC001 D ABC\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleTransactionInput();

        verify(bankService, never()).processTransaction(any(), any(), any(), anyDouble());
    }

    @Test
    void testHandleInterestRuleInput_InvalidDateFormat() {
        String input = "20231515 RULE01 5.0\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleInterestRuleInput();

        verify(bankService, never()).defineInterestRule(any(), any(), anyDouble());
    }

    @Test
    void testHandleInterestRuleInput_InvalidRate() {
        String input = "20231015 RULE01 105.0\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleInterestRuleInput();

        verify(bankService, never()).defineInterestRule(any(), any(), anyDouble());
    }

    @Test
    void testHandlePrintStatementInput_InvalidFormat() {
        String input = "AC001 2023\n\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handlePrintStatementInput();

        verify(bankService, never()).printAccountStatement(any(), any());
    }

    @Test
    void testHandleTransactionInput_ValidInput() {
        String input = "20231015 AC001 D 100.50\n\nQ\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        Scanner testScanner = new Scanner(inputStream);

        BankServiceImpl realBankService = new BankServiceImpl();
        bankService = spy(realBankService);

        userInputProcessor = new UserInputProcessor(bankService, testScanner);

        userInputProcessor.handleTransactionInput();

        verify(bankService, times(1)).processTransaction("20231015", "AC001", "D", 100.50);

        BankAccount account = bankService.getAccount("AC001");
        assertNotNull(account);
        assertEquals(1, account.getTransactions().size());
    }


    @Test
    void testHandlePrintStatementInput_ValidInput() {
        String input = "AC001 202310\nQ\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handlePrintStatementInput();

        verify(bankService, times(1)).printAccountStatement("AC001", "202310");
    }

    @Test
    void testHandleInterestRuleInput_ValidInput() {
        String input = "20231015 RULE01 5.0\nQ\n";
        InputStream inputStream = new ByteArrayInputStream(input.getBytes());
        scanner = new Scanner(inputStream);
        userInputProcessor = new UserInputProcessor(bankService, scanner);

        userInputProcessor.handleInterestRuleInput();

        verify(bankService, times(1)).defineInterestRule("20231015", "RULE01", 5.0);
    }

    @Test
    void testIsValidDate_ValidDate() {
        assertTrue(userInputProcessor.isValidDate("20231015"));
    }

    @Test
    void testIsValidDate_InvalidDate() {
        assertFalse(userInputProcessor.isValidDate("20231515"));
    }

    @Test
    void testIsValidType_ValidType() {
        assertTrue(userInputProcessor.isValidType("D"));
        assertTrue(userInputProcessor.isValidType("W"));
    }

    @Test
    void testIsValidType_InvalidType() {
        assertFalse(userInputProcessor.isValidType("X"));
    }

    @Test
    void testIsValidAmount_ValidAmount() {
        assertTrue(userInputProcessor.isValidAmount("100.50"));
        assertTrue(userInputProcessor.isValidAmount("-100.50"));
    }

    @Test
    void testIsValidAmount_InvalidAmount() {
        assertFalse(userInputProcessor.isValidAmount("ABC"));
        assertFalse(userInputProcessor.isValidAmount("100.555"));
    }
}