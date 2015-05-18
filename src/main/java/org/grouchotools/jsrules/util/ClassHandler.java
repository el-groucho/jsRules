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
package org.grouchotools.jsrules.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.grouchotools.jsrules.exception.ClassHandlerException;

import java.io.IOException;
import java.util.Set;

/**
 *
 * @author Paul
 */
public enum ClassHandler {
    BOOLEAN {
        @Override
        public Class getMyClass() {
            return Boolean.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Boolean convertString(String string) {
            return Boolean.parseBoolean(string);
        }
    },
    DOUBLE {
        @Override
        public Class getMyClass() {
            return Double.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Double convertString(String string) {
            return Double.parseDouble(string);
        }
    },
    LONG {
        @Override
        public Class getMyClass() {
            return Long.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Long convertString(String string) {
            return Long.parseLong(string);
        }
    },
    STRING {
        @Override
        public Class getMyClass() {
            return String.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public String convertString(String string) {
            return string;
        }
    },
    NUMBERSET {
        @Override
        public Class getMyClass() {
            return Set.class;
        }

        @Override
        @SuppressWarnings("unchecked")
        public Set<Number> convertString(String string) throws ClassHandlerException {
            Set<Number> numberSet;
                    
            try {
                numberSet = MAPPER.readValue(string, Set.class);
            } catch (IOException ex) {
                throw new ClassHandlerException("Unable to convert " + string + " into a Set of Numbers", ex);
            }

            return numberSet;
        }
    };
    
    public abstract Class getMyClass();
    public abstract <T> T convertString(String string) throws ClassHandlerException;

    private static final ObjectMapper MAPPER = new ObjectMapper();
}
