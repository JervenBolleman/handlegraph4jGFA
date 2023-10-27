package io.github.jervenbolleman.handlegraph4j.gfa1;

import java.util.List;
import java.util.stream.LongStream;

import io.github.jervenbolleman.handlegraph4j.HandleGraph;
import io.github.jervenbolleman.handlegraph4j.PathGraph;
import io.github.jervenbolleman.handlegraph4j.iterators.AutoClosedIterator;
import io.github.jervenbolleman.handlegraph4j.sequences.Sequence;

class PathHandleGraphForTests implements HandleGraph<TestNodeHandle, TestEdgeNodeHandle>,
		PathGraph<TestPathHandle, TestStepHandle, TestNodeHandle, TestEdgeNodeHandle> {
	private final List<TestPathHandle> paths;

	public PathHandleGraphForTests(List<TestPathHandle> paths) {
		super();
		this.paths = paths;
	}

	@Override
	public AutoClosedIterator<TestPathHandle> paths() {
		return AutoClosedIterator.from(paths.iterator());
	}

	@Override
	public AutoClosedIterator<TestStepHandle> steps() {
		return AutoClosedIterator.from(paths.stream().map(TestPathHandle::steps).flatMap(List::stream).iterator());
	}

	@Override
	public AutoClosedIterator<TestStepHandle> stepsOf(TestPathHandle path) {
		
		return AutoClosedIterator.from(path.steps().iterator());
	}

	@Override
	public TestPathHandle pathOfStep(TestStepHandle step) {
		return step.path();
	}

	@Override
	public TestNodeHandle nodeOfStep(TestStepHandle step) {
		return step.node();
	}

	@Override
	public long beginPositionOfStep(TestStepHandle step) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long endPositionOfStep(TestStepHandle step) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public long rankOfStep(TestStepHandle step) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public TestStepHandle stepByRankAndPath(TestPathHandle path, long rank) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isCircular(TestPathHandle path) {
		return path.circular();
	}

	@Override
	public String nameOfPath(TestPathHandle path) {
		return path.name();
	}

	@Override
	public TestPathHandle pathByName(String name) {
		for (TestPathHandle path:paths)
			if (path.name().equals(name))
				return path;
		return null;
	}

	@Override
	public LongStream positionsOf(TestPathHandle path) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isReverseNodeHandle(TestNodeHandle nh) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public TestNodeHandle flip(TestNodeHandle nh) {
		return new TestNodeHandle(nh.id(), ! nh.reverse(), nh.sequence());
	}

	@Override
	public long asLong(TestNodeHandle nh) {
		return nh.id();
	}

	@Override
	public TestNodeHandle fromLong(long id) {
		throw new UnsupportedOperationException();
	}

	@Override
	public TestEdgeNodeHandle edge(long leftId, long rightId) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AutoClosedIterator<TestEdgeNodeHandle> followEdgesToWardsTheRight(TestNodeHandle left) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AutoClosedIterator<TestEdgeNodeHandle> followEdgesToWardsTheLeft(TestNodeHandle right) {
		throw new UnsupportedOperationException();
	}

	@Override
	public AutoClosedIterator<TestEdgeNodeHandle> edges() {
		throw new UnsupportedOperationException();
	}

	@Override
	public AutoClosedIterator<TestNodeHandle> nodes() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Sequence sequenceOf(TestNodeHandle handle) {
		return handle.sequence();
	}

	@Override
	public AutoClosedIterator<TestNodeHandle> nodesWithSequence(Sequence s) {
		throw new UnsupportedOperationException();
	}

}
