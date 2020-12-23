package com.sharkz.largeimage.sdk.load;

/**
 * ================================================
 * 作    者：SharkZ
 * 邮    箱：229153959@qq.com
 * 创建日期：2020/10/26  14:52
 * 描    述 图片块的位置
 * 修订历史：
 * ================================================
 */
public class Position {

    public int row;
    public int col;

    public Position() {
        super();
    }

    public Position(int row, int col) {
        super();
        this.row = row;
        this.col = col;
    }

    public Position set(int row, int col) {
        this.row = row;
        this.col = col;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Position) {
            Position position = (Position) o;
            return row == position.row && col == position.col;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int iTotal = 17;
        int iConstant = 37;
        iTotal = iTotal * iConstant + row;
        iTotal = iTotal * iConstant + col;
        return iTotal;
    }

    @Override
    public String toString() {
        return "row:" + row + " col:" + col;
    }
}
