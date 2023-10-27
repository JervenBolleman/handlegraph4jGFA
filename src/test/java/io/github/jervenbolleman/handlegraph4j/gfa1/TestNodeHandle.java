package io.github.jervenbolleman.handlegraph4j.gfa1;

import io.github.jervenbolleman.handlegraph4j.NodeHandle;
import io.github.jervenbolleman.handlegraph4j.sequences.Sequence;

record TestNodeHandle(long id, boolean reverse, Sequence sequence) implements NodeHandle {

	
	@Override
	public long id() {
		return id;
	}	
}