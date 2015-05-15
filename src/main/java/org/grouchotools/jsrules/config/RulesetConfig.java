package org.grouchotools.jsrules.config;

import java.util.List;

/**
 * Created by Paul Richardson 5/14/2015
 */
public class RulesetConfig implements Config {
    private String rulesetName;
    private String rulesetType;
    private ResponseConfig responseConfig;
    private List<String> components;

    public RulesetConfig() {

    }

    public RulesetConfig(String rulesetName, String rulesetType, ResponseConfig responseConfig, List<String> components) {
        this.rulesetName = rulesetName;
        this.rulesetType = rulesetType;
        this.responseConfig = responseConfig;
        this.components = components;
    }

    public String getRulesetName() {
        return rulesetName;
    }

    public void setRulesetName(String rulesetName) {
        this.rulesetName = rulesetName;
    }

    public String getRulesetType() {
        return rulesetType;
    }

    public void setRulesetType(String rulesetType) {
        this.rulesetType = rulesetType;
    }

    public ResponseConfig getResponseConfig() {
        return responseConfig;
    }

    public void setResponseConfig(ResponseConfig responseConfig) {
        this.responseConfig = responseConfig;
    }

    public List<String> getComponents() {
        return components;
    }

    public void setComponents(List<String> components) {
        this.components = components;
    }
}
