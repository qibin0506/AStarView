package org.loader.astar.helper;

import java.util.ArrayList;
import java.util.List;

/**
 * <p class="note">File Note</p>
 * Created by qibin on 2017/11/30.
 */

public class AStarHelper {

    public static final int STEP_LINE = 10;
    public static final int STEP_ANGLE = 14;

    private Pane[][] mAllPanes;
    private List<Pane> mOpenedPanes;
    private List<Pane> mClosedPanes;

    public AStarHelper(Pane[][] Panes) {
        mAllPanes = Panes;

        mOpenedPanes = new ArrayList<>();
        mClosedPanes = new ArrayList<>();
    }

    public List<Pane> findPath(Pane start, Pane end) {
        mOpenedPanes.add(start);

        while(!mOpenedPanes.isEmpty()) {
            Pane best = getBestPane();
            mOpenedPanes.remove(best);
            mClosedPanes.add(best);

            List<Pane> surround = getSurroundPanes(best);
            for (Pane item : surround) {
                if (mOpenedPanes.contains(item)) {
                    reWriteG(best, item);
                    continue;
                }

                calcF(best, item, end);
                mOpenedPanes.add(item);
            }
            if (mOpenedPanes.contains(end)) { return resolvePath(end);}
        }

        return resolvePath(end);
    }

    private List<Pane> resolvePath(Pane end) {
        List<Pane> list = new ArrayList<>();

        Pane p = end;
        while(p.getParent() != null) {
            list.add(p.getParent());
            p = p.getParent();
        }

        return list;
    }

    private void calcF(Pane start, Pane pane, Pane end) {
        pane.setParent(start);

        int h = calcH(pane, end);
        int g = calcG(start, pane);

        pane.setH(h);
        pane.setG(g);
        pane.calcF();
    }

    private void reWriteG(Pane start, Pane pane) {
        int g = calcG(start, pane);
        if (g < pane.getG()) {
            pane.setParent(start);

            pane.setG(g);
            pane.calcF();
        }
    }

    private int calcH(Pane pane, Pane end) {
        return Math.abs(pane.getX() - end.getX()) + Math.abs(pane.getY() - end.getY());
    }

    private int calcG(Pane start, Pane pane) {
        if (start.getX() == pane.getX() ||
                start.getY() == pane.getY()) {
            return pane.getParent() == null ? STEP_LINE : pane.getParent().getG() + STEP_LINE;
        }

        return pane.getParent() == null ? STEP_ANGLE : pane.getParent().getG() + STEP_ANGLE;
    }

    private List<Pane> getSurroundPanes(Pane best) {
        List<Pane> surround = new ArrayList<>(8);

        int startY = best.getY() - 1;
        if (startY == -1) { startY = 0;}

        int endY = best.getY() + 1;
        if (endY > mAllPanes.length - 1) { endY = mAllPanes.length - 1;}

        int startX = best.getX() - 1;
        if (startX == -1) { startX = 0;}

        int endX = best.getX() + 1;
        if (endX > mAllPanes[0].length - 1) { endX = mAllPanes[0].length - 1;}

        for (int j = startY; j <= endY; j++) {
            for (int i = startX; i <= endX; i++) {
                Pane Pane = mAllPanes[j][i];
                if (!canReach(best, Pane)) { continue;}
                surround.add(Pane);
            }
        }

        return surround;
    }

    private boolean canReach(Pane current, Pane pane) {
        if (pane.isWall()) { return false;}

        if (mClosedPanes.contains(pane)) { return false;}

        if (current.getX() == pane.getX() || current.getY() == pane.getY()) { return true;}

        if (mAllPanes[current.getY()][pane.getX()].isWall() ||
                mAllPanes[pane.getY()][current.getX()].isWall()) { return false;}

        return true;
    }

    private Pane getBestPane() {
        Pane pane = mOpenedPanes.get(0);

        for (Pane item : mOpenedPanes) {
            if (item.getF() < pane.getF()) {
                pane = item;
            }
        }

        return pane;
    }
}
