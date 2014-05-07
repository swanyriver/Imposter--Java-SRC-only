package com.brandonswanson.imposter;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * Created by Brandon on 4/24/14.
 */
public class Fly extends ImageView {

    private BrainForImposter mBrain;
    private final int MyID = BrainForImposter.FLY;

    public Fly(Context context, BrainForImposter brain) {
        super(context);

        mBrain = brain;

        setLayoutParams(new FrameLayout.LayoutParams(20,20));
        setBackgroundColor(Color.RED);
        setX(20);
        setY(20);


    }
}
