package com.ssomar.score.utils.strings;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.ssomar.score.utils.numbers.NTools;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * A simple utility class for joining {@link String Strings}.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class StringJoiner {

	/**
	 * Joins elements from the input {@link String} array using spaces within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param array      the input {@link String} array.
	 * @return a {@link String} containing the joined elements within the specified range.
	 */
	public static String joinRange(final int startIndex, final String[] array) {
		return joinRange(startIndex, array.length, array);
	}

	/**
	 * Joins elements from the input {@link String} array using spaces within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param list       the input {@link String} {@link List}.
	 * @return a {@link String} containing the joined elements within the specified range.
	 */
	public static String joinRange(final int startIndex, final List<String> list) {
		return joinRange(startIndex, list.size(), list);
	}

	/**
	 * Joins elements from the input {@link String} array using spaces within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param stopIndex  the index to join until.
	 * @param array      the input {@link String} array.
	 * @return a {@link String} containing the joined elements within the specified range.
	 */
	public static String joinRange(final int startIndex, final int stopIndex, final String[] array) {
		return joinRange(startIndex, stopIndex, Arrays.asList(array), " ");
	}

	/**
	 * Joins elements from the input {@link String} {@link List} using spaces within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param stopIndex  the index to join until.
	 * @param list       the input {@link String} {@link List}.
	 * @return a {@link String} containing the joined elements within the specified range.
	 */
	public static String joinRange(final int startIndex, final int stopIndex, final List<String> list) {
		return joinRange(startIndex, stopIndex, list, " ");
	}

	/**
	 * Joins elements from the input {@link String} array using a specified delimiter within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param stopIndex  the index to join until.
	 * @param array      the input {@link String} array.
	 * @param delimiter  the delimiter used to separate the joined elements.
	 * @return a {@link String} containing the joined elements within the specified range, separated by the given delimiter.
	 */
	public static String joinRange(final int startIndex, final int stopIndex, final String[] array, final String delimiter) {
		return joinRange(startIndex, stopIndex, Arrays.asList(array), delimiter);
	}

	/**
	 * Joins elements from the input {@link String} {@link List} using a specified delimiter within a specific range.
	 *
	 * @param startIndex the index to start joining from.
	 * @param stopIndex  the index to join until.
	 * @param list       the input {@link String} {@link List}.
	 * @param delimiter  the delimiter used to separate the joined elements.
	 * @return a {@link String} containing the joined elements within the specified range, separated by the given delimiter.
	 */
	public static String joinRange(final int startIndex, final int stopIndex, final List<String> list, final String delimiter) {
		final StringBuilder joined = new StringBuilder();

		for (int i = startIndex; i < NTools.range(stopIndex, 0, list.size()); i++)
			joined.append((joined.length() == 0) ? "" : delimiter).append(list.get(i));

		return joined.toString();
	}

	/**
	 * Joins elements from the input array, separated by ", ". Each element is simplified using
	 * {@link StringSimplifier#simplify(Object)} given it is not {@code null}, otherwise an empty {@link String} is
	 * returned.
	 *
	 * @param array the input array.
	 * @param <T>   the type of elements in the array.
	 * @return a {@link String} containing the joined elements of the array.
	 */
	public static <T> String join(final T[] array) {
		return array == null ? "null" : join(Arrays.asList(array));
	}

	/**
	 * Joins elements from the input {@link Iterable}, separated by ", ". Each element is simplified using
	 * {@link StringSimplifier#simplify(Object)} given it is not {@code null}, otherwise an empty {@link String} is
	 * returned.
	 *
	 * @param iterable the input {@link Iterable}.
	 * @param <T>      the type of elements in the {@link Iterable}.
	 * @return a {@link String} containing the joined elements of the {@link Iterable}.
	 */
	public static <T> String join(@Nullable final Iterable<T> iterable) {
		return iterable == null ? "null" : join(iterable, ", ");
	}

	/**
	 * Joins elements from the input array, separated by the specified delimiter. Each element is simplified using
	 * {@link StringSimplifier#simplify(Object)} given it is not {@code null}, otherwise an empty {@link String} is
	 * returned.
	 *
	 * @param array     the input array.
	 * @param delimiter the delimiter used to separate the joined elements.
	 * @param <T>       the type of elements in the array.
	 * @return a {@link String} containing the joined elements of the array with the specified delimiter.
	 */
	public static <T> String join(final T[] array, final String delimiter) {
		return join(array, delimiter, object -> Optional.ofNullable(object)
				.map(StringSimplifier::simplify)
				.orElse(""));
	}

	/**
	 * Joins elements from the input {@link Iterable}, separated by the specified delimiter. Each element is simplified
	 * using {@link StringSimplifier#simplify(Object)} given it is not {@code null}, otherwise an empty {@link String}
	 * is returned.
	 *
	 * @param iterable  the input {@link Iterable}.
	 * @param delimiter the delimiter used to separate the joined elements.
	 * @param <T>       the type of elements in the Iterable.
	 * @return a {@link String} containing the joined elements of the array with the specified delimiter.
	 */
	public static <T> String join(final Iterable<T> iterable, final String delimiter) {
		return join(iterable, delimiter, object -> Optional.ofNullable(object)
				.map(StringSimplifier::simplify)
				.orElse(""));
	}

	/**
	 * Joins elements from the input array, separated by ", ". Each element is converted to its {@link String}
	 * representation using the provided {@link Stringer}.
	 *
	 * @param array    the input array.
	 * @param stringer the {@link Stringer} used to convert each element to its String representation.
	 * @param <T>      the type of elements in the array.
	 * @return a {@link String} containing the joined elements of the array with the specified delimiter.
	 */
	public static <T> String join(@NotNull final T[] array, final Stringer<T> stringer) {
		return join(array, ", ", stringer);
	}

	/**
	 * Joins elements from the input array, separated by the specified delimiter. Each element is converted to its
	 * {@link String} representation using the provided {@link Stringer}.
	 *
	 * @param array     the input array.
	 * @param delimiter the delimiter used to separate the joined elements.
	 * @param stringer  the {@link Stringer} used to convert each element to its String representation.
	 * @param <T>       the type of elements in the array.
	 * @return a {@link String} containing the joined elements of the array with the specified delimiter.
	 */
	public static <T> String join(@NotNull final T[] array, final String delimiter, final Stringer<T> stringer) {
		return join(Arrays.asList(array), delimiter, stringer);
	}

	/**
	 * Joins elements from the input {@link Iterable}, separated by ", ". Each element is converted to its
	 * {@link String} representation using the provided {@link Stringer}.
	 *
	 * @param iterable the input {@link Iterable}.
	 * @param stringer the {@link Stringer} used to convert each element to its String representation.
	 * @param <T>      the type of elements in the {@link Iterable}.
	 * @return a {@link String} containing the joined elements of the {@link Iterable} with the specified delimiter.
	 */
	public static <T> String join(final Iterable<T> iterable, final Stringer<T> stringer) {
		return join(iterable, ", ", stringer);
	}

	/**
	 * Joins elements from the input {@link Iterable}, separated by the specified delimiter. Each element is converted
	 * to its {@link String} representation using the provided {@link Stringer}.
	 *
	 * @param iterable  the input {@link Iterable}.
	 * @param delimiter the delimiter used to separate the joined elements.
	 * @param stringer  the {@link Stringer} used to convert each element to its String representation.
	 * @param <T>       the type of elements in the {@link Iterable}.
	 * @return a {@link String} containing the joined elements of the {@link Iterable} with the specified delimiter.
	 */
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

	/**
	 * A simple interface for converting objects into {@link String Strings}.
	 *
	 * @param <T> the type that is being converted.
	 */
	@FunctionalInterface
	public interface Stringer<T> {

		/**
		 * Converts the given object into a {@link String}.
		 *
		 * @param object the object to convert.
		 * @return the {@link String} representation of the given object.
		 */
		String toString(T object);
	}
}
