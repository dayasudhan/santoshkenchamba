package com.kuruvatech.santoshkenchaamba;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.santoshkenchaamba.adapter.Adapter;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.ImageLoader;

import java.util.ArrayList;

public class FeedDetail extends AppCompatActivity implements YouTubeThumbnailView.OnInitializedListener{

    FeedItem feedItem;
    TextView description;
    TextView feedtime;
    TextView feedheading;
    ImageView imageshareButton;
    public ImageLoader imageLoader;
   // Button btnBack;
    RecyclerView recyclerView;
    Adapter adapter;
    FrameLayout frameLayout;;
    ImageView imagePlayBotton ,toolbarImage;
    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_detail);
        imageLoader = new ImageLoader(getApplicationContext(),500,500);
        Intent intent = getIntent();
        Gson gson = new Gson();
        feedItem = gson.fromJson(intent.getStringExtra("FeedItem"), FeedItem.class);
        description= (TextView) findViewById(R.id.detail_feed_description);
        feedtime= (TextView) findViewById(R.id.detail_feed_time);
        imageshareButton= (ImageView) findViewById(R.id.detail_shareit );
        toolbarImage = (ImageView) findViewById(R.id.toolbarImage );
         feedheading= (TextView) findViewById(R.id.detail_feed_name);
        recyclerView = (RecyclerView) findViewById(R.id.detail_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
        // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
        gridLayoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);
        ArrayList<String> imagelist = feedItem.getFeedimages();
        if(feedItem.getFeedimages().size()>0)
        {
            imageLoader.DisplayImage(feedItem.getFeedimages().get(0),toolbarImage);
            imagelist.remove(0);
        }
        else
        {
            toolbarImage.setVisibility(View.GONE);
        }


        adapter = new Adapter(this,imagelist);
        recyclerView.setAdapter(adapter);
        frameLayout = (FrameLayout)findViewById(R.id.youtube_frame);
        imagePlayBotton = (ImageView)findViewById(R.id.play_video);

        if( feedItem.getVideoid().length() > 0) {
            youTubeThumbnailView = (YouTubeThumbnailView)findViewById(R.id.youtubethumbnailview);
            youTubeThumbnailView.setTag(feedItem.getVideoid());
            youTubeThumbnailView.initialize(API_KEY, this);
            youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View arg0) {
                    Intent i = new Intent(getApplicationContext(), YouTubePlayerFragmentActivity.class);
                    i.putExtra("VIDEO_ID", feedItem.getVideoid());
                    startActivity(i);
                }});
        }
        else {
            frameLayout.setVisibility(View.GONE);
            imagePlayBotton.setVisibility(View.GONE);
        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getApplicationContext(),0,recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position2, String myposition) {
                        Intent i = new Intent(getApplicationContext(), SingleViewActivity.class);
                        i.putExtra("url", feedItem.getFeedimages().get(position2));
                        startActivity(i);
                    }

                    @Override
                    public void onLongItemClick(View view, int position2) {


                    }
                })
        );

      //  btnBack = (Button) findViewById(R.id.back_button);
        description.setText(feedItem.getDescription());
        feedheading.setText(feedItem.getHeading());
        feedtime.setText(feedItem.getTime());
        //imageLoader.DisplayImage(feedItem.getFeedimages().get(0), imageView);

//        btnBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View arg0) {
//                onBackPressed();
//            }
//        });

        imageshareButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
//                Intent sendIntent = new Intent();
//                sendIntent.setAction(Intent.ACTION_SEND);
//                sendIntent.setType("text/plain");
//                sendIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getHeading());
//                sendIntent.putExtra(Intent.EXTRA_TEXT, feedItem.getDescription());

                Intent shareIntent = new Intent();
               // shareIntent.setType("text/html");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getHeading());
                shareIntent.putExtra(Intent.EXTRA_TEXT, feedItem.getDescription());



                shareIntent.setAction(Intent.ACTION_SEND);

                if(feedItem.getFeedimages().size() > 0)
                {
                    ArrayList<Uri> imageUris = new ArrayList<Uri>();
                    for(int i = 0 ; i< adapter.getFilePaths().size() && i < 1 ;i++)
                    {
                        //Uri imageFilePath = Uri.parse(adapter.getFilePaths().get(i));
                        imageUris.add(Uri.parse(adapter.getFilePaths().get(i)));
                        // Toast.makeText(FeedDetail.this, adapter.getFilePaths().get(i), Toast.LENGTH_SHORT).show();
                    }
                    shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                    shareIntent.setType("image/*");
                }
                else
                {
                    shareIntent.setType("text/plain");
                }
                startActivity(Intent.createChooser(shareIntent, "Share it ...."));
                //startActivity(Intent.createChooser(sendIntent, "Share link!"));
            }
        });
       setToolBar("");
    } //9740668897

    private void setToolBar(String areaClicked) {
        Toolbar tb = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.mipmap.ic_back);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle(areaClicked);

        CollapsingToolbarLayout collapsingToolbar;
        collapsingToolbar =  (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);
       // collapsingToolbar.set
//        tb.setNavigationIcon(android.support.v7.appcompat.R.drawable.abc_ic_ab_back_material);
//        tb.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                onBackPressed();
//            }
//        });
    }

    @Override
    public boolean onOptionsItemSelected(android.view.MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
        youTubeThumbnailLoader = youTubeThumbnailLoader;
   //     youTubeThumbnailLoader.setOnThumbnailLoadedListener(new ThumbnailLoadedListener());
        youTubeThumbnailLoader.setVideo(String.valueOf(youTubeThumbnailView.getTag()));
    }

    @Override
    public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {

    }

    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int i) {

//            if(i == 0 || i == 1) {
//                // grid items on positions 0 and 1 will occupy 2 spans of the grid
//                return 2;
//            } else {
//                // the rest of the items will behave normally and occupy only 1 span
                return 2;
//            }
        }
    }
}
