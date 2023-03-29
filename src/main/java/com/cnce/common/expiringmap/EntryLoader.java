package com.cnce.common.expiringmap;

/**
 * Loads entries on demand.
 * 
 * @param <K> Key type
 * @param <V> Value type
 */
/*
  interface EntryLoader<K, V>：按需加载，内有 V load(K key) 方法，调用时将 key 的新值加载到即将过期的映射中。
 */
public interface EntryLoader<K, V> {
  /**
   * Called to load a new value for the {@code key} into an expiring map.
   * 
   * @param key to load a value for
   * @return new value to load
   */
  V load(K key);
}