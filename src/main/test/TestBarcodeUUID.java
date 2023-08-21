import org.junit.jupiter.api.Test;
import utils.SyntaxChecker;
import utils.response.ResponseMessage;
import utils.response.responseMessageImpl.SyntaxResponseMessage;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestBarcodeUUID {
    @Test
    public void testValidUUID() {
        ResponseMessage result = SyntaxChecker.isUUID("123e4567-e89b-12d3-a456-426614174000");
        assertEquals(SyntaxResponseMessage.SUCCESSFUL, result);
    }

    @Test
    public void testInvalidUUID() {
        ResponseMessage result = SyntaxChecker.isUUID("not_a_uuid");
        assertEquals(SyntaxResponseMessage.INVALID_BARCODE_UUID, result);
    }

    @Test
    public void testNullUUID() {
        ResponseMessage result = SyntaxChecker.isUUID(null);
        assertEquals(SyntaxResponseMessage.INVALID_BARCODE_UUID, result);
    }
}
