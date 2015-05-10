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
import org.grouchotools.jsrules.loader.impl.RuleLoaderImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.initMocks;

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
    private final Boolean response = true;
    
    @InjectMocks
    private RuleLoaderImpl ruleLoader;
    
    @Mock
    private ParamLoader paramLoader;
    
    @Mock
    private ResponseLoader responseLoader;
    
    public RuleLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() throws Exception {
        initMocks(this);
        
        when(paramLoader.load(getLeftParamConfig())).
                thenReturn(getLeftRuleParameter());
        when(paramLoader.load(getRightParamConfig())).
                thenReturn(getRightRuleParameter());
        when(responseLoader.load(getResponseConfig())).thenReturn(response);
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
        String operator = "GT";
               
        ResponseConfig responseConfig = getResponseConfig();
        
        RuleConfig ruleConfig = new RuleConfig(ruleName, getLeftParamConfig(), 
                operator, getRightParamConfig(), responseConfig);
        
        return ruleConfig;
    }
    
    private ParamConfig getLeftParamConfig() {
        String leftParameterClass = "Long";
        String leftParameterStaticValue = null;
        
        ParamConfig leftParamConfig = new ParamConfig(leftParameterName, 
                leftParameterClass, leftParameterStaticValue);
        
        return leftParamConfig;
    }
    
    private ParamConfig getRightParamConfig() {
        String rightParameterClass = "Long";
        String rightParameterStaticValue = "10";
        
        ParamConfig rightParamConfig = new ParamConfig(rightParameterName, 
                rightParameterClass, rightParameterStaticValue);
        
        return rightParamConfig;
    }
    
    private Rule getRule() {
        Parameter<Long> leftParameter = getLeftRuleParameter();
        Operator operator = Operator.GT;
        Parameter<Long> rightParameter = getRightRuleParameter();
        
        Rule rule = new Rule(ruleName, leftParameter, operator, rightParameter, 
                response);
        
        return rule;
    }
    
    private Parameter<Long> getLeftRuleParameter() {
        return new Parameter<>(leftParameterName, Long.class);
    }
    
    private Parameter<Long> getRightRuleParameter() {
        return new Parameter<>(rightParameterName, Long.class, 10l);
    }
    
    private ResponseConfig getResponseConfig() {
        String responseString = "true";
        String responseClass = "Boolean";
        
        return new ResponseConfig(responseString, responseClass);
    }
}
