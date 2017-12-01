package org.loader.astar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import org.loader.astar.helper.AStarHelper;
import org.loader.astar.helper.Pane;

import java.util.List;

/**
 * <p class="note">File Note</p>
 * Created by qibin on 2017/12/1.
 */

public class AStarView extends View {

    private int mItemWidth;
    private int mItemHeight;

    private Pane[][] mPanes;
    private List<Pane> mPath;

    private Pane mStartPane;
    private Pane mEndPane;

    private Paint mPaint;
    private Paint mBorderPaint;
    private Paint mPathPaint;

    private AStarHelper mHelper;

    public AStarView(Context context) {
        super(context);
    }

    public AStarView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AStarView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setPanes(Pane[][] panes, Pane start, Pane end) {
        mPanes = panes;
        mStartPane = start;
        mEndPane = end;

        mHelper = new AStarHelper(panes);

        mPaint = new Paint();
        mBorderPaint = new Paint();
        mBorderPaint.setStyle(Paint.Style.STROKE);
        mBorderPaint.setStrokeWidth(2);
        mBorderPaint.setColor(Color.DKGRAY);

        mPathPaint = new Paint();
        mPathPaint.setColor(Color.LTGRAY);

        mPath = mHelper.findPath(start, end);

        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if (mPanes == null || mPanes.length == 0 || mPanes[0].length == 0) { return;}

        mItemWidth = getMeasuredWidth() / mPanes[0].length;
        mItemHeight = getMeasuredHeight() / mPanes.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mPanes == null || mPanes.length == 0 || mPanes[0].length == 0) { return;}

        int cols = mPanes.length;
        int rows = mPanes[0].length;

        int width = 0;
        int height = 0;

        for (int j = 0; j < cols; j++) {
            for (int i = 0; i < rows; i++) {
                if (mPanes[j][i].isWall()) {
                    mPaint.setColor(Color.GREEN);
                } else if (mPanes[j][i] == mStartPane) {
                    mPaint.setColor(Color.CYAN);
                } else if (mPanes[j][i] == mEndPane) {
                    mPaint.setColor(Color.MAGENTA);
                } else {
                    mPaint.setColor(Color.WHITE);
                }

                canvas.drawRect(width, height, width + mItemWidth, height + mItemHeight, mPaint);
                canvas.drawRect(width, height, width + mItemWidth, height + mItemHeight, mBorderPaint);

                for (Pane pane : mPath) {
                    if (pane.getX() == i && pane.getY() == j) {
                        if (pane == mStartPane) { continue;}
                        if (pane == mEndPane) { continue;}
                        canvas.drawRect(width, height, width + mItemWidth, height + mItemHeight, mPathPaint);
                    }
                }

                width += mItemWidth;
            }

            width = 0;
            height += mItemHeight;
        }
    }
}
