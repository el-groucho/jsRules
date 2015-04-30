/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;

/**
 *
 * @author Paul
 */
public interface RuleExecutor<T> {    
    T execute(Object leftParameter, Object rightParameter) throws InvalidParameterException;

    T execute(Object leftParameter) throws InvalidParameterException;

    Parameter getLeftParameter();

    Parameter getRightParameter();
}
