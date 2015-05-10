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
package org.grouchotools.jsrules.loader;

import org.grouchotools.jsrules.Operator;
import org.grouchotools.jsrules.Parameter;
import org.grouchotools.jsrules.Rule;
import org.grouchotools.jsrules.config.ParamConfig;
import org.grouchotools.jsrules.config.ResponseConfig;
import org.grouchotools.jsrules.config.RuleConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.loader.RuleLoader;
import org.grouchotools.jsrules.loader.impl.RuleLoaderImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Paul
 */
public class RuleLoaderTest {
    @org.junit.Rule
    public ExpectedException exception= ExpectedException.none();
        
    private final String ruleName = "Left is greater than 10";
    private final String leftParameterName = "left";
    private final String rightParameterName = "right";
    
    private final RuleLoader ruleLoader = new RuleLoaderImpl();
    
    public RuleLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void loadFromRuleConfigTest() throws Exception {
        Rule mockRule = getRule();
        RuleConfig ruleConfig = getRuleConfig();
        
        assertEquals(mockRule, ruleLoader.load(ruleConfig));
    }
    
    @Test
    public void loadFromRuleConfigInvalidOperatorTest() throws Exception {
        exception.expect(InvalidConfigException.class);
        
        RuleConfig ruleConfig = getRuleConfig();
        
        ruleConfig.setOperator("invalid!");
        
        ruleLoader.load(ruleConfig);
    }
    
    private RuleConfig getRuleConfig() {
        String leftParameterClass = "Long";
        String leftParameterStaticValue = null;
        String rightParameterClass = "Long";
        String rightParameterStaticValue = "10";
        String operator = "GT";
        String response = "true";
        String responseClass = "Boolean";
        
        ParamConfig leftParamConfig = new ParamConfig(leftParameterName, 
                leftParameterClass, leftParameterStaticValue);
        ParamConfig rightParamConfig = new ParamConfig(rightParameterName, 
                rightParameterClass, rightParameterStaticValue);
        
        ResponseConfig responseConfig = new ResponseConfig(response, 
                responseClass);
        
        RuleConfig ruleConfig = new RuleConfig(ruleName, leftParamConfig, 
                operator, rightParamConfig, responseConfig);
        
        return ruleConfig;
    }
    
    private Rule getRule() {
        Parameter<Long> leftParameter = new Parameter<>(leftParameterName, 
                Long.class);
        Operator operator = Operator.GT;
        Parameter<Long> rightParameter = new Parameter<>(rightParameterName, 
                Long.class, 10l);
        Boolean response = true;
        
        Rule rule = new Rule(ruleName, leftParameter, operator, rightParameter, 
                response);
        
        return rule;
    }
}
