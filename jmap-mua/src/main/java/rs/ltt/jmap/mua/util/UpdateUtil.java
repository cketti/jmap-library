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

package rs.ltt.jmap.mua.util;

import com.google.common.util.concurrent.ListenableFuture;
import rs.ltt.jmap.client.JmapClient;
import rs.ltt.jmap.client.MethodResponses;
import rs.ltt.jmap.common.Request;
import rs.ltt.jmap.common.entity.Email;
import rs.ltt.jmap.common.method.call.email.ChangesEmailMethodCall;
import rs.ltt.jmap.common.method.call.email.GetEmailMethodCall;
import rs.ltt.jmap.common.method.call.identity.ChangesIdentityMethodCall;
import rs.ltt.jmap.common.method.call.identity.GetIdentityMethodCall;
import rs.ltt.jmap.common.method.call.mailbox.ChangesMailboxMethodCall;
import rs.ltt.jmap.common.method.call.mailbox.GetMailboxMethodCall;
import rs.ltt.jmap.common.method.call.thread.ChangesThreadMethodCall;
import rs.ltt.jmap.common.method.call.thread.GetThreadMethodCall;

public class UpdateUtil {

    private static MethodResponsesFuture add(JmapClient.MultiCall multiCall, Invocation invocation) {
        final ListenableFuture<MethodResponses> changes = multiCall.add(invocation.changes);
        final ListenableFuture<MethodResponses> created = multiCall.add(invocation.created);
        final ListenableFuture<MethodResponses> updated = multiCall.add(invocation.updated);
        return new MethodResponsesFuture(changes, created, updated);
    }

    public static MethodResponsesFuture emails(JmapClient.MultiCall multiCall, String state) {
        return add(multiCall, emails(state));
    }

    private static Invocation emails(String state) {
        final Request.Invocation changes = Request.Invocation.create(new ChangesEmailMethodCall(state));
        final Request.Invocation created = Request.Invocation.create(new GetEmailMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.CREATED),
                true
        ));
        final Request.Invocation updated = Request.Invocation.create(new GetEmailMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.UPDATED),
                Email.MUTABLE_PROPERTIES
        ));
        return new Invocation(changes, created, updated);
    }

    public static MethodResponsesFuture identities(JmapClient.MultiCall multiCall, String state) {
        return add(multiCall, identities(state));
    }

    private static Invocation identities(String state) {
        final Request.Invocation changes = Request.Invocation.create(new ChangesIdentityMethodCall(state));
        final Request.Invocation created = Request.Invocation.create(new GetIdentityMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.CREATED)
        ));
        final Request.Invocation updated = Request.Invocation.create(new GetIdentityMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.UPDATED)
        ));
        return new Invocation(changes, created, updated);
    }

    public static MethodResponsesFuture mailboxes(JmapClient.MultiCall multiCall, String state) {
        return add(multiCall, mailboxes(state));
    }

    private static Invocation mailboxes(String state) {
        final Request.Invocation changes = Request.Invocation.create(new ChangesMailboxMethodCall(state));
        final Request.Invocation created = Request.Invocation.create(new GetMailboxMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.CREATED)
        ));
        final Request.Invocation updated = Request.Invocation.create(new GetMailboxMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.UPDATED),
                changes.createReference(Request.Invocation.ResultReference.Path.UPDATED_PROPERTIES)
        ));
        return new Invocation(changes, created, updated);
    }

    public static MethodResponsesFuture threads(JmapClient.MultiCall multiCall, String state) {
        return add(multiCall, threads(state));
    }

    private static Invocation threads(String state) {
        final Request.Invocation changes = Request.Invocation.create(new ChangesThreadMethodCall(state));
        final Request.Invocation created = Request.Invocation.create(new GetThreadMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.CREATED)
        ));
        final Request.Invocation updated = Request.Invocation.create(new GetThreadMethodCall(
                changes.createReference(Request.Invocation.ResultReference.Path.UPDATED)
        ));
        return new Invocation(changes, created, updated);
    }

    public static class Invocation {

        public final Request.Invocation changes;
        public final Request.Invocation created;
        public final Request.Invocation updated;

        private Invocation(Request.Invocation changes, Request.Invocation created, Request.Invocation updated) {
            this.changes = changes;
            this.created = created;
            this.updated = updated;
        }
    }

    public static class MethodResponsesFuture {
        public final ListenableFuture<MethodResponses> changes;
        public final ListenableFuture<MethodResponses> created;
        public final ListenableFuture<MethodResponses> updated;

        private MethodResponsesFuture(ListenableFuture<MethodResponses> changes, ListenableFuture<MethodResponses> created, ListenableFuture<MethodResponses> updated) {
            this.changes = changes;
            this.created = created;
            this.updated = updated;
        }
    }

}
