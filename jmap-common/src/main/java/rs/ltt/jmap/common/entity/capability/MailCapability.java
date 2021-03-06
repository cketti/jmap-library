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

package rs.ltt.jmap.common.entity.capability;

import com.google.common.base.MoreObjects;
import rs.ltt.jmap.Namespace;
import rs.ltt.jmap.annotation.JmapCapability;
import rs.ltt.jmap.common.entity.Capability;

@JmapCapability(namespace = Namespace.MAIL)
public class MailCapability implements Capability {
    private int maxMailboxesPerEmail;
    private int maxMailboxDepth;
    private int maxSizeMailboxName;
    private int maxSizeAttachmentsPerEmail;
    private String[] emailsListSortOptions;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxMailboxesPerEmail", maxMailboxesPerEmail)
                .add("maxMailboxDepth", maxMailboxDepth)
                .add("maxSizeMailboxName", maxSizeMailboxName)
                .add("maxSizeAttachmentsPerEmail", maxSizeAttachmentsPerEmail)
                .add("emailsListSortOptions", emailsListSortOptions)
                .toString();
    }

    public int getMaxMailboxesPerEmail() {
        return maxMailboxesPerEmail;
    }

    public int getMaxMailboxDepth() {
        return maxMailboxDepth;
    }

    public int getMaxSizeMailboxName() {
        return maxSizeMailboxName;
    }

    public int getMaxSizeAttachmentsPerEmail() {
        return maxSizeAttachmentsPerEmail;
    }

    public String[] getEmailsListSortOptions() {
        return emailsListSortOptions;
    }
}
