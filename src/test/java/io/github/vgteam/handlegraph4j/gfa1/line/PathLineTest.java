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

import java.util.BitSet;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class PathLineTest {
    
    public PathLineTest() {
    }

    /**
     * Test of parser method, of class PathLine.
     */
    @Test
    public void testParser() {
        String s = "P\tx\t1+,3+,5+,6+,8+,9+,11+,12+,14+,15+\t8M,1M,1M,3M,1M,19M,1M,4M,1M,11M";
        PathLine expResult = new PathLine(new byte[]{'x'}, new BitSet(),new int[]{1,3,5,6,8,9,11,12,14,15});
        Function<String, Line> result = PathLine.parser();
        assertEquals(expResult, result.apply(s));
    }

    /**
     * Test of parseFromString method, of class PathLine.
     */
    @Test
    public void testParseFromString() {
        String s = "P\tx\t1+,3+,5+,6+,8+,9+,11+,12+,14+,15+\t8M,1M,1M,3M,1M,19M,1M,4M,1M,11M";
        PathLine expResult = new PathLine(new byte[]{'x'}, new BitSet(),new int[]{1,3,5,6,8,9,11,12,14,15});
        PathLine result = PathLine.parseFromString(s);
        assertEquals(expResult, result);
    }   
}
