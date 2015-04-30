/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules.impl;

import org.grouchotools.jsrules.Rule;
import org.grouchotools.jsrules.RuleExecutor;
import org.grouchotools.jsrules.exception.InvalidParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Paul
 * @param <T>
 */
public class RuleExecutorImpl<T> implements RuleExecutor<T> {
    private static final Logger LOG = LoggerFactory.getLogger(RuleExecutorImpl.class);
            
    private final Rule<T> rule;
    
    public RuleExecutorImpl(Rule<T> rule) {
        this.rule = rule;
    }
    
    @Override
    public T execute(Object leftParameter, Object rightParameter) throws InvalidParameterException {
        Object staticValue = rule.getRightParameter().getStaticValue();
        if (staticValue != null) {
            LOG.error("Right parameter has a static value of {} and should not be specified", staticValue);
            throw new InvalidParameterException();
        }

        return executeRule(leftParameter, rightParameter);
    }

    @Override
    public T execute(Object leftParameter) throws InvalidParameterException {
        Object rightParameter = rule.getRightParameter().getStaticValue();

        if (rightParameter == null) {
            LOG.error("Right parameter must be specified");
            throw new InvalidParameterException();
        }

        return executeRule(leftParameter, rightParameter);
    }

    private T executeRule(Object leftParameter, Object rightParameter) throws InvalidParameterException {
        boolean leftValid = validateParameter(rule.getLeftParameter().getName(), leftParameter, rule.getLeftParameter().getKlasse());
        boolean rightValid = validateParameter(rule.getRightParameter().getName(), rightParameter, rule.getRightParameter().getKlasse());

        if (!leftValid || !rightValid) {
            throw new InvalidParameterException();
        }

        T response = null;

        if (rule.getOperator().compare(leftParameter, rightParameter)) {
            response = rule.getResponse();
        }

        return response;
    }
    
    /**
     * Validate that parameter is present and is an instance of the correct 
     * class
     * 
     * @param key
     * @param parameter
     * @param expectedClass
     * @return 
     */
    private boolean validateParameter(String key, Object parameter, Class expectedClass) {
        boolean valid = true;

        if (parameter == null) {
            LOG.error("Expected Parameter {} is missing", key);
            valid = false;
        } else if (!expectedClass.isInstance(parameter)) {
            LOG.error("Parameter {} is invalid | Expected class: {} | Parameter Class: {}",
                    key, expectedClass, parameter.getClass());
            valid = false;
        }

        return valid;
    }

    @Override
    public String getLeftParameterName() {
        return rule.getLeftParameter().getName();
    }

    @Override
    public String getRightParameterName() {
        return rule.getRightParameter().getName();
    }
    
}
