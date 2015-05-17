package org.grouchotools.jsrules.loader.impl;

import org.grouchotools.jsrules.*;
import org.grouchotools.jsrules.config.ResponseConfig;
import org.grouchotools.jsrules.config.RulesetConfig;
import org.grouchotools.jsrules.exception.ClassHandlerException;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.impl.RuleExecutorImpl;
import org.grouchotools.jsrules.loader.RulesetLoader;
import org.grouchotools.jsrules.util.ClassHandler;
import org.grouchotools.jsrules.util.RulesetTypeHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paul Richardson 5/14/2015
 */
public class RulesetLoaderImpl implements RulesetLoader {
    private JsRules jsRules;

    public RulesetLoaderImpl(JsRules jsRules) {
        this.jsRules = jsRules;
    }

    @Override
    @SuppressWarnings("unchecked")
    public RulesetExecutor load(RulesetConfig config) throws InvalidConfigException {
        String type;
        if (config.getRulesetType() != null) {
            type = config.getRulesetType().toUpperCase();
        } else {
            throw new InvalidConfigException("Ruleset Type must be provided");
        }
        RulesetTypeHandler rulesetTypeHandler = RulesetTypeHandler.valueOf(type);

        ResponseConfig responseConfig = config.getResponseConfig();
        ClassHandler classHandler;
        try {
            classHandler = ClassHandler.valueOf(responseConfig.getResponseClass().toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw new InvalidConfigException("Invalid response class: " + responseConfig.getResponseClass());
        }
        Object response;
        String responseString = responseConfig.getResponse();
        try {
            response = classHandler.convertString(responseString);
        } catch (ClassHandlerException ex) {
            throw new InvalidConfigException("Unable to parse response " + responseString, ex);
        }

        List<Executor> ruleSet = new ArrayList<>();
        List<String> components = config.getComponents();
        for (String component : components) {
            Rule rule = jsRules.loadRuleByName(component);
            RuleExecutor ruleExecutor = new RuleExecutorImpl(rule);
            ruleSet.add(ruleExecutor);
        }

        String name = config.getRulesetName();

        return rulesetTypeHandler.getRulesetExecutor(name, ruleSet, response);
    }
}
