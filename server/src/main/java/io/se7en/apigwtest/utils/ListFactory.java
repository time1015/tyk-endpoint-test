package io.se7en.apigwtest.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ListFactory {
  public static <A, B> List<B> map(Function<A, B> mapper, List<A> list) {
    return list.stream().map(mapper).collect(Collectors.toList());
  }

  public static <T> List<T> from(Enumeration<T> enumeration) {
    List<T> list = new LinkedList<>();
    while (enumeration.hasMoreElements())
      list.add(enumeration.nextElement());
    return Collections.unmodifiableList(list);
  }

  public static <A, B> List<B> from(Function<A, B> mapper, Enumeration<A> enumeration) {
    return map(mapper, from(enumeration));
  }

  @SafeVarargs
  public static <T> List<T> from(T... entries) {
    return Arrays.asList(entries);
  }

  @SafeVarargs
  public static <A, B> List<B> from(Function<A, B> mapper, A... entries) {
    return map(mapper, from(entries));
  }

  public static <T> List<T> from(Collection<T> collection) {
    return Collections.unmodifiableList(new ArrayList<>(collection));
  }

  public static <A, B> List<B> from(Function<A, B> mapper, Collection<A> collection) {
    return map(mapper, from(collection));
  }
}
