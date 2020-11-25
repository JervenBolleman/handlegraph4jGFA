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

import static java.nio.charset.StandardCharsets.US_ASCII;
import java.util.Arrays;
import java.util.function.Function;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class LinkLine implements Line {

    public static final char CODE = 'L';
    private final byte[] from;
    private final boolean reverseComplimentOfFrom;
    private final byte[] to;
    private final boolean reverseComplimentOfTo;

    public LinkLine(byte[] from, boolean reverseComplimentOfFrom, byte[] to, boolean reverseComplimentOfTo) {
        this.from = from;
        this.reverseComplimentOfFrom = reverseComplimentOfFrom;
        this.to = to;
        this.reverseComplimentOfTo = reverseComplimentOfTo;
    }

    public static Function<String, Line> parser() {
        return LinkLine::parseFromString;
    }

    public static LinkLine parseFromString(String s) {
//      From 	String 	[!-)+-<>-~][!-~]* 	Name of segment
// 	FromOrient 	String 	+\|- 	Orientation of From segment
// 	To 	String 	[!-)+-<>-~][!-~]* 	Name of segment
// 	ToOrient 	String 	+\|-
        int nt = s.indexOf('\t', 2);
        byte[] fromId = s.substring(2, nt).getBytes(US_ASCII);
        boolean reverseComplimentOfFrom = s.charAt(nt + 1) == '-';
        int pt = nt + 3;
        nt = s.indexOf('\t', pt);
        byte[] toId = s.substring(pt, nt).getBytes(US_ASCII);
        boolean reverseComplimentOfTo = s.charAt(nt + 1) == '-';
        return new LinkLine(fromId, reverseComplimentOfFrom, toId, reverseComplimentOfTo);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 13 * hash + Arrays.hashCode(this.from);
        hash = 13 * hash + (this.reverseComplimentOfFrom ? 1 : 0);
        hash = 13 * hash + Arrays.hashCode(this.to);
        hash = 13 * hash + (this.reverseComplimentOfTo ? 1 : 0);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final LinkLine other = (LinkLine) obj;
        if (this.reverseComplimentOfFrom != other.reverseComplimentOfFrom) {
            return false;
        }
        if (this.reverseComplimentOfTo != other.reverseComplimentOfTo) {
            return false;
        }
        if (!Arrays.equals(this.from, other.from)) {
            return false;
        }
        return Arrays.equals(this.to, other.to);
    }

    @Override
    public String toString() {
        return "L\t" + new String(from, US_ASCII) + "\t" + (reverseComplimentOfFrom ? '-' : '+') + '\t' + new String(to, US_ASCII) + '\t' + (reverseComplimentOfTo ? '-' : '+');
    }

    @Override
    public int getCode() {
        return CODE;
    }

    public String getToNameAsString() {
        return new String(to, US_ASCII);
    }

    public String getFromNameAsString() {
        return new String(from, US_ASCII);
    }

    public boolean isReverseComplimentOfFrom() {
        return reverseComplimentOfFrom;
    }

    public boolean isReverseComplimentOfTo() {
        return reverseComplimentOfTo;
    }
}
