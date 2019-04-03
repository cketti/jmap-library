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

import com.google.common.base.MoreObjects;
import com.google.common.base.Preconditions;

public class QueryResultItem {

    private final String emailId;
    private final String threadId;

    private QueryResultItem(String emailId, String threadId) {
        this.emailId = emailId;
        this.threadId = threadId;
    }

    public String getEmailId() {
        return emailId;
    }

    public String getThreadId() {
        return threadId;
    }

    public static QueryResultItem of(String emailId, String threadId) {
        Preconditions.checkNotNull(emailId);
        Preconditions.checkNotNull(threadId);
        return new QueryResultItem(emailId, threadId);
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("emailId", emailId)
                .add("threadId", threadId)
                .toString();
    }
}
