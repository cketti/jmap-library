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

import rs.ltt.jmap.common.entity.AbstractIdentifiableEntity;

public abstract class AbstractUpdate<T extends AbstractIdentifiableEntity> {

    private final String oldState;
    private final String newState;
    private final boolean hasMore;

    protected AbstractUpdate(String oldState, String newState, boolean hasMore) {
        this.oldState = oldState;
        this.newState = newState;
        this.hasMore = hasMore;
    }

    public String getOldState() {
        return oldState;
    }

    public String getNewState() {
        return newState;
    }

    public abstract boolean hasChanges();

    public boolean isHasMore() {
        return hasMore;
    }
}
