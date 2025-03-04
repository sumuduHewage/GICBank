
import com.bank.model.Transaction;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class TransactionTest {
    @Test
    void testTransactionInitialization() {
        Transaction txn = new Transaction("20250326", "20250326-01", "D", 100.00);

        assertEquals("20250326", txn.getDate());
        assertEquals("20250326-01", txn.getTransactionId());
        assertEquals("D", txn.getType());
        assertEquals(100.00, txn.getAmount());
    }
}
