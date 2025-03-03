import model.BankAccount;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import service.BankServiceImpl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BankServiceImplTest {
    private BankServiceImpl bankService;

    @BeforeEach
    void setUp() {
        bankService = new BankServiceImpl();
    }

    @Test
    void testDepositTransaction() {
        bankService.processTransaction("20250301", "AC001", "D", 200.00);
        BankAccount account = bankService.getAccount("AC001");

        assertNotNull(account);
        assertEquals(200.00, account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void testWithdrawalWithSufficientBalance() {
        bankService.processTransaction("20250301", "AC001", "D", 500.00);
        bankService.processTransaction("20250302", "AC001", "W", 100.00);
        BankAccount account = bankService.getAccount("AC001");

        assertNotNull(account);
        assertEquals(400.00, account.getBalance());
        assertEquals(2, account.getTransactions().size());
    }

    @Test
    void testWithdrawalWithInsufficientBalance() {
        bankService.processTransaction("20250301", "AC001", "D", 50.00);
        bankService.processTransaction("20250302", "AC001", "W", 100.00);

        BankAccount account = bankService.getAccount("AC001");
        assertNotNull(account);
        assertEquals(50.00, account.getBalance());
        assertEquals(1, account.getTransactions().size());
    }

    @Test
    void testInterestRuleDefinition() {
        bankService.defineInterestRule("20250301", "RULE01", 2.0);

        assertEquals(1, bankService.getInterestRules().size());
        assertEquals(2.0, bankService.getInterestRules().get(0).getInterestRate());
    }
}
