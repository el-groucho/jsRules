package org.grouchotools.jsrules;

import org.grouchotools.jsrules.exception.InvalidParameterClassException;
import org.grouchotools.jsrules.exception.InvalidParameterException;
import org.grouchotools.jsrules.exception.MissingParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Contains common methods used by various executors
 * <p/>
 * Created by Paul Richardson 4/30/2015
 */
public abstract class Executor {
    private static final Logger LOG = LoggerFactory.getLogger(Executor.class);

    /**
     * Validate that parameter is present (not null) and is an instance of the correct
     * class
     *
     * @param key           the name of the parameter
     * @param parameter     the parameter object
     * @param expectedClass the class we are expecting the parameter to be
     */
    protected void validateParameter(String key, Object parameter, Class expectedClass) throws InvalidParameterException {
        if (parameter == null) {
            LOG.error("Expected Parameter {} is missing", key);
            throw new MissingParameterException();
        } else if (!expectedClass.isInstance(parameter)) {
            LOG.error("Parameter {} is invalid | Expected class: {} | Parameter Class: {}",
                    key, expectedClass, parameter.getClass());
            throw new InvalidParameterClassException();
        }
    }
}
