/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jsrules;

import org.jsrules.exception.InvalidParameterException;

/**
 *
 * @author Paul
 */
public enum Operator {
   GT {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           Number leftNumber = getNumber(left);
           Number rightNumber = getNumber(right);
           return leftNumber.doubleValue() > rightNumber.doubleValue();
       }      
   },
   LT {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           return GT.compare(right, left);
       }      
   },
   GTE {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           Number leftNumber = getNumber(left);
           Number rightNumber = getNumber(right);
           return leftNumber.doubleValue() >= rightNumber.doubleValue();
       }      
   },
   LTE {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           return GTE.compare(right, left);
       }      
   },
   EQ {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           left = getDoubleValue(left);
           right = getDoubleValue(right);
           return left.equals(right);
       }      
   },
   NE {
       @Override
       public Boolean compare(Object left, Object right) throws InvalidParameterException {
           return !EQ.compare(left, right);
       }      
   };
   
   public abstract Boolean compare(Object left, Object right) throws InvalidParameterException;
     
   protected Number getNumber(Object obj) throws InvalidParameterException {
       if (obj instanceof Number) {
           return (Number) obj;
       } else {
           throw new InvalidParameterException(obj.toString()+" is not a number. Unable to compare");
       }
   }
   
   protected Object getDoubleValue(Object obj) {
       if (obj instanceof Number) {
           Number number = (Number) obj;
           obj = number.doubleValue();
       }
       return obj;
   }
}
