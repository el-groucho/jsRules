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
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;
import org.grouchotools.jsrules.impl.AllTrueRulesetExecutorImpl;
import org.grouchotools.jsrules.impl.RuleExecutorImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 *
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class AllTrueRulesetExecutorTest {
    private RulesetExecutor<String> executor;
    private String responseMock = "mock";

    @org.junit.Rule
    public ExpectedException exception= ExpectedException.none();

    @Mock
    Rule<String, Long> ruleMock;

    private List<RuleExecutor> ruleListMock;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        ruleListMock = new ArrayList<>();
        executor = new AllTrueRulesetExecutorImpl<>(ruleListMock, responseMock);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void executeRulesetTest() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        
        assertEquals(responseMock, executor.execute(parameters));
    }
    
    @Test
    public void executeRulesetInvalidParametersTest() throws Exception {
        exception.expect(InvalidParameterException.class);

        when(ruleMock.getLeftParameter()).thenReturn(new Parameter<>("left",
                Long.class));
        when(ruleMock.getRightParameter()).thenReturn(new Parameter<>("right",
                Long.class));

        RuleExecutor<String> ruleExecutorMock = new RuleExecutorImpl<>(ruleMock);
        ruleListMock.add(ruleExecutorMock);
                        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("left", "21");
        parameters.put("right", 10l);
        
        executor.execute(parameters);
    }
    
    @Test
    public void executeRulesetMissingParametersTest() throws Exception {
        exception.expect(InvalidParameterException.class);

        when(ruleMock.getLeftParameter()).thenReturn(new Parameter<>("left",
                Long.class));
        when(ruleMock.getRightParameter()).thenReturn(new Parameter<>("right",
                Long.class));

        RuleExecutor<String> ruleExecutorMock = new RuleExecutorImpl<>(ruleMock);
        ruleListMock.add(ruleExecutorMock);
                        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Bob");
        parameters.put("right", 10l);
        
        executor.execute(parameters);
    }
    
    @Test
    public void executeRulesetValidParametersTest() throws Exception {

        when(ruleMock.getLeftParameter()).thenReturn(new Parameter<>("left",
                Long.class));
        when(ruleMock.getOperator()).thenReturn(Operator.GT);
        when(ruleMock.getRightParameter()).thenReturn(new Parameter<>("right",
                Long.class));
        when(ruleMock.getResponse()).thenReturn("Left is greater than right");

        RuleExecutor<String> ruleExecutorMock = new RuleExecutorImpl<>(ruleMock);
        ruleListMock.add(ruleExecutorMock);
                        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("left", 21l);
        parameters.put("right", 10l);
        
        assertEquals(responseMock, executor.execute(parameters));

        parameters.put("left", 10l);
        assertNull(executor.execute(parameters));

        parameters.put("left", 5l);
        assertNull(executor.execute(parameters));
    }
    
    @Test
    public void executeRulesetExtraParameterTest() throws Exception {

        when(ruleMock.getLeftParameter()).thenReturn(new Parameter<>("left",
                Long.class));
        when(ruleMock.getOperator()).thenReturn(Operator.GT);
        when(ruleMock.getRightParameter()).thenReturn(new Parameter<>("right",
                Long.class));
        when(ruleMock.getResponse()).thenReturn("Left is greater than right");

        RuleExecutor<String> ruleExecutorMock = new RuleExecutorImpl<>(ruleMock);
        ruleListMock.add(ruleExecutorMock);
                        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("left", 21l);
        parameters.put("right", 10l);
        parameters.put("name", "Bob");
        
        assertEquals(responseMock, executor.execute(parameters));
    }

    @Test
    public void executeRulesetStaticParameterTest() throws Exception {
        Long left = 21l;
        Long right = 10l;
        Long staticRight = 5l;

        String leftName = "left";
        String rightName = "right";

        RuleExecutor ruleExecutorMock = mock(RuleExecutor.class);

        when(ruleExecutorMock.getLeftParameter()).thenReturn(new Parameter<>(leftName, Long.class));
        when(ruleExecutorMock.getRightParameter()).thenReturn(new Parameter<>(rightName, Long.class, staticRight));
        when(ruleExecutorMock.execute(left)).thenReturn(responseMock);

        ruleListMock.add(ruleExecutorMock);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(leftName, left);
        parameters.put(rightName, right);

        assertEquals(responseMock, executor.execute(parameters));

        parameters.put(leftName, 20l);
        assertNull(executor.execute(parameters));
    }
}
