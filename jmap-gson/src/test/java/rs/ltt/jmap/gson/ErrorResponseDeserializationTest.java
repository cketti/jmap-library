package rs.ltt.jmap.gson;

import org.junit.Assert;
import org.junit.Test;
import rs.ltt.jmap.common.ErrorResponse;
import rs.ltt.jmap.common.GenericResponse;
import rs.ltt.jmap.common.entity.ErrorType;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;


public class ErrorResponseDeserializationTest extends AbstractGsonTest {

    @Test
    public void deserializeUnknownCapability() throws IOException {
        GenericResponse genericResponse = parseFromResource("response-error/unknown-capability.json", GenericResponse.class);
        Assert.assertThat(genericResponse, instanceOf(ErrorResponse.class));
        ErrorResponse errorResponse = (ErrorResponse) genericResponse;
        Assert.assertEquals(errorResponse.getType(), ErrorType.UNKNOWN_CAPABILITY);
    }

    @Test
    public void deserializeNotJson() throws IOException {
        GenericResponse genericResponse = parseFromResource("response-error/not-json.json", GenericResponse.class);
        Assert.assertThat(genericResponse, instanceOf(ErrorResponse.class));
        ErrorResponse errorResponse = (ErrorResponse) genericResponse;
        Assert.assertEquals(errorResponse.getType(), ErrorType.NOT_JSON);
    }

    @Test
    public void deserializeNotRequest() throws IOException {
        GenericResponse genericResponse = parseFromResource("response-error/not-request.json", GenericResponse.class);
        Assert.assertThat(genericResponse, instanceOf(ErrorResponse.class));
        ErrorResponse errorResponse = (ErrorResponse) genericResponse;
        Assert.assertEquals(errorResponse.getType(), ErrorType.NOT_REQUEST);
    }

}