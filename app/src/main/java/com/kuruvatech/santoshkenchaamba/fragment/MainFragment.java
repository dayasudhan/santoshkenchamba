package com.kuruvatech.santoshkenchaamba.fragment;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.kuruvatech.santoshkenchaamba.MainActivity;
import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.adapter.FeedAdapter;
import com.kuruvatech.santoshkenchaamba.adapter.MainAdapter;
import com.kuruvatech.santoshkenchaamba.adapter.ScreenSlidePagerAdapter;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.CirclePageIndicator;
import com.kuruvatech.santoshkenchaamba.utils.Constants;
import com.kuruvatech.santoshkenchaamba.utils.MyViewPager;
import com.kuruvatech.santoshkenchaamba.utils.SessionManager;
import com.kuruvatech.santoshkenchaamba.utils.ZoomOutPageTransformer;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;


public class MainFragment extends Fragment {
    private static final String TAG_FEEDS = "newsfeed";
    private static final String TAG_SCROLLIMAGES = "scrollimages";
    private static final String TAG_HEADING = "heading";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_FEEDIMAGES = "feedimages";
    private static final String TAG_URL = "url";
    private static final String TAG_VIDEO = "feedvideo";
    public static final String API_KEY = "AIzaSyBRLKO5KlEEgFjVgf4M-lZzeGXW94m9w3U";
    public static final String VIDEO_ID = "gy5_T2ACerk";
    private static final String TAG_TIME = "time";
    private static final String TAG_FEEDVIDEOS = "feedvideos";
    private static final String TAG_FEEDAUDIOS = "feedaudios";

    Button btnshareApp;
    ArrayList<FeedItem> feedList;
    ArrayList<String> scrollimages;
    FeedAdapter adapter;
    MainAdapter adapter2;
    View rootview;
    //ListView listView;
    RecyclerView listView;
    TextView noFeedstv;
    boolean isSwipeRefresh;
    private SwipeRefreshLayout swipeRefreshLayout;
    SessionManager session;
    private MyViewPager pager;
    int sliderIndex=0,sliderMaxImages = 4;
    int delayMiliSec = 8000;
	private Handler handler;
    ScreenSlidePagerAdapter pagerAdapter;
    CirclePageIndicator indicator;
    CardView video_cardview;
    boolean isResponsereceived = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootview = inflater.inflate(R.layout.fragment_main, container, false);
        listView = (RecyclerView) rootview.findViewById(R.id.listView_feedlist);
        video_cardview = (CardView) rootview.findViewById(R.id.video_cardview);
        video_cardview.setVisibility(View.GONE);
       // recyclerView=(RecyclerView)view.findViewById(R.id.video_list);
        listView.setHasFixedSize(true);
        //to use RecycleView, you need a layout manager. default is LinearLayoutManager
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        listView.setLayoutManager(linearLayoutManager);



        noFeedstv = (TextView)rootview.findViewById(R.id.textView_no_feeds);
        session = new SessionManager(getActivity().getApplicationContext());
        swipeRefreshLayout = (SwipeRefreshLayout) rootview.findViewById(R.id.swipe_refresh_layout);
        ((MainActivity) getActivity())
                .setActionBarTitle(getString(R.string.titletext));

        isSwipeRefresh = false;
      //  feedList  =session.getLastNewsFeed();
//        if(feedList !=null)
//        {
//            initAdapter();
//        }


        handler = new Handler();
        pager = (MyViewPager) rootview.findViewById(R.id.pager);
//        int displayWidth2 =pager.getWidth();
//        int displayHeight2 = pager.getHeight();
//        int displayHeight = getActivity().getWindowManager().getDefaultDisplay().getHeight();
        int displayWidth = getActivity().getWindowManager().getDefaultDisplay().getWidth();
//        String dimention = " height=>" + String.valueOf(displayHeight) + " width=>" + String.valueOf(displayWidth) +
//                " height2=>" + String.valueOf(displayHeight2) + "width2=>" + String.valueOf(displayWidth2);
//        alertMessage(dimention);
        pagerAdapter =new ScreenSlidePagerAdapter(getActivity().getSupportFragmentManager(),getActivity().getApplicationContext(),displayWidth);
        pager.setPageTransformer(true, new ZoomOutPageTransformer());

        indicator = (CirclePageIndicator) rootview.findViewById(R.id.indicator);

//pager.setMinimumHeight();

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
//                if(isResponsereceived) {
//                    isSwipeRefresh = true;
//                    isResponsereceived = false;
//                    getFeeds();
//                }
            }

        });

        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
        swipeRefreshLayout.setProgressBackgroundColor(android.R.color.transparent);

        feedList = new ArrayList<FeedItem>();
        initfromsession();
        getFeeds();

       return rootview;
    }



    @Override
    public void onResume() {
        super.onResume();
        handler.postDelayed(runnable, delayMiliSec);
    }


    public void initfromsession()
    {
        String data  = session.getLastNewsFeed();

        if(data == null)
        {
            return;
        }
        feedList.clear();
        try {
            JSONObject feed_object2 = new JSONObject(data);

            if (feed_object2.has(TAG_FEEDS)) {
                //feed_object2.getString(TAG_FEEDS);
                JSONArray feedsarray = new JSONArray(feed_object2.getString(TAG_FEEDS));

                for (int i = feedsarray.length() - 1; i >= 0; i--) {
                    JSONObject feed_object = feedsarray.getJSONObject(i);
                    FeedItem feedItem = new FeedItem();
                    if (feed_object.has(TAG_HEADING)) {
                        feedItem.setHeading(feed_object.getString(TAG_HEADING));
                    }
                    if (feed_object.has(TAG_VIDEO)) {
                        feedItem.setVideoid(feed_object.getString(TAG_VIDEO));
                    }

                    if (feed_object.has(TAG_DESCRIPTION)) {
                        feedItem.setDescription(TextUtils.htmlEncode(feed_object.getString(TAG_DESCRIPTION)));
                    }
                    if (feed_object.has(TAG_FEEDIMAGES)) {
                        JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDIMAGES);
                        ArrayList<String> strList = new ArrayList<String>();
                        strList.clear();
                        for (int j = 0; j < feedimagesarray.length(); j++) {
                            JSONObject image_object = feedimagesarray.getJSONObject(j);
                            if (image_object.has(TAG_URL)) {
                                strList.add(image_object.getString(TAG_URL));
                            }
                        }
                        feedItem.setFeedimages(strList);

                    }
                    if (feed_object.has(TAG_TIME)) {
                        String time = feed_object.getString(TAG_TIME);
                        Date getDate = null;
                        SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s");
                        SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yy HH:mm a");
                        try {
                            getDate = existingUTCFormat.parse(time);
                            feedItem.setTime(fmtOut.format(getDate).toString());
                        } catch (java.text.ParseException e) {
                            e.printStackTrace();
                        }
                    }
                    if (feed_object.has(TAG_FEEDVIDEOS)) {
                        JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDVIDEOS);
                        ArrayList<String> strList = new ArrayList<String>();
                        strList.clear();
                        for (int j = 0; j < feedimagesarray.length(); j++) {
                            JSONObject image_object = feedimagesarray.getJSONObject(j);
                            if (image_object.has(TAG_URL)) {
                                strList.add(image_object.getString(TAG_URL));
                            }
                        }
                        feedItem.setFeedvideos(strList);

                    }
                    if (feed_object.has(TAG_FEEDAUDIOS)) {
                        JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDAUDIOS);
                        ArrayList<String> strList = new ArrayList<String>();
                        strList.clear();
                        for (int j = 0; j < feedimagesarray.length(); j++) {
                            JSONObject image_object = feedimagesarray.getJSONObject(j);
                            if (image_object.has(TAG_URL)) {
                                strList.add(image_object.getString(TAG_URL));
                            }
                        }
                        feedItem.setFeedaudios(strList);

                    }
                    feedList.add(feedItem);
                }
            }
            if (feed_object2.has(TAG_SCROLLIMAGES)) {
                JSONArray feedimagesarray = new JSONArray(feed_object2.getString(TAG_SCROLLIMAGES));
                scrollimages = new ArrayList<String>();
                scrollimages.clear();
                for (int j = 0; j < feedimagesarray.length(); j++) {
                    JSONObject image_object = feedimagesarray.getJSONObject(j);
                    if (image_object.has(TAG_URL)) {
                        scrollimages.add(image_object.getString(TAG_URL));
                    }
                }
            }
            initAdapter();
        }
        catch (Exception e) {

            e.printStackTrace();
        }
    }
    public void initAdapter()
    {

     //   adapter = new FeedAdapter(getActivity(),R.layout.feeditem,feedList);
        adapter2 = new  MainAdapter(getActivity(),feedList);
        adapter2.notifyDataSetChanged();
        listView.setAdapter(adapter2);
        if(feedList.size() > 0 ) {
            noFeedstv.setVisibility(View.INVISIBLE);
        }
        else
        {
            noFeedstv.setVisibility(View.VISIBLE);
        }
        if(scrollimages.size()>2) {
            pagerAdapter.addAll(scrollimages);
            pager.setAdapter(pagerAdapter);
            indicator.setViewPager(pager);
            pager.setVisibility(View.VISIBLE);
            video_cardview.setVisibility(View.VISIBLE);
        }
        else
        {
            video_cardview.setVisibility(View.GONE);
        }

    }
    public void getFeeds()
    {
        String getFeedsUrl = Constants.GET_FEEDS_URL;
        getFeedsUrl = getFeedsUrl + Constants.USERNAME;
        new JSONAsyncTask().execute(getFeedsUrl);
    }


public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        Dialog dialog;
        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if(isSwipeRefresh == false) {
                swipeRefreshLayout.setRefreshing(true);
//                dialog = new Dialog(getActivity(), android.R.style.Theme_Translucent);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.custom_progress_dialog);
//                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
//                dialog.show();
//                dialog.setCancelable(true);
            }

        }

        @Override
        protected Boolean doInBackground(String... urls) {
            try {

                //------------------>>
                HttpGet request = new HttpGet(urls[0]);
//                request.addHeader(Constants.SECUREKEY_KEY, Constants.SECUREKEY_VALUE);
//                request.addHeader(Constants.VERSION_KEY, Constants.VERSION_VALUE);
//                request.addHeader(Constants.CLIENT_KEY, Constants.CLIENT_VALUE);

                HttpClient httpclient = new DefaultHttpClient();

                HttpResponse response = httpclient.execute(request);

                int status = response.getStatusLine().getStatusCode();

                //feedList = new ArrayList<FeedItem>();
                feedList.clear();
                if (status == 200) {
                    HttpEntity entity = response.getEntity();

                    String data = EntityUtils.toString(entity,HTTP.UTF_8);
                    session.setLastNewsFeed(data);
                    //JSONArray feedsarray = new JSONArray(data);
                    JSONObject feed_object2 = new JSONObject(data);

                    if (feed_object2.has(TAG_FEEDS)) {
                        //feed_object2.getString(TAG_FEEDS);
                        JSONArray feedsarray = new JSONArray(feed_object2.getString(TAG_FEEDS));

                        for (int i = feedsarray.length() -1; i >= 0; i--) {
                            JSONObject feed_object = feedsarray.getJSONObject(i);
                            FeedItem feedItem = new FeedItem();
                            if (feed_object.has(TAG_HEADING)) {
                                feedItem.setHeading(feed_object.getString(TAG_HEADING));
                            }
                            if (feed_object.has(TAG_VIDEO)) {
                                feedItem.setVideoid(feed_object.getString(TAG_VIDEO));
                            }

                            if (feed_object.has(TAG_DESCRIPTION)) {
                                feedItem.setDescription(TextUtils.htmlEncode(feed_object.getString(TAG_DESCRIPTION)));
                            }
                            if (feed_object.has(TAG_FEEDIMAGES)) {
                                JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDIMAGES);
                                ArrayList<String> strList = new ArrayList<String>();
                                strList.clear();
                                for (int j = 0; j < feedimagesarray.length(); j++) {
                                    JSONObject image_object = feedimagesarray.getJSONObject(j);
                                    if (image_object.has(TAG_URL)) {
                                        strList.add(image_object.getString(TAG_URL));
                                    }
                                }
                                feedItem.setFeedimages(strList);

                            }
                            if (feed_object.has(TAG_TIME)) {
                                String time = feed_object.getString(TAG_TIME);
                                Date getDate = null;
                                SimpleDateFormat existingUTCFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:s");
                                SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MMM-yy HH:mm a");
                                try {
                                    getDate = existingUTCFormat.parse(time);
                                    feedItem.setTime(fmtOut.format(getDate).toString());
                                } catch (java.text.ParseException e) {
                                    e.printStackTrace();
                                }
                            }
                            if (feed_object.has(TAG_FEEDVIDEOS)) {
                                JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDVIDEOS);
                                ArrayList<String> strList = new ArrayList<String>();
                                strList.clear();
                                for (int j = 0; j < feedimagesarray.length(); j++) {
                                    JSONObject image_object = feedimagesarray.getJSONObject(j);
                                    if (image_object.has(TAG_URL)) {
                                        strList.add(image_object.getString(TAG_URL));
                                    }
                                }
                                feedItem.setFeedvideos(strList);

                            }
                            if (feed_object.has(TAG_FEEDAUDIOS)) {
                                JSONArray feedimagesarray = feed_object.getJSONArray(TAG_FEEDAUDIOS);
                                ArrayList<String> strList = new ArrayList<String>();
                                strList.clear();
                                for (int j = 0; j < feedimagesarray.length(); j++) {
                                    JSONObject image_object = feedimagesarray.getJSONObject(j);
                                    if (image_object.has(TAG_URL)) {
                                        strList.add(image_object.getString(TAG_URL));
                                    }
                                }
                                feedItem.setFeedaudios(strList);

                            }
                            feedList.add(feedItem);
                        }
                    }
                    if(feed_object2.has(TAG_SCROLLIMAGES))
                    {
                        JSONArray feedimagesarray = new JSONArray(feed_object2.getString(TAG_SCROLLIMAGES));
                        scrollimages = new ArrayList<String>();
                        scrollimages.clear();
                        for (int j = 0; j < feedimagesarray.length(); j++) {
                            JSONObject image_object = feedimagesarray.getJSONObject(j);
                            if (image_object.has(TAG_URL)) {
                                scrollimages.add(image_object.getString(TAG_URL));
                            }
                        }
                    }
                    return true;
                }
           } catch (IOException e) {

                e.printStackTrace();

            }
            catch (Exception e) {

                e.printStackTrace();
            }

            return false;

        }
        protected void onPostExecute(Boolean result) {
//            if(dialog != null && isSwipeRefresh ==false)
//                dialog.cancel();

            if(swipeRefreshLayout != null)
             swipeRefreshLayout.setRefreshing(false);
            isSwipeRefresh = false;
            isResponsereceived = true;
            if(getActivity() != null) {
                if (result == false) {

                   // Toast.makeText(getActivity().getApplicationContext(), "Unable to fetch data from server", Toast.LENGTH_LONG).show();
                    alertMessage("Unable to fetch data from server");
                } else {
                    initAdapter();

                }
            }

        }
    }
    public void alertMessage(String message) {
        DialogInterface.OnClickListener dialogClickListeneryesno = new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {

                    case DialogInterface.BUTTON_NEUTRAL:
                        break;
                }
            }
        };
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(getString(R.string.app_name));
        builder.setMessage(message).setNeutralButton("Ok", dialogClickListeneryesno)
                .setIcon(R.drawable.ic_action_about).show();

    }


    @Override
    public void onPause() {
        super.onPause();
        handler.removeCallbacks(runnable);
    }

	    Runnable runnable = new Runnable() {
        public void run() {
            if (sliderMaxImages == sliderIndex) {
                sliderIndex = 0;
            } else {
                sliderIndex++;
            }
            pager.setCurrentItem(sliderIndex, true);
            handler.postDelayed(this, delayMiliSec);
        }
    };
}
