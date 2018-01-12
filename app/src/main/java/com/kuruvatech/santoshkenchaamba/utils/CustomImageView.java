package com.kuruvatech.santoshkenchaamba.utils;



import android.content.Context;
import android.graphics.drawable.Drawable;

/**
 * Created by dayas on 24-11-2017.
 */

public class CustomImageView extends android.support.v7.widget.AppCompatImageView {
    public CustomImageView(Context context) {
        super(context);
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        Drawable drawable = getDrawable();
        if (drawable != null)
        {
            //get imageview width
            int width =  MeasureSpec.getSize(widthMeasureSpec);


            int diw = drawable.getIntrinsicWidth();
            int dih = drawable.getIntrinsicHeight();
            float ratio = (float)diw/dih; //get image aspect ratio

            int height = (int) (width * ratio);

            //don't let height exceed width
            if (height > width){
                height = width;
            }


            setMeasuredDimension(width, height);
        }
        else
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

}
