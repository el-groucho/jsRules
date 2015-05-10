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

import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

/**
 *
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class RuleExecutorTest {
    
    private RuleExecutor<String> executor;
    
    @org.junit.Rule
    public ExpectedException exception= ExpectedException.none();
    
    @Mock
    Rule<String, Long> ruleMock;
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        executor = new RuleExecutorImpl<>(ruleMock);
        
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
