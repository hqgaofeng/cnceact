package com.cnce.common.expiringmap;

/**
 * Loads entries on demand, with control over each value's expiry duration (i.e. variable expiration).
 *
 * @param <K> Key type
 * @param <V> Value type
 */
/*
  interface ExpiringEntryLoader<K, V>：按需加载，并控制每个值的过期时间(即可变过期时间)，
  内有 ExpiringValue<V> load(K key) 方法，调用时将 key 的新值加载到即将过期的映射中
 */
public interface ExpiringEntryLoader<K, V> {
  /**
   * Called to load a new value for the {@code key} into an expiring map.
   *
   * @param key to load a value for
   * @return contains new value to load along with its expiry duration
   */
  ExpiringValue<V> load(K key);
}