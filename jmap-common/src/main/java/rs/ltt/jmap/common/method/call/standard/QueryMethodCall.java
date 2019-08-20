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

package rs.ltt.jmap.common.method.call.standard;

import rs.ltt.jmap.common.entity.AbstractIdentifiableEntity;
import rs.ltt.jmap.common.entity.Comparator;
import rs.ltt.jmap.common.entity.Query;
import rs.ltt.jmap.common.entity.filter.Filter;
import rs.ltt.jmap.common.method.MethodCall;


public abstract class QueryMethodCall<T extends AbstractIdentifiableEntity> implements MethodCall {

    private String accountId;
    private Filter filter;
    private Comparator[] sort;
    private Integer position;
    private String anchor;
    private Integer anchorOffset;
    private Integer limit;

    public QueryMethodCall() {
        
    }

    public QueryMethodCall(Filter<T> filter) {
        this.filter = filter;
    }

    public QueryMethodCall(Query<T> query) {
        this.filter = query.filter;
        this.sort = query.comparators;
    }

    public QueryMethodCall(Query<T> query, Integer limit) {
        this.filter = query.filter;
        this.sort = query.comparators;
        this.limit = limit;
    }

    public QueryMethodCall(Query<T> query, String afterId) {
        this.filter = query.filter;
        this.sort = query.comparators;
        this.anchor = afterId;
        this.anchorOffset = 1;
    }

    public QueryMethodCall(Query<T> query, String afterId, Integer limit) {
        this.filter = query.filter;
        this.sort = query.comparators;
        this.anchor = afterId;
        this.anchorOffset = 1;
        this.limit = limit;
    }

}
