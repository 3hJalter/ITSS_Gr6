import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

public class TestInputMonth {

    @Test
    public void testValidMonth() {
        ResponseMessage result = SyntaxChecker.isMonth("7");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidMonth() {
        ResponseMessage result = SyntaxChecker.isMonth("13");
        assertEquals(SyntaxResponseMessage.INVALID_MONTH_INPUT, result);
    }

    @Test
    public void testInvalidMonthInput() {
        ResponseMessage result = SyntaxChecker.isMonth("abc");
        assertEquals(SyntaxResponseMessage.INVALID_MONTH_INPUT, result);
    }

    @Test
    public void testMonthOutOfRange() {
        ResponseMessage result = SyntaxChecker.isMonth("0");
        assertEquals(SyntaxResponseMessage.INVALID_MONTH_INPUT, result);
    }

    @Test
    public void testNullMonth() {
        ResponseMessage result = SyntaxChecker.isMonth(null);
        assertEquals(SyntaxResponseMessage.INVALID_MONTH_INPUT, result);
    }
}