import com.bank.util.DateValidator;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DateValidatorTest {
    @Test
    void testValidDate() {
        assertTrue(DateValidator.isValidDate("20250315"));
        assertTrue(DateValidator.isValidDate("20251231"));
    }

    @Test
    void testInvalidDate() {
        assertFalse(DateValidator.isValidDate("2025-03-15"));
        assertFalse(DateValidator.isValidDate("20250332"));
        assertFalse(DateValidator.isValidDate("20251301"));
        assertFalse(DateValidator.isValidDate("abcd1234"));
    }
}
