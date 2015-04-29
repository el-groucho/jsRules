/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;
import org.grouchotools.jsrules.impl.RuleExecutorImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 *
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleExecutorTest {
    
    private RuleExecutor<String> executor;
    private String resultMock;
    
    @org.junit.Rule
    public ExpectedException exception= ExpectedException.none();
    
    @Mock
    Rule<String> ruleMock;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        executor = new RuleExecutorImpl<>(ruleMock);
        resultMock = "mock";
        when(ruleMock.getResult()).thenReturn(resultMock);
        
        Parameter leftParameterMock = new Parameter("left", Long.class);
        when(ruleMock.getLeftParameter()).thenReturn(leftParameterMock);
        
        Parameter rightParameterMock = new Parameter("right", Long.class);
        when(ruleMock.getRightParameter()).thenReturn(rightParameterMock);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void instantiateWithRuleTest() {     
        assertNotNull(executor);
    }
    
    @Test
    public void executeRuleTest() throws Exception {
        Map<String, Object> parameters = new HashMap<>();
        
        assertEquals(resultMock, executor.execute(parameters));
    }
    
    @Test
    public void executeRuleInvalidParametersTest() throws Exception {
        exception.expect(InvalidParameterException.class);
                
        Map<String, Class> ruleMockParameters = new HashMap<>();
        ruleMockParameters.put("age", Long.class);
        
        when(ruleMock.getParameters()).thenReturn(ruleMockParameters);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("age", "21");
        
        executor.execute(parameters);
    }
    
    @Test
    public void executeRuleMissingParametersTest() throws Exception {
        exception.expect(InvalidParameterException.class);
                
        Map<String, Class> ruleMockParameters = new HashMap<>();
        ruleMockParameters.put("age", Long.class);
        
        when(ruleMock.getParameters()).thenReturn(ruleMockParameters);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", "Bob");
        
        executor.execute(parameters);
    }
    
    @Test
    public void executeRuleValidParametersTest() throws Exception {
        Map<String, Class> ruleMockParameters = new HashMap<>();
        ruleMockParameters.put("age", Long.class);
        
        when(ruleMock.getParameters()).thenReturn(ruleMockParameters);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("age", 21l);
        
        assertEquals(resultMock, executor.execute(parameters));
    }
    
    @Test
    public void executeRuleExtraParameterTest() throws Exception {
        Map<String, Class> ruleMockParameters = new HashMap<>();
        ruleMockParameters.put("age", Long.class);
        
        when(ruleMock.getParameters()).thenReturn(ruleMockParameters);
        
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("age", 21l);
        parameters.put("name", "Bob");
        
        assertEquals(resultMock, executor.execute(parameters));
    }
    
    @Test
    public void executeRuleBasic() throws Exception {
        String responseMock = "Left is greater than right!";
        
        when(ruleMock.getResponse()).thenReturn(responseMock);        
        when(ruleMock.getOperator()).thenReturn(Operator.GT);
        
        assertEquals(responseMock, executor.execute(10l, 5l));
        assertNull(executor.execute(5l, 10l));
        assertNull(executor.execute(5l, 5l));        
    }
    
    @Test
    public void executeRuleInvalidLeftParameter() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        executor.execute("invalid", 5l);
    }
    
    @Test
    public void executeRuleInvalidRightParameter() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        executor.execute(10l, "invalid");
    }

    @Test
    public void executeRuleStatic() throws Exception {
        String responseMock = "Left is less than right!";

        Parameter<Long> rightStaticParameterMock = new Parameter<>("right", Long.class, 20l);
        when(ruleMock.getRightParameter()).thenReturn(rightStaticParameterMock);

        when(ruleMock.getResponse()).thenReturn(responseMock);
        when(ruleMock.getOperator()).thenReturn(Operator.LT);

        assertEquals(responseMock, executor.execute(10l));
        assertNull(executor.execute(20l));
        assertNull(executor.execute(25l));
    }

    @Test
    public void executeRuleStaticExtraParameter() throws Exception {
        exception.expect(InvalidParameterException.class);

        Parameter<Long> rightStaticParameterMock = new Parameter<>("right", Long.class, 20l);
        when(ruleMock.getRightParameter()).thenReturn(rightStaticParameterMock);

        when(ruleMock.getOperator()).thenReturn(Operator.LT);

        executor.execute(10l, 20l);
    }

    @Test
    public void executeRuleStaticMissingParameter() throws Exception {
        exception.expect(InvalidParameterException.class);

        executor.execute(10l);
    }

}
