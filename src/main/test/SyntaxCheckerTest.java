import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * The SyntaxCheckerTest class contains unit tests for the SyntaxChecker utility class.
 * It tests various methods to check the validity of different syntax patterns.
 */
public class SyntaxCheckerTest {
    /**
     * Test case to verify the isInteger method.
     */
    @Test
    public void testIsInteger() {
        assertTrue(SyntaxChecker.isInteger("123"));
        assertFalse(SyntaxChecker.isInteger("abc"));
    }

    /**
     * Test case to verify the isMonth method.
     */
    @Test
    public void testIsMonth() {
        assertTrue(SyntaxChecker.isMonth(1));
        assertTrue(SyntaxChecker.isMonth(12));
        assertFalse(SyntaxChecker.isMonth(0));
        assertFalse(SyntaxChecker.isMonth(13));
    }

    /**
     * Test case to verify the isYear method.
     */
    @Test
    public void testIsYear() {
        assertTrue(SyntaxChecker.isYear(0));
        assertTrue(SyntaxChecker.isYear(99));
        assertFalse(SyntaxChecker.isYear(-1));
        assertFalse(SyntaxChecker.isYear(100));
    }

    /**
     * Test case to verify the isUUID method.
     */
    @Test
    public void testIsUUID() {
        assertTrue(SyntaxChecker.isUUID("550e8400-e29b-41d4-a716-446655440000"));
        assertFalse(SyntaxChecker.isUUID("invalid-uuid"));
    }

    /**
     * Test case to verify the isCardNumber method.
     */
    @Test
    public void testIsCardNumber() {
        assertTrue(SyntaxChecker.isCardNumber("1234567890123456"));
        assertFalse(SyntaxChecker.isCardNumber("12345"));
        assertFalse(SyntaxChecker.isCardNumber("12345678901234567"));
    }

    /**
     * Test case to verify the isValidName method.
     */
    @Test
    public void testIsValidName() {
        assertTrue(SyntaxChecker.isValidName("John Doe"));
        assertTrue(SyntaxChecker.isValidName("Alice"));
        assertFalse(SyntaxChecker.isValidName(""));
        assertFalse(SyntaxChecker.isValidName(" Jane"));
        assertFalse(SyntaxChecker.isValidName("123"));
    }

    /**
     * Test case to verify the isValidSecurityCode method.
     */
    @Test
    public void testIsValidSecurityCode() {
        assertTrue(SyntaxChecker.isValidSecurityCode("123"));
        assertFalse(SyntaxChecker.isValidSecurityCode("12"));
        assertFalse(SyntaxChecker.isValidSecurityCode("1234"));
        assertFalse(SyntaxChecker.isValidSecurityCode("abc"));
    }

    /**
     * Test case to verify the isAmount method.
     */
    @Test
    public void testIsAmount() {
        assertTrue(SyntaxChecker.isAmount("123.45"));
        assertTrue(SyntaxChecker.isAmount("0.0"));
        assertFalse(SyntaxChecker.isAmount("abc"));
        assertFalse(SyntaxChecker.isAmount("123.45.67"));
    }
}
