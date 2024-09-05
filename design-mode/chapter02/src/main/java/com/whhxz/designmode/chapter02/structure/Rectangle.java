package com.whhxz.designmode.chapter02.structure;

import com.whhxz.designmode.chapter02.Point;
import com.whhxz.designmode.chapter02.Rect;
import com.whhxz.designmode.chapter02.Window;

/**
 * 矩形
 */
public class Rectangle implements Glyph {
    @Override
    public void draw(Window window) {

    }

    @Override
    public void bounds(Rect rect) {

    }

    @Override
    public void intersects(Point point) {

    }

    @Override
    public void insert(Glyph glyph, int i) {

    }

    @Override
    public void remove(Glyph glyph) {

    }

    @Override
    public Glyph child(int i) {
        return null;
    }

    @Override
    public Glyph parent() {
        return null;
    }
}
