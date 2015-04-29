/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsrules;

import java.util.Map;
import org.jsrules.exception.InvalidParameterException;

/**
 *
 * @author Paul
 */
public interface RuleExecutor<T> {
    @Deprecated //TODO move to ruleset before deleting
    T execute(Map<String, Object> parameters) throws InvalidParameterException;
    
    T execute(Object leftParameter, Object rightParameter) throws InvalidParameterException;
}
