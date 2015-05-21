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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRules {

    private static final JsRules INSTANCE = new JsRules();

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RuleLoader ruleLoader = new RuleLoaderImpl();
    private final RulesetLoader rulesetLoader = new RulesetLoaderImpl(this);

    // these hash maps provide rudimentory caching
    private final Map<String, Rule> ruleMap = new HashMap<>();
    private final Map<String, RulesetExecutor> rulesetExecutorMap = new HashMap<>();

    public static JsRules getInstance() {
        return INSTANCE;
    }

    public Rule loadRuleByJson(String json) throws InvalidConfigException {
        try {
            RuleConfig ruleConfig = objectMapper.readValue(json, RuleConfig.class);
            return getRule(ruleConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse json: " + json, ex);
        }
    }

    public Rule loadRuleByName(String ruleName) throws InvalidConfigException {
        Rule rule = ruleMap.get(ruleName);

        if (rule == null) {
            String fileName = ruleName + ".json";

            InputStream stream = getFileFromClasspath(fileName);

            if (stream == null) {
                throw new InvalidConfigException("Unable to find rule file: " + fileName);
            }

            try {
                RuleConfig ruleConfig = objectMapper.readValue(stream, RuleConfig.class);
                rule = getRule(ruleConfig);
            } catch (IOException ex) {
                throw new InvalidConfigException("Unable to parse rule file: " + ruleName, ex);
            }
        }

        return rule;
    }

    public RulesetExecutor loadRulesetByJson(String json) throws InvalidConfigException {
        try {
            RulesetConfig rulesetConfig = objectMapper.readValue(json, RulesetConfig.class);
            return getRulesetExecutor(rulesetConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse json: " + json, ex);
        }
    }

    public RulesetExecutor loadRulesetByName(String rulesetName) throws InvalidConfigException {
        RulesetExecutor ruleset = rulesetExecutorMap.get(rulesetName);

        if (ruleset == null) {
            String fileName = rulesetName + ".json";

            InputStream stream = getFileFromClasspath(fileName);

            if (stream == null) {
                throw new InvalidConfigException("Unable to find ruleset file: " + fileName);
            }

            try {
                RulesetConfig rulesetConfig = objectMapper.readValue(stream, RulesetConfig.class);
                ruleset = getRulesetExecutor(rulesetConfig);
            } catch (IOException ex) {
                throw new InvalidConfigException("Unable to parse ruleset file: " + rulesetName, ex);
            }
        }

        return ruleset;
    }

    @SuppressWarnings("unchecked")
    public <T> T executeRuleset(String rulesetName, Map<String, Object> parameters) throws JsRulesException {
        RulesetExecutor<T> executor = loadRulesetByName(rulesetName);

        return executor.execute(parameters);
    }

    private Rule getRule(RuleConfig ruleConfig) throws InvalidConfigException {
        String ruleName = ruleConfig.getRuleName();
        Rule rule = ruleMap.get(ruleName);
        if (rule == null) {
            rule = ruleLoader.load(ruleConfig);
            ruleMap.put(ruleName, rule);
        }
        return rule;
    }

    private RulesetExecutor getRulesetExecutor(RulesetConfig rulesetConfig) throws InvalidConfigException {
        String rulesetName = rulesetConfig.getRulesetName();
        RulesetExecutor ruleset = rulesetExecutorMap.get(rulesetName);
        if (ruleset == null) {
            ruleset = rulesetLoader.load(rulesetConfig);
            rulesetExecutorMap.put(rulesetName, ruleset);
        }
        return ruleset;
    }

    private InputStream getFileFromClasspath(String fileName) {
        return this.getClass().getResourceAsStream("/" + fileName);
    }
}
