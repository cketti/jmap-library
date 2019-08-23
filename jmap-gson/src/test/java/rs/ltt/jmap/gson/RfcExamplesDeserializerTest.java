/*
 * Copyright 2019 Daniel Gultsch
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package rs.ltt.jmap.gson;

import org.junit.Assert;
import org.junit.Test;
import rs.ltt.jmap.common.Response;
import rs.ltt.jmap.common.entity.Email;
import rs.ltt.jmap.common.entity.Identity;
import rs.ltt.jmap.common.method.response.email.GetEmailMethodResponse;
import rs.ltt.jmap.common.method.response.identity.GetIdentityMethodResponse;

import java.io.IOException;

import static org.hamcrest.CoreMatchers.instanceOf;

public class RfcExamplesDeserializerTest extends AbstractGsonTest {

    @Test
    public void emailGetResponse() throws IOException {
        Response.Invocation[] responseInvocation = parseFromResource("rfc-example/email-get-response.json", Response.Invocation[].class);
        Assert.assertEquals(responseInvocation.length, 1);
        Assert.assertThat(responseInvocation[0].getMethodResponse(), instanceOf(GetEmailMethodResponse.class));
        final GetEmailMethodResponse methodResponse = (GetEmailMethodResponse) responseInvocation[0].getMethodResponse();
        Email[] emails = methodResponse.getList();
        Assert.assertEquals(emails.length, 1);
        Email email = emails[0];
        Assert.assertEquals(email.getId(), "f123u457");
        Assert.assertEquals(email.getBodyValues().size(), 2);
        Assert.assertEquals(email.getFrom().size(), 1);
        Assert.assertEquals(email.getSubject(), "Dinner on Thursday?");
    }

    @Test
    public void identityGetResponse() throws IOException {
        Response.Invocation invocation =  parseFromResource("rfc-example/identity-get-response.json", Response.Invocation.class);
        Assert.assertThat(invocation.getMethodResponse(), instanceOf(GetIdentityMethodResponse.class));
        GetIdentityMethodResponse methodResponse = (GetIdentityMethodResponse) invocation.getMethodResponse();
        Identity[] identities = methodResponse.getList();
        Assert.assertEquals(identities.length, 2);
        Assert.assertEquals(identities[0].getName(), "Joe Bloggs");
    }
}
