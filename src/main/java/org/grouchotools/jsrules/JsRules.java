package org.grouchotools.jsrules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.config.RuleConfig;
import org.grouchotools.jsrules.config.RulesetConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.exception.JsRulesException;
import org.grouchotools.jsrules.loader.RuleLoader;
import org.grouchotools.jsrules.loader.RulesetLoader;
import org.grouchotools.jsrules.loader.impl.RuleLoaderImpl;
import org.grouchotools.jsrules.loader.impl.RulesetLoaderImpl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRules {

    private static final JsRules INSTANCE = new JsRules();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final RuleLoader RULE_LOADER = new RuleLoaderImpl();
    private static final RulesetLoader RULESET_LOADER = new RulesetLoaderImpl();

    public static JsRules getInstance() {
        return INSTANCE;
    }

    public Rule loadRuleByJson(String json) throws InvalidConfigException {
        try {
            RuleConfig ruleConfig = OBJECT_MAPPER.readValue(json, RuleConfig.class);
            return RULE_LOADER.load(ruleConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse json: " + json, ex);
        }
    }

    public Rule loadRuleByName(String ruleName) throws InvalidConfigException {
        String fileName = ruleName + ".json";

        InputStream stream = ClassLoader.class.getResourceAsStream("/" + fileName);

        if (stream == null) {
            throw new InvalidConfigException("Unable to find rule file: " + fileName);
        }

        try {
            RuleConfig ruleConfig = OBJECT_MAPPER.readValue(stream, RuleConfig.class);
            return RULE_LOADER.load(ruleConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse rule file: " + ruleName, ex);
        }
    }

    public RulesetExecutor loadRulesetByJson(String json) throws InvalidConfigException {
        try {
            RulesetConfig rulesetConfig = OBJECT_MAPPER.readValue(json, RulesetConfig.class);
            return RULESET_LOADER.load(rulesetConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse json: " + json, ex);
        }
    }

    public RulesetExecutor loadRulesetByName(String ruleName) throws InvalidConfigException {
        String fileName = ruleName + ".json";

        InputStream stream = ClassLoader.class.getResourceAsStream("/" + fileName);

        if (stream == null) {
            throw new InvalidConfigException("Unable to find ruleset file: " + fileName);
        }

        try {
            RulesetConfig rulesetConfig = OBJECT_MAPPER.readValue(stream, RulesetConfig.class);
            return RULESET_LOADER.load(rulesetConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse ruleset file: " + ruleName, ex);
        }
    }

    @SuppressWarnings("unchecked")
    public <T> T executeRuleset(String ruleName, Map<String, Object> parameters) throws JsRulesException {
        RulesetExecutor<T> executor = loadRulesetByName(ruleName);

        return executor.execute(parameters);
    }
}
