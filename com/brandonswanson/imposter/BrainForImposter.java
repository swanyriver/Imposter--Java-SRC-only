package com.brandonswanson.imposter;


import android.content.Context;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.WindowManager;

import java.util.ArrayList;

import SwansonLibrary.ViewTools;

/**
 * Created by Brandon on 4/24/14.
 */
public class BrainForImposter {

    static final int UNFOCUSED = 00;
    static final int FLY = 10;
    static final int FROG = 20;
    static final int FINGER = 30;
    static final int REVEALED_FROG = 40;

    private float FlyFocusDistanceNONSQRTD=0;

    private static float FLY_FOCUS_DISTANCE_PERCENT_DEFAULT=.2f;

    private ArrayList<Face> mFaces;

    private Rect mBounds;

    public BrainForImposter(ArrayList<Face> faces, Rect bounds){

        mFaces=faces;
        mBounds=bounds;

        setFlyFocusDistace(FLY_FOCUS_DISTANCE_PERCENT_DEFAULT);

    }

    ////Initialization Methods////////
    ////////////////////////////////
    public void setFlyFocusDistace(float percent) {

        if(percent<0||percent>1){
            setFlyFocusDistace(FLY_FOCUS_DISTANCE_PERCENT_DEFAULT);
            return;
        }

        double diagonalDistance = ViewTools.getHypotenuse(mBounds.width(),mBounds.height());

        FlyFocusDistanceNONSQRTD= (float)Math.pow(diagonalDistance*percent,2);

    }

    ////Public Methods///////////
    ////////////////////////////
    /////Selection Brain////////

    public void lookHere(int x, int y, int ID){
        switch (ID){
            case FLY:
                //find faces close enough
                for(int i=0;i<mFaces.size();i++){
                    Face face = mFaces.get(i);
                    //int location[] = new int[2];
                    //ViewTools.findCenterInWindow(face,location);
                    if(ViewTools.getDistancetoNonSQRTD(x,y,face.getCenterX(),face.getCenterY())<FlyFocusDistanceNONSQRTD){
                       lookHere(x,y,face,FLY);
                    }else StopLookingOne(FLY, face);

                }

                break;
            case FINGER:
                ////dont know, maybe stop looking at other things
                break;
            case FROG:
                ///select a face to look at the frog
                //command to look only sent once, eyes stay looking at frog, until relase code sent

                break;
            case REVEALED_FROG:
                ///
                break;

        }
    }

    public void toucedByFinger(Face touchedFace){

    }



    public void StopLookingAll(int ID){
        for(int i=0;i<mFaces.size();i++){
            if(mFaces.get(i).getFocus()==ID) mFaces.get(i).setFocus(UNFOCUSED);
        }
    } 
    private void StopLookingOne(int ID, Face face){

        if(face.getFocus()==ID)face.setFocus(UNFOCUSED);

    }





    ////Private Methods///////////
    ////////////////////////////
    /////Priority Brain////////

    private void lookHere(int x, int y, Face face, int ID){
        if(ID==face.getFocus()){
            face.Eyes().focusHere(x,y);
        }else if(ID>face.getFocus()){
            face.Eyes().focusHere(x,y);
            face.setFocus(ID);
        }
    }

}
