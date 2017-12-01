package org.loader.astar.helper;

/**
 * <p class="note">File Note</p>
 * Created by qibin on 2017/11/30.
 */

public class Pane {
    private int x;
    private int y;

    private float f;
    private int g;
    private float h;

    private boolean isWall;

    private Pane parent;

    public Pane(int x, int y, boolean isWall) {
        this.x = x;
        this.y = y;
        this.isWall = isWall;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getG() {
        return g;
    }

    public void setG(int g) {
        this.g = g;
    }

    public float getH() {
        return h;
    }

    public void setH(float h) {
        this.h = h;
    }

    public Pane getParent() {
        return parent;
    }

    public void setParent(Pane parent) {
        this.parent = parent;
    }

    public boolean isWall() {
        return isWall;
    }

    public void setWall(boolean wall) {
        isWall = wall;
    }

    public float getF() {
        return f;
    }

    public void calcF() {
        this.f = this.g + this.h;
    }
}
