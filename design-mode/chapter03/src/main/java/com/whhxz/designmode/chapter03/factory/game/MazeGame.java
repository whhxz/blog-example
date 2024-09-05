package com.whhxz.designmode.chapter03.factory.game;

import com.whhxz.designmode.chapter03.factory.*;

public class MazeGame {
    /**
     * 编码太固定
     */
    public static Maze createMaze() {
        Maze maze = new Maze();

        Room r1 = new Room(1);
        Room r2 = new Room(1);
        Door door = new Door(r1, r2);

        r1.setSide(Direction.NORTH, new Wall());
        r1.setSide(Direction.EAST, door);
        r1.setSide(Direction.SOUTH, new Wall());
        r1.setSide(Direction.WEST, new Wall());

        r2.setSide(Direction.NORTH, new Wall());
        r2.setSide(Direction.EAST, new Wall());
        r2.setSide(Direction.SOUTH, new Wall());
        r2.setSide(Direction.WEST, door);

        maze.addRoom(r1);
        maze.addRoom(r2);

        return maze;
    }
}
