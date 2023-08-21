import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

public class TestInputID {

    @Test
    public void testValidId() {
        ResponseMessage result = SyntaxChecker.isId("12345");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidId() {
        ResponseMessage result = SyntaxChecker.isId("abc");
        assertEquals(SyntaxResponseMessage.INVALID_ID_INPUT, result);
    }

    @Test
    public void testEmptyId() {
        ResponseMessage result = SyntaxChecker.isId("");
        assertEquals(SyntaxResponseMessage.INVALID_ID_INPUT, result);
    }

    @Test
    public void testNullId() {
        ResponseMessage result = SyntaxChecker.isId(null);
        assertEquals(SyntaxResponseMessage.INVALID_ID_INPUT, result);
    }
}