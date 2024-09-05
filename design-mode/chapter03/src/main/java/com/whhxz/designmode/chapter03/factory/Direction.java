package com.whhxz.designmode.chapter03.factory;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/**
 * 方向
 */
@RequiredArgsConstructor
@Getter
public enum Direction {
    NORTH("北"), SOUTH("南"), EAST("西"), WEST("东");
    private final String desc;
}
