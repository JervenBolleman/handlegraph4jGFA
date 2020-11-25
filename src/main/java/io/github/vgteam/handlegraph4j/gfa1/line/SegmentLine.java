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

import io.github.vgteam.handlegraph4j.sequences.Sequence;
import io.github.vgteam.handlegraph4j.sequences.SequenceType;
import static java.nio.charset.StandardCharsets.US_ASCII;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class SegmentLine implements Line {

    public static final char CODE = 'S';
    private final byte[] name;
    private final Sequence sequence;

    public SegmentLine(byte[] name, Sequence sequence) {
        this.name = name;
        this.sequence = sequence;
    }

    //<sid:id> <slen:int> <sequence> <tag>*
    public static Function<String, Line> parser() {
        return SegmentLine::parseFromString;
    }

    public static SegmentLine parseFromString(String s) {
        int nt = s.indexOf('\t', 2);
        byte[] id = s.substring(2, nt).getBytes(US_ASCII);
        Sequence seq;
        int secondTab = s.indexOf('\t', nt + 1);
        if (secondTab == -1) {
            seq = SequenceType.fromByteArray(s.substring(nt + 1).getBytes(US_ASCII));
        } else {
            seq = SequenceType.fromByteArray(s.substring(nt + 1, secondTab).getBytes(US_ASCII));
        }
        return new SegmentLine(id, seq);
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + Arrays.hashCode(this.name);
        hash = 19 * hash + Objects.hashCode(this.sequence);
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
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        return Objects.equals(this.sequence, other.sequence);
    }

    public String toString() {
        return "S\t" + new String(name, US_ASCII) + '\t' + sequence.toString();
    }

    public byte[] getName() {
        return name;
    }

    public CharSequence getNameAsCharSequence() {
        return new ByteCharSequence(name);
    }

    public String getNameAsString() {
        return new String(name, US_ASCII);
    }

    public Sequence getSequence() {
        return sequence;
    }

    @Override
    public int getCode() {
        return CODE;
    }
}
