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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import io.github.jervenbolleman.handlegraph4j.gfa1.line.Line;

/**
 *
 * @author <a href="mailto:jerven.bolleman@sib.swiss">Jerven Bolleman</a>
 */
public class GFA1WriterTest {
	private static final String TEST_DATA = """
			H\tVN:Z:1.0
			S\t1\tCAAATAAG
			S\t2\tA
			S\t3\tG
			S\t4\tT
			S\t5\tC
			S\t6\tTTG
			S\t7\tA
			S\t8\tG
			S\t9\tAAATTTTCTGGAGTTCTAT
			S\t10\tA
			S\t11\tT
			S\t12\tATAT
			S\t13\tA
			S\t14\tT
			S\t15\tCCAACTCTCTG
			P\tx\t1+,3+,5+,6+,8+,9+,11+,12+,14+,15+\t8M,1M,1M,3M,1M,19M,1M,4M,1M,11M
			L\t1\t+\t2\t+\t0M
			L\t1\t+\t3\t+\t0M
			L\t2\t+\t4\t+\t0M
			L\t2\t+\t5\t+\t0M
			L\t3\t+\t4\t+\t0M
			L\t3\t+\t5\t+\t0M
			L\t4\t+\t6\t+\t0M
			L\t5\t+\t6\t+\t0M
			L\t6\t+\t7\t+\t0M
			L\t6\t+\t8\t+\t0M
			L\t7\t+\t9\t+\t0M
			L\t8\t+\t9\t+\t0M
			L\t9\t+\t10\t+\t0M
			L\t9\t+\t11\t+\t0M
			L\t10\t+\t12\t+\t0M
			L\t11\t+\t12\t+\t0M
			L\t12\t+\t13\t+\t0M
			L\t12\t+\t14\t+\t0M
			L\t13\t+\t15\t+\t0M
			L\t14\t+\t15\t+\t0M""";

	@Test
	public void testByCountingLineTypes() {
		int paths = 0;
		int max_path = 1;
		int links = 0;
		int max_links = 20;
		int nodes = 0;
		int max_nodes = 15;
		GFA1Reader gfA1Reader = new GFA1Reader(Arrays.asList(TEST_DATA.split("\n")).iterator());
		while (gfA1Reader.hasNext()) {
			Line line = gfA1Reader.next();
			assertNotNull(line);
			if (line.getCode() == 'S') {
				nodes++;
			}
			if (line.getCode() == 'P') {
				paths++;
			}
			if (line.getCode() == 'L') {
				links++;
			}
		}
		assertEquals(max_links, links);
		assertEquals(max_path, paths);
		assertEquals(max_nodes, nodes);
	}
}
