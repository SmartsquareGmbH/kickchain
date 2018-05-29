package de.smartsquare.kickchain.domain;

import java.util.function.BiFunction;

@FunctionalInterface
public interface Proof<T> extends BiFunction<Long, Long, T> {
}
