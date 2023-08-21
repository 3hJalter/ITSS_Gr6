import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInputSecurityCode {
    @Test
    public void testValidSecurityCode() {
        ResponseMessage result = SyntaxChecker.isValidSecurityCode("123");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidSecurityCodeShort() {
        ResponseMessage result = SyntaxChecker.isValidSecurityCode("12");
        assertEquals(SyntaxResponseMessage.INVALID_SECURITY_CODE, result);
    }

    @Test
    public void testInvalidSecurityCodeLong() {
        ResponseMessage result = SyntaxChecker.isValidSecurityCode("1234");
        assertEquals(SyntaxResponseMessage.INVALID_SECURITY_CODE, result);
    }

    @Test
    public void testInvalidSecurityCodeNonNumeric() {
        ResponseMessage result = SyntaxChecker.isValidSecurityCode("abc");
        assertEquals(SyntaxResponseMessage.INVALID_SECURITY_CODE, result);
    }

    @Test
    public void testNullSecurityCode() {
        ResponseMessage result = SyntaxChecker.isValidSecurityCode(null);
        assertEquals(SyntaxResponseMessage.INVALID_SECURITY_CODE, result);
    }
}
