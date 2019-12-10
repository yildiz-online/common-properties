/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.common.properties;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


/**
 * @author Grégory Van den Borre
 */
class PropertiesHelperTest {

    @Test
    void testGetPropertiesFromFile() {
        Properties p =  getPropertiesFromFile("test.properties");
        Assertions.assertEquals("value1", p.getProperty("key1"));
        Assertions.assertEquals("value2", p.getProperty("key2"));
        Assertions.assertEquals("value3", p.getProperty("key3"));
    }

    @Nested
    class GetBoolean {

        @Test
        void nullProperties() {
            Assertions.assertThrows(NullPointerException.class, () -> PropertiesHelper.getBooleanValue(null, "key5"));
        }

        @Test
        void nullKey() {
            Properties p = getPropertiesFromFile("test.properties");
            Assertions.assertThrows(NullPointerException.class, () -> PropertiesHelper.getBooleanValue(p, null));
        }

        @Test
        void keyNotFound() {
            Properties p = getPropertiesFromFile("test.properties");
            Assertions.assertThrows(PropertiesException.class, () -> PropertiesHelper.getBooleanValue(p, "notexistingkey"));
        }

        @Test
        void getDefault() {
            Properties p = getPropertiesFromFile("test.properties");
            Assertions.assertFalse(PropertiesHelper.getBooleanValue(p, "notExistingKey", false));
            Assertions.assertTrue(PropertiesHelper.getBooleanValue(p, "notExistingKey", true));
        }

        @Test
        void happyFlowDefault() {
            Properties p = getPropertiesFromFile("test.properties");
            Assertions.assertFalse(PropertiesHelper.getBooleanValue(p, "key5", true));
            Assertions.assertTrue(PropertiesHelper.getBooleanValue(p, "key4", false));
        }

        @Test
        void happyFlow() {
            Properties p = getPropertiesFromFile("test.properties");
            Assertions.assertFalse(PropertiesHelper.getBooleanValue(p, "key5"));
            Assertions.assertTrue(PropertiesHelper.getBooleanValue(p, "key4"));
        }

    }

    private static Properties getPropertiesFromFile(String path) {
        Properties p = new Properties();
        try (InputStream is = PropertiesHelperTest.class.getClassLoader().getResourceAsStream(path)) {
            p.load(is);
        } catch (IOException e) {
            throw new IllegalArgumentException(e);
        }
        return p;
    }

}
