/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import java.util.Map;

/**
 *
 * @author Paul
 * @param <T>
 */
public class Rule<T> {
    
    @Deprecated //TODO this is now response
    private T result;
    
    @Deprecated //TODO move to ruleset before removing
    private Map<String, Class> parameters;
    
    private Parameter leftParameter;
    private Operator operator;
    private Parameter rightParameter;
    
    private T response;
    

    @Deprecated
    public T getResult() {
        return result;
    }

    @Deprecated
    public void setResult(T result) {
        this.result = result;
    }

    @Deprecated
    public Map<String, Class> getParameters() {
        return parameters;
    }

    @Deprecated
    public void setParameters(Map<String, Class> parameters) {
        this.parameters = parameters;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public Parameter getLeftParameter() {
        return leftParameter;
    }

    public void setLeftParameter(Parameter leftParameter) {
        this.leftParameter = leftParameter;
    }

    public Parameter getRightParameter() {
        return rightParameter;
    }

    public void setRightParameter(Parameter rightParameter) {
        this.rightParameter = rightParameter;
    }

    public T getResponse() {
        return response;
    }

    public void setResponse(T response) {
        this.response = response;
    }
   
}
