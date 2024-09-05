package com.whhxz.designmode.chapter02.structure;

import com.whhxz.designmode.chapter02.Point;
import com.whhxz.designmode.chapter02.Rect;
import com.whhxz.designmode.chapter02.Window;

/**
 * 文档的结构，图元
 */
public interface Glyph {
    // ↓外观

    /**
     * 重新绘画
     *
     * @param window 窗口，用于子类重新绘画
     */
    void draw(Window window);

    /**
     * 返回图元占用的矩形区域，最小矩形的对角顶点
     *
     * @param rect
     */

    void bounds(Rect rect);
    // ↓检查

    /**
     * 判断指定点是否与图元相交
     *
     * @param point 点
     */
    void intersects(Point point);


    //  ↓结构

    /**
     * 图元包含子类，用于指定位置插入
     *
     * @param glyph 子类图元
     * @param i     插入点
     */
    void insert(Glyph glyph, int i);

    /**
     * 移除指定图元
     *
     * @param glyph 移除的图元
     */
    void remove(Glyph glyph);

    /**
     * 封装的子图元
     *
     * @param i 指定位置
     * @return 对应的图元
     */
    Glyph child(int i);

    /**
     * 大区图元的父图元
     *
     * @return 父图元
     */
    Glyph parent();
}
