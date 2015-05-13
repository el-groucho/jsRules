package org.grouchotools.jsrules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.config.RuleConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.loader.RuleLoader;
import org.grouchotools.jsrules.loader.impl.RuleLoaderImpl;

import java.io.IOException;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRules {
    private JsRules() {
    }

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final RuleLoader RULE_LOADER = new RuleLoaderImpl();

    public static Rule loadRuleByJson(String json) throws InvalidConfigException {
        try {
            RuleConfig ruleConfig = OBJECT_MAPPER.readValue(json, RuleConfig.class);
            return RULE_LOADER.load(ruleConfig);
        } catch (IOException ex) {
            throw new InvalidConfigException("Unable to parse json: " + json, ex);
        }
    }
}
