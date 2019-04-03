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
 */

package rs.ltt.android.util;

import java.util.ArrayList;

public class EmailAddressUtil {

     public static String shorten(String input) {
        String[] parts = removeInitials(input.split("\\s"));
        if (parts.length == 0 || parts[0].length() <= 3) {
            return input;
        } else {
            return parts[0];
        }
    }

    private static String[] removeInitials(String[] input) {
        ArrayList<String> output = new ArrayList<>(input.length);
        for(String part : input) {
            if (part.length() == 1 || (part.length() == 2 && part.charAt(1) == '.')) {
                continue;
            }
            output.add(part);
        }
        return output.toArray(new String[0]);
    }

}
