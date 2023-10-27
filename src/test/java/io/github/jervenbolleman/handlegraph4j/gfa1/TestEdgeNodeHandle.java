package io.github.jervenbolleman.handlegraph4j.gfa1;

import io.github.jervenbolleman.handlegraph4j.EdgeHandle;

record TestEdgeNodeHandle(TestNodeHandle right, TestNodeHandle left) implements EdgeHandle<TestNodeHandle> {

}
