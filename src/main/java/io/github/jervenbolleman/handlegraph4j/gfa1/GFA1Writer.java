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
package io.github.jervenbolleman.handlegraph4j.gfa1;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import io.github.jervenbolleman.handlegraph4j.EdgeHandle;
import io.github.jervenbolleman.handlegraph4j.HandleGraph;
import io.github.jervenbolleman.handlegraph4j.NodeHandle;
import io.github.jervenbolleman.handlegraph4j.iterators.AutoClosedIterator;
import io.github.jervenbolleman.handlegraph4j.sequences.Sequence;

public class GFA1Writer {

	public static void write(OutputStream io, HandleGraph<NodeHandle, EdgeHandle<NodeHandle>> hg) throws IOException {
		writeHeader(io);
		writeSegments(io, hg);
		writePaths(io, hg);
		writeEdges(io, hg);
	}

	private static void writeEdges(OutputStream io, HandleGraph<NodeHandle, EdgeHandle<NodeHandle>> hg)
			throws IOException {
		try (AutoClosedIterator<EdgeHandle<NodeHandle>> edges = hg.edges()) {
			while (edges.hasNext()) {
				io.write('L');
				io.write('\t');
				EdgeHandle<NodeHandle> edge = edges.next();
				final NodeHandle left = edge.left();
				writeEdgeNode(io, hg, left);
				io.write('\t');
				writeEdgeNode(io, hg, edge.right());
				io.write('\n');
			}
		}

	}

	public static void writeEdgeNode(OutputStream io, HandleGraph<NodeHandle, EdgeHandle<NodeHandle>> hg,
			final NodeHandle left) throws IOException {
		writeNodeId(io, hg, left);
		io.write('\t');
		if (hg.isReverseNodeHandle(left)) {
			io.write('-');
		} else {
			io.write('+');
		}
	}

	private static void writePaths(OutputStream io, HandleGraph<?, ?> hg) {
		// TODO Auto-generated method stub

	}

	private static void writeSegments(OutputStream io, HandleGraph<NodeHandle, ?> hg) throws IOException {
		try (AutoClosedIterator<NodeHandle> nodes = hg.nodes()) {
			while (nodes.hasNext()) {
				io.write('S');
				io.write('\t');
				NodeHandle node = nodes.next();
				writeNodeId(io, hg, node);
				io.write('\t');
				Sequence seq = hg.sequenceOf(node);
				io.write(seq.asAsciiBytes());
				io.write('\n');
			}
		}

	}

	public static void writeNodeId(OutputStream io, HandleGraph<NodeHandle, ?> hg, NodeHandle node) throws IOException {
		final long nId = hg.asLong(node);
		io.write(Long.toString(nId).getBytes(StandardCharsets.US_ASCII));
	}

	private static void writeHeader(OutputStream io) throws IOException {
		io.write(new byte[] { 'H', '\t', 'V', 'N', ':', 'Z', ':', '1', '.', '0', '\n' });
	}

}
