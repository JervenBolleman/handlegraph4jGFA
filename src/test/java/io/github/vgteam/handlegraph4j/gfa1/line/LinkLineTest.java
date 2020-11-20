/*
 * The MIT License
 *
 * Copyright 2020 Jerven Bolleman <jerven.bolleman@sib.swiss>.
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
package io.github.vgteam.handlegraph4j.gfa1.line;

import java.util.function.Function;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class LinkLineTest {

    public LinkLineTest() {
    }

    /**
     * Test of parser method, of class LinkLine.
     */
    @Test
    public void testParser() {
        String s = "L\t1\t+\t2\t+\t0M";
        LinkLine expResult = new LinkLine(new byte[]{'1'}, false, new byte[]{'2'}, false);
        Function<String, Line> result = LinkLine.parser();
        assertEquals(expResult, result.apply(s));
    }

    /**
     * Test of parseFromString method, of class LinkLine.
     */
    @Test
    public void testParseFromString() {
        String s = "L\t1\t+\t2\t+\t0M";
        LinkLine expResult = new LinkLine(new byte[]{'1'}, false, new byte[]{'2'}, false);
        LinkLine result = LinkLine.parseFromString(s);
        assertEquals(expResult, result);
    }
    
     @Test
    public void testToString() {
        String s = "L\t1\t+\t2\t+";
        LinkLine expResult = new LinkLine(new byte[]{'1'}, false, new byte[]{'2'}, false);
        assertEquals(s, expResult.toString());
        s = "L\t1\t-\t2\t+";
        expResult = new LinkLine(new byte[]{'1'}, true, new byte[]{'2'}, false);
        assertEquals(s, expResult.toString());
    }
}
