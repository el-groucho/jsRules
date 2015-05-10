/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.util.JsonBean;

/**
 *
 * @author Paul
 * @param <T>
 * @param <P>
 */
public class Rule<T, P> extends JsonBean {
    
    private final String ruleName;
    
    private final Parameter<P> leftParameter;
    private final Operator operator;
    private final Parameter<P> rightParameter;
    
    private final T response;

    public Rule(String ruleName, Parameter<P> leftParameter, Operator operator, 
            Parameter<P> rightParameter, T response) {
        this.ruleName = ruleName;
        this.leftParameter = leftParameter;
        this.operator = operator;
        this.rightParameter = rightParameter;
        this.response = response;
    }

    public String getRuleName() {
        return ruleName;
    }
    
    public Operator getOperator() {
        return operator;
    }

    public Parameter getLeftParameter() {
        return leftParameter;
    }

    public Parameter getRightParameter() {
        return rightParameter;
    }

    public T getResponse() {
        return response;
    }
   
}
