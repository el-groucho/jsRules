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
package org.grouchotools.jsrules.util;

import org.grouchotools.jsrules.Executor;
import org.grouchotools.jsrules.impl.AllTrueRulesetExecutorImpl;
import org.grouchotools.jsrules.impl.AllTrueRulesetListExecutorImpl;
import org.grouchotools.jsrules.impl.FirstTrueRulesetExecutorImpl;
import org.grouchotools.jsrules.impl.FirstTrueRulesetListExecutorImpl;
import org.junit.*;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * Created by Paul Richardson 5/14/2015
 */
@RunWith(MockitoJUnitRunner.class)
public class RulesetTypeHandlerTest {
    private final String rulesetName = "mockRuleset";

    @Mock
    List<Executor> ruleSet;

    @Mock
    Object response;

    public RulesetTypeHandlerTest() {
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
    public void AllTrueTest() {
        RulesetTypeHandler handler = RulesetTypeHandler.ALLTRUE;

        assertTrue(handler.getRulesetExecutor(rulesetName, ruleSet, response) instanceof AllTrueRulesetExecutorImpl);
    }

    @Test
    public void FirstTrueTest() {
        RulesetTypeHandler handler = RulesetTypeHandler.FIRSTTRUE;

        assertTrue(handler.getRulesetExecutor(rulesetName, ruleSet, response) instanceof FirstTrueRulesetExecutorImpl);
    }

    @Test
    public void AllTrueListTest() {
        RulesetTypeHandler handler = RulesetTypeHandler.ALLTRUELIST;

        assertTrue(handler.getRulesetExecutor(rulesetName, ruleSet, response) instanceof AllTrueRulesetListExecutorImpl);
    }

    @Test
    public void FirstTrueListTest() {
        RulesetTypeHandler handler = RulesetTypeHandler.FIRSTTRUELIST;

        assertTrue(handler.getRulesetExecutor(rulesetName, ruleSet, response) instanceof FirstTrueRulesetListExecutorImpl);
    }
}
