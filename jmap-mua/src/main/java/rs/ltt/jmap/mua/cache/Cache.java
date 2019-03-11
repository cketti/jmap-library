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

import org.checkerframework.checker.nullness.compatqual.NonNullDecl;
import org.checkerframework.checker.nullness.compatqual.NullableDecl;
import rs.ltt.jmap.common.entity.Email;
import rs.ltt.jmap.common.entity.Identity;
import rs.ltt.jmap.common.entity.Mailbox;
import rs.ltt.jmap.common.entity.Thread;
import rs.ltt.jmap.mua.entity.QueryResultItem;

import java.util.Collection;

public interface Cache {

    //states
    String getIdentityState(String username);

    String getMailboxState(String username);

    @NonNullDecl QueryState getQueryState(String username, @NullableDecl String query);

    @NonNullDecl ObjectsState getObjectsState(String username);

    void setMailboxes(String account, String state, Mailbox[] mailboxes);

    void updateMailboxes(String account, Update<Mailbox> mailboxUpdate, String[] updatedProperties) throws CacheWriteException;

    Collection<Mailbox> getMailboxes(String account) throws NotSynchronizedException;

    //Threads
    //we probably don’t need a getThreads() method since we always access using the Queries
    void setThreads(String account, String state, Thread[] threads);

    void addThreads(String account, String state, Thread[] threads) throws CacheConflictException;

    void updateThreads(String account, Update<Thread> threadUpdate) throws CacheWriteException;

    //Email
    //we probably don’t need a getEmail() method since we always access using the Queries
    void setEmails(String account, String state, Email[] emails);

    void addEmails(String account, String state, Email[] emails) throws CacheConflictException;

    void updateEmails(String account, Update<Email> emailUpdate, String[] updatedProperties) throws CacheWriteException;


    //Identity
    void setIdentities(String account, String state, Identity[] identities);


    void updateIdentities(String account, Update<Identity> identityUpdate) throws CacheWriteException;

    //Queries

    void setQueryResult(String account, String query, String queryState, QueryResultItem[] items);

    void updateQueryResults(String account, String query, QueryUpdate<Email,QueryResultItem> update) throws CacheWriteException, CacheConflictException;

    Missing getMissingThreadIds(String account, String query) throws CacheReadException;

    Missing getMissingEmailIds(String account, String query) throws CacheReadException;


}
