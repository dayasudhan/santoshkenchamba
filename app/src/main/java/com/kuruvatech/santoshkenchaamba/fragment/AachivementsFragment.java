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


public class AachivementsFragment extends Fragment {

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

        return rootview;
    }

    private void initContent() {
        feedItem =  new FeedItem();
        feedItem.setDescription("Packages of Rs.2689.64 Crore for six districts which reported high incidence of farmer suicides. The district are Belguam,Hassan,Chickmagalore ,Chitradirga,shimaga and Kodagu\n" +
                "Moved by the grievances from the women whom he met during his Janatha darshana and village stay where they complained about their husbands who are drunkards and there families were in trouble from Arrack and lottery Kumaraswamy took a decision of Banning Arrack and Lottery.\n" +
                "Suvarna Karnataka Udyanagala Pratistana where in all garden and parks belonging to the government will be taken care of.\n" +
                "Swavalambana Scheme lending money to women engaged in fishing at the rate of 4%.\n" +
                "Karnataka is the only state next to Sikkim where decentralization of power has been implemented in letter as the spirit as envisaged in 73rd amendment to the constitution.\n" +
                "‘Suvarna Kayaka Udyoga Shikshana Yojane’ has been launched. The scheme aims to identify the needs of the organized sectors for skilled man power in various trades and to train qualified eligible youths to undergo hands-on training. The expenditure on training is to be shared equally by industry and the Government. This Public Private Partnership will provide employment opportunities to the rural and urban youth of the State.\n" +
                "Declining sex ratio is a matter of great concern . To arrest the trend, the Kumaraswamy Government has launched a novel scheme called “Bhagyalakshmi” to protect the girl child and change society’s attitude towards her. Under the scheme, an amount Rs. 10,000 is deposited in the name of each female child born after 31st March 2006\n" +
                "Waving loan up to 25000 for all the farmers of Karnataka .\n" +
                "Jawaharlal Nehru national Urban renewal mission scheme for improving quality of life of slum dwellers.\n" +
                "Reduced the interest burden of all institutional loans to farmers to 4% as has in respect of the cooperative sector.\n" +
                "Announced the package 0f 7000 crore rupees for the welfare of the farmers.\n" +
                "70% of the application received in janathadarshana were solved and 25 crore Rupees were given directly to the people whom he met in janathadarshana.\n" +
                "Suvarna Gramodaya Program integrating the Implementation of various development programmes and providing infrastructure facilities comprehensively to transform 1000 villages every year into model villages\n" +
                "Work on alamatti dam was speeded up and it was also presented to nation during his tenure.\n" +
                "Water projects like siganalur project, yaragol project in kolar,chimalagi project in Bijapur and also concentrated on Badra projectwhich can provide water for Tumkur and Kolar.\n" +
                "Bruhat Bangalore project in which the rural parts of Bangalore were inculeded into Bangalore by which the basic infrastructure of those places were improved.\n" +
                "Constituted a committee to deal with land Mafia which had aquried the government lands illegally and recovered around 40 thousand acres of land from land mafia\n" +
                "Ramanagara and chikkaballapura were made as district keeping in mind that Bangalore growing fast and to create an alternate place for industrial growth.\n" +
                "Special high court circuit bench was granted to North Karnataka where people had to travel to Bangalore for there court cases\n" +
                "716 primary schools 1000 high schools 500 pu colleges and 184 degree colleges 7 engineering colleges 6 government medical college were presented for educational growth\n" +
                "To motivate the Students of rural Karnataka towards education and control the dropout from the school especially among girl students the Kumaraswamy government started a scheme where bicycles were distributed for the students who study in high school ,which was a very popular programes.\n" +
                "Udyogini,Asare ,Amrutha programes were introduced for the welfare of women.\n" +
                "Giving loan at the rate of 4% interest to Stree Shakti groups to improve credit to self employed individuals.\n" +
                "350 hostels for the welfare of the SC/ST students were provided,350 new Morarji hostel were sanctioned.\n" +
                "5 acre land worth Rs 90 crore was given to Haj committee to facilitate the Haj Pilgrims.\n" +
                "120 crore rupees were reserved for the welfare of minority communities.\n" +
                "Waqf board was given additional 9 crore rupees along with the 13 crore Which was given every year.\n" +
                "190 Km of Bangalore road widening project was taken up.");
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
