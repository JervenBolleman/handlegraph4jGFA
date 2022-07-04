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
package io.github.jervenbolleman.handlegraph4j.gfa1.line;

import static java.nio.charset.StandardCharsets.US_ASCII;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.StringTokenizer;
import java.util.function.Function;

/**
 *
 * @author <a href="mailto:jerven.bolleman@sib.swiss">Jerven Bolleman</a>
 */
public class PathLine implements Line {

	/**
	 * Path lines start with a L http://gfa-spec.github.io/GFA-spec/GFA1.html#p-path-line
	 */
	public static final char CODE = 'P';
	private final byte[] name;
	private final BitSet reverseComplement;
	private final long[] intNodeIds;
	private final byte[][] stringNodeIds;

	public PathLine(byte[] name, BitSet revereseCompliment, long[] nodeid) {
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

	// <sid:id> <slen:int> <sequence> <tag>*
	public static Function<String, Line> parser() {
		return PathLine::parseFromString;
	}

	public static PathLine parseFromString(String s) {
		StringTokenizer tok = new StringTokenizer(s, "\t");
		String shouldBeP = tok.nextToken("\t"); // Skip first "P"
		assert "P".equals(shouldBeP);
		String pathIdS = tok.nextToken();
		byte[] pathId = pathIdS.getBytes(US_ASCII);
		final BitSet orientation = new BitSet();
		final List<String> bl = new ArrayList<>();
		parsePathPart(tok, orientation, bl);
		try {
			return makeLongPathLine(pathId, orientation, bl);
		} catch (NumberFormatException e) {
			return makeStringPathLine(pathId, orientation, bl);
		}
	}

	private static void parsePathPart(StringTokenizer tok, final BitSet orientation, final List<String> bl) {
		int i = 0;
		StringTokenizer segments = new StringTokenizer(tok.nextToken(), ",");
		while (segments.hasMoreTokens()) {
			String segment = segments.nextToken();
			if (segment.length() > 0) {
				bl.add(segment.substring(0, segment.length() - 1));
				if (segment.charAt(segment.length() - 1) == '-') {
					orientation.set(i);
				}
			}
			i++;
		}
	}

	private static PathLine makeStringPathLine(byte[] pathId, final BitSet bitSet, final List<String> bl) {
		int i;
		byte[][] bytes = new byte[bl.size()][];
		for (i = 0; i < bl.size(); i++) {
			bytes[i] = bl.get(i).getBytes(US_ASCII);
		}
		return new PathLine(pathId, bitSet, bytes);
	}

	private static PathLine makeLongPathLine(byte[] pathId, final BitSet bitSet, final List<String> bl) {
		int i;
		long[] ids = new long[bl.size()];
		for (i = 0; i < bl.size(); i++) {
			String get = bl.get(i);
			ids[i] = Long.parseLong(get);		
		}
		return new PathLine(pathId, bitSet, ids);
	}

	@Override
	public String toString() {
		StringBuilder path = new StringBuilder();
		if (stringNodeIds == null) {
			for (int i = 0; i < intNodeIds.length; i++) {
				path.append(Long.toString(intNodeIds[i]));
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
						return new LongStep(at, isReverseCompliment, intNodeIds[(int) at]);
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

		public boolean nodeHasLongId();

		public long nodeLongId();
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
		public boolean nodeHasLongId() {
			return false;
		}

		@Override
		public long nodeLongId() {
			throw new IllegalStateException("Check nodeHasIntId before calling"); // To change body of generated
																					// methods, choose Tools |
																					// Templates.
		}
	}

	private class LongStep implements Step {

		private final long rank;
		private final boolean isReverseComplement;
		private final long intNodeId;

		private LongStep(long rank, boolean isReverseComplement, long intNodeId) {
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
			return Long.toString(intNodeId).getBytes(US_ASCII);
		}

		@Override
		public boolean nodeHasLongId() {
			return true;
		}

		@Override
		public long nodeLongId() {
			return intNodeId;
		}
	}

	public int numberOfSteps() {
		if (stringNodeIds != null) {
			return stringNodeIds.length;
		} else {
			return intNodeIds.length;
		}
	}
}
