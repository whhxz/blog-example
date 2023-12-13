package com.whhxz.blogexample.mybatis.util;

import java.util.ArrayList;
import java.util.List;

public class TimeRecord {
    private List<Long> pile;

    public TimeRecord() {
        pile = new ArrayList<>();
        pile.add(System.currentTimeMillis());
    }

    public void step() {
        pile.add(System.currentTimeMillis());
    }

    public void stop() {
        pile.add(System.currentTimeMillis());
        for (int i = 1; i < pile.size(); i++) {
            System.out.printf("耗时~~~：%f\n", (pile.get(i) - pile.get(i - 1)) / 1000.0);
        }
    }
}
