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

package rs.ltt.jmap.common.method.response.standard;

import com.google.common.base.MoreObjects;
import rs.ltt.jmap.common.method.MethodResponse;

public abstract class QueryMethodResponse implements MethodResponse {
    private String accountId;
    private String queryState;
    private boolean canCalculateChanges;
    private int position;
    private String[] ids;
    private int total;
    private int limit;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("queryState", queryState)
                .add("canCalculateChanges", canCalculateChanges)
                .add("position", position)
                .add("ids", ids)
                .add("total", total)
                .add("limit", limit)
                .toString();
    }

    public String getAccountId() {
        return accountId;
    }

    public String getQueryState() {
        return queryState;
    }

    public boolean isCanCalculateChanges() {
        return canCalculateChanges;
    }

    public int getPosition() {
        return position;
    }

    public String[] getIds() {
        return ids;
    }

    public int getTotal() {
        return total;
    }

    public int getLimit() {
        return limit;
    }
}
