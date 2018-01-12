package com.kuruvatech.santoshkenchaamba.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.RecyclerItemClickListener;
import com.kuruvatech.santoshkenchaamba.SingleViewActivity;
import com.kuruvatech.santoshkenchaamba.adapter.Adapter;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.ImageLoader;
import com.kuruvatech.santoshkenchaamba.utils.SessionManager;

import java.util.ArrayList;


public class AboutFragment extends Fragment {

    View rootview;
    SessionManager session;
    FeedItem feedItem;
    TextView description;
    TextView feedheading;
    ImageView imageshareButton;
    public ImageLoader imageLoader;
    // Button btnBack;
    RecyclerView recyclerView;
    Adapter adapter;
    FrameLayout frameLayout;;
    ImageView imagePlayBotton;
    private YouTubeThumbnailView youTubeThumbnailView;
    private YouTubeThumbnailLoader youTubeThumbnailLoader;

    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.about_candidate, container, false);
        session = new SessionManager(getActivity().getApplicationContext());

        imageLoader = new ImageLoader(getContext(),500,500);
       // Intent intent = getIntent();
        initContent();
        Gson gson = new Gson();
        //feedItem = gson.fromJson(intent.getStringExtra("FeedItem"), FeedItem.class);
        description= (TextView) rootview.findViewById(R.id.detail_feed_description);
        imageshareButton= (ImageView) rootview.findViewById(R.id.detail_shareit );
        feedheading= (TextView) rootview.findViewById(R.id.detail_feed_name);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.detail_recycler_view);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 2);
        // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
        gridLayoutManager.setSpanSizeLookup(new CustomSpanSizeLookup());
        recyclerView.setLayoutManager(gridLayoutManager);
        adapter = new Adapter(getActivity(),feedItem.getFeedimages());
        recyclerView.setAdapter(adapter);
        frameLayout = (FrameLayout)rootview.findViewById(R.id.youtube_frame);
        imagePlayBotton = (ImageView)rootview.findViewById(R.id.play_video);

//        if( feedItem.getVideoid().length() > 0) {
//            youTubeThumbnailView = (YouTubeThumbnailView)rootview.findViewById(R.id.youtubethumbnailview);
//            youTubeThumbnailView.setTag(feedItem.getVideoid());
//            youTubeThumbnailView.initialize(API_KEY, this);
//            youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){
//
//                @Override
//                public void onClick(View arg0) {
//                    Intent i = new Intent(getContext(), YouTubePlayerFragmentActivity.class);
//                    i.putExtra("VIDEO_ID", feedItem.getVideoid());
//                    startActivity(i);
//                }});
//        }
//        else {
//            frameLayout.setVisibility(View.GONE);
//            imagePlayBotton.setVisibility(View.GONE);
//        }

        recyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(getContext(),0,recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position2, String myposition) {
                        Intent i = new Intent(getContext(), SingleViewActivity.class);
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
                Intent shareIntent = new Intent();
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, feedItem.getHeading());
                String strshare = "";
                if(feedItem.getDescription().length() > 0) {
                    strshare = new String(feedItem.getDescription());
                }
                else
                {
                    strshare = new String(feedItem.getHeading());
                }
                strshare = strshare  + "\n";
                strshare = strshare + getString(R.string.invitelink);
                shareIntent.putExtra(Intent.EXTRA_TEXT, strshare);

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

        return rootview;
    }

    private void initContent() {
        feedItem =  new FeedItem();
        feedItem.setDescription(getString(R.string.about_person_description));
    }


    @Override
    public void onResume() {
        super.onResume();
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
