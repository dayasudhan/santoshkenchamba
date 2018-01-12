package com.kuruvatech.santoshkenchaamba.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.VideoView;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubeStandalonePlayer;
import com.google.android.youtube.player.YouTubeThumbnailLoader;
import com.google.android.youtube.player.YouTubeThumbnailView;
import com.google.gson.Gson;
import com.kuruvatech.santoshkenchaamba.CustomMediaPlayer;
import com.kuruvatech.santoshkenchaamba.FeedDetail;
import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.RecyclerItemClickListener;
import com.kuruvatech.santoshkenchaamba.SingleViewActivity;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.ImageLoader;

import java.util.ArrayList;

/**
 * Created by dayas on 27-11-2017.
 */
public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MainFragmentInfoHolder> {


        ArrayList<FeedItem> mFeedList;
        Context con;
        public ImageLoader imageLoader;
        public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
        public


        MainAdapter(Context context , ArrayList<FeedItem> afeedList) {
            con = context;

            mFeedList = afeedList;
          //  layoutResID = layoutResourceID;
//       //     Display display = context.getWindowManager().getDefaultDisplay();
//            Point size = new Point();
//            display.getSize(size);
//            int width = size.x;
//            int height = size.y;
            imageLoader = new ImageLoader(con.getApplicationContext(),500,500);
        }

        @Override
        public MainFragmentInfoHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.feeditem, parent, false);
            return new MainFragmentInfoHolder(itemView);
        }

    @Override
        public void onBindViewHolder(final MainFragmentInfoHolder holder , final int position) {
            try {
                final MainFragmentInfoHolder itemViewHolder = (MainFragmentInfoHolder) holder;
                GridLayoutManager gridLayoutManager = new GridLayoutManager(con, 2);
                // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
                gridLayoutManager.setSpanSizeLookup(new CustomSpanSizeLookup(mFeedList.get(position).getFeedimages().size()));
                // provide our GridLayoutManager to the view
                itemViewHolder.itemHolder.recyclerView.setLayoutManager(gridLayoutManager);
                // this is fake list of images
                itemViewHolder.itemHolder.recyclerView.setVisibility(View.VISIBLE);
                itemViewHolder.itemHolder.youTubeThumbnailView.setVisibility(View.VISIBLE);
                itemViewHolder.itemHolder.frameLayout.setVisibility(View.VISIBLE);
                itemViewHolder.itemHolder.frameLayout2.setVisibility(View.VISIBLE);
                final YouTubeThumbnailLoader.OnThumbnailLoadedListener onThumbnailLoadedListener = new YouTubeThumbnailLoader.OnThumbnailLoadedListener() {
                    @Override
                    public void onThumbnailError(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader.ErrorReason errorReason) {

                    }

                    @Override
                    public void onThumbnailLoaded(YouTubeThumbnailView youTubeThumbnailView, String s) {
                        youTubeThumbnailView.setVisibility(View.VISIBLE);
                        itemViewHolder.itemHolder.relativeLayoutOverYouTubeThumbnailView.setVisibility(View.VISIBLE);
                    }
                };
                if (mFeedList.get(position).getFeedvideos().size() > 0 || mFeedList.get(position).getFeedaudios().size() > 0) {
                    itemViewHolder.itemHolder.youTubeThumbnailView.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.frameLayout.setVisibility(View.GONE);
                    Uri video;
                    if (mFeedList.get(position).getFeedvideos().size() > 0)
                        video = Uri.parse(mFeedList.get(position).getFeedvideos().get(0));
                    else
                        video = Uri.parse(mFeedList.get(position).getFeedaudios().get(0));
                    itemViewHolder.itemHolder.videoview.setVideoURI(video);
                    itemViewHolder.itemHolder.videoview.requestFocus();
                    itemViewHolder.itemHolder.videoview.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                        public void onPrepared(MediaPlayer mp) {

                            itemViewHolder.itemHolder.videoview.requestLayout();
                            itemViewHolder.itemHolder.progressBar.setVisibility(View.GONE);
                            itemViewHolder.itemHolder.videoview.seekTo(1000);
                        }
                    });


                } else if (!mFeedList.get(position).getVideoid().equals("")) {
                    itemViewHolder.itemHolder.frameLayout2.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.videoview.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.progressBar.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.playButton.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.youTubeThumbnailView.initialize(API_KEY, new YouTubeThumbnailView.OnInitializedListener() {
                        @Override
                        public void onInitializationSuccess(YouTubeThumbnailView youTubeThumbnailView, YouTubeThumbnailLoader youTubeThumbnailLoader) {
                            youTubeThumbnailLoader.setVideo(mFeedList.get(position).getVideoid());
                            youTubeThumbnailLoader.setOnThumbnailLoadedListener(onThumbnailLoadedListener);
                        }

                        @Override
                        public void onInitializationFailure(YouTubeThumbnailView youTubeThumbnailView, YouTubeInitializationResult youTubeInitializationResult) {
                            //write something for failure
                        }
                    });
                } else {
                    itemViewHolder.itemHolder.frameLayout2.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.videoview.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.progressBar.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.playButton.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.youTubeThumbnailView.setVisibility(View.GONE);
                    itemViewHolder.itemHolder.frameLayout.setVisibility(View.GONE);
                }
                itemViewHolder.itemHolder.imageshareButton.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View arg0) {


                        ArrayList<Uri> imageUris = new ArrayList<Uri>();


                        Intent shareIntent = new Intent();
                        // shareIntent.setType("text/html");
                        String strshare = "";
                        if (mFeedList.get(position).getDescription().length() > 0) {
                            strshare = new String(mFeedList.get(position).getDescription());
                        } else {
                            strshare = new String(mFeedList.get(position).getHeading());
                        }
                        if (!mFeedList.get(position).getVideoid().equals("")) {
                            String youtube_link = "https://www.youtube.com/watch?v=";
                            youtube_link = youtube_link + mFeedList.get(position).getVideoid();
                            youtube_link = youtube_link + "\n";

                            strshare = strshare + "\n";
                            strshare = strshare + youtube_link;
                        }
                        strshare = strshare + "\n";
                        strshare = strshare + con.getString(R.string.invitelink);
                        shareIntent.putExtra(Intent.EXTRA_SUBJECT, mFeedList.get(position).getHeading());
                        shareIntent.putExtra(Intent.EXTRA_TEXT, strshare);


                        if (mFeedList.get(position).getFeedimages().size() > 0) {
                            shareIntent.setType("image/*");

                            if (mFeedList.get(position).getFeedimages().size() > 0) {
                                imageUris.add(Uri.parse(imageLoader.getFilePath(mFeedList.get(position).getFeedimages().get(0))));
                                shareIntent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, imageUris);
                            }

                        } else {
                            shareIntent.setType("text/plain");
                        }
                        shareIntent.setAction(Intent.ACTION_SEND);
                        con.startActivity(Intent.createChooser(shareIntent, "Share it ...."));
                        //startActivity(Intent.createChooser(sendIntent, "Share link!"));
                    }
                });
                if (mFeedList.get(position).getFeedimages().size() > 0) {
                    Adapter adapter = new Adapter((Activity) con, mFeedList.get(position).getFeedimages());
                    itemViewHolder.itemHolder.recyclerView.setAdapter(adapter);


                } else {
                    itemViewHolder.itemHolder.recyclerView.setVisibility(View.GONE);
                }
                itemViewHolder.itemHolder.recyclerView.addOnItemTouchListener(
                        new RecyclerItemClickListener(con, position, itemViewHolder.itemHolder.recyclerView,
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
                itemViewHolder.itemHolder.btShowmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //  Toast.makeText(con,"hi hwllow",Toast.LENGTH_LONG).show();
                        if (mFeedList.size() > position) {
                            Intent i = new Intent(con, FeedDetail.class);
                            Gson gson = new Gson();
                            String strFeed = gson.toJson(mFeedList.get(position));
                            i.putExtra("FeedItem", strFeed);
                            con.startActivity(i);
                        }
                    }
                });

                itemViewHolder.itemHolder.description.setText(mFeedList.get(position).getDescription());
                itemViewHolder.itemHolder.feedtime.setText(mFeedList.get(position).getTime());
                itemViewHolder.itemHolder.feedheading.setText(mFeedList.get(position).getHeading());

                if (mFeedList.get(position).getDescription().length() > 500) {
                    itemViewHolder.itemHolder.description.setMaxLines(5);
                    itemViewHolder.itemHolder.btShowmore.setVisibility(View.VISIBLE);
                } else {
                    itemViewHolder.itemHolder.btShowmore.setVisibility(View.GONE);
                }

                itemViewHolder.itemHolder.playButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(con, CustomMediaPlayer.class);
                        String video = new String();
                        if (!mFeedList.get(position).getFeedvideos().isEmpty())
                            video = mFeedList.get(position).getFeedvideos().get(0);
                        else
                            video = mFeedList.get(position).getFeedaudios().get(0);
                        intent.putExtra("url", video);
                        con.startActivity(intent);
                    }
                });
            }
            catch(Exception e)
            {

            }
        }

        @Override
        public int getItemCount() {
            return mFeedList.size();
        }

        public class MainFragmentInfoHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            ItemHolder itemHolder;


            public MainFragmentInfoHolder(View view) {

                super(view);

              //  LayoutInflater inflater = ((Activity)con).getLayoutInflater();
              //  itemView = inflater.inflate(layoutResID, parent, false);


                itemHolder = new ItemHolder();
                itemHolder.description= (TextView) view.findViewById(R.id.feed_description);
//            itemHolder.imageView= (ImageView) view.findViewById(R.id.vendor_image_view);
                itemHolder.recyclerView = (RecyclerView) view.findViewById(R.id.recycler_view);
                itemHolder.feedheading= (TextView) view.findViewById(R.id.feed_name);
                itemHolder.feedtime= (TextView) view.findViewById(R.id.feed_time);
                itemHolder.btShowmore=(Button)view.findViewById(R.id.btShowmore);
                itemHolder.imageshareButton= (ImageView)view.findViewById(R.id.shareit);
                // specify that grid will consist of 2 columns
                itemHolder.frameLayout = (FrameLayout)view.findViewById(R.id.youtube_frame);
                itemHolder.imagePlayBotton = (ImageView) view.findViewById(R.id.play_video);
                itemHolder.relativeLayoutOverYouTubeThumbnailView = (RelativeLayout) itemView.findViewById(R.id.relativeLayout_over_youtube_thumbnail);
                itemHolder.youTubeThumbnailView = (YouTubeThumbnailView) itemView.findViewById(R.id.youtube_thumbnail);
                itemHolder.imagePlayBotton.setOnClickListener(this);
                itemHolder.videoview = (VideoView) view.findViewById(R.id.VideoView);
                itemHolder.playButton = (ImageView) itemView.findViewById(R.id.btnvideo_player);
                itemHolder.progressBar = (ProgressBar) itemView.findViewById(R.id.progressBar2);
                itemHolder.frameLayout2 = (FrameLayout)view.findViewById(R.id.framelayout2);
//                if( mFeedList.get(position).getVideoid().length() > 0) {
//                    youTubeThumbnailView = (YouTubeThumbnailView)view.findViewById(R.id.youtubethumbnailview);
//                    youTubeThumbnailView.setTag(mFeedList.get(position).getVideoid());
//                    youTubeThumbnailView.initialize(API_KEY, this);
//                    youTubeThumbnailView.setOnClickListener(new View.OnClickListener(){
//
//                        @Override
//                        public void onClick(View arg0) {
//                            Intent i = new Intent(con, YouTubePlayerFragmentActivity.class);
//                            i.putExtra("VIDEO_ID", mFeedList.get(position).getVideoid());
//                            con.startActivity(i);
//                        }});
//                }
//                else {
//                    itemHolder.frameLayout.setVisibility(View.GONE);
//                    itemHolder.imagePlayBotton.setVisibility(View.GONE);
//                }
                view.setTag(itemHolder);
            }

            @Override
            public void onClick(View v) {

                Intent intent = YouTubeStandalonePlayer.createVideoIntent((Activity) con, API_KEY, mFeedList.get(getLayoutPosition()).getVideoid(),0,true,false);
                con.startActivity(intent);
            }
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
    private static class ItemHolder {
        TextView description;
        TextView feedheading;
        TextView feedtime;
        ImageView imageView;
        Button btShowmore;
        RecyclerView recyclerView;
        ImageView imageshareButton;
        FrameLayout frameLayout;;
        ImageView imagePlayBotton;
        YouTubeThumbnailView youTubeThumbnailView;
        protected RelativeLayout relativeLayoutOverYouTubeThumbnailView;
        VideoView videoview;
        ProgressBar progressBar ;
        //        ProgressDialog pDialog;
        ImageView playButton;
        FrameLayout frameLayout2;;
    }
    }
