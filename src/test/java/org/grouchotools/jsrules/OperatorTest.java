/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;
import org.junit.*;
import org.junit.rules.ExpectedException;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Paul
 */
public class OperatorTest {
    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();
    
    public OperatorTest() {
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
    public void greaterThanTest() throws Exception {
        Operator operator = Operator.GT;
        Long left = 10l;
        Long right = 5l;
        assertTrue(operator.compare(left, right));
        assertFalse(operator.compare(right, left));
        
        right = left;
        assertFalse(operator.compare(left, right));
        
        Integer rightInt = 5;
        assertTrue(operator.compare(left, rightInt));
    }
    
    @Test
    public void greaterThanExceptionTest() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        Operator operator = Operator.GT;
        Long left = 10l;
        String right = "Bob";
        
        operator.compare(left, right);
    }
    
    @Test
    public void lessThanTest() throws Exception {
        Operator operator = Operator.LT;
        Long left = 10l;
        Long right = 5l;
        assertFalse(operator.compare(left, right));
        assertTrue(operator.compare(right, left));
        
        right = left;
        assertFalse(operator.compare(left, right));
        
        Integer rightInt = 5;
        assertFalse(operator.compare(left, rightInt));
    }
    
    @Test
    public void lessThanExceptionTest() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        Operator operator = Operator.LT;
        Long left = 10l;
        String right = "Bob";
        
        operator.compare(left, right);
    }
    
    @Test
    public void greaterThanEqualsTest() throws Exception {
        Operator operator = Operator.GTE;
        Long left = 10l;
        Long right = 5l;
        assertTrue(operator.compare(left, right));
        assertFalse(operator.compare(right, left));
        
        right = left;
        assertTrue(operator.compare(left, right));
        
        Integer rightInt = 5;
        assertTrue(operator.compare(left, rightInt));
    }
    
    @Test
    public void greaterThanEqualsExceptionTest() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        Operator operator = Operator.GTE;
        Long left = 10l;
        String right = "Bob";
        
        operator.compare(left, right);
    }
       
    @Test
    public void lessThanEqualsTest() throws Exception {
        Operator operator = Operator.LTE;
        Long left = 10l;
        Long right = 5l;
        assertFalse(operator.compare(left, right));
        assertTrue(operator.compare(right, left));
        
        right = left;
        assertTrue(operator.compare(left, right));
        
        Integer rightInt = 5;
        assertFalse(operator.compare(left, rightInt));
    }
    
    @Test
    public void lessThanEqualsExceptionTest() throws Exception {
        exception.expect(InvalidParameterException.class);
        
        Operator operator = Operator.LTE;
        Long left = 10l;
        String right = "Bob";
        
        operator.compare(left, right);
    }
    
    @Test
    public void equalsTest() throws Exception {
        Operator operator = Operator.EQ;
        Long left = 10l;
        Long right = 5l;
        assertFalse(operator.compare(left, right));
        assertFalse(operator.compare(right, left));
        
        right = left;
        assertTrue(operator.compare(left, right));
        
        Integer rightInt = 10;
        assertTrue(operator.compare(left, rightInt));
        
        String rightString = "Bob";
        assertFalse(operator.compare(left, rightString));
    }
    
    @Test
    public void notEqualsTest() throws Exception {
        Operator operator = Operator.NE;
        Long left = 10l;
        Long right = 5l;
        assertTrue(operator.compare(left, right));
        assertTrue(operator.compare(right, left));
        
        right = left;
        assertFalse(operator.compare(left, right));
        
        Integer rightInt = 10;
        assertFalse(operator.compare(left, rightInt));
        
        String rightString = "Bob";
        assertTrue(operator.compare(left, rightString));
    }

    @Test
    public void inTest() throws Exception {
        Operator operator = Operator.IN;

        Long[] longArray = new Long[]{5l, 10l, 15l};
        Set<Long> longSet = new HashSet<>(Arrays.asList(longArray));

        Long left = 10l;
        assertTrue(operator.compare(left, longSet));

        left = 20l;
        assertFalse(operator.compare(left, longSet));
    }

    @Test
    public void inTestException() throws Exception {
        exception.expect(InvalidParameterException.class);

        Operator operator = Operator.IN;

        operator.compare("string", "not a set");
    }

    @Test
    public void notInTest() throws Exception {
        Operator operator = Operator.NOT_IN;

        Long[] longArray = new Long[]{5l, 10l, 15l};
        Set<Long> longSet = new HashSet<>(Arrays.asList(longArray));

        Long left = 10l;
        assertFalse(operator.compare(left, longSet));

        left = 20l;
        assertTrue(operator.compare(left, longSet));
    }

    @Test
    public void notInTestException() throws Exception {
        exception.expect(InvalidParameterException.class);

        Operator operator = Operator.NOT_IN;

        operator.compare("string", "not a set");
    }
}
