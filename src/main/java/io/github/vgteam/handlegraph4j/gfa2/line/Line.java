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
package io.github.vgteam.handlegraph4j.gfa2.line;

import io.github.vgteam.handlegraph4j.gfa2.line.EdgeLine;
import io.github.vgteam.handlegraph4j.gfa2.line.FragmentLine;
import io.github.vgteam.handlegraph4j.gfa2.line.GapLine;
import io.github.vgteam.handlegraph4j.gfa2.line.HeaderLine;
import io.github.vgteam.handlegraph4j.gfa2.line.OGroupLine;
import io.github.vgteam.handlegraph4j.gfa2.line.SegmentLine;
import io.github.vgteam.handlegraph4j.gfa2.line.UGroupLine;
import java.util.Arrays;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public interface Line {

    public static final char[] KNOWN_LINE_TYPE_CODES = new char[]{EdgeLine.EDGE_CODE,
        FragmentLine.FRAGMENT_CODE, GapLine.GAP_CODE, HeaderLine.HEADER_CODE, OGroupLine.O_GROUP_CODE, SegmentLine.SEGMENT_CODE,
        UGroupLine.U_GROUP_LINE_CODE
    };
    public static final int[] KNOWN_LINE_TYPE_CODES_AS_INT = new int[]{EdgeLine.EDGE_CODE,
        FragmentLine.FRAGMENT_CODE, GapLine.GAP_CODE, HeaderLine.HEADER_CODE, OGroupLine.O_GROUP_CODE, SegmentLine.SEGMENT_CODE,
        UGroupLine.U_GROUP_LINE_CODE
    };
    public static final char MIN_CODE = (char) Arrays.stream(KNOWN_LINE_TYPE_CODES_AS_INT).min().getAsInt();
    public static final char MAX_CODE = (char) Arrays.stream(KNOWN_LINE_TYPE_CODES_AS_INT).max().getAsInt();
}
