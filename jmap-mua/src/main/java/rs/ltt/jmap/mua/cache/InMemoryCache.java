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

package rs.ltt.jmap.mua.cache;

import com.google.common.collect.ImmutableList;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rs.ltt.jmap.common.entity.*;
import rs.ltt.jmap.common.entity.Thread;
import rs.ltt.jmap.mua.entity.QueryResultItem;

import java.lang.reflect.Field;
import java.util.*;

public class InMemoryCache implements Cache {

    private static final Logger LOGGER = LoggerFactory.getLogger(InMemoryCache.class);

    protected final HashMap<String, Mailbox> mailboxes = new HashMap<>();
    protected final HashMap<String, Thread> threads = new HashMap<>();
    protected final HashMap<String, Email> emails = new HashMap<>();
    protected final HashMap<String, Identity> identities = new HashMap<>();
    protected final HashMap<String, QueryResult> queryResults = new HashMap<>();
    private String mailboxState = null;
    private String threadState = null;
    private String emailState = null;
    private String identityState = null;

    @Override
    public String getIdentityState(String username) {
        return identityState;
    }

    @Override
    public String getMailboxState(String account) {
        return mailboxState;
    }


    @Override
    public QueryState getQueryState(String username, String query) {
        synchronized (this.queryResults) {
            final String mailboxState = this.mailboxState;
            final String threadState = this.threadState;
            final String emailState = this.emailState;
            final QueryResult queryResult = queryResults.get(query);
            return new QueryState(queryResult == null ? null : queryResult.queryState, new ObjectsState(mailboxState, threadState, emailState));
        }
    }

    @NonNullDecl
    @Override
    public ObjectsState getObjectsState(String username) {
        return new ObjectsState(mailboxState, threadState, emailState);
    }

    @Override
    public void setMailboxes(String account, String state, Mailbox[] mailboxes) {
        synchronized (this.mailboxes) {
            this.mailboxes.clear();
            for (Mailbox mailbox : mailboxes) {
                this.mailboxes.put(mailbox.getId(), mailbox);
            }
            this.mailboxState = state;
        }

    }

    @Override
    public void updateMailboxes(String account, Update<Mailbox> mailboxUpdate, final String[] updatedProperties) throws CacheWriteException {
        synchronized (this.mailboxes) {
            for (Mailbox mailbox : mailboxUpdate.getCreated()) {
                this.mailboxes.put(mailbox.getId(), mailbox);
            }
            for (Mailbox mailbox : mailboxUpdate.getUpdated()) {
                Mailbox target = mailboxes.get(mailbox.getId());
                if (target == null) {
                    throw new CacheWriteException(String.format("Unable to update Mailbox(%s). Can not find in cache", mailbox.getId()));
                }
                if (updatedProperties != null) {
                    for (String property : updatedProperties) { //can be null
                        try {
                            copyProperty(target, mailbox, property, Mailbox.class);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new CacheWriteException(String.format("Unable to update Mailbox(%s). Can not update field %s", mailbox.getId(), property), e);
                        }
                    }
                } else {
                    this.mailboxes.put(mailbox.getId(), mailbox);
                }
            }
            for (String id : mailboxUpdate.getDestroyed()) {
                this.mailboxes.remove(id);
            }
            this.mailboxState = mailboxUpdate.getNewState();
        }
    }

    @Override
    public Collection<Mailbox> getMailboxes(String account) throws NotSynchronizedException {
        synchronized (this.mailboxes) {
            if (this.mailboxState == null) {
                throw new NotSynchronizedException("Mailboxes have not been synchronized yet. Run refresh() first.");
            }
            return this.mailboxes.values();
        }
    }

    @Override
    public void setThreads(final String account, final String state, Thread[] threads) {
        synchronized (this.threads) {
            this.threads.clear();
            for (Thread thread : threads) {
                this.threads.put(thread.getId(), thread);
            }
            this.threadState = state;
        }
    }

    @Override
    public void addThreads(String account, String state, Thread[] threads) throws CacheConflictException {
        synchronized (this.threads) {
            if (state == null || !state.equals(this.threadState)) {
                throw new CacheConflictException(String.format("Trying to add threads with an outdated state. Run update first. Cached state=%s. Your state=%s", this.threadState, state));
            }
            for (Thread thread : threads) {
                this.threads.put(thread.getId(), thread);
            }
        }
    }

    @Override
    public void updateThreads(final String account, Update<Thread> threadUpdate) throws CacheWriteException {
        synchronized (this.threads) {
            for (Thread thread : threadUpdate.getCreated()) {
                if (threads.containsKey(thread.getId())) {
                    throw new CacheWriteException(String.format("Unable to create Thread(%s). Thread already exists", thread.getId()));
                } else {
                    this.threads.put(thread.getId(), thread);
                }
            }
            for (Thread thread : threadUpdate.getUpdated()) {
                if (!this.threads.containsKey(thread.getId())) {
                    throw new CacheWriteException(String.format("Unable to update Thread(%s). Thread doesnt exists", thread.getId()));
                }
                this.threads.put(thread.getId(), thread);
            }
            for (String id : threadUpdate.getDestroyed()) {
                this.threads.remove(id);
            }
            this.threadState = threadUpdate.getNewState();
        }
    }

    @Override
    public void setEmails(String account, String state, Email[] emails) {
        synchronized (this.emails) {
            this.emails.clear();
            for (Email email : emails) {
                this.emails.put(email.getId(), email);
            }
            this.emailState = state;
        }
    }

    @Override
    public void addEmails(String account, String state, Email[] emails) throws CacheConflictException {
        synchronized (this.emails) {
            if (state == null || !state.equals(this.emailState)) {
                throw new CacheConflictException(String.format("Trying to add threads with an outdated state. Run update first. Cached state=%s. Your state=%s", this.threadState, state));
            }
            for (Email email : emails) {
                this.emails.put(email.getId(), email);
            }
        }
    }

    @Override
    public void updateEmails(String account, Update<Email> emailUpdate, String[] updatedProperties) throws CacheWriteException {
        synchronized (this.emails) {
            for (Email email : emailUpdate.getCreated()) {
                this.emails.put(email.getId(), email);
            }
            for (Email email : emailUpdate.getUpdated()) {
                Email target = emails.get(email.getId());
                if (target == null) {
                    throw new CacheWriteException(String.format("Unable to update Email(%s). Can not find in cache", email.getId()));
                }
                for (String property : updatedProperties) {
                    try {
                        copyProperty(target, email, property, Email.class);
                    } catch (NoSuchFieldException | IllegalAccessException e) {
                        throw new CacheWriteException(String.format("Unable to update Mailbox(%s). Can not update field %s", email.getId(), property), e);
                    }
                }
            }
            for (String id : emailUpdate.getDestroyed()) {
                this.emails.remove(id);
            }
            this.emailState = emailUpdate.getNewState();
        }
    }

    @Override
    public void setIdentities(final String account, final String state, final Identity[] identities) {
        synchronized (this.identities) {
            this.identities.clear();
            for(Identity identity : identities) {
                this.identities.put(identity.getId(), identity);
            }
            System.err.println("setting identity state to="+state);
            this.identityState = state;
        }

    }

    @Override
    public void updateIdentities(String account, Update<Identity> identityUpdate) throws CacheWriteException {
         synchronized (this.identities) {
            for (Identity identity : identityUpdate.getCreated()) {
                if (this.identities.containsKey(identity.getId())) {
                    throw new CacheWriteException(String.format("Unable to create Identity(%s). Identity already exists", identity.getId()));
                } else {
                    this.identities.put(identity.getId(), identity);
                }
            }
            for (Identity identity : identityUpdate.getUpdated()) {
                if (!this.identities.containsKey(identity.getId())) {
                    throw new CacheWriteException(String.format("Unable to update Identity(%s). Identity doesnt exists", identity.getId()));
                }
                this.identities.put(identity.getId(), identity);
            }
            for (String id : identityUpdate.getDestroyed()) {
                this.identities.remove(id);
            }
            this.identityState = identityUpdate.getNewState();
        }
    }

    @Override
    public void setQueryResult(String account, String query, String queryState, QueryResultItem[] items) {
        synchronized (this.queryResults) {
            this.queryResults.put(query, new QueryResult(queryState, items));
        }
    }

    @Override
    public void updateQueryResults(String account, String query, QueryUpdate<Email, QueryResultItem> update) throws CacheWriteException, CacheConflictException {
        synchronized (this.queryResults) {
            final QueryResult queryResult = this.queryResults.get(query);
            if (queryResult == null) {
                throw new CacheWriteException("Unable to update query. Can not find cached version");
            }
            if (update.getOldState() == null || !update.getOldState().equals(queryResult.queryState)) {
                throw new CacheConflictException(String.format("OldState (%s) did not match our expectation ",update.getOldState()));
            }
            for (String removed : update.getRemoved()) {
                LOGGER.info("no removing id "+removed);
                queryResult.remove(removed);
            }
            for (AddedItem<QueryResultItem> addedItem : update.getAdded()) {
                //TODO it is probably save to just not add an item that exceeds the range (position > length) but this indicates a broken uper layer
                LOGGER.info("now adding "+addedItem.getItem().getEmailId()+" on index "+addedItem.getIndex());
                queryResult.items.add(addedItem.getIndex(), addedItem.getItem());
            }
            queryResult.queryState = update.getNewState();
        }
    }

    @Override
    public Missing getMissingThreadIds(String account, final String query) throws CacheReadException {
        final List<String> threadIds = new ArrayList<>();
        synchronized (this.queryResults) {
            final QueryResult queryResult = this.queryResults.get(query);
            if (queryResult == null) {
                throw new CacheReadException("Unable to find cached version");
            }
            for (QueryResultItem item : queryResult.items) {
                threadIds.add(item.getThreadId());
            }
        }
        synchronized (this.threads) {
            Iterator<String> iterator = threadIds.iterator();
            while (iterator.hasNext()) {
                if (this.threads.containsKey(iterator.next())) {
                    iterator.remove();
                }
            }
            return new Missing(query, this.threadState, threadIds.toArray(new String[0]));
        }
    }

    @Override
    public Missing getMissingEmailIds(String account, String query) throws CacheReadException {
        final List<String> threadIds = new ArrayList<>();
        synchronized (this.queryResults) {
            final QueryResult queryResult = this.queryResults.get(query);
            if (queryResult == null) {
                throw new CacheReadException("Unable to find cached version");
            }
            for (QueryResultItem item : queryResult.items) {
                threadIds.add(item.getThreadId());
            }
        }
        final List<String> emailIds = new ArrayList<>();
        synchronized (this.threads) {
            for(String threadId : threadIds) {
                final Thread thread = this.threads.get(threadId);
                if (thread == null) {
                    throw new CacheReadException("Unable to find cached version of thread "+threadId);
                }
                emailIds.addAll(thread.getEmailIds());
            }
        }
        synchronized (this.emails) {
            Iterator<String> iterator = emailIds.iterator();
            while (iterator.hasNext()) {
                if (this.emails.containsKey(iterator.next())) {
                    iterator.remove();
                }
            }
            return new Missing(query, this.emailState, emailIds.toArray(new String[0]));
        }
    }


    private static <T extends AbstractIdentifiableEntity> void copyProperty(T target, T source, String property, Class<T> clazz) throws NoSuchFieldException, IllegalAccessException {
        Field field = clazz.getDeclaredField(property);
        field.setAccessible(true);
        field.set(target, field.get(source));
    }

    protected static class QueryResult {

        private String queryState;
        private ArrayList<QueryResultItem> items;

        QueryResult(String queryState, QueryResultItem[] items) {
            this.queryState = queryState;
            this.items = new ArrayList<>(Arrays.asList(items));
        }

        private void remove(String emailId) {
            Iterator<QueryResultItem> iterator = items.iterator();
            while (iterator.hasNext()) {
                if (emailId.equals(iterator.next().getEmailId())) {
                    iterator.remove();
                    break;
                }
            }
        }

        public List<QueryResultItem> getItems() {
            return ImmutableList.copyOf(items);
        }
    }
}
