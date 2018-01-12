package com.kuruvatech.santoshkenchaamba.adapter;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by gagan on 10/27/2017.
 */
public class Adapter extends RecyclerView.Adapter {
    // I assume that you will pass images as list of resources, but this can be easily switched to a list of URLS

    public ArrayList<String> urls = new ArrayList<String>();
    public ImageLoader imageLoader;
    Activity con;
    public Adapter(Activity context, ArrayList<String> aurls) {

        this.urls = aurls;
        con = context;
        imageLoader = new ImageLoader(con.getApplicationContext(),500,500);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {

        View itemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item, viewGroup, false);
  //      LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


  //      LinearLayout mainLayout = (LinearLayout) itemView.findViewById((R.layout.recycler_view_item));
   //     ViewGroup.LayoutParams params = mainLayout.getLayoutParams();
//        params.height = 200;


        Display screenDisplay = con.getWindowManager().getDefaultDisplay();
        int LayoutHeight = screenDisplay.getHeight();
        int Layoutwidth = screenDisplay.getWidth();
        float ht = LayoutHeight;
        float wd = Layoutwidth;
        float ratio = 1;
        float height = wd /3;
        if(ht > wd)
        {
            ratio = ht/wd;
            height = height * ratio;

        }
        int height2 = (int)height;
        LinearLayout.LayoutParams listLayoutParams = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, height2);
        itemView.setLayoutParams(listLayoutParams);
        return new ItemViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder viewHolder, int i) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        imageLoader.DisplayImage(urls.get(i),  itemViewHolder.item);
       // itemViewHolder.item.setImageResource(imageResList.get(i));

    }
    public ArrayList<String> getFilePaths()
    {
        ArrayList<String> paths = new ArrayList<String>();
        for(int i = 0 ; i< urls.size();i++)
        {
            paths.add(imageLoader.getFilePath(urls.get(i)));
        }
        return paths;
    }

    public ArrayList<String> getImages()
    {
        return urls;
    }
    @Override
    public int getItemCount() {
        return urls.size();
    }


    private static class ItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView item;

        public ItemViewHolder(View itemView) {
            super(itemView);

            this.item = (ImageView) itemView.findViewById(R.id.item_image);
        }
    }

//    private class ScreenResolution {
//        int width;
//        int height;
//        public ScreenResolution(int width, int height) {
//            this.width = width;
//            this.height = height;
//        }
//    }
//
//    ScreenResolution deviceDimensions() {
//        int currentapiVersion = android.os.Build.VERSION.SDK_INT;
//        // getsize() is available from API 13
//        if (currentapiVersion >= android.os.Build.VERSION_CODES.HONEYCOMB_MR2) {
//            Display display = con.getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            return new ScreenResolution(size.x, size.y);
//        }
//        else {
//            Display display = con.getWindowManager().getDefaultDisplay();
//            // getWidth() & getHeight() are deprecated
//            return new ScreenResolution(display.getWidth(), display.getHeight());
//        }
//    }

}