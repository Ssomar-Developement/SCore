package com.ssomar.score.utils.strings;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringJoiner {

	public static <T> String join(final T[] array, final String delimiter) {
		return join(array, delimiter, object -> Optional.ofNullable(object)
				.map(StringSimplifier::simplify)
				.orElse(""));
	}

	public static <T> String join(final Iterable<T> iterable, final String delimiter) {
		return join(iterable, delimiter, object -> Optional.ofNullable(object)
				.map(StringSimplifier::simplify)
				.orElse(""));
	}

	public static <T> String join(@NotNull final T[] array, final Stringer<T> stringer) {
		return join(array, ", ", stringer);
	}

	public static <T> String join(@NotNull final T[] array, final String delimiter, final Stringer<T> stringer) {
		return join(Arrays.asList(array), delimiter, stringer);
	}

	public static <T> String join(final Iterable<T> iterable, final Stringer<T> stringer) {
		return join(iterable, ", ", stringer);
	}

	public static <T> String join(final Iterable<T> iterable, final String delimiter, final Stringer<T> stringer) {
		final Iterator<T> iterator = iterable.iterator();
		final StringBuilder message = new StringBuilder();

		while (iterator.hasNext()) {
			final T t = iterator.next();

			if (t != null)
				message.append(stringer.toString(t)).append(iterator.hasNext() ? delimiter : "");
		}

		return message.toString();
	}

	public interface Stringer<T> {

		String toString(T object);
	}
}
