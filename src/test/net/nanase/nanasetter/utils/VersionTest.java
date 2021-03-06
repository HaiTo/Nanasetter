/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2014 Tomona Nanase
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package net.nanase.nanasetter.utils;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Project: Nanasetter
 * Created by nanase on 14/05/18.
 */


public class VersionTest {
    @Test
    public void testEquals() throws Exception {
        final Version ver0a = new Version(1, 2, 42, 1982, "-rc2");
        final Version ver0b = new Version(1, 2, 42, 1982, "-rc2");

        final Version ver1a = new Version(3);
        final Version ver1b = new Version(3);

        assertTrue(ver0a.equals(ver0b));
        assertTrue(ver1a.equals(ver1b));
    }

    @Test
    public void testToString() throws Exception {
        final Version ver0 = new Version(1, 2, 42, 1982, "-rc2");
        final Version ver1 = new Version(1, 2, 42, 1982);
        final Version ver2 = new Version(1, 2, 42);
        final Version ver3 = new Version(1, 2);
        final Version ver4 = new Version(1);
        final Version ver5 = Version.parse("1.2a2");

        assertEquals(ver0.toString(), "1.2.42.1982-rc2");
        assertEquals(ver1.toString(), "1.2.42.1982");
        assertEquals(ver2.toString(), "1.2.42");
        assertEquals(ver3.toString(), "1.2");
        assertEquals(ver4.toString(), "1");
        assertEquals(ver5.toString(), "1.2a2");
    }

    @Test
    public void testCompareTo() throws Exception {
        final Version ver0a = new Version(3);
        final Version ver0b = new Version(5);

        final Version ver1a = new Version(3, 1);
        final Version ver1b = new Version(3, 0);

        final Version ver2a = new Version(3, 1, 6);
        final Version ver2b = new Version(3, 1, 4);

        final Version ver3a = new Version(3, 1, 4, 3);
        final Version ver3b = new Version(3, 1, 4, 1);
        final Version ver3c = new Version(3, 1, 4, 1);
        final Version ver3d = new Version(3, 1, 4, 1, "b5");

        assertTrue(ver0a.compareTo(ver0b) <= -1);
        assertTrue(ver1a.compareTo(ver1b) >= 1);
        assertTrue(ver2a.compareTo(ver2b) >= 1);
        assertTrue(ver3a.compareTo(ver3b) >= 1);
        assertTrue(ver3b.compareTo(ver3c) == 0);
        assertTrue(ver3b.compareTo(ver3d) == 0);
    }

    @Test
    public void testParse() throws Exception {
        // legal pattern
        final Version ver0 = Version.parse("1.2.42.1982-rc2");
        final Version expect0 = new Version(1, 2, 42, 1982, "-rc2");

        final Version ver1 = Version.parse("1.2.42.1982");
        final Version expect1 = new Version(1, 2, 42, 1982);

        final Version ver2 = Version.parse("1.2.42");
        final Version expect2 = new Version(1, 2, 42);

        final Version ver3 = Version.parse("1.2");
        final Version expect3 = new Version(1, 2);

        final Version ver4 = Version.parse("1");
        final Version expect4 = new Version(1);

        final Version ver5 = Version.parse("1.2a2");
        final Version expect5 = new Version(1, 2, -1, -1, "a2");

        assertEquals(ver0, expect0);
        assertEquals(ver1, expect1);
        assertEquals(ver2, expect2);
        assertEquals(ver3, expect3);
        assertEquals(ver4, expect4);
        assertEquals(ver5, expect5);

        // illegal pattern
        final String[] illegals = {
                null,
                "",
                "version",
                "ver1.2",
                "3..4",
                "5.6 rc1",      // space is illegal
                "7.-1",
                "2147483648"    // overflow!
        };

        for (String illegal : illegals) {
            try {
                Version.parse(illegal);
                fail("No exception thrown: \"" + illegal + "\"");
            } catch (IllegalArgumentException e) {
                // legal behavior
            } catch (Exception e) {
                // unexpected exception
                fail();
            }
        }
    }
}