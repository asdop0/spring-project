package com.asd.common;

public enum OrderCancelStatus {
    REQUESTED,    // 주문 취소 요청됨
    APPROVED,     // 주문 취소 승인됨
    REJECTED,     // 주문 취소 거부됨
    COMPLETED;    // 주문 취소 완료됨
}
