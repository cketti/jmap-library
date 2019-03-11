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
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class Identity  extends AbstractIdentifiableEntity {
    private String name;

    private String email;

    private List<EmailAddress> replyTo;

    private List<EmailAddress> bcc;

    private String textSignature;

    private String htmlSignature;

    private Boolean mayDelete;

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("name", name)
                .add("email", email)
                .add("replyTo", replyTo)
                .add("bcc", bcc)
                .add("textSignature", textSignature)
                .add("htmlSignature", htmlSignature)
                .add("mayDelete", mayDelete)
                .add("id", id)
                .toString();
    }
}
