/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterException;

import java.util.ArrayList;
import java.util.List;
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
            Set set = getSet(right);
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
    },
    BETWEEN {
        @Override
        public Boolean compare(Object left, Object right) throws InvalidParameterException {
            Set set = getSet(right);

            if (set.size() != 2) {
                throw new InvalidParameterException("Right parameter must be a set of 2");
            }

            List<Double> doubleList = new ArrayList<>();

            for (Object obj : set) {
                doubleList.add(getNumber(obj).doubleValue());
            }

            Double[] doubleArray = new Double[2];
            doubleArray = doubleList.toArray(doubleArray);

            Double leftDouble = getNumber(left).doubleValue();

            boolean between;

            if (doubleArray[0] < doubleArray[1]) {
                between = (leftDouble >= doubleArray[0] && leftDouble <= doubleArray[1]);
            } else {
                between = (leftDouble >= doubleArray[1] && leftDouble <= doubleArray[0]); // no code coverage
            }

            return between;
        }
    },
    NOT_BETWEEN {
        @Override
        public Boolean compare(Object left, Object right) throws InvalidParameterException {
            return !BETWEEN.compare(left, right);
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

    protected Set getSet(Object param) throws InvalidParameterException {
        Set set;
        if (param instanceof Set) {
            set = (Set) param;
        } else {
            throw new InvalidParameterException("Parameter must be a Set");
        }
        return set;
    }
}
