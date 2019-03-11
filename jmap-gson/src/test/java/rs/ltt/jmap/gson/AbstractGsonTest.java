package rs.ltt.jmap.gson;

import com.google.common.io.Resources;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.nio.charset.Charset;

abstract class AbstractGsonTest {

    static <T> T parseFromResource(String filename, Class<T> clazz) throws IOException {
        GsonBuilder gsonBuilder = new GsonBuilder();
        JmapAdapters.register(gsonBuilder);
        final Gson gson = gsonBuilder.create();
        return gson.fromJson(Resources.asCharSource(Resources.getResource(filename), Charset.defaultCharset()).read(),clazz);
    }

    public String readResourceAsString(String filename) throws IOException {
        return Resources.asCharSource(Resources.getResource(filename), Charset.defaultCharset()).read().trim();
    }

}
