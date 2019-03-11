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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import rs.ltt.jmap.client.api.EndpointNotFoundException;
import rs.ltt.jmap.client.api.UnauthorizedException;
import rs.ltt.jmap.client.http.HttpAuthentication;
import rs.ltt.jmap.client.util.WellKnownUtil;
import rs.ltt.jmap.common.SessionResource;
import rs.ltt.jmap.gson.JmapAdapters;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SessionClient {

    private final URL sessionResource;
    private HttpAuthentication httpAuthentication;
    private SessionCache sessionCache;
    private Session currentSession = null;

    public SessionClient(HttpAuthentication authentication) {
        this.sessionResource = null;
        this.httpAuthentication = authentication;
    }

    public SessionClient(HttpAuthentication authentication, URL sessionResource) {
        this.sessionResource = sessionResource;
        this.httpAuthentication = authentication;
    }

    public Session get() throws Exception {
        final Session existingSession = this.currentSession;
        if (existingSession != null) {
            return existingSession;
        }
        synchronized (this) {
            if (currentSession != null) {
                return currentSession;
            }
            final String username = httpAuthentication.getUsername();
            final URL resource;
            if (sessionResource != null) {
                resource = sessionResource;
            } else {
                resource = WellKnownUtil.fromUsername(username);
            }

            final SessionCache cache = sessionCache;
            Session session = cache != null ? cache.load(username, resource) : null;

            if (session == null) {
                session = fetchSession(resource, 3);
                if (cache != null) {
                    cache.store(username, resource, session);
                }
            }

            currentSession = session;

        }
        return currentSession;
    }

    public void setSessionCache(SessionCache sessionCache) {
        this.sessionCache = sessionCache;
    }

    private Session fetchSession(URL base, int remainingRedirects) throws Exception {
        final HttpURLConnection connection = (HttpURLConnection) base.openConnection();
        connection.setRequestMethod("GET");
        connection.setInstanceFollowRedirects(false);
        httpAuthentication.authenticate(connection);
        connection.connect();
        final int code = connection.getResponseCode();
        if (code == 200 || code == 201) {
            InputStream inputStream = connection.getInputStream();
            GsonBuilder builder = new GsonBuilder();
            JmapAdapters.register(builder);
            Gson gson = builder.create();
            final SessionResource sessionResource = gson.fromJson(new InputStreamReader(inputStream), SessionResource.class);
            return new Session(base, sessionResource);
        } else if (code == 301 || code == 302) {
            if (remainingRedirects > 0) {
                final String location = connection.getHeaderField("Location");
                if (location == null) {
                    throw new IOException("Unable to parse redirect location while resolving base url");
                }
                URL redirectTo = new URL(base, location);
                return fetchSession(redirectTo, remainingRedirects - 1);
            } else {
                throw new IOException("Too many redirects");
            }
        } else if (code == 401) {
            throw new UnauthorizedException(String.format("Session object(%s) was unauthorized", connection.getURL()));
        } else {
            throw new EndpointNotFoundException(String.format("Unable to fetch session object(%s)", connection.getURL()));
        }
    }

}
