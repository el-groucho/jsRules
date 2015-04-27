/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsrules;

import java.util.HashMap;
import java.util.Map;
import org.jsrules.exception.InvalidParameterException;
import org.jsrules.impl.RuleExecutorImpl;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleExecutorTest {
    
    private RuleExecutor<Boolean> executor;
    private Boolean resultMock;
    
    @org.junit.Rule
    public ExpectedException exception= ExpectedException.none();
    
    @Mock
    Rule<Boolean> ruleMock;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        executor = new RuleExecutorImpl<>(ruleMock);
        resultMock = true;
        when(ruleMock.getResult()).thenReturn(resultMock);
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
        Boolean result = executor.execute(parameters);
        
        assertEquals(resultMock, result);
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
}
