/*


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.*;
import org.mockito.Mockito;
import service.BankService;
import util.UserInputProcessor;

import java.util.Scanner;

import static org.mockito.Mockito.*;

public class UserInputProcessorTest {
    private BankService bankService;
    private Scanner scanner;
    private UserInputProcessor inputProcessor;

    @BeforeEach
    void setUp() {
        bankService = mock(BankService.class);
        scanner = mock(Scanner.class);
        inputProcessor = new UserInputProcessor(bankService, scanner);
    }

    @Test
    void testHandleTransactionInput_ValidDeposit() {
        when(scanner.nextLine()).thenReturn("20250326 AC001 D 150.00", "");

        inputProcessor.handleTransactionInput();

        verify(bankService, times(1)).processTransaction("20250326", "AC001", "D", 150.00);
    }

    @Test
    void testHandleTransactionInput_InvalidFormat() {
        when(scanner.nextLine()).thenReturn("invalid input", "");

        inputProcessor.handleTransactionInput();

        verify(bankService, never()).processTransaction(anyString(), anyString(), anyString(), anyDouble());
    }

    @Test
    void testHandleTransactionInput_InvalidAmount() {
        when(scanner.nextLine()).thenReturn("20250326 AC001 D -50.00", "");

        inputProcessor.handleTransactionInput();

        verify(bankService, never()).processTransaction(anyString(), anyString(), anyString(), anyDouble());
    }
}
*/
