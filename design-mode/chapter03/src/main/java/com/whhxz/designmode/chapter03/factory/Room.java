package com.whhxz.designmode.chapter03.factory;

/**
 * 房间
 */
public class Room implements MapSite {
    // 4方向
    public MapSite[] sites = new MapSite[4];
    private final int rootNumber;

    public Room(int rootNumber) {
        this.rootNumber = rootNumber;
    }

    public void setSide(Direction d, MapSite site) {

    }

    public MapSite getSige(Direction d) {
        return null;
    }

    @Override
    public void enter() {

    }
}
