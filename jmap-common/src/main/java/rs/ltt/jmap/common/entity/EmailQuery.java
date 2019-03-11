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

package rs.ltt.jmap.common.entity;

import rs.ltt.jmap.common.entity.filter.Filter;
import rs.ltt.jmap.common.util.IndexableStringUtils;

public class EmailQuery extends Query<Email> {

    public final Boolean collapseThreads;

    private EmailQuery(Filter<Email> filter, Comparator[] comparators, Boolean collapseThreads) {
        super(filter, comparators);
        this.collapseThreads = collapseThreads;
    }

    @Override
    public String toQueryString() {
        return IndexableStringUtils.toIndexableString(L0_DIVIDER, L1_DIVIDER, filter, comparators, collapseThreads);
    }

    public static EmailQuery unfiltered() {
        return new EmailQuery(null, null, null);
    }

    public static EmailQuery unfiltered(boolean collapseThreads) {
        return new EmailQuery(null, null, collapseThreads);
    }

    public static EmailQuery of(Filter<Email> filter) {
        return new EmailQuery(filter, null, null);
    }

    public static EmailQuery of(Filter<Email> filter, Comparator[] comparators) {
        return new EmailQuery(filter, comparators, null);
    }

    public static EmailQuery of(Filter<Email> filter, boolean collapseThreads) {
        return new EmailQuery(filter, null, collapseThreads);
    }

    public static EmailQuery of(Filter<Email> filter, Comparator[] comparators, boolean collapseThreads) {
        return new EmailQuery(filter, comparators, collapseThreads);
    }
}
