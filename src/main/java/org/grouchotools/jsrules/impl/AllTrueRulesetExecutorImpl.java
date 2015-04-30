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
package org.grouchotools.jsrules.impl;

import org.grouchotools.jsrules.RuleExecutor;
import org.grouchotools.jsrules.RulesetExecutor;
import org.grouchotools.jsrules.exception.InvalidParameterException;

import java.util.List;
import java.util.Map;

/**
 * This executor evaluates a series of rules in order.
 * 
 * If all rules evaluate as true, it returns the given response. Otherwise, the
 * response is null.
 * 
 * @author Paul
 * @param <T>
 */
public class AllTrueRulesetExecutorImpl<T> implements RulesetExecutor<T> {
    private final List<RuleExecutor> ruleSet;
    private final T response;
    
    public AllTrueRulesetExecutorImpl(List<RuleExecutor> ruleSet, T response) {
        this.ruleSet = ruleSet;
        this.response = response;
    }
    
    @Override
    public T execute(Map<String, Object> parameters) throws InvalidParameterException {
        T result = response;
        for(RuleExecutor rule:ruleSet) {
            Object leftParameter = parameters.get(rule.getLeftParameterName());
            Object rightParameter = parameters.get(rule.getRightParameterName());
            // failed rule checks return null
            if (rule.execute(leftParameter, rightParameter) == null) {
                result = null;
            }
        }
        return result;
    }
    
}
