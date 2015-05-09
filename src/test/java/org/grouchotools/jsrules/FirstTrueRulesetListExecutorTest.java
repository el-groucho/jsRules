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
import org.junit.*;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.grouchotools.jsrules.impl.FirstTrueRulesetListExecutorImpl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class FirstTrueRulesetListExecutorTest {
    private RulesetExecutor<String> executor;
    private final String responseMock = "mock";

    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    private List<RulesetExecutor<String>> rulesetListMock;
    private Map<String, Object> parameters;

    @Mock
    private RulesetExecutor<String> rulesetExecutorMock;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        rulesetListMock = new ArrayList<>();
        parameters = new HashMap<>();

        executor = new FirstTrueRulesetListExecutorImpl<>(rulesetListMock);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void executeFirstTrueRulesetListEmptyTest() throws Exception {
        assertNull(executor.execute(parameters));
    }

    @Test
    public void executeFirstTrueRulesetListInvalidParametersTest() throws Exception {
        exception.expect(InvalidParameterException.class);

        when(rulesetExecutorMock.execute(parameters)).thenThrow(new InvalidParameterException());

        rulesetListMock.add(rulesetExecutorMock);

        executor.execute(parameters);
    }

    @Test
    public void executeFirstTrueRulesetListValidParametersTrueTest() throws Exception {
        parameters.put("left", 21l);
        parameters.put("right", 10l);

        when(rulesetExecutorMock.execute(parameters)).thenReturn(responseMock);
        rulesetListMock.add(rulesetExecutorMock);

        assertEquals(responseMock, executor.execute(parameters));
    }
    
    @Test
    public void executeFirstTrueRulesetListValidParametersFalseTest() throws Exception {
        parameters.put("left", 21l);
        parameters.put("right", 10l);

        when(rulesetExecutorMock.execute(parameters)).thenReturn(responseMock);
        rulesetListMock.add(rulesetExecutorMock);

        Map<String, Object> otherParameters = new HashMap<>();
        parameters.put("left", 10l);
        parameters.put("right", 10l);

        assertNull(executor.execute(otherParameters));
    }

    @Test
    public void executeRulesetListFirstTrue() throws Exception {
        when(rulesetExecutorMock.execute(parameters)).thenReturn(responseMock);

        RulesetExecutor rulesetExecutorMock2 = mock(RulesetExecutor.class);
        when(rulesetExecutorMock2.execute(parameters)).thenReturn("mock 2");

        rulesetListMock.add(rulesetExecutorMock);
        rulesetListMock.add(rulesetExecutorMock2);

        assertEquals(responseMock, executor.execute(parameters));
    }
    
    @Test
    public void executeRulesetListSecondTrue() throws Exception {
        when(rulesetExecutorMock.execute(parameters)).thenReturn(responseMock);

        RulesetExecutor rulesetExecutorMock2 = mock(RulesetExecutor.class);
        when(rulesetExecutorMock2.execute(parameters)).thenReturn(null);

        rulesetListMock.add(rulesetExecutorMock2);
        rulesetListMock.add(rulesetExecutorMock);

        assertEquals(responseMock, executor.execute(parameters));
    }
}
