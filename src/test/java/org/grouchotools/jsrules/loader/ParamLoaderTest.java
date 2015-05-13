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
package org.grouchotools.jsrules.loader;

import org.grouchotools.jsrules.Parameter;
import org.grouchotools.jsrules.config.ParamConfig;
import org.grouchotools.jsrules.exception.InvalidConfigException;
import org.grouchotools.jsrules.loader.impl.ParamLoaderImpl;
import org.junit.*;
import org.junit.rules.ExpectedException;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Paul
 */
public class ParamLoaderTest {
    @Rule
    public ExpectedException exception= ExpectedException.none();
    
    private final ParamLoader paramLoader = new ParamLoaderImpl();
    
    private final String paramName = "param";
    
    public ParamLoaderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void loadTest() throws Exception {
        ParamConfig paramConfig = new ParamConfig(paramName, "long", "10");
        Parameter mockParam = new Parameter(paramName, Long.class, 10l);
        
        assertEquals(mockParam, paramLoader.load(paramConfig));
    }
    
    @Test
    public void loadInvalidParamClassTest() throws Exception {
        exception.expect(InvalidConfigException.class);
        
        ParamConfig paramConfig = new ParamConfig(paramName, "loong", "10");
        
        paramLoader.load(paramConfig);
    }
    
    @Test
    public void loadNullStaticValueTest() throws Exception {
        ParamConfig paramConfig = new ParamConfig(paramName, "long", null);
        Parameter mockParam = new Parameter(paramName, Long.class);
        
        assertEquals(mockParam, paramLoader.load(paramConfig));
    }
    
    @Test
    public void loadInvalidStaticValueTest() throws Exception {
        exception.expect(InvalidConfigException.class);
        
        ParamConfig paramConfig = new ParamConfig(paramName, "long", "NaN");
        
        paramLoader.load(paramConfig);
    }

    @Test
    public void loadNullParamClassTest() throws Exception {
        exception.expect(InvalidConfigException.class);

        ParamConfig paramConfig = new ParamConfig(null, null, null);

        paramLoader.load(paramConfig);
    }
}
