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
package io.github.jervenbolleman.handlegraph4j.gfa2.line;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

import io.github.jervenbolleman.handlegraph4j.sequences.Sequence;
import io.github.jervenbolleman.handlegraph4j.sequences.SequenceType;

/**
 *
 * @author <a href="mailto:jerven.bolleman@sib.swiss">Jerven Bolleman</a>
 */
public class SegmentLine implements Line {
	/**
	 * http://gfa-spec.github.io/GFA-spec/GFA2.html
	 */
    public static final char SEGMENT_CODE = 'S';
    private final byte[] name;
    private final int length;
    private final Sequence sequence;
    private final byte[] tags;

    public SegmentLine(byte[] id, int length, Sequence sequence, byte[] tags) {
        this.name = id;
        this.length = length;
        this.sequence = sequence;
        this.tags = tags;
    }

    //<sid:id> <slen:int> <sequence> <tag>*
    public static Function<String, Line> parser() {
        return (s) -> {
            int nt = s.indexOf('\t');
            byte[] id = s.substring(0, nt).getBytes(StandardCharsets.US_ASCII);
            int pt = nt;
            nt = s.indexOf('\t', nt + 1);
            int length = Integer.parseInt(s.substring(pt, nt));
            pt = nt;
            nt = s.indexOf('\t', pt + 1);
            Sequence seq = SequenceType.fromByteArray(s.substring(pt, nt).getBytes(StandardCharsets.US_ASCII));
            byte[] tags;
            if (nt == s.length()) {
                tags = null;
            } else {
                tags = s.substring(nt + 1).getBytes(StandardCharsets.US_ASCII);
            }
            return new SegmentLine(id, length, seq, tags);
        };
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Arrays.hashCode(this.name);
        hash = 97 * hash + this.length;
        hash = 97 * hash + Objects.hashCode(this.sequence);
        hash = 97 * hash + Arrays.hashCode(this.tags);
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
        final SegmentLine other = (SegmentLine) obj;
        if (this.length != other.length) {
            return false;
        }
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.sequence, other.sequence)) {
            return false;
        }
        if (!Arrays.equals(this.tags, other.tags)) {
            return false;
        }
        return true;
    }
}
