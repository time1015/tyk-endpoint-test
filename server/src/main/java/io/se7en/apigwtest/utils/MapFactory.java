package io.se7en.apigwtest.utils;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.function.Function;

public final class MapFactory {
  public static <K, V1, V2> Map<K, V2> mapValue(Map<K, V1> map, Function<V1, V2> mapper) {
    if (mapper == null)
      throw new IllegalArgumentException("Null mapper");

    if (map == null)
      return null;

    Map<K, V2> newMap = new HashMap<>();
    for (Entry<K, V1> e : map.entrySet())
      newMap.put(e.getKey(), mapper.apply(e.getValue()));
    return Collections.unmodifiableMap(newMap);
  }

  public static <K, V> Entry<K, V> entry(K key, V value) {
    return new Entry<K, V>() {
      @Override
      public V setValue(V value) {
        throw new UnsupportedOperationException();
      }

      @Override
      public V getValue() {
        return value;
      }

      @Override
      public K getKey() {
        return key;
      }
    };
  }
}
