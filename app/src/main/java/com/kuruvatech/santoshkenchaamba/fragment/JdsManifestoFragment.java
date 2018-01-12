package com.kuruvatech.santoshkenchaamba.fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kuruvatech.santoshkenchaamba.FullScreenViewActivity;
import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.RecyclerItemClickListener;
import com.kuruvatech.santoshkenchaamba.adapter.Adapter;
import com.kuruvatech.santoshkenchaamba.model.FeedItem;
import com.kuruvatech.santoshkenchaamba.utils.Constants;
import com.kuruvatech.santoshkenchaamba.utils.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import cz.msebera.android.httpclient.HttpEntity;
import cz.msebera.android.httpclient.HttpResponse;
import cz.msebera.android.httpclient.client.HttpClient;
import cz.msebera.android.httpclient.client.methods.HttpGet;
import cz.msebera.android.httpclient.impl.client.DefaultHttpClient;
import cz.msebera.android.httpclient.protocol.HTTP;
import cz.msebera.android.httpclient.util.EntityUtils;

public class JdsManifestoFragment extends Fragment {
    private static final String TAG_HEADING = "heading";
    private static final String TAG_DESCRIPTION = "description";
    private static final String TAG_FEEDIMAGES = "feedimages";
    private static final String TAG_URL = "url";
    RecyclerView recyclerView;
    Adapter adapter;
    // boolean isSwipeRefresh =true;
    ArrayList<FeedItem> feedList;
    ArrayList<String> imageList;
    SessionManager session;
    //private SwipeRefreshLayout swipeRefreshLayout;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.image_fragment_item_list, container, false);

        // Set the adapter
        if (view instanceof RecyclerView) {
            Context context = view.getContext();
//            swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_layout);
//            isSwipeRefresh = false;
            recyclerView = (RecyclerView) view;
            session = new SessionManager(getActivity().getApplicationContext());
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(), 3);
            // provide our CustomSpanSizeLookup which determines how many spans each item in grid will occupy
            gridLayoutManager.setSpanSizeLookup(new JdsManifestoFragment.CustomSpanSizeLookup());
            recyclerView.setLayoutManager(gridLayoutManager);
            imageList = new ArrayList<String>();
            initfromsession();
            getImages();
            recyclerView.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(),0,recyclerView ,new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position2, String myposition) {
                            Intent i = new Intent(getActivity(), FullScreenViewActivity.class);
                            i.putExtra("url", imageList.get(position2));
                            i.putExtra("position", position2);
                            i.putExtra("imageurls",imageList);
                            startActivity(i);
                        }

                        @Override
                        public void onLongItemClick(View view, int position2) {


                        }
                    })
            );
//            swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//                @Override
//                public void onRefresh() {
//                    isSwipeRefresh = true;
//                    getImages();
//                }
//
//            });
//
//            swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, R.color.colorAccent, R.color.colorPrimaryDark);
//            swipeRefreshLayout.setProgressBackgroundColor(android.R.color.transparent);

        }

        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
    public void initfromsession()
    {
        String data  = session.getManifestoFeed();

        if(data == null)
        {
            return;
        }
        imageList.clear();
        try
        {
            JSONArray feedimagesarray = new JSONArray(data);
            for (int j = 0; j < feedimagesarray.length(); j++) {
                JSONObject image_object = feedimagesarray.getJSONObject(j);
                if (image_object.has(TAG_URL)) {
                    imageList.add(image_object.getString(TAG_URL));
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
        // adapter = new FeedAdapter(getActivity(),R.layout.feeditem,feedList);
        adapter = new Adapter(getActivity(),imageList);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

//        if(imageList.size() > 0 ) {
//            noFeedstv.setVisibility(View.INVISIBLE);
//        }
//        else
//        {
//            noFeedstv.setVisibility(View.VISIBLE);
//        }
    }
    public void getImages()
    {
        String getFeedsUrl = Constants.GET_MANIFESTO_URL;
       // getFeedsUrl = getFeedsUrl + getString(R.string.username);
        new JdsManifestoFragment.JSONAsyncTask().execute(getFeedsUrl);
    }


    public  class JSONAsyncTask extends AsyncTask<String, Void, Boolean> {
        Dialog dialog;
        public JSONAsyncTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            if(isSwipeRefresh == false) {
//                swipeRefreshLayout.setRefreshing(true);
//            }

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

                if (status == 200) {
                    HttpEntity entity = response.getEntity();
                    String data = EntityUtils.toString(entity, HTTP.UTF_8);
                    session.setManifestFeed(data);
                    JSONArray feedimagesarray = new JSONArray(data);
                    imageList.clear();
                    for (int j = 0; j < feedimagesarray.length(); j++) {
                        JSONObject image_object = feedimagesarray.getJSONObject(j);
                        if (image_object.has(TAG_URL)) {
                            imageList.add(image_object.getString(TAG_URL));
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
//            if(swipeRefreshLayout != null)
//                swipeRefreshLayout.setRefreshing(false);
//            isSwipeRefresh = false;
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
    private static class CustomSpanSizeLookup extends GridLayoutManager.SpanSizeLookup {
        @Override
        public int getSpanSize(int i) {

//            if(i == 0 || i == 1) {
//                // grid items on positions 0 and 1 will occupy 2 spans of the grid
//                return 2;
//            } else {
//                // the rest of the items will behave normally and occupy only 1 span
            return 1;
//            }
        }
    }


}
