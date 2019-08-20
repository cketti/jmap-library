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

package rs.ltt.jmap.gson.adapter;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import rs.ltt.jmap.common.Request;
import rs.ltt.jmap.common.method.MethodCall;
import rs.ltt.jmap.gson.JmapAdapters;

import java.io.IOException;

import static rs.ltt.jmap.common.util.Mapper.METHOD_CALLS;

public class RequestInvocationTypeAdapter extends TypeAdapter<Request.Invocation> {

    private static final Gson REGULAR_GSON;
    private static final Gson NULL_SERIALIZING_GSON;


    static {
        GsonBuilder regularBuilder = new GsonBuilder();
        JmapAdapters.register(regularBuilder);
        REGULAR_GSON = regularBuilder.create();
        GsonBuilder nullSerializingBuilder = new GsonBuilder();
        nullSerializingBuilder.serializeNulls();
        NULL_SERIALIZING_GSON = nullSerializingBuilder.create();
    }

    public static void register(final GsonBuilder builder) {
        builder.registerTypeAdapter(Request.Invocation.class, new RequestInvocationTypeAdapter());
    }

    @Override
    public void write(JsonWriter jsonWriter, Request.Invocation invocation) throws IOException {
        final MethodCall methodCall = invocation.getMethodCall();
        final String name = METHOD_CALLS.inverse().get(methodCall.getClass());
        jsonWriter.beginArray();
        jsonWriter.value(name);
        NULL_SERIALIZING_GSON.toJson(REGULAR_GSON.toJsonTree(methodCall), jsonWriter);
        jsonWriter.value(invocation.getId());
        jsonWriter.endArray();
    }

    @Override
    public Request.Invocation read(JsonReader jsonReader) throws IOException {
        throw new IOException("No deserialization support for Request.Invocation via Type Adapter");
    }
}
