package org.grouchotools.jsrules.integration;

import org.grouchotools.jsrules.JsRules;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * This integration test executes a simply ruleset that evaluates whether an inventory item is in stock at a particular
 * store.
 * <p/>
 * There are two rules in the set:
 * <p/>
 * 1. Evaluate that the store is one that carries the item
 * 2. Evaluate that the current inventory of the item is greater than zero.
 * <p/>
 * If both rules evaluate to true, the ruleset will return the text "Item is in stock"
 * <p/>
 * Stores with the item in stock: 1, 2, 3, 5, 7
 * <p/>
 * Files:
 * InventoryRuleset.json
 * CarriesItem.json
 * HasInventory.json
 * <p/>
 * Created by Paul Richardson 5/16/2015
 */
public class AllTrueIntegrationTest {
    private final String success = "Item is in stock";

    private JsRules jsRules;

    Map<String, Object> parameters;

    @Before
    public void beforeEach() {
        jsRules = new JsRules();
        parameters = new HashMap<>();
    }

    /**
     * This test looks to see if the item is in stock at store 3
     * <p/>
     * Store 3 carries the item and has 8 in inventory, so it should return the success message
     */
    @Test
    public void AllRulesTrueTest() throws Exception {
        parameters.put("store", 3l);
        parameters.put("inventory", 8l);

        assertEquals(success, jsRules.executeRuleset("InventoryRuleset", parameters));
    }

    /**
     * This test looks to see if the item is in stock at store 4
     * <p/>
     * Store 4 does not carry the item, so it should return a null result (false)
     */
    @Test
    public void FirstRuleFalseTest() throws Exception {
        parameters.put("store", 4l);
        parameters.put("inventory", 3l);

        assertNull(jsRules.executeRuleset("InventoryRuleset", parameters));
    }

    /**
     * This test looks to see if the item is in stock at store 5
     * <p/>
     * Store 5 carries the item, but doesn't have any in stock, so it should return a null result (false)
     */
    @Test
    public void SecondRuleFalseTest() throws Exception {
        parameters.put("store", 5l);
        parameters.put("inventory", 0l);

        assertNull(jsRules.executeRuleset("InventoryRuleset", parameters));
    }
}
