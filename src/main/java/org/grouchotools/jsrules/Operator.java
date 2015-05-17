/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;

import java.util.Set;

/**
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
    },
    IN {
        @Override
        @SuppressWarnings("unchecked")
        public Boolean compare(Object left, Object right) throws InvalidParameterException {
            Set set;
            if (right instanceof Set) {
                set = (Set) right;
            } else {
                throw new InvalidParameterException("Right parameter must be a Set");
            }
            Number leftNumber = getNumber(left);
            boolean valueMatched = false;
            for (Object setValueObject : set) {
                Number setValue = getNumber(setValueObject);
                if (setValue.doubleValue() == leftNumber.doubleValue()) {
                    valueMatched = true;
                    return valueMatched;
                }
            }
            return valueMatched;
        }
    },
    NOT_IN {
        @Override
        public Boolean compare(Object left, Object right) throws InvalidParameterException {
            return !IN.compare(left, right);
        }
    };

    public abstract Boolean compare(Object left, Object right) throws InvalidParameterException;

    protected Number getNumber(Object obj) throws InvalidParameterException {
        if (obj instanceof Number) {
            return (Number) obj;
        } else {
            throw new InvalidParameterException(obj.toString() + " is not a number. Unable to compare");
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
