/*
 * The MIT License
 *
 * Copyright 2015 Paul.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.grouchotools.jsrules.config;

/**
 *  Java representation of the json object used to configure a rule
 * 
 * @author Paul
 */
public class RuleConfig implements Config {
    private String ruleName;
    private ParamConfig leftParamConfig;
    private String operator;
    private ParamConfig rightParamConfig;
    private ResponseConfig responseConfig;
    
    public RuleConfig(String ruleName, ParamConfig leftParamConfig, String operator, ParamConfig rightParamConfig, ResponseConfig responseConfig) {
        this.ruleName = ruleName;
        this.leftParamConfig = leftParamConfig;
        this.operator = operator;
        this.rightParamConfig = rightParamConfig;
        this.responseConfig = responseConfig;
    }

    public String getRuleName() {
        return ruleName;
    }

    public void setRuleName(String ruleName) {
        this.ruleName = ruleName;
    }

    public ParamConfig getLeftParamConfig() {
        return leftParamConfig;
    }

    public void setLeftParamConfig(ParamConfig leftParamConfig) {
        this.leftParamConfig = leftParamConfig;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public ParamConfig getRightParamConfig() {
        return rightParamConfig;
    }

    public void setRightParamConfig(ParamConfig rightParamConfig) {
        this.rightParamConfig = rightParamConfig;
    }

    public ResponseConfig getResponseConfig() {
        return responseConfig;
    }

    public void setResponseConfig(ResponseConfig responseConfig) {
        this.responseConfig = responseConfig;
    }
    
    
}
