package com.cnce.common.expiringmap;

/**
 * A listener for expired object events.
 * 
 * @param <K> Key type
 * @param <V> Value type
 */
/*
    interface ExpirationListener<K, V>：过期监听接口，
    有一个 void expired(K key, V value) 方法，过期时自动调用。
 */
public interface ExpirationListener<K, V> {
  /**
   * Called when a map entry expires.
   * 
   * @param key Expired key
   * @param value Expired value
   */
  void expired(K key, V value);
}