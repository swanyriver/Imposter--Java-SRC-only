package TestFrames;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import SwansonLibrary.OFFSET;
import SwansonLibrary.PointGenerator;
import SwansonLibrary.ViewTools;
import SwansonLibrary.WindyPath;
import SwansonLibrary.oneFingerMoveListener;

/**
 * Created by Brandon on 5/5/14.
 */

public class DoublePiFrame extends FrameLayout {

    private Context mContext;

    private PIFrame startofPath;
    private PIFrame endofPath;
    private oneFingerMoveListener mDoubleTouchDownListener;

    private OnTouchListener startpointmover;
    private OnTouchListener startOrigonMover;
    private OnTouchListener endpointmover;
    private OnTouchListener endOrigonMover;
    private PointGenerator mPointGenerator;




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



        mPointGenerator = new PointGenerator(startofPath.getmMinLength(),startofPath.getBounds(), WindyPath.MINIMUM_ANGLE_DEFAULT);


        mDoubleTouchDownListener = new oneFingerMoveListener() {
            @Override
            public void touchDown(float x, float y) {

                //check for wich button/point recieves touch in priority order
                if(!ViewTools.lengthLessThanDistance(new Point((int)x, (int)y),startofPath.getCurrentPoint(),startofPath.getmMinLength())){


                    //setOnTouchListener(startofPath.getMovePointTouch());
                    setOnTouchListener(startpointmover);

                }
                else if(!ViewTools.lengthLessThanDistance(new Point((int)x, (int)y),startofPath.getOrigin(),startofPath.getmMinLength()/4)){


                    //setOnTouchListener(startofPath.getMoveOrigonTouch());
                    setOnTouchListener(startOrigonMover);
                }else if(!ViewTools.lengthLessThanDistance(new Point((int)x, (int)y),endofPath.getCurrentPoint(),endofPath.getmMinLength())){


                    setOnTouchListener(endpointmover);
                }
                else if(!ViewTools.lengthLessThanDistance(new Point((int)x, (int)y),endofPath.getOrigin(),endofPath.getmMinLength()/4)){


                    setOnTouchListener(endOrigonMover);
                }

            }





            @Override
            public void touchAt(float x, float y) {

                //Log.d("PATH", "we went to far, touch listener changed");
            }

            @Override
            public void touchOver() {

            }
        };

        endofPath.setTouchDownListener(mDoubleTouchDownListener);
        startofPath.setTouchDownListener(mDoubleTouchDownListener);
        startofPath.removeAllViews();
        endofPath.removeAllViews();

        startOrigonMover= new OrigonMover(startofPath,this,mDoubleTouchDownListener);
        startpointmover= new PointMover(startofPath,this,mDoubleTouchDownListener);
        endOrigonMover= new OrigonMover(endofPath,this,mDoubleTouchDownListener);
        endpointmover= new PointMover(endofPath,this,mDoubleTouchDownListener);

        setOnTouchListener(mDoubleTouchDownListener);


    }

    public void updatePoints(){




        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

    }
}

class OrigonMover extends oneFingerMoveListener{
    PIFrame mFrame;
    DoublePiFrame mHostframe;
    oneFingerMoveListener mTouchdownlistener;

    public OrigonMover(PIFrame frame, DoublePiFrame hostframe, oneFingerMoveListener touchdownlistener) {
        mFrame=frame;
        mHostframe=hostframe;
        mTouchdownlistener = touchdownlistener;
    }

    @Override
    public void touchDown(float x, float y) {

    }

    @Override
    public void touchAt(float x, float y) {

        boolean inside = ViewTools.containsInner(x, y, mFrame.getBounds());
        boolean lengthAllowed = ViewTools.lengthLessThanDistance(mFrame.getCurrentPoint(),new Point((int)x,(int)y), mFrame.getmMinLength());

        if(inside && lengthAllowed){
            mFrame.setOrigin(new Point((int) x, (int) y));
        }else {

            touchOver();
        }

    }

    @Override
    public void touchOver() {

        mHostframe.setOnTouchListener(mTouchdownlistener);
    }
}

class PointMover extends oneFingerMoveListener{

        private OFFSET Offset;

    PIFrame mFrame;
    DoublePiFrame mHostframe;
    oneFingerMoveListener mTouchdownlistener;

    public PointMover(PIFrame frame, DoublePiFrame hostframe, oneFingerMoveListener touchdownlistener) {
        mFrame=frame;
        mHostframe=hostframe;
        mTouchdownlistener = touchdownlistener;
    }
        @Override
        public void touchDown(float x, float y) {
            //shouldnt be reached

        }

        @Override
        public void touchAt(float x, float y) {

            // Log.d("PATH", "point move touch");

            if(Offset==null){
                Point currentPoint=mFrame.getCurrentPoint();
                Offset=new OFFSET(currentPoint.x,currentPoint.y,(int)x,(int)y);
            }
            Point touchPoint = Offset.get((int)x,(int)y);

            boolean inside = ViewTools.containsInner(touchPoint.x, touchPoint.y, mFrame.getBounds());
            boolean lengthAllowed = ViewTools.lengthLessThanDistance(mFrame.getOrigin(),touchPoint, mFrame.getmMinLength());

            if(inside && lengthAllowed){
                mFrame.setCurrentPoint(touchPoint);
            }else {
                touchOver();
            }

        }

        @Override
        public void touchOver() {
            Offset=null;
            mHostframe.setOnTouchListener(mTouchdownlistener);

        }
}
