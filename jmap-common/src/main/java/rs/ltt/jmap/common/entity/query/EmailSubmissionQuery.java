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

package rs.ltt.jmap.common.entity.query;

import rs.ltt.jmap.common.entity.Comparator;
import rs.ltt.jmap.common.entity.EmailSubmission;
import rs.ltt.jmap.common.entity.filter.Filter;
import rs.ltt.jmap.common.util.IndexableStringUtils;

public class EmailSubmissionQuery extends Query<EmailSubmission> {

    private EmailSubmissionQuery(Filter<EmailSubmission> filter, Comparator[] comparators) {
        super(filter, comparators);
    }

    @Override
    public String toQueryString() {
        return IndexableStringUtils.toIndexableString(L0_DIVIDER, L1_DIVIDER, filter, comparators);
    }

    public static EmailSubmissionQuery unfiltered() {
        return new EmailSubmissionQuery(null, null);
    }

    public static EmailSubmissionQuery unfiltered(Comparator[] comparators) {
        return new EmailSubmissionQuery(null, comparators);
    }

    public static EmailSubmissionQuery of(Filter<EmailSubmission> filter) {
        return new EmailSubmissionQuery(filter, null);
    }

    public static EmailSubmissionQuery of(Filter<EmailSubmission> filter, Comparator[] comparators) {
        return new EmailSubmissionQuery(filter, comparators);
    }
}
