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

import io.github.vgteam.handlegraph4j.sequences.SequenceType;
import java.util.function.Function;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class SegmentLineTest {
    
    public SegmentLineTest() {
    }

    /**
     * Test of parser method, of class SegmentLine.
     */
    @Test
    public void testParser() {
        String s = "S\t1\tCAAATAAG";
        Function<String, Line> result = SegmentLine.parser();
        final SegmentLine expected = new SegmentLine(new byte[]{'1'}, SequenceType.fromByteArray(new byte[]{'C', 'A', 'A', 'A', 'T', 'A', 'A', 'G'}));
        assertEquals(expected, SegmentLine.parseFromString(s));
        assertEquals(expected, result.apply(s));
    }
    
}
