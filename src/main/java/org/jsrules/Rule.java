/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsrules;

import java.util.Map;

/**
 *
 * @author Paul
 * @param <T>
 */
public class Rule<T> {
    
    private T result;
    private Map<String, Class> parameters;
    private Operator operator;

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    public Map<String, Class> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, Class> parameters) {
        this.parameters = parameters;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
}
