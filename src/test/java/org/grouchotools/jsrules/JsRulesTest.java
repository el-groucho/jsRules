package org.grouchotools.jsrules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.config.ResponseConfig;
import org.grouchotools.jsrules.config.RulesetConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.junit.Before;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRulesTest {
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    private JsRules jsRules;
    private ObjectMapper objectMapper = new ObjectMapper();

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

    @Test
    public void testLoadRulesetJson() throws Exception {
        String rulesetName = "mockRuleset";

        RulesetConfig rulesetConfig = new RulesetConfig(rulesetName, "ALLTRUE", new ResponseConfig("true", "Boolean"),
                new ArrayList<String>());

        String json = objectMapper.writeValueAsString(rulesetConfig);

        RulesetExecutor rulesetExecutor = jsRules.loadRulesetByJson(json);

        assertEquals(rulesetName, rulesetExecutor.getName());
    }

    @Test
    public void testLoadRulesetJsonInvalid() throws Exception {
        exception.expect(InvalidConfigException.class);

        jsRules.loadRulesetByJson("{ bad json");
    }

    @Test
    public void testLoadRulesetByName() throws Exception {
        String rulesetName = "MockRuleset";

        RulesetExecutor ruleSet = jsRules.loadRulesetByName(rulesetName);

        assertEquals(rulesetName, ruleSet.getName());
    }

    @Test
    public void testLoadRulesetByNameFileMissing() throws Exception {
        exception.expect(InvalidConfigException.class);

        String rulesetName = "BogusRuleName";

        jsRules.loadRulesetByName(rulesetName);
    }

    @Test
    public void testLoadRulesetByNameIOError() throws Exception {
        exception.expect(InvalidConfigException.class);

        String rulesetName = "EmptyFile";

        jsRules.loadRulesetByName(rulesetName);
    }

    @Test
    public void testExecuteRulesetByName() throws Exception {
        Map<String, Object> parameters = new HashMap<>();

        parameters.put("left", 15l);

        assertNotNull(jsRules.executeRuleset("MockRuleset", parameters));
    }
}
