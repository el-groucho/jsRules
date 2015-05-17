package org.grouchotools.jsrules.integration;

import org.grouchotools.jsrules.JsRules;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This integration test executes a simple ruleset that evaluates which inventory item fits in the customer's budget.
 * <p/>
 * There are two rules in the set:
 * <p/>
 * 1. Evaluate that the customer can afford trousers.
 * 2. Evaluate that the customer can afford a shirt.
 * <p/>
 * If the first rule evaluates as true, the ruleset will return "Trousers".
 * If the second rule evalutes as true, the ruleset will return "Shirt".
 * <p/>
 * Trousers are $200.00. Shirts are $100.00.
 * <p/>
 * Files:
 * InBudgetRuleset.json
 * TrousersInBudget.json
 * ShirtInBudget.json
 * <p/>
 * Created by Paul Richardson 5/16/2015
 */
public class FirstTrueIntegrationTest {
    private final String trousers = "Trousers";
    private final String shirt = "Shirt";

    private JsRules jsRules = new JsRules();

    Map<String, Object> parameters;

    @Before
    public void beforeEach() {
        parameters = new HashMap<>();
    }

    /**
     * This test looks to see what the customer can afford with $250.00.
     * <p/>
     * Trousers are $200.00, so it should return "Trousers".
     */
    @Test
    public void FirstRuleTrueTest() throws Exception {
        parameters.put("budget", 250.00);

        assertEquals(trousers, jsRules.executeRuleset("InBudgetRuleset", parameters));
    }

    /**
     * This test looks to see what the customer can afford with $150.00.
     * <p/>
     * A shirt is $100.00, so it should return "Shirt".
     */
    @Test
    public void SecondRuleTrueTest() throws Exception {
        parameters.put("budget", 150.00);

        assertEquals(shirt, jsRules.executeRuleset("InBudgetRuleset", parameters));
    }

    /**
     * This test looks to see what the customer can afford with $50.00.
     * <p/>
     * Sadly, the customer can't afford anything so the rulset should return null.
     */
    @Test
    public void NoRulesTrueTest() throws Exception {
        parameters.put("budget", 50.00);

        assertNull(jsRules.executeRuleset("InBudgetRuleset", parameters));
    }
}
