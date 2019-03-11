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

import com.google.common.base.MoreObjects;

public class MailboxRights {

    private boolean mayReadItems;

    private boolean mayAddItems;

    private boolean mayRemoveItems;

    private boolean maySetSeen;

    private boolean maySetKeywords;

    private boolean mayCreateChild;

    private boolean mayRename;

    private boolean mayDelete;

    private boolean maySubmit;

    public boolean isMayReadItems() {
        return mayReadItems;
    }

    public boolean isMayAddItems() {
        return mayAddItems;
    }

    public boolean isMayRemoveItems() {
        return mayRemoveItems;
    }

    public boolean isMaySetSeen() {
        return maySetSeen;
    }

    public boolean isMaySetKeywords() {
        return maySetKeywords;
    }

    public boolean isMayCreateChild() {
        return mayCreateChild;
    }

    public boolean isMayRename() {
        return mayRename;
    }

    public boolean isMayDelete() {
        return mayDelete;
    }

    public boolean isMaySubmit() {
        return maySubmit;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("mayReadItems", mayReadItems)
                .add("mayAddItems", mayAddItems)
                .add("mayRemoveItems", mayRemoveItems)
                .add("maySetSeen", maySetSeen)
                .add("maySetKeywords", maySetKeywords)
                .add("mayCreateChild", mayCreateChild)
                .add("mayRename", mayRename)
                .add("mayDelete", mayDelete)
                .add("maySubmit", maySubmit)
                .toString();
    }
}
