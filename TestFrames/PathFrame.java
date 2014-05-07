package TestFrames;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import SwansonLibrary.PathPlus;
import SwansonLibrary.ViewTools;

/**
 * Created by Brandon on 5/6/14.
 */
public class PathFrame extends FrameLayout{

    public Context mContext;
    public FrameLayout mMyself;

    private PathPlus mCurvePath = new PathPlus();
    private Paint mCurvePaint = new Paint();
    private PathPlus mLinePath = new PathPlus();
    private Paint mLinePaint = new Paint();

    public PathFrame(Context context) {
        super(context);

        mMyself=this;
        mContext=context;
        setWillNotDraw(false);

        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));


        mLinePaint.setColor(Color.BLUE);
        mCurvePaint.setColor(Color.RED);
        mLinePaint.setStrokeWidth(8);
        mCurvePaint.setStrokeWidth(5);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mCurvePaint.setStyle(Paint.Style.STROKE);


        Rect bounds = ViewTools.getWindowBounds(context);

        ViewTools.rectToLines(bounds,mLinePath);
        //bounds.inset(60,60);
        //ViewTools.rectToLines(bounds,mLinePath);
        ViewTools.marginRectPercent(bounds,.30f);
        ViewTools.rectToLines(bounds,mCurvePath);

        mCurvePath.moveTo(bounds.centerX(),bounds.centerY());
        mCurvePath.lineTo(bounds.centerX()+20,bounds.centerY()+20);
        mCurvePath.lineTo(bounds.centerX()+50,bounds.centerY()+20);
        mCurvePath.lineTo(bounds.centerX()-60,bounds.centerY()-67);



    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mLinePath,mLinePaint);
        canvas.drawPath(mCurvePath,mCurvePaint);

    }
}
