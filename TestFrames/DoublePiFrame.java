package TestFrames;

import android.content.Context;
import android.graphics.Rect;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import SwansonLibrary.ViewTools;
import SwansonLibrary.oneFingerMoveListener;

/**
 * Created by Brandon on 5/5/14.
 */

public class DoublePiFrame extends FrameLayout {

    private Context mContext;

    PIFrame startofPath;
    PIFrame endofPath;

    public DoublePiFrame(Context context) {
        super(context);

        mContext=context;

        setWillNotDraw(false);
        setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        TextView textView = new TextView(context);

        startofPath=new PIFrame(mContext,textView);
        endofPath = new PIFrame(mContext,textView);

        addView(startofPath);
        addView(endofPath);

        endofPath.setOrigin(ViewTools.getRandomPoint(startofPath.getBounds()));
        endofPath.setCurrentPoint(ViewTools.getRandomPoint(startofPath.getBounds()));

        endofPath.setClickable(false);
        startofPath.setClickable(false);

        setClickable(true);
        setOnTouchListener(new oneFingerMoveListener() {
            @Override
            public void touchDown(float x, float y) {
                //pick one of the origins or points
            }

            @Override
            public void touchAt(float x, float y) {

                //move them around
                //try making those created listeners publicly accesible!

            }

            @Override
            public void touchOver() {

            }
        });

    }
}
