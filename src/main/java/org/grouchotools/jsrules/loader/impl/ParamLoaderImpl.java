/*
 * The MIT License
 *
 * Copyright 2015 Paul.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package org.grouchotools.jsrules.loader.impl;

import org.grouchotools.jsrules.Parameter;
import org.grouchotools.jsrules.config.ParamConfig;
import org.grouchotools.jsrules.exception.ClassHandlerException;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.loader.ParamLoader;
import org.grouchotools.jsrules.util.ClassHandler;

/**
 *
 * @author Paul
 */
public class ParamLoaderImpl implements ParamLoader {

    /**
     *
     * @param config
     * @return
     * @throws org.grouchotools.jsrules.exception.InvalidConfigException
     */
    @Override
    public Parameter load(ParamConfig config) throws InvalidConfigException {
        String parameterClassName = config.getParameterClass().toUpperCase();
        ClassHandler handler;
        try {
            handler = ClassHandler.valueOf(parameterClassName);
        } catch (IllegalArgumentException ex) {
            throw new InvalidConfigException(parameterClassName+" is not a supported class", ex);
        }
        Class paramClass = handler.getMyClass();
        String paramStaticValue = config.getParameterStaticValue();
        Object paramStaticObject = null;
        if (paramStaticValue != null) {
            try {
                paramStaticObject = handler.convertString(paramStaticValue);
            } catch (ClassHandlerException|IllegalArgumentException ex) {
                throw new InvalidConfigException(paramStaticValue+" is not a valid "+parameterClassName, ex);
            }
        }
        return new Parameter(config.getParameterName(), paramClass, 
                paramStaticObject);
    }

}
