package rs.ltt.jmap.common;

import org.junit.Assert;
import org.junit.Test;
import rs.ltt.jmap.common.entity.Email;
import rs.ltt.jmap.common.entity.filter.EmailFilterCondition;
import rs.ltt.jmap.common.entity.filter.Filter;
import rs.ltt.jmap.common.entity.filter.FilterOperator;

public class QueryStringTest {

    @Test
    public void complexEmailFilterQueryString() {
        Filter<Email> emailFilter1 = FilterOperator.and(
                EmailFilterCondition.builder().text("one").build(),
                FilterOperator.not(EmailFilterCondition.builder().text("three").build()),
                EmailFilterCondition.builder().text("two").build()
        );
        Filter<Email> emailFilter2 = FilterOperator.and(
                EmailFilterCondition.builder().text("two").build(),
                FilterOperator.not(EmailFilterCondition.builder().text("three").build()),
                EmailFilterCondition.builder().text("one").build()
        );
        Filter<Email> emailFilter3 = FilterOperator.and(
                EmailFilterCondition.builder().text("two").build(),
                EmailFilterCondition.builder().text("one").build(),
                FilterOperator.not(EmailFilterCondition.builder().text("three").build())
        );
        Assert.assertEquals(emailFilter1.toQueryString(), emailFilter2.toQueryString());
        Assert.assertEquals(emailFilter2.toQueryString(), emailFilter3.toQueryString());
    }

    @Test
    public void inMailboxOtherThan() {
        Filter<Email> a = EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"spam", "trash"}).build();
        Filter<Email> b = EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"trash", "spam"}).build();
        Assert.assertEquals(a.toQueryString(), b.toQueryString());
    }

    @Test
    public void inMailboxOtherThanOr() {
        Filter<Email> a = FilterOperator.or(
                EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"spam", "trash"}).build(),
                EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"inbox"}).build()
        );
        Filter<Email> b = FilterOperator.or(
                EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"inbox"}).build(),
                EmailFilterCondition.builder().inMailboxOtherThan(new String[]{"trash", "spam"}).build()
        );
        Assert.assertEquals(a.toQueryString(), b.toQueryString());
    }
}
