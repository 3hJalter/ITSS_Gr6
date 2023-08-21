import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

public class TestInputYear {

    @Test
    public void testValidYear() {
        ResponseMessage result = SyntaxChecker.isYear("50");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidYear() {
        ResponseMessage result = SyntaxChecker.isYear("100");
        assertEquals(SyntaxResponseMessage.INVALID_YEAR_INPUT, result);
    }

    @Test
    public void testInvalidYearInput() {
        ResponseMessage result = SyntaxChecker.isYear("abc");
        assertEquals(SyntaxResponseMessage.INVALID_YEAR_INPUT, result);
    }

    @Test
    public void testYearOutOfRange() {
        ResponseMessage result = SyntaxChecker.isYear("-5");
        assertEquals(SyntaxResponseMessage.INVALID_YEAR_INPUT, result);
    }

    @Test
    public void testNullYear() {
        ResponseMessage result = SyntaxChecker.isYear(null);
        assertEquals(SyntaxResponseMessage.INVALID_YEAR_INPUT, result);
    }
}
