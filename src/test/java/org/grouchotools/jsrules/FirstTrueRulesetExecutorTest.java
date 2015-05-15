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

import org.grouchotools.jsrules.exception.InvalidParameterClassException;
import org.grouchotools.jsrules.exception.MissingParameterException;
import org.grouchotools.jsrules.impl.FirstTrueRulesetExecutorImpl;
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
import static org.mockito.Mockito.when;

/**
 * @author Paul
 */
@RunWith(MockitoJUnitRunner.class)
public class FirstTrueRulesetExecutorTest {
    private RulesetExecutor<String> executor;

    private final String leftName = "left";
    private final String rightName = "right";
    private final String nameName = "name";

    private final String rulesetName = "mockRuleset";

    @org.junit.Rule
    public ExpectedException exception = ExpectedException.none();

    @Mock
    RuleExecutor<String> ruleExecutorMock1;

    @Mock
    RuleExecutor<String> ruleExecutorMock2;

    private List<RuleExecutor<String>> ruleListMock;

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
        when(ruleExecutorMock1.getLeftParameter()).thenReturn(new Parameter<>(leftName, Long.class));
        when(ruleExecutorMock1.getRightParameter()).thenReturn(new Parameter<>(rightName, Long.class));

        when(ruleExecutorMock2.getLeftParameter()).thenReturn(new Parameter<>(leftName, Long.class));
        when(ruleExecutorMock2.getRightParameter()).thenReturn(new Parameter<>(rightName, Long.class));

        ruleListMock = new ArrayList<>();
        ruleListMock.add(ruleExecutorMock1);
        ruleListMock.add(ruleExecutorMock2);

        executor = new FirstTrueRulesetExecutorImpl<>(rulesetName, ruleListMock);
    }

    @After
    public void tearDown() {
    }

    @Test
    public void executeEmptyRulesetTest() throws Exception {
        ruleListMock.clear();

        Map<String, Object> parameters = new HashMap<>();

        assertNull(executor.execute(parameters));
    }

    @Test
    public void executeRulesetInvalidLeftParameterTest() throws Exception {
        exception.expect(InvalidParameterClassException.class);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(leftName, "21");
        parameters.put(rightName, 10l);

        executor.execute(parameters);
    }

    @Test
    public void executeRulesetInvalidRightParameterTest() throws Exception {
        exception.expect(InvalidParameterClassException.class);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(leftName, 21l);
        parameters.put(rightName, "10");

        executor.execute(parameters);
    }

    @Test
    public void executeRulesetMissingParametersTest() throws Exception {
        exception.expect(MissingParameterException.class);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(nameName, "Bob");
        parameters.put(rightName, 10l);

        executor.execute(parameters);
    }

    @Test
    public void executeRulesetValidParametersTest() throws Exception {
        Long left1 = 21l;
        Long left2 = 10l;
        Long right = 10l;
        String response1Mock = "executeRulesetValidParametersTest1";
        String response2Mock = "executeRulesetValidParametersTest2";

        when(ruleExecutorMock1.execute(left1, right)).thenReturn(response1Mock);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put(leftName, left1);
        parameters.put(rightName, right);

        assertEquals(response1Mock, executor.execute(parameters));

        parameters.put(leftName, left2);
        assertNull(executor.execute(parameters));

        when(ruleExecutorMock2.execute(left2, right)).thenReturn(response2Mock);
        assertEquals(response2Mock, executor.execute(parameters));
    }

    @Test
    public void executeRulesetExtraParameterTest() throws Exception {
        Long left = 21l;
        Long right = 10l;

        String responseMock = "executeRulesetExtraParameterTest";

        when(ruleExecutorMock1.execute(left, right)).thenReturn(responseMock);

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("left", left);
        parameters.put("right", right);
        parameters.put("name", "Bob");

        assertEquals(responseMock, executor.execute(parameters));
    }

    @Test
    public void executeRulesetStaticParameterTest() throws Exception {
        Long left = 21l;
        Long differentLeft = 20l;
        Long right = 10l;
        Long staticRight = 5l;

        String responseMock = "executeRulesetStaticParameterTest";

        when(ruleExecutorMock1.getRightParameter()).thenReturn(new Parameter<>(rightName, Long.class, staticRight));
        when(ruleExecutorMock1.execute(left)).thenReturn(responseMock);

        Map<String, Object> parameters = new HashMap<>();

        parameters.put("left", left);
        parameters.put("right", right);
        assertEquals(responseMock, executor.execute(parameters));

        parameters.put("left", differentLeft);
        assertNull(executor.execute(parameters));
    }

    @Test
    public void rulesetNameTest() {
        assertEquals(rulesetName, executor.getName());
    }
}
