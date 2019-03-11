package rs.ltt.jmap.gson;

import org.junit.Assert;
import org.junit.Test;
import rs.ltt.jmap.common.GenericResponse;
import rs.ltt.jmap.common.Response;
import rs.ltt.jmap.common.method.error.RequestTooLargeMethodErrorResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;

public class ResponseDeserializationTest extends AbstractGsonTest {

    @Test
    public void deserializeMailboxGetEmailGetResponse() throws IOException {
        GenericResponse genericResponse = parseFromResource("response/mailbox-get-email-get.json", GenericResponse.class);
        Assert.assertThat(genericResponse, instanceOf(Response.class));
        final Response response = (Response) genericResponse;
        Assert.assertNotNull(response.getMethodResponses());
        Assert.assertEquals(response.getMethodResponses().length, 2);
        Assert.assertThat(response.getMethodResponses()[1].getMethodResponse(), instanceOf(RequestTooLargeMethodErrorResponse.class));
    }

}
