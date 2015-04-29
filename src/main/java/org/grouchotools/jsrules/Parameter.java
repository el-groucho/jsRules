/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

/**
 *
 * @author Paul
 */
public class Parameter {
    public Parameter(String name, Class klasse) {
        this.name = name;
        this.klasse = klasse;
    }
    
    private final String name;
    private final Class klasse;

    public String getName() {
        return name;
    }

    public Class getKlasse() {
        return klasse;
    }

}
