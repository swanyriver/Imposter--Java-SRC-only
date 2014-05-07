package SwansonLibrary;

import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;

import java.util.Random;

/**
 * Created by Brandon on 4/29/14.
 */
public class WindyPath extends Path{

    ////CONTROL VARIABLES
    private int mLineNumber;
    /////////////////////

    ///DEFAULT CONTROL VARIABLES
    private int mLineNumberDEFAULT=100;
    private int mMarginDEFAULT=30;
    private float mMinLengthPercentageDEFAULT=.17F;

    ///FIXED CONTROL VARIABLES
    static public float MINIMUM_ANGLE_DEFAULT = .23f;  //as a percent of 180  .25 = 45degrees = 90 degrees total


    ///DATA STRUCTURE
    private Point mLinePoints[];
    private PointGenerator mPointGenerator;
    private Rect mBounds;

    /////UTILITY
    private Random randomGen = new Random();



    //////////////CONSTRUCTOR

    public WindyPath(int Width, int Height) {

        inflateData(mLineNumberDEFAULT,mMarginDEFAULT,Width,Height,mMinLengthPercentageDEFAULT,MINIMUM_ANGLE_DEFAULT);
    }
    public WindyPath(Point size){
        inflateData(mLineNumberDEFAULT,mMarginDEFAULT,size.x,size.y,mMinLengthPercentageDEFAULT,MINIMUM_ANGLE_DEFAULT);
    }
    public WindyPath(int Width, int Height, int LineNumber, float MinLengthPercentage, int Margin, float minAngle) {

        inflateData(LineNumber,Margin,Width,Height,MinLengthPercentage,minAngle);

    }
    public WindyPath(int Width, int Height,int LineNumber ,float MinLengthPercentage, float minAngle,Rect bounds) {
        inflateData(LineNumber,MinLengthPercentage,minAngle,bounds);

    }
    public WindyPath(Rect bounds, int LineNumber) {
        inflateData(LineNumber,mMinLengthPercentageDEFAULT,MINIMUM_ANGLE_DEFAULT,bounds);

    }



    private void inflateData(int LineNumber, int Margin, int Width, int Height, float MinLengthPercentage, float MinAngle){
        Rect bounds = new Rect();

        bounds.right = Width-Margin;
        bounds.left = 0+Margin;
        bounds.top = Height-Margin;
        bounds.bottom= 0+Margin;

        inflateData(LineNumber,MinLengthPercentage, MinAngle,bounds);
    }

    private void inflateData(int LineNumber,float MinLengthPercentage, float MinAngle, Rect bounds) {

        mBounds = bounds;

        mLineNumber=LineNumber;

        int MaxLength = (int) ViewTools.getHypotenuse(bounds.right-bounds.left,bounds.top-bounds.bottom);
        int MinLength = (int) (MaxLength*MinLengthPercentage);

        if(MinAngle>MINIMUM_ANGLE_DEFAULT)MinAngle=MINIMUM_ANGLE_DEFAULT;

        mPointGenerator = new PointGenerator(MaxLength,MinLength,bounds,MinAngle);

        generate();

    }

    public void generate(){
        rewind();

        mLinePoints = new Point[mLineNumber+1];

        mLinePoints[0]=getRandomPoint();
        mLinePoints[1]=mPointGenerator.makeMap(mLinePoints[0],mLinePoints[0]).getPoint();
        for(int x=2;x<mLineNumber+1;x++){

            mLinePoints[x]=mPointGenerator.makeMap(mLinePoints[x-2],mLinePoints[x-1]).getPoint();

        }

        makeCurves();



    }

    private void makeCurves() {
        Point midpoints[] = new Point[mLineNumber];
        for(int x=0;x<mLineNumber;x++)midpoints[x]=getMidPoint(mLinePoints[x], mLinePoints[x+1]);

        moveTo(midpoints[0].x, midpoints[0].y);
        for(int i=1;i<mLineNumber;i++){
            quadTo(mLinePoints[i].x, mLinePoints[i].y, midpoints[i].x, midpoints[i].y);
        }
    }



    ////////////UTILITY FUCTIONS

    private Point getRandomPoint(){
        Point thisPoint = new Point();
        thisPoint.x = randomGen.nextInt(mBounds.right-mBounds.left)+mBounds.left;
        thisPoint.y = randomGen.nextInt(mBounds.top-mBounds.bottom)+mBounds.bottom;
        return thisPoint;

    }

    private Point getMidPoint(Point start, Point end){
        int dx = end.x - start.x;
        int dy = end.y - start.y;

        int orginalLineMidX = start.x + Math.round(.5f * dx);
        int orginalLineMidY = start.y + Math.round(.5f * dy);

        return new Point(orginalLineMidX,orginalLineMidY);
    }

    ///FOR DEBUG
    public Point[] getLinePoints(){
        return mLinePoints;
    }
    public int getLineNumber() {
        return mLineNumber;
    }
}
