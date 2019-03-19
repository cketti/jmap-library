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

package rs.ltt.jmap.client.session;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rs.ltt.jmap.gson.JmapAdapters;

import java.io.*;
import java.net.URL;

public class SessionFileCache implements SessionCache {

    private final File directory;

    private final GsonBuilder gsonBuilder = new GsonBuilder();

    public SessionFileCache() {
        JmapAdapters.register(this.gsonBuilder);
        this.directory = null;
    }

    public SessionFileCache(File directory) {
        JmapAdapters.register(this.gsonBuilder);
        this.directory = directory;
    }

    @Override
    public void store(String username, URL sessionResource, Session session) {
        Gson gson = this.gsonBuilder.create();
        try {
            final String filename = getFilename(username, sessionResource);
            final File file;
            if (directory == null) {
                file = new File(filename);
            } else {
                file = new File(directory, filename);
            }
            final FileWriter fileWriter = new FileWriter(file);
            gson.toJson(session, fileWriter);
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getFilename(String username, URL sessionResource) {
        final String name = username + ':' + (sessionResource == null ? '\00' : sessionResource.toString());
        return "session-cache-"+Hashing.sha256().hashString(name, Charsets.UTF_8).toString();
    }

    @Override
    public Session load(String username, URL sessionResource) {
        Gson gson = this.gsonBuilder.create();
        try {
            return gson.fromJson(new FileReader(new File(getFilename(username, sessionResource))), Session.class);
        } catch (FileNotFoundException e) {
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
