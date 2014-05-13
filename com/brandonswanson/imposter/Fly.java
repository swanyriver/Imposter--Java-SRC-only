package com.brandonswanson.imposter;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;

import SwansonLibrary.PathCrawler;
import SwansonLibrary.ViewTools;
import SwansonLibrary.WindyPath;

/**
 * Created by Brandon on 4/24/14.
 */
public class Fly extends ImageView {

    private BrainForImposter mBrain;
    private final int MyID = BrainForImposter.FLY;
    private Path mWindyPath;
    private PathCrawler mPathCrawler;

    public Fly(Context context, BrainForImposter brain) {
        super(context);

        mBrain = brain;

        setLayoutParams(new FrameLayout.LayoutParams(100,100));
        setImageResource(R.drawable.fly);

        Rect bounds = ViewTools.getWindowBounds(context);
        bounds=ViewTools.marginRectPercent(bounds,.10f);
        bounds=ViewTools.squareRect(bounds);

        mWindyPath= new WindyPath(bounds,100);

        mPathCrawler = new PathCrawler(mWindyPath) {
            @Override
            public void onCrawl(ViewTools.PathPosition pathPosition) {
                setRotation(getRotation()+90);
                mBrain.lookHere((int)pathPosition.position.x,(int)pathPosition.position.y,BrainForImposter.FLY);
            }
        };

        mPathCrawler.setView(this);
        mPathCrawler.setDuration(100 * 1500);
        mPathCrawler.setInterpolator(new LinearInterpolator());
        mPathCrawler.setRepeatCount(ValueAnimator.INFINITE);
        mPathCrawler.setRepeatMode(ValueAnimator.REVERSE);
        mPathCrawler.start();

    }
}
