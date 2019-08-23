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
import rs.ltt.jmap.common.method.MethodCall;

import java.util.Map;

public abstract class CopyMethodCall<T extends AbstractIdentifiableEntity> implements MethodCall {

    private String fromAccountId;

    private String ifFromInState;

    private String accountId;

    private String ifInState;

    private Map<String, T> create;

    private Boolean onSuccessDestroyOriginal;

    private String destroyFromIfInState;

    protected CopyMethodCall(String fromAccountId, String accountId, Map<String, T> create) {
        this.fromAccountId = fromAccountId;
        this.accountId = accountId;
        this.create = create;
    }

    protected CopyMethodCall(String fromAccountId, String ifFromInState, String accountId, String ifInState, Map<String, T> create, Boolean onSuccessDestroyOriginal, String destroyFromIfInState) {
        this.fromAccountId = fromAccountId;
        this.ifFromInState = ifFromInState;
        this.accountId = accountId;
        this.ifInState = ifInState;
        this.create = create;
        this.onSuccessDestroyOriginal = onSuccessDestroyOriginal;
        this.destroyFromIfInState = destroyFromIfInState;
    }

}
