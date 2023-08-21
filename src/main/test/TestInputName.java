import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestInputName {
    @Test
    public void testValidName() {
        ResponseMessage result = SyntaxChecker.isValidName("John Doe");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidNameStartingWithSpace() {
        ResponseMessage result = SyntaxChecker.isValidName(" John Doe");
        assertEquals(SyntaxResponseMessage.INVALID_NAME, result);
    }

    @Test
    public void testInvalidEmptyName() {
        ResponseMessage result = SyntaxChecker.isValidName("");
        assertEquals(SyntaxResponseMessage.INVALID_NAME, result);
    }

    @Test
    public void testInvalidNullName() {
        ResponseMessage result = SyntaxChecker.isValidName(null);
        assertEquals(SyntaxResponseMessage.INVALID_NAME, result);
    }

    @Test
    public void testInvalidNameWithNumbers() {
        ResponseMessage result = SyntaxChecker.isValidName("John123");
        assertEquals(SyntaxResponseMessage.INVALID_NAME, result);
    }
}
