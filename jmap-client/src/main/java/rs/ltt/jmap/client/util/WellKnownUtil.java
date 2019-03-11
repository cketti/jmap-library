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

package rs.ltt.jmap.client.util;

import java.net.MalformedURLException;
import java.net.URL;

public final class WellKnownUtil {

    private WellKnownUtil() {

    }

    public static URL fromUsername(String username) throws MalformedUsernameException {
        int index = username.lastIndexOf("@");
        if (index == -1) {
            throw new MalformedUsernameException("Username has no domain part");
        }
        final String domain = username.substring(index + 1);
        if (domain.isEmpty()) {
            throw new MalformedUsernameException("Domain part was empty");
        }
        try {
            return new URL("https://" + domain + "/.well-known/jmap");
        } catch (MalformedURLException e) {
            throw new MalformedUsernameException(e);
        }
    }

    public static class MalformedUsernameException extends Exception {
        public MalformedUsernameException(Exception e) {
            super(e);
        }
        public MalformedUsernameException(String message) {
            super(message);
        }
    }

}
