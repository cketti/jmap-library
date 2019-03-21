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

package rs.ltt.jmap.mua.cache;

import com.google.common.base.MoreObjects;
import rs.ltt.jmap.common.entity.AbstractIdentifiableEntity;
import rs.ltt.jmap.common.entity.TypedState;
import rs.ltt.jmap.common.method.response.standard.ChangesMethodResponse;
import rs.ltt.jmap.common.method.response.standard.GetMethodResponse;

//TODO: together with AbstractUpdate and QueryUpdate this can probably be moved to jmap-client
public class Update<T extends AbstractIdentifiableEntity> extends AbstractUpdate<T> {

    private final T[] created;
    private final T[] updated;
    private final String[] destroyed;

    private Update(TypedState<T> oldState, TypedState<T> newState, T[] created, T[] updated, String[] destroyed, boolean hasMore) {
        super(oldState, newState, hasMore);
        this.created = created;
        this.updated = updated;
        this.destroyed = destroyed;
    }

    public static <T extends AbstractIdentifiableEntity> Update<T> of(ChangesMethodResponse<T> changesMethodResponse, GetMethodResponse<T> createdMethodResponse, GetMethodResponse<T> updatedMethodResponse) {
        return new Update<T>(changesMethodResponse.getTypedOldState(),
                changesMethodResponse.getTypedNewState(),
                createdMethodResponse.getList(),
                updatedMethodResponse.getList(),
                changesMethodResponse.getDestroyed(),
                changesMethodResponse.isHasMoreChanges());
    }

    public T[] getCreated() {
        return created;
    }

    public T[] getUpdated() {
        return updated;
    }

    public String[] getDestroyed() {
        return destroyed;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("oldTypedState", getOldTypedState())
                .add("newTypedState", getNewTypedState())
                .add("created", created)
                .add("updated", updated)
                .add("destroyed", destroyed)
                .add("hasMore", isHasMore())
                .toString();
    }

    @Override
    public boolean hasChanges() {
        final boolean modifiedItems = created.length + updated.length + destroyed.length > 0;
        return modifiedItems || hasStateChange();
    }

}
