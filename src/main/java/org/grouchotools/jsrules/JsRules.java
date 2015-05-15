package org.grouchotools.jsrules;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.config.RuleConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.loader.RuleLoader;
import org.grouchotools.jsrules.loader.impl.RuleLoaderImpl;

import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Paul Richardson 5/13/2015
 */
public class JsRules {

    private static final JsRules INSTANCE = new JsRules();
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
    private static final RuleLoader RULE_LOADER = new RuleLoaderImpl();

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
            throw new InvalidConfigException("Unable to parse file: " + ruleName, ex);
        }

//        InputStreamReader isr = new InputStreamReader(stream);
//        BufferedReader br = new BufferedReader(isr);
//
//        StringBuilder builder = new StringBuilder("");
//
//        try {
//            String line = br.readLine();
//            while (line != null) {
//                builder.append(line);
//                line = br.readLine();
//            }
//            return loadRuleByJson(builder.toString());
//        } catch (IOException ex) {
//            throw new InvalidConfigException("Unable to read rule file: " + fileName, ex);
//        }
    }
}
