package io.github.vgteam.handlegraph4j.gfa1;

import io.github.vgteam.handlegraph4j.gfa1.line.Line;
import io.github.vgteam.handlegraph4j.gfa1.line.LinkLine;
import io.github.vgteam.handlegraph4j.gfa1.line.PathLine;
import io.github.vgteam.handlegraph4j.gfa1.line.SegmentLine;
import java.util.Iterator;
import java.util.function.Function;

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
/**
 *
 * @author Jerven Bolleman <jerven.bolleman@sib.swiss>
 */
public class GFA1Reader implements Iterator<Line> {

    private final Iterator<String> toWrap;
    private Line next;
    private static final Function[] PARSERS = new Function[Line.MAX_CODE - Line.MIN_CODE];
    private int atLine = 0;

    static {
        PARSERS[SegmentLine.CODE - (Line.MIN_CODE + 1)] = SegmentLine.parser();
        PARSERS[LinkLine.CODE - (Line.MIN_CODE + 1)] = LinkLine.parser();
        PARSERS[PathLine.CODE - (Line.MIN_CODE + 1)] = PathLine.parser();
    }

    public GFA1Reader(Iterator<String> toWrap) {
        this.toWrap = toWrap;
    }

    @Override
    public boolean hasNext() {
        while (next == null && toWrap.hasNext()) {
            String line = toWrap.next();
            if (!line.isEmpty()) {
                try {
                    next = parseLine(line);
                } catch (Exception e) {
                    System.err.println("Error at line :" + atLine);
                    e.printStackTrace();
                    return false;
                }
            }
            atLine++;
        }
        return next != null;
    }

    private Line parseLine(String line) {
        char firstChar = line.charAt(0);
        switch (firstChar) {
            case SegmentLine.CODE:
                return SegmentLine.parseFromString(line);
            case PathLine.CODE:
                return PathLine.parseFromString(line);
            case LinkLine.CODE:
                return LinkLine.parseFromString(line);
            default:
                return null;
        }
    }

    @Override
    public Line next() {
        try {
            return next;
        } finally {
            next = null;
        }
    }
}
