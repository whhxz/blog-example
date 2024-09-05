package com.whhxz.designmode.chapter03.factory;

import lombok.Getter;

/**
 * 门
 */
public class Door implements MapSite {
    private Room r1;
    private Room r2;

    @Getter
    private boolean open;

    public Door(Room r1, Room r2) {
        this.r1 = r1;
        this.r2 = r2;
    }

    public Room otherSideRoom(Room r) {
        return null;
    }

    @Override
    public void enter() {

    }
}
