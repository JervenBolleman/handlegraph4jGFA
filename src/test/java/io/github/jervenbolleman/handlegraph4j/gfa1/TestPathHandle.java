package io.github.jervenbolleman.handlegraph4j.gfa1;

import java.util.List;

import io.github.jervenbolleman.handlegraph4j.PathHandle;

public record TestPathHandle(List<TestStepHandle> steps, boolean circular, String name) implements PathHandle {

}
