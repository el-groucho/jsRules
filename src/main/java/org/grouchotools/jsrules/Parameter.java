/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.util.JsonBean;

/**
 *
 * @author Paul
 * @param <T>
 */
public class Parameter<T> extends JsonBean {
    public Parameter(String name, Class<T> klasse) {
        this.name = name;
        this.klasse = klasse;
        this.staticValue = null;
    }

    public Parameter(String name, Class<T> klasse, T staticValue) {
        this.name = name;
        this.klasse = klasse;
        this.staticValue = staticValue;
    }
    
    private final String name;
    private final Class<T> klasse;
    private final T staticValue;

    public String getName() {
        return name;
    }

    public Class<T> getKlasse() {
        return klasse;
    }

    public T getStaticValue() {
        return staticValue;
    }
   
}
