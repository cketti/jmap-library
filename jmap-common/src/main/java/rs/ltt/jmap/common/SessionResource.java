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

package rs.ltt.jmap.common;

import com.google.common.base.MoreObjects;
import rs.ltt.jmap.common.entity.Account;
import rs.ltt.jmap.common.entity.Capability;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collection;
import java.util.Map;

public class SessionResource {

    private String username;
    private String apiUrl;
    private String downloadUrl;
    private String uploadUrl;
    private String eventSourceUrl;
    private Map<String, Account> accounts;
    private Map<Class<?extends Capability>, Capability> capabilities;
    private String state;

    public String getUsername() {
        return username;
    }

    public URL getApiUrl(final URL base) {
        try {
            return new URL(base, apiUrl);
        } catch (MalformedURLException e) {
            return base;
        }
    }

    public String getApiUrl() {
        return this.apiUrl;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public String getUploadUrl() {
        return uploadUrl;
    }

    public String getEventSourceUrl() {
        return eventSourceUrl;
    }

    public Map<String, Account> getAccounts() {
        return accounts;
    }

    public <T extends Capability> T getCapability(Class<T> clazz) {
        return clazz.cast(capabilities.get(clazz));
    }

    public Collection<Capability> getCapabilities() {
        return capabilities.values();
    }

    public String getState() {
        return state;
    }

    @Override
    public String toString() {
        return MoreObjects.toStringHelper(this)
                .add("username", username)
                .add("apiUrl", apiUrl)
                .add("downloadUrl", downloadUrl)
                .add("uploadUrl", uploadUrl)
                .add("eventSourceUrl", eventSourceUrl)
                .add("accounts", accounts)
                .add("capabilities", capabilities)
                .add("state", state)
                .toString();
    }
}
