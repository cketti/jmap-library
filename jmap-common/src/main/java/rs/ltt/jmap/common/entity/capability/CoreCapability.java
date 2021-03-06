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

@JmapCapability(namespace = Namespace.CORE)
public class CoreCapability implements Capability {

    private int maxSizeUpload;
    private int maxConcurrentUpload;
    private int maxCallsInRequest;
    private int maxObjectsInGet;
    private int maxObjectsInSet;
    private String[] collationAlgorithms;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("maxSizeUpload", maxSizeUpload)
                .add("maxConcurrentUpload", maxConcurrentUpload)
                .add("maxCallsInRequest", maxCallsInRequest)
                .add("maxObjectsInGet", maxObjectsInGet)
                .add("maxObjectsinSet", maxObjectsInSet)
                .add("collationAlgorithms", collationAlgorithms)
                .toString();
    }

    public int getMaxSizeUpload() {
        return maxSizeUpload;
    }

    public int getMaxConcurrentUpload() {
        return maxConcurrentUpload;
    }

    public int getMaxCallsInRequest() {
        return maxCallsInRequest;
    }

    public int getMaxObjectsInGet() {
        return maxObjectsInGet;
    }

    public int getMaxObjectsInSet() {
        return maxObjectsInSet;
    }

    public String[] getCollationAlgorithms() {
        return collationAlgorithms;
    }
}
