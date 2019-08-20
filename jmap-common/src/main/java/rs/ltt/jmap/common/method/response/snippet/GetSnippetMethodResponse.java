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

package rs.ltt.jmap.common.method.response.snippet;

import com.google.common.base.MoreObjects;
import lombok.Getter;
import rs.ltt.jmap.annotation.JmapMethod;
import rs.ltt.jmap.common.entity.SearchSnippet;
import rs.ltt.jmap.common.method.MethodResponse;

@JmapMethod("SearchSnippet/get")
@Getter
public class GetSnippetMethodResponse implements MethodResponse {

    protected String accountId;

    protected String state;

    protected String[] notFound;

    protected SearchSnippet[] list;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("accountId", accountId)
                .add("state", state)
                .add("notFound", notFound)
                .add("list", list)
                .toString();
    }
}
