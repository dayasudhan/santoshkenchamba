package com.kuruvatech.santoshkenchaamba.adapter;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.santoshkenchaamba.FeedDetail;
import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.RecyclerItemClickListener;
import com.kuruvatech.santoshkenchaamba.SingleViewActivity;
import com.kuruvatech.santoshkenchaamba.YouTubePlayerFragmentActivity;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by gagan on 10/23/2017.
 */
public class FeedAdapter extends BaseAdapter implements YouTubeThumbnailView.OnInitializedListener{
    Activity con;
    Typeface cr;
    int layoutResID;
    private ArrayList<FeedItem> mFeedList;
    public ImageLoader imageLoader;

    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    // public static final String VIDEO_ID = "gy5_T2ACerk";

    public FeedAdapter(Activity context, int layoutResourceID,
                       ArrayList<FeedItem> itemList) {
        con = context;
        mFeedList = itemList;
        layoutResID = layoutResourceID;
        Display display = context.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        imageLoader = new ImageLoader(con.getApplicationContext(),width,height);
       // gridLayoutManager = new GridLayoutManager(con, 2);
    }
    @Override
    public int getCount() {
        return mFeedList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }
    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView,
                                        YouTubeThumbnailLoader thumbnailLoader) {

        youTubeThumbnailLoader = thumbnailLoader;
//        youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());
        youTubeThumbnailLoader.setVideo(String.valueOf(youTubeThumbnailView.getTag()));
    }


    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
    }





    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        private int totalcount;
        public CustomSpanSizeLookup(int total)
        {
            totalcount = total;
        }
        @Override
        public int getSpanSize(int i) {

            if(i == 0 ) {
                // grid items on positions 0 and 1 will occupy 2 spans of the grid
                return 2;
            } else if(totalcount%2 == 0 && i == 1){
                // the rest of the items will behave normally and occupy only 1 span

                return 2;
            }
            else
            {
                return 1;
            }
        }
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       ItemHolder itemHolder;
        View view = convertView;


        itemHolder = new ItemHolder();
        if(view==null){
            LayoutInflater inflater = ((Activity)con).getLayoutInflater();
            view = inflater.inflate(layoutResID, parent, false);


            itemHolder = new ItemHolder();
            itemHolder.description= (TextView) view.findViewById(R.id.feed_description);
//            itemHolder.imageView= (ImageView) view.findViewById(R.id.vendor_image_view);
            itemHolder.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
            itemHolder.feedheading= (TextView) view.findViewById(R.id.feed_name);
            itemHolder.btShowmore=(Button)view.findViewById(R.id.btShowmore);
            itemHolder.imageshareButton= (ImageView)view.findViewById(R.id.shareit);
            // specify that grid will consist of 2 columns
            itemHolder.frameLayout = (FrameLayout)view.findViewById(R.id.youtube_frame);
            itemHolder.imagePlayBotton = (ImageView) view.findViewById(R.id.play_video);
            if( mFeedList.get(position).getVideoid().length() > 0) {
                youTubeThumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.youtubethumbnailview);
                youTubeThumbnailView.setTag(mFeedList.get(position).getVideoid());
                youTubeThumbnailView.initialize(API_KEY, this);
                youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){

                    @Override
                    public void onClick(View arg0) {
                        Intent i = new Intent(con, YouTubePlayerFragmentActivity.class);
                        i.putExtra("VIDEO_ID", mFeedList.get(position).getVideoid());
                        con.startActivity(i);
                    }});
            }
            else {
                itemHolder.frameLayout.setVisibility(View.GONE);
                itemHolder.imagePlayBotton.setVisibility(View.GONE);
            }
            view.setTag(itemHolder);
        }else{
            itemHolder = (ItemHolder) view.getTag();
            if( mFeedList.get(position).getVideoid().length() > 0) {
                youTubeThumbnailView = (YouTubeThumbnailView) view.findViewById(R.id.youtubethumbnailview);
                if (youTubeThumbnailLoader == null) {
                    // Case 3 - The loader is currently initializing
                    youTubeThumbnailView.setTag(mFeedList.get(position).getVideoid());
                } else {
                    // Case 2 - The loader is already initialized
                    youTubeThumbnailView.setImageResource(R.drawable.circle_indicator_black);
                    youTubeThumbnailLoader.setVideo(mFeedList.get(position).getVideoid());
                }
                youTubeThumbnailView.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {
//                        Intent i = new Intent(con, YouTubePlayerFragmentActivity.class);
//                        i.putExtra("VIDEO_ID", mFeedList.get(position).getVideoid());
//                        con.startActivity(i);
                        Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) con, API_KEY, mFeedList.get(position).getVideoid(),0,true,false);
                        con.startActivity(intent);

                    }
                });
            }
            else {
                itemHolder.frameLayout.setVisibility(View.GONE);
                itemHolder.imagePlayBotton.setVisibility(View.GONE);
            }

        }
        GridLayoutManager gridLayoutManager = new GridLayoutManager(con, 2);
        // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
        gridLayoutManager.setSpanSizeLookup(new CustomSpanSizeLookup(mFeedList.get(position).getFeedimages().size()));
        // provide our GridLayoutManager to the view
        itemHolder.recyclerView.setLayoutManager(gridLayoutManager);
        // this is fake list of images

        itemHolder.imageshareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {


                ArrayList<Uri> imageUris = new ArrayList<Uri>();


                Intent shareIntent = new Intent();
                // shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT,  mFeedList.get(position).getHeading());
                shareIntent.putExtra(Intent.EXTRA_TEXT,  mFeedList.get(position).getDescription());
                if(mFeedList.get(position).getFeedimages().size()> 0) {
                    shareIntent.setType("image/*");

                    if (mFeedList.get(position).getFeedimages().size() > 0) {
                        imageUris.add(Uri.parse(imageLoader.getFilePath(mFeedList.get(position).getFeedimages().get(0))));
                        shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    }

                }
                else
                {
                    shareIntent.setType("text/plain");
                }
                shareIntent.setAction(Intent.ACTION_SEND);
                con.startActivity(Intent.createChooser(shareIntent, "Share it ...."));
                //startActivity(Intent.createChooser(sendIntent, "Share link!"));
            }
        });
        if(mFeedList.get(position).getFeedimages().size()> 0) {
            Adapter adapter = new Adapter(con, mFeedList.get(position).getFeedimages());
            itemHolder.recyclerView.setAdapter(adapter);
            itemHolder.recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(con, position,itemHolder.recyclerView ,
                            new RecyclerItemClickListener.OnItemClickListener() {

                                @Override
                                public void onItemClick(View view, int position2, String mypositionurl) {
                                    Intent i = new Intent(con, SingleViewActivity.class);

                                    i.putExtra("url", mypositionurl);

                                    con.startActivity(i);

                            // do whatever
                            //mFeedList.get(position).getFeedimages().get(position2);
                            //    Toast.makeText(con,"hi click"+position2+mFeedList.get(position).getFeedimages().get(position2), Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onLongItemClick(View view, int position2) {


                        }
                    })
            );
        }
        else
        {
            itemHolder.recyclerView.setVisibility(View.GONE);
        }

        itemHolder.btShowmore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Toast.makeText(con,"hi hwllow",Toast.LENGTH_LONG).show();
                Intent i = new Intent(con, FeedDetail.class);
                Gson gson = new Gson();
                String strFeed = gson.toJson(mFeedList.get(position));
                i.putExtra("FeedItem", strFeed);
                con.startActivity(i);
            }
        });

        itemHolder.description.setText(mFeedList.get(position).getDescription());

        itemHolder.feedheading.setText(mFeedList.get(position).getHeading());

        if(mFeedList.get(position).getDescription().length() > 500 )
        {
            itemHolder.description.setMaxLines(5);
            itemHolder.btShowmore.setVisibility(View.VISIBLE);
        }
        else
        {
            itemHolder.btShowmore.setVisibility(View.GONE);
        }
        return view;

    }
    private static class ItemHolder {
        TextView description;
        TextView feedheading;
        ImageView imageView;
        Button btShowmore;
        RecyclerView recyclerView;
        ImageView imageshareButton;
        FrameLayout frameLayout;;
        ImageView imagePlayBotton;
    }


}
