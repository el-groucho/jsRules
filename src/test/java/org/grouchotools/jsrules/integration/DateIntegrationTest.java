package org.grouchotools.jsrules.integration;

import org.grouchotools.jsrules.JsRules;
import org.grouchotools.jsrules.Rule;
import org.grouchotools.jsrules.RuleExecutor;
import org.grouchotools.jsrules.impl.RuleExecutorImpl;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Created by Paul Richardson 5/20/2015
 */
public class DateIntegrationTest {

    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    private JsRules jsRules;

    @Before
    public void beforeEach() {
        jsRules = new JsRules();
    }

    @Test
    @SuppressWarnings("unchecked")
    public void betweenDatesTest() throws Exception {
        Rule rule = jsRules.loadRuleByName("BetweenDatesRule");

        RuleExecutor ruleExecutor = new RuleExecutorImpl(rule);

        DateTime dateTime = DateTime.parse("2015-05-20");

        assertEquals(true, ruleExecutor.execute(dateTime));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void betweenDatesFalseTest() throws Exception {
        Rule rule = jsRules.loadRuleByName("BetweenDatesRule");

        RuleExecutor ruleExecutor = new RuleExecutorImpl(rule);

        DateTime dateTime = DateTime.parse("2014-05-20");

        assertNull(ruleExecutor.execute(dateTime));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void beforeDateTest() throws Exception {
        Rule rule = jsRules.loadRuleByName("BeforeDateRule");

        RuleExecutor ruleExecutor = new RuleExecutorImpl(rule);

        DateTime dateTime = DateTime.parse("2014-05-20");

        assertEquals(true, ruleExecutor.execute(dateTime));
    }

    @Test
    @SuppressWarnings("unchecked")
    public void beforeDateFalseTest() throws Exception {
        Rule rule = jsRules.loadRuleByName("BeforeDateRule");

        RuleExecutor ruleExecutor = new RuleExecutorImpl(rule);

        DateTime dateTime = DateTime.parse("2015-05-20");

        assertNull(ruleExecutor.execute(dateTime));
    }
}
