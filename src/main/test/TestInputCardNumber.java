import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInputCardNumber {
    @Test
    public void testValidCardNumber() {
        ResponseMessage result = SyntaxChecker.isCardNumber("1234567890123456");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidCardNumber() {
        ResponseMessage result = SyntaxChecker.isCardNumber("12345");
        assertEquals(SyntaxResponseMessage.INVALID_CARD_NUMBER, result);
    }

    @Test
    public void testNullCardNumber() {
        ResponseMessage result = SyntaxChecker.isCardNumber(null);
        assertEquals(SyntaxResponseMessage.INVALID_CARD_NUMBER, result);
    }

    @Test
    public void testNonNumericCardNumber() {
        ResponseMessage result = SyntaxChecker.isCardNumber("abcd1234567890123");
        assertEquals(SyntaxResponseMessage.INVALID_CARD_NUMBER, result);
    }
}
