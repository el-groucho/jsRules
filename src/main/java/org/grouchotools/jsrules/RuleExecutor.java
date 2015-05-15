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
public abstract class RuleExecutor<T> extends Executor {
    public abstract T execute(Object leftParameter, Object rightParameter) throws InvalidParameterException;

    public abstract T execute(Object leftParameter) throws InvalidParameterException;

    public abstract Parameter getLeftParameter();

    public abstract Parameter getRightParameter();
}
