package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRulesTest {
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    private JsRules jsRules;

    @Before
    public void setUp() throws Exception {
        jsRules = JsRules.getInstance();
    }

    @Test
    public void testLoadJsonString() throws Exception {
        String ruleName = "mock rule";

        String json = "{" +
                "\"ruleName\": \"" + ruleName + "\"," +
                "\"leftParamConfig\": {" +
                "\"parameterName\": \"left\"," +
                "\"parameterClass\": \"Long\"" +
                "}," +
                "\"operator\": \"GT\"," +
                "\"rightParamConfig\": {" +
                "\"parameterName\": \"right\"," +
                "\"parameterClass\": \"Long\"," +
                "\"parameterStaticValue\": \"10\"" +
                "}," +
                "\"responseConfig\": {" +
                "\"response\": \"true\"," +
                "\"responseClass\": \"Boolean\"" +
                "}" +
                "}";

        System.out.println("Json: \n" + json);

        Rule rule = jsRules.loadRuleByJson(json);

        assertEquals(ruleName, rule.getRuleName());
    }

    @Test
    public void testLoadJsonInvalid() throws Exception {
        exception.expect(InvalidConfigException.class);

        jsRules.loadRuleByJson("{ bad json");
    }

    @Test
    public void testLoadRuleByName() throws Exception {
        String ruleName = "GreaterThan10";

        Rule rule = jsRules.loadRuleByName(ruleName);

        assertEquals(ruleName, rule.getRuleName());
    }

    @Test
    public void testLoadRuleByNameFileMissing() throws Exception {
        exception.expect(InvalidConfigException.class);

        String ruleName = "BogusRuleName";

        jsRules.loadRuleByName(ruleName);
    }

    @Test
    public void testLoadRuleByNameIOError() throws Exception {
        exception.expect(InvalidConfigException.class);

        String ruleName = "EmptyFile";

        jsRules.loadRuleByName(ruleName);
    }
}
