package com.brandonswanson.imposter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.FrameLayout;

import EyeBalls.EyeBall;
import EyeBalls.EyeDrawables;
import EyeBalls.EyeFocus;
import EyeBalls.EyeSet;
import SwansonLibrary.ViewTools;



/**
 * Created by Brandon on 4/18/14.
 */
public class Face extends FrameLayout{

    private Face MySelf = this;

    static final int mWidth = 120;
    private int mEyeWidth= (int) (mWidth/2);
    private EyeSet mEyeset;

    private int[] mCenterInWindow = new int[]{0,0};
    public int getCenterX(){return mCenterInWindow[0];}
    public int getCenterY(){return mCenterInWindow[1];}

    private int mFocuesTarget = BrainForImposter.UNFOCUSED;
    public int getFocus() {return mFocuesTarget; }
    public void setFocus(int focusTarget){
        mFocuesTarget=focusTarget;
        if(focusTarget==BrainForImposter.UNFOCUSED)Eyes().request(EyeBall.PLEASE_LOOSE_FOCUS);
    }






    public Face(Context context) {
        super(context);

        post(new Runnable() {
            @Override
            public void run() {
                ViewTools.findCenterInWindow(MySelf,mCenterInWindow);
            }
        });

        addOnLayoutChangeListener(new OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                ViewTools.findCenterInWindow(MySelf,mCenterInWindow);
            }
        });

        setLayoutParams(new LayoutParams(mWidth, mWidth));
        setBackgroundColor(Color.BLUE);

        mEyeset=new EyeSet(this);
        mEyeset.addEyeball(mEyeWidth,mEyeWidth,mWidth/4,mWidth/2,EyeDrawables.getEyeDraw(2,getContext()));
        mEyeset.addEyeball(mEyeWidth,mEyeWidth,mWidth/4*3,mWidth/2,EyeDrawables.getEyeDraw(2,getContext()));

    }


    public EyeFocus Eyes(){
        return mEyeset;
    }
}
