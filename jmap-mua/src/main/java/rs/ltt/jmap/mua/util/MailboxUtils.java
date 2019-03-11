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

import com.google.common.base.CaseFormat;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import rs.ltt.jmap.common.entity.Mailbox;
import rs.ltt.jmap.common.entity.Role;

import java.util.Collection;

public class MailboxUtils {

    public static @NullableDecl
    Mailbox find(Collection<Mailbox> mailboxes, Role role) {
        for (Mailbox mailbox : mailboxes) {
            if (mailbox.getRole() == role) {
                return mailbox;
            }
        }
        return null;
    }

    public static Mailbox create(Role role) {
        return Mailbox.builder().role(role).name(CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, role.toString())).build();
    }
}
