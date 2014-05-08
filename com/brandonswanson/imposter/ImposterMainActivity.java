package com.brandonswanson.imposter;

import android.app.Activity;


import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;


import java.util.ArrayList;


import SwansonLibrary.oneFingerMoveListener;
import TestFrames.DoublePiFrame;
import TestFrames.PathFrame;


public class ImposterMainActivity extends Activity {

    private FrameLayout MasterLayout;


    private View.OnTouchListener PlacerTouch;
    private View.OnTouchListener WatchTouch;

    private ArrayList<Face> mFaces;
    private BrainForImposter mBrain;

    private boolean mPlacing = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.imposter_main_layout);

        //if (savedInstanceState == null) {}

        MasterLayout = (FrameLayout) findViewById(R.id.container);
        MasterLayout.setClickable(true);

        MasterLayout.addView(new Fly(this,mBrain));



        mFaces = new ArrayList<Face>();

        ///temporary  Debug
        Face newFace = new Face(getApplicationContext());
        newFace.setX(200);
        newFace.setY(200);
        newFace.setVisibility(View.INVISIBLE);
        MasterLayout.addView(newFace);
        mFaces.add(newFace);
        /////// Temporrary
        //////////////////

        mBrain = new BrainForImposter(mFaces);


        WatchTouch = new oneFingerMoveListener() {
            @Override
            public void touchDown(float x, float y) {
               // for(int i=0;i<mFaces.size();i++)mFaces.get(i).Eyes().focusHere((int) x, (int) y);
                mBrain.lookHere((int)x,(int)y,BrainForImposter.FLY);

            }
            @Override
            public void touchAt(float x, float y) {
               // for(int i=0;i<mFaces.size();i++)mFaces.get(i).Eyes().focusHere((int)x,(int)y);
                mBrain.lookHere((int)x,(int)y,BrainForImposter.FLY);

            }
            @Override
            public void touchOver() {
               // for(int i=0;i<mFaces.size();i++)mFaces.get(i).Eyes().request(EyeBall.PLEASE_LOOSE_FOCUS);
                mBrain.StopLookingAll(BrainForImposter.FLY);
            }
        };

        PlacerTouch = new oneFingerMoveListener() {
            @Override
            public void touchDown(float x, float y) {
                Face newFace = new Face(getApplicationContext());
                newFace.setX(x);
                newFace.setY(y);
                MasterLayout.addView(newFace);
                mFaces.add(newFace);

            }

            @Override
            public void touchAt(float x, float y) {


            }

            @Override
            public void touchOver() {

            }
        };


        MasterLayout.setOnTouchListener(PlacerTouch);

        //Path Visualzation stuff///////////
        ///////////////////////////////////
        ((Button)findViewById(R.id.toggleButton)).setEnabled(false);
        ((Button)findViewById(R.id.toggleButton)).setVisibility(View.INVISIBLE);
        MasterLayout.setOnTouchListener(null);

        MasterLayout.addView(new TestFrames.drawFrame(this));


       // MasterLayout.addView(new TestFrames.PIFrame(this,(TextView) findViewById(R.id.readout)));

        //MasterLayout.addView(new DoublePiFrame(this));

        //MasterLayout.addView(new PathFrame(this));

        //Path Visualzation stuff///////////
        ///////////////////////////////////
    }

    public void toggleButton(View view){
        if(mPlacing){
            mPlacing=false;
            MasterLayout.setOnTouchListener(WatchTouch);
            ((Button) view).setText("Place More");
        }else {
            mPlacing=true;
            MasterLayout.setOnTouchListener(PlacerTouch);
            ((Button) view).setText("Start Watching");
        }
    }

}
