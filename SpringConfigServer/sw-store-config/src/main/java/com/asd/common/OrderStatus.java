package com.asd.common;

public enum OrderStatus {
    PENDING,   //결제 대기 중
    PAID,      //결제 완료
    SHIPPED,   //배송 중
    DELIVERED, //배송 완료
    CANCELLED //주문 취소
}