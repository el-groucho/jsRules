package org.grouchotools.jsrules.integration;

import org.grouchotools.jsrules.JsRules;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This integration test executes a simple "first true" ruleset list
 * <p/>
 * There are two rulesets in the list:
 * <p/>
 * 1. Evaluate that the current inventory at that store is greater than zero.
 * 2. Evaluate that the customer can afford something in that store
 * <p/>
 * If the first ruleset returns true, the response will be "Item is in stock"
 * If the first ruleset is false but the second ruleset returns true, the response will be "Item is in budget"
 * <p/>
 * Stores with the item in stock: 1, 2, 3, 5, 7
 * Trouser cost: $200.00 / Shirt cost: $100.00
 * <p/>
 * Files:
 * FirstTrueIntegrationRulesetList.json
 * InventoryRuleset.json
 * InBudgetRuleset.json
 * <p/>
 * Created by Paul Richardson 5/16/2015
 */
public class FirstTrueRulesetListIntegrationTest {
    private final String itemInStock = "Item is in stock";

    private JsRules jsRules;

    Map<String, Object> parameters;

    @Before
    public void beforeEach() {
        jsRules = new JsRules();
        parameters = new HashMap<>();
    }

    /**
     * In this test, the customer has $250.00 and is shopping in store 3
     * <p/>
     * With $250.00, the customer can afford trousers, and store 3 carries that item and has 8 in inventory, so it
     * should return the "Item is in stock" message because that rule is evaluated first
     */
    @Test
    public void AllRulesetsTrueTest() throws Exception {
        parameters.put("budget", 250.00);
        parameters.put("store", 3l);
        parameters.put("inventory", 8l);

        assertEquals(itemInStock, jsRules.executeRuleset("FirstTrueIntegrationRulesetList", parameters));
    }

    /**
     * In this test, the customer is still shopping at store 3 but only has $50.00
     * <p/>
     * However, the because the the inventory rule is evaluated first, the "Item is in stock" message should return
     */
    @Test
    public void SecondRulesetFalseTest() throws Exception {
        parameters.put("budget", 50.00);
        parameters.put("store", 3l);
        parameters.put("inventory", 8l);

        assertEquals(itemInStock, jsRules.executeRuleset("FirstTrueIntegrationRulesetList", parameters));
    }

    /**
     * In this test, the customer has $150.00 but is shopping at store 4.
     * <p/>
     * The customer can afford a shirt, so the "Shirt" message should appear even though Store 4 does not
     * stock the item
     */
    @Test
    public void FirstRulesetFalseTest() throws Exception {
        String shirt = "Shirt";

        parameters.put("budget", 150.00);
        parameters.put("store", 4l);
        parameters.put("inventory", 8l);

        assertEquals(shirt, jsRules.executeRuleset("FirstTrueIntegrationRulesetList", parameters));
    }

    /**
     * In this test, the customer has $50.00 and is shopping at store 5 which has zero inventory.
     * <p/>
     * Because none of the rulesets are true, the rulesetList response will be null (false)
     */
    @Test
    public void AllRulesetsFalseTest() throws Exception {
        parameters.put("budget", 50.00);
        parameters.put("store", 5l);
        parameters.put("inventory", 0l);

        assertNull(jsRules.executeRuleset("FirstTrueIntegrationRulesetList", parameters));
    }
}
