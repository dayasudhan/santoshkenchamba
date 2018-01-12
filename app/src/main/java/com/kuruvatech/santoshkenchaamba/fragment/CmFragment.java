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


public class CmFragment extends Fragment {

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
        feedItem.setDescription("H D Kumaraswamy took oath as 18th Chief Minister of Karnataka on February 3, 2006 at the age of 46 Mr.Kumaraswamy was fourth youngest chief minister of Karnataka. As soon as Kumaraswamy took oath as the CM of Karnataka he announced his priorities towards the development of the state.\n" +
                "\n" +
                "They are\n" +
                "\n" +
                "Clean and transparent administration\n" +
                "To implement 11 point development vision of Abdul kalam\n" +
                "Improve infrastructure facility in and around the city\n" +
                "More power to Lok ayukta.\n" +
                "Encouragement to IT & BT.\n" +
                "Terrorism and Naxalism to be curbed.\n" +
                "Power generation to be increased.\n" +
                "Stem action against land grabbers .\n" +
                "Krishna Cauvery basin irrigation project to be expedited.\n" +
                "Cordial relation with center and neighboring state.\n" +
                "Not only he announced his top priorities but also worked sincerely towards the completion of the same. Fully aware that the dead lines issued by his predecessors remained only on papers Kumaraswamy made it sure that the infrastructure works are completed on time In spite of being CM of Karnataka he did not want to put fellow motorist in inconvenience while traveling he ordered the officers not to put traffic curbs unnecessarily. He wanted to travel like a common man for city inspection, to review the progress of the projects. He choose to go on inspection once a month at night from 10 am till midnight so as not to cause inconvenience to the people. Which shows his concern about the common man who traveled in Bangalore traffic.\n" +
                "\n" +
                "Ever since Kumaraswamy took oath as Chief minister of Karnataka he received on an average of 800 Emails a week that was actually the extension of his Janata darshana among them 75 percent mails are grievances and petitions .Printouts of all Emails were sent to Kumaraswamy’s house by about 7 in the evening apparently he himself sifts through the mail, read them all and next morning responded personally to chosen few.\n" +
                "\n" +
                "Kumaraswamy made it mandatory for officials to compulsory visit at least two villages every week to educate people on government schemes and programs.\n" +
                "\n" +
                "Vidhana soudha the seat of power was an impregnable fortress for the comman man .as they were not allowed inside to meet any elected representatives because of the security reasons.Kumaraswamy ordered the state police chief to relax the restriction on the movement of people around vidhanasoudha ,he said that people come there to get their problem redressed they should not be stoped at the gates of vidhanasoudha in the name of security. security to VIPs is duty of police.but that cannot be done at the cost of common mans freedom.\n" +
                "\n" +
                "Kumaraswamy was very much impressed by president Abdul Kalam Visions to make Karnataka a model state and he put his true efforts to implement them.\n" +
                "\n" +
                "Kumaraswamy is the only Chief Minister who stayed in the house which belonged to a backward Class during his Village stay ,he just wanted to bring a difference in the treatment by the society towards the backward class where even today in most parts of Karnataka the backward classes are treated as untouchables, Kumaraswamy’s visit made the top officers to stay with him in the same house and the upper caste of the village who treated the backward as untouchables also visited the same house to give there complaints and grievance to the chief minister.\n" +
                "\n" +
                "Mr.Kumaraswamy also stayed in the house of an HIV infected family, His intention was to remove the Misconceptions of the people about AIDS he just wanted to show his concerns about the people who were HIV infected and he wanted to spread the message to the people that AIDS doesn’t spreads by staying with Infected people or by eating the food prepared by them. Famous Hollywood actor Richard Gere visited Kumaraswamy personally along with his wife and greatly appreciated his concerns towards HIV infected peoples.\n" +
                "\n" +
                "Kumaraswamy’s hard work and dedication took him to the great heights in the heart of people, the people of Karnataka elected him the for super achiever of Karnataka during 2006 in a poll conducted by Deccan herald H.D.Kumaraswamy was on the top ranking He was followed by The then Indian team Captain Rahul Dravid , National award winner Girish Kasarvalli ,retired lokayukta justice N.Venkatachala and right livelihood winner Ruth Manorama .Thousands of people elected him by sending Email, Post card and letter sent via fax.\n" +
                "\n" +
                "The Chief Minister would prefer to spend more time among people than sitting in a plush officing files A hands on man Karnataka’s 18th CM H.D.Kumaraswamy is just that accessible 24×7 and a mobile person.\n" +
                "\n" +
                "His Janata darshana and Village stay gave him an edge over his predecessors of being a visible CM. He created history by being a first CM to stay in a house of an HIV +ve family .Administrating a coalition government ,where each partner had its own priorities and ideologies to look after is not an easy task but during his tenure as CM came up with number of programs which gave boost to greater Bangalore happen installing confidence among Kannadigas along the border with Maharastra by holding the Belgaum legislative sessions or coming there to rescue of farmers facing mounting debts with the promise of waving loans taken from the Co-operative sector hopes have soared for the people. Loan and 4% interest is by no means a popular measure it came as a result of deep understanding that availability cheap credit is a principal need of agriculture today, The Reserve bank of India appreciated the fiscal reform measures taken while the credit rating information service of India limited (CRISIL) had placed Karnataka in AA-grade based on the economic progress achieved The budget presented under his leadership has concept of gender based budgeting which helped to prioritize public expenditure to respect the concern of the Women.The new industrilal policy 2006-2011 emphasized the proper infrastructure will result in Industrialization\n" +
                "\n" +
                "The floods in mid 2006 created havoc in 817 villages affecting 12 lakh people considering the gravity of the issue his government put into place a plan to take up permanent relief measure perennially flood-prone district and for the first time 90 villages in Belguam,Bijapur, Bagalkot, Raichur, Koppal and Gulbarga were shifted. North Karnataka was the one of his top priorities. Unfortunately because of his short tenure he could not complete all his dreams about Karnataka,though his tenure was too short he did what his predecessors couldn’t in last 60 years. Kumaraswamy was a common man’s CM.");
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
