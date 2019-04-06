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

package rs.ltt.jmap.mua.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

public class EmailAddressUtil {

    private static final List<String> STOP_WORDS = Arrays.asList("the");

     public static String shorten(String input) {
        String[] parts = removeInvalidShorts(input.split("\\s"));
        if (parts.length == 0) {
            return input;
        } else {
            return parts[0];
        }
    }

    private static String[] removeInvalidShorts(String[] input) {
        ArrayList<String> output = new ArrayList<>(input.length);
        for(String part : input) {
            if (isInitial(part)) {
                continue;
            }
            if (isStopWord(part)) {
                continue;
            }
            output.add(part);
        }
        return output.toArray(new String[0]);
    }

    private static boolean isInitial(String input) {
         return input.length() == 1 || (input.length() == 2 && input.charAt(1) == '.');
    }

    private static boolean isStopWord(String input) {
         return STOP_WORDS.contains(input.toLowerCase(Locale.US));
    }

}
