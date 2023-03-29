package com.cnce.common.expiringmap;

/** 
 * Determines how ExpiringMap entries should be expired.
 */
/*
  enum ExpirationPolicy：枚举，确定 ExpiringMap 元素应如何过期，ACCESSED 根据上次访问的时间使它们过期，
  即从上次访问后开始计时；CREATED 根据条目的创建时间使它们过期，即从创建之后开始计时。
 */
public enum ExpirationPolicy {
  /** Expires entries based on when they were last accessed */
  ACCESSED,
  /** Expires entries based on when they were created */
  CREATED;
}