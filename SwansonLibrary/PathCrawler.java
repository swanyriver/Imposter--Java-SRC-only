package SwansonLibrary;

import android.animation.ValueAnimator;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.view.View;

public abstract class PathCrawler extends ValueAnimator{

    protected float mCrawlPathLength;
    protected Path mCrawlPath;
    protected PathMeasure mCrawlPathMeasure;
    protected float mPreviousValue=-1;
    protected View mCrawlingView=null;

    @Override
    public void setFloatValues(float... values) {
        //not allowed
    }

    @Override
    public void setIntValues(int... values) {
        //not allowed
    }

    @Override
    public void setObjectValues(Object... values) {
        //not allowed
    }







    public PathCrawler(Path path) {

        new ValueAnimator();

        mCrawlPath=new Path(path);
        mCrawlPathMeasure=new PathMeasure(path,false);
        mCrawlPathLength = mCrawlPathMeasure.getLength();


        super.setFloatValues(0, mCrawlPathMeasure.getLength());



       addUpdateListener(new AnimatorUpdateListener() {
           @Override
           public void onAnimationUpdate(ValueAnimator animation) {

               float pathPostion = (Float)getAnimatedValue();


               /*float pos[] = new float[2];
               float tan[] = new float[2];
               mCrawlPathMeasure.getPosTan(pathPostion,pos,tan);

               double direction=ViewTools.getArcTan2Mapped(tan);
               PointF crawlPointF = new PointF(pos[0],pos[1]);*/

               //if(getRepeatMode()==REVERSE&&getRepeatCount()%2==0) direction=(direction+Math.PI)%ViewTools.FULLCIRCLE;

               ViewTools.PathPosition pathPosition = ViewTools.getPathPosition(mCrawlPathMeasure,pathPostion);

               if(pathPostion<mPreviousValue){
                   pathPosition.rotation=(pathPosition.rotation+Math.PI)%ViewTools.FULLCIRCLE;
               }
               mPreviousValue=pathPostion;


               if(mCrawlingView!=null){
                   ViewTools.setCenter(mCrawlingView,pathPosition.position);

                   mCrawlingView.setRotation((float) Math.toDegrees(pathPosition.rotation));
               }

               onCrawl(pathPosition);
           }
       });
    }

    public void setView(View view){
        mCrawlingView=view;
    }



    public boolean setSegmenent(float startD, float endD){
        Path segment = new Path();
        if(mCrawlPathMeasure.getSegment(startD,endD,segment,true)){
            mCrawlPath=segment;
            mCrawlPathMeasure.setPath(segment,false);
            mCrawlPathLength=mCrawlPathMeasure.getLength();
            super.setFloatValues(0, mCrawlPathLength);
            return true;
        }else return false;

    }
    public abstract void onCrawl(ViewTools.PathPosition pathPosition);
}
