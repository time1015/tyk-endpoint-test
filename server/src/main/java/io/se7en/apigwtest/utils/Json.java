package io.se7en.apigwtest.utils;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public interface Json extends Supplier<String> {
  public static Json literal(String val) {
    return () -> val.toString();
  }

  public static <T> Json literal(T val, Function<T, String> toString) {
    return literal(toString.apply(val));
  }

  public static Json quote(String val) {
    String quotedVal = "\"" + val.replaceAll("([\"\'\\\t\b\n\r\f])", "\\\\$1") + "\"";
    return () -> quotedVal;
  }

  public static <T> Json quote(T val, Function<T, String> toString) {
    return quote(toString.apply(val));
  }

  public static Json quoteOrNull(String val) {
    if (val == null)
      return () -> "null";

    if (val.equals("null"))
      return () -> "null";

    return quote(val);
  }

  public static <T> Json quoteOrNull(T val, Function<T, String> toString) {
    return quoteOrNull(toString.apply(val));
  }

  public static Json list(Json... elements) {
    return list(ListFactory.from(elements));
  }

  public static Json list(List<Json> elements) {
    return () -> elements.stream().map(Json::get).collect(Collectors.joining(",", "[", "]"));
  }

  public static Json map(Map<String, Json> map) {
    return () -> map
      .entrySet()
      .stream()
      .map(e -> quoteOrNull(e.getKey()) + ":" + e.getValue().get())
      .collect(Collectors.joining(",", "{", "}"));
  }

  @SafeVarargs
  public static Json map(Entry<String, Json>... entries) {
    return map(ListFactory.from(entries));
  }

  public static Json map(List<Entry<String, Json>> entries) {
    return () -> entries
      .stream()
      .map(e -> MapFactory.entry(quoteOrNull(e.getKey()), e.getValue()))
      .map(e -> e.getKey().get() + ":" + e.getValue().get())
      .collect(Collectors.joining(",", "{", "}"));
  }
}
