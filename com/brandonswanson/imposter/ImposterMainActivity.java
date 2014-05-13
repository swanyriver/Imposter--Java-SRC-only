package com.brandonswanson.imposter;

import android.app.Activity;


import android.graphics.Point;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;


import java.util.ArrayList;


import SwansonLibrary.ViewTools;
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
        //MasterLayout.setClickable(true);

        //SetUp();


        //Path Visualzation stuff///////////
        ///////////////////////////////////


        MasterLayout.addView(new TestFrames.drawFrame(this));


       // MasterLayout.addView(new TestFrames.PIFrame(this,(TextView) findViewById(R.id.readout)));

        //MasterLayout.addView(new DoublePiFrame(this));

        //MasterLayout.addView(new PathFrame(this));

        //Path Visualzation stuff///////////
        ///////////////////////////////////*/
    }

    private void SetUp() {
        mFaces = new ArrayList<Face>();

        Point size= ViewTools.getWindowSize(this);

        int width = size.x/4;
        int faceSize = (int) (width*.9);
        int margin = (int) (width*.1);
        int vstart = (size.y-size.x)/2;


        Log.d("PATH", "adding faces start");
        for(int x=margin/2;x<=size.x-faceSize-margin/2;x+=faceSize+margin){
            for(int y=vstart;y<=width*5;y+=faceSize+margin){
                Face newFace = new Face(this,faceSize,faceSize);
                newFace.setX(x);
                newFace.setY(y);
                MasterLayout.addView(newFace);
                mFaces.add(newFace);
            }
        }
        Log.d("PATH", "adding faces end");

        mBrain = new BrainForImposter(mFaces,ViewTools.getWindowBounds(this) );
        mBrain.setFlyFocusDistace(.15f);

        Log.d("PATH", "adding fly");
        MasterLayout.addView(new Fly(this,mBrain));
        Log.d("PATH", "adding fly finished");
    }


}
