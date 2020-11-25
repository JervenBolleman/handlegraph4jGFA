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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;

/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class PathLine implements Line {

    public static final char CODE = 'P';
    private final byte[] name;
    private final BitSet reverseComplement;
    private final int[] intNodeIds;
    private final byte[][] stringNodeIds;

    public PathLine(byte[] name, BitSet revereseCompliment, int[] nodeid) {
        this.name = name;
        this.reverseComplement = revereseCompliment;
        this.intNodeIds = nodeid;
        this.stringNodeIds = null;
    }

    public PathLine(byte[] name, BitSet revereseCompliment, byte[][] stringNodeIds) {
        this.name = name;
        this.reverseComplement = revereseCompliment;
        this.intNodeIds = null;
        this.stringNodeIds = stringNodeIds;
    }

    //<sid:id> <slen:int> <sequence> <tag>*
    public static Function<String, Line> parser() {
        return PathLine::parseFromString;
    }

    public static PathLine parseFromString(String s) {
        int nt = s.indexOf('\t', 2);
        byte[] pathId = s.substring(2, nt).getBytes(US_ASCII);
        nt = nt + pathId.length;
        int nc = nt;
        int pc = nc;
        nt = s.indexOf('\t', nt);
        if (nt < 0) {
            nt = s.length();
        }
        int i = 0;
        final BitSet bitSet = new BitSet();
        final List<String> bl = new ArrayList<>();
        while (nc != -1 && nc <= nt) {
            nc = s.indexOf(',', nc + 1);
            String seg;
            if (nc < 0 || nc > nt) {
                seg = s.substring(pc, nt);
            } else {
                seg = s.substring(pc, nc);
            }
            bl.add(seg.substring(0, seg.length() - 1));
            if (seg.charAt(seg.length() - 1) == '-') {
                bitSet.set(i);
            }
            pc = nc + 1;
            i++;
        }
        try {
            int[] ids = new int[bl.size()];
            for (i = 0; i < bl.size(); i++) {
                String get = bl.get(i);
                ids[i] = Integer.parseInt(get);
            }
            return new PathLine(pathId, bitSet, ids);
        } catch (NumberFormatException e) {
            byte[][] bytes = new byte[bl.size()][];
            for (i = 0; i < bl.size(); i++) {
                bytes[i] = bl.get(i).getBytes(US_ASCII);
            }
            return new PathLine(pathId, bitSet, bytes);
        }

    }

    @Override
    public String toString() {
        StringBuilder path = new StringBuilder();
        if (stringNodeIds == null) {
            for (int i = 0; i < intNodeIds.length; i++) {
                path.append(Integer.toString(intNodeIds[i]));
                if (reverseComplement.get(i)) {
                    path.append('-');
                } else {
                    path.append('+');
                }
                if (i != intNodeIds.length - 1) {
                    path.append(',');
                }
            }
        } else {
            for (int i = 0; i < stringNodeIds.length; i++) {
                path.append(new String(stringNodeIds[i], US_ASCII));
                if (reverseComplement.get(i)) {
                    path.append('-');
                } else {
                    path.append('+');
                }
                if (i != stringNodeIds.length - 1) {
                    path.append(',');
                }
            }
        }
        return "P\t" + new String(name, US_ASCII) + "\t" + path;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Arrays.hashCode(this.name);
        hash = 97 * hash + Objects.hashCode(this.reverseComplement);
        hash = 97 * hash + Arrays.hashCode(this.intNodeIds);
        hash = 97 * hash + Arrays.deepHashCode(this.stringNodeIds);
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
        final PathLine other = (PathLine) obj;
        if (!Arrays.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.reverseComplement, other.reverseComplement)) {
            return false;
        }
        if (!Arrays.equals(this.intNodeIds, other.intNodeIds)) {
            return false;
        }
        return Arrays.deepEquals(this.stringNodeIds, other.stringNodeIds);
    }

    @Override
    public int getCode() {
        return CODE;
    }

    public String getNameAsString() {
        return new String(name, US_ASCII);
    }

    public Iterator<Step> steps() {
        long max;
        if (stringNodeIds != null) {
            max = stringNodeIds.length;
        } else {
            max = intNodeIds.length;
        }
        return new Iterator<Step>() {
            private long at = 0;

            @Override
            public boolean hasNext() {
                return at < max;
            }

            @Override
            public Step next() {
                try {
                    boolean isReverseCompliment = reverseComplement.get((int) at);
                    if (stringNodeIds != null) {

                        return new StringStep(at, isReverseCompliment, stringNodeIds[(int) at]);
                    } else {
                        return new IntStep(at, isReverseCompliment, intNodeIds[(int) at]);
                    }
                } finally {
                    at++;
                }
            }

            @Override
            public void remove() {
            }
        };
    }

    public interface Step {

        public long rank();

        public boolean isReverseComplement();

        public byte[] nodeId();
        
        public boolean nodeHasIntId();
        
        public int nodeIntId();
    }

    private class StringStep implements Step {

        private final long rank;
        private final boolean isReverseComplement;
        private final byte[] stringNodeId;

        private StringStep(long rank, boolean isReverseComplement, byte[] stringNodeId) {
            this.rank = rank;
            this.isReverseComplement = isReverseComplement;
            this.stringNodeId = stringNodeId;
        }

        @Override
        public long rank() {
            return rank;
        }

        @Override
        public boolean isReverseComplement() {
            return isReverseComplement;
        }

        @Override
        public byte[] nodeId() {
            return stringNodeId;
        }

        @Override
        public boolean nodeHasIntId() {
            return false;
        }

        @Override
        public int nodeIntId() {
            throw new IllegalStateException("Check nodeHasIntId before calling"); //To change body of generated methods, choose Tools | Templates.
        }
    }

    private class IntStep implements Step {

        private final long rank;
        private final boolean isReverseComplement;
        private final int intNodeId;

        private IntStep(long rank, boolean isReverseComplement, int intNodeId) {
            this.rank = rank;
            this.isReverseComplement = isReverseComplement;
            this.intNodeId = intNodeId;

        }

        @Override
        public long rank() {
            return rank;
        }

        @Override
        public boolean isReverseComplement() {
            return isReverseComplement;
        }

        @Override
        public byte[] nodeId() {
            return Integer.toString(intNodeId).getBytes(US_ASCII);
        }
        
         @Override
        public boolean nodeHasIntId() {
            return true;
        }

        @Override
        public int nodeIntId() {
            return intNodeId;
        }
    }
}
