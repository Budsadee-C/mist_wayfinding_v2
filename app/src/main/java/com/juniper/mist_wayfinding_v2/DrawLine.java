package com.juniper.mist_wayfinding_v2;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.widget.ImageView;

import com.mist.android.MSTMap;
import com.mist.android.MSTPoint;
import com.juniper.mist_wayfinding_v2.MSTPath;


import java.util.ArrayList;

public class DrawLine extends View {
    Paint paint = new Paint();
    Paint paint1 = new Paint();
    Path path = new Path();
    ArrayList<MSTPath> pathArrayList;
    ArrayList<String> pathArr;
    MSTPoint nearestMstPoint;
    ImageView imageView;
    private double scaleXFactor;
    private double scaleYFactor;
    MSTMap mstMap;
    boolean isActualData;


    public DrawLine(Context context, ArrayList<MSTPath> pathArrayList,
                    ArrayList<String> pathArr, MSTPoint nearestMstPoint, double scaleXFactor,
                    double scaleYFactor, MSTMap mstMap, boolean isActualData, ImageView image) {
        super(context);
        this.pathArrayList = pathArrayList;
        this.pathArr = pathArr;
        this.nearestMstPoint = nearestMstPoint;
        this.scaleXFactor = scaleXFactor;
        this.scaleYFactor = scaleYFactor;
        this.mstMap = mstMap;
        this.isActualData = isActualData;
        this.imageView = image;

        if(!isActualData) {
            this.scaleXFactor =  scaleXFactor * mstMap.getPpm();
            this.scaleYFactor =  scaleYFactor * mstMap.getPpm();
        }



        paint.setColor(Color.parseColor("#d5e4fb"));
        paint.setStrokeWidth(6);
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.STROKE);

        paint1.setColor(Color.parseColor("#f498ad"));
        paint1.setStrokeWidth(6);
        paint1.setAntiAlias(true);
        paint1.setStyle(Paint.Style.STROKE);

    }



    @Override
    public void onDraw(Canvas canvas) {
        if (pathArrayList != null && pathArrayList.size() > 0) {
            boolean nearestPointAdded = false;
            boolean hasWayfindingValue = false;
            for (MSTPath path : pathArrayList) {
                if (path.isWayfinding()) {
                    hasWayfindingValue = true;
                    if (pathArr != null && pathArr.size() > 1 && nearestMstPoint != null) {
                        boolean hasNearestPoint = path.getMstPointArrayList().contains(nearestMstPoint);
                        if (hasNearestPoint) {
                            nearestPointAdded = true;
                            String sName = pathArr.get(pathArr.size() - 2);
                            String eName = pathArr.get(pathArr.size() - 1);
                            if (path.getStartingEdgeName().equals(sName) && path.getEndEdgeName().equals(eName)) {

                                    Path path1 = new Path();
                                    path1.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                                    path1.lineTo((float) (path.getStartingPoint().getX() * scaleXFactor), (float) (path.getStartingPoint().getY() * scaleYFactor));
                                    canvas.drawPath(path1, paint1);


                                Path path2 = new Path();
                                path2.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                                path2.lineTo((float) (path.getEndPoint().getX() * scaleXFactor), (float) (path.getEndPoint().getY() * scaleYFactor));
                                canvas.drawPath(path2, paint);
                            } else {

                                    Path path1 = new Path();
                                    path1.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                                    path1.lineTo((float) (path.getStartingPoint().getX() * scaleXFactor), (float) (path.getStartingPoint().getY() * scaleYFactor));
                                    canvas.drawPath(path1, paint);


                                Path path2 = new Path();
                                path2.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                                path2.lineTo((float) (path.getEndPoint().getX() * scaleXFactor), (float) (path.getEndPoint().getY() * scaleYFactor));
                                canvas.drawPath(path2, paint1);
                            }

                        } else {
                            canvas.drawPath(path.getPath(), paint1);
                        }
                    } else
                        canvas.drawPath(path.getPath(), paint1);
                } else   {
                    canvas.drawPath(path.getPath(), paint);
                }
            }
            //imageView.setImageBitmap(bitmap);
            if (hasWayfindingValue && !nearestPointAdded) {
                if (pathArr != null && pathArr.size() > 1 && nearestMstPoint != null) {
                    String eName = pathArr.get(pathArr.size() - 1);
                    for (MSTPath path : pathArrayList) {
                        if (path.getStartingEdgeName().equals(eName)) {
                            Path path2 = new Path();
                            path2.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                            path2.lineTo((float) (path.getStartingPoint().getX() * scaleXFactor), (float) (path.getStartingPoint().getY() * scaleYFactor));
                            canvas.drawPath(path2, paint1);

                            break;
                        } else if (path.getEndEdgeName().equals(eName)) {
                            Path path2 = new Path();
                            path2.moveTo((float) (nearestMstPoint.getX()), (float) (nearestMstPoint.getY()));
                            path2.lineTo((float) (path.getEndPoint().getX() * scaleXFactor), (float) (path.getEndPoint().getY() * scaleYFactor));
                            canvas.drawPath(path2, paint1);
                            break;
                        }
                    }
                    //imageView.setImageBitmap(bitmap);
                }
            }

        } else
            canvas.drawPath(path, paint);
    }

}

