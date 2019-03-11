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
import rs.ltt.jmap.common.entity.AbstractIdentifiableEntity;
import rs.ltt.jmap.common.method.MethodResponse;


public abstract class ChangesMethodResponse<T extends AbstractIdentifiableEntity> implements MethodResponse {

    private String accountId;
    private String oldState;
    private String newState;
    private boolean hasMoreChanges;
    private String[] created;
    private String[] updated;
    private String[] destroyed;

    public String getAccountId() {
        return accountId;
    }

    public String getOldState() {
        return oldState;
    }

    public String getNewState() {
        return newState;
    }

    public boolean isHasMoreChanges() {
        return hasMoreChanges;
    }

    public String[] getCreated() {
        return created;
    }

    public String[] getUpdated() {
        return updated;
    }

    public String[] getDestroyed() {
        return destroyed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("oldState", oldState)
                .add("newState", newState)
                .add("hasMoreChanges", hasMoreChanges)
                .add("created", created)
                .add("updated", updated)
                .add("destroyed", destroyed)
                .toString();
    }
}
