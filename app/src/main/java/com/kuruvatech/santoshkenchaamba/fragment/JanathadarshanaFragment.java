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


public class JanathadarshanaFragment extends Fragment {

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
        feedItem.setDescription("Soon after Mr.H.D.Kumaraswamy became Chief Minister of Karnataka ,once in every week he interacted with the common man directly. Janata darshana is said to be an eye-opener.It not only exposed to plethora of grievance but also to bureaucracy’s apathy and limitation moved by the plight of womens who met him in his Janata darshana who have either been separated from or deserted by their husbands Kumaraswamy decided to put in place a mechanism that would provide them Financial support and he announced Rs 400 financial assistance every month to them this made them self reliant ant not allow them to go into depression because of want of money\n" +
                "\n" +
                "Janata darshana drew thousands of people majority of them were being disabled,Unemployed and seeking medical help There are examples of Kumaraswamy attending to the people for more than 18 hours without taking even a launch break and sustaining himself on a glass of fruit juice without caring of his health He used to stand on the lawns of his office “Krishna” hearing to the grievances of people receiving applications and also giving instructions to the officers.\n" +
                "\n" +
                "The popular programe Janata Darshana was Extended to villages also as the number of people visiting him became more and he understood that it will be difficult for a common man to come to Bangalore to meet him by spending money and also wasting time so he decided to go to the people on extension of his Janata darshana he Visited number of remote villages and stayed in the common mans house and he listened to the problems of villagers and solved many of the problems on the spot .People from the neighboring villages where he stayed also visited him in Thousands in number ,Kumaraswamy used to carry his Janata darshana till 3am In the morning and he only used to sleep for only 3hrs and back to people at around 8am in the morning, Kumaraswamy received around 20 lakh applications in Janata darshana among which 70% of them used to be solved on the spot Kumaraswamy created a record by distributing 25 crore rupee directly to the common man in his 20 months tenure after learning that their problems are Authentic.\n" +
                "\n" +
                "Janata darshana not only brought his a great popularity but also bought him closer to the Common man and made him the CM who was easily accessible to the people ,\n" +
                "Here are some information about the incidents which occurred during his Janthadarshana\n" +
                "“Hello is this the commissioner Primary and secondary education department?…..CM speaking here ….. please have some sense when you transfer a handicapped Person” This is how H.D. Kumaraswamy reacted when a physically challenged Higher primary school lady teacher complained to him about her recent transfer To a far place . ManjulaDevi told Kumaraswamy that despite her repeated request she was transferred from Jamakandi to Tubachi ,as a result she has to travel For over 12 Km every day, after giving a patience hearing to her Kumaraswamy called up department commissioner Madan Gopal and directed him to withdraw the Transfer order beside warning him against repeating such mistakes.\n" +
                "\n" +
                "In another case he sanctioned Rs 25000 on the spot to Bharathi Kalamarahalli On the spot from the chief minister relief fund she complained that her father had recently met with an accident and she is finding it extremely difficult to run the Family.\n" +
                "\n" +
                "Someshekar a 20 year old flower vendor who was shoot in the knee during the Rajkumar riots needed a plastic surgery done on his right knee as it was chipped off Considering his case Kumaraswamy sanctioned him 50,000 rupees from CM relief fund and he also asked Someshekar to be admitted to the SanjayGandhi hospital where he will be given free treatment.\n" +
                "\n" +
                "Manjula a mother of two children was fighting for her rights she had alleged that Sub-inspector Timmegowda Mandya district is a father of her child ,Manjula was befriended by Timmegowda two years ago in Hassan when she met him with regards to a compliant after promising to marry her ,Timmmegowda sought a transfer when he realised that Manjula was a pregnant “He was infact brought a stay order not permitting her to get DNA test done to prove that he is the father of the child and also he married one of his relative. Kumaraswamy ordered the Super indent of police Mandya district to ask Timmegowda to be present at the CM’s office on the next day evening ,or else he would be dismissed ,the SP was told.\n" +
                "\n" +
                "Timmappa a physically challenged person from MC Layout was driving an autorikshaw for 8 years the display card drive by the RTO left him jobless for timmappa had no driving licence .Amused that timmmapa could drive his auto rickshaw at Bangalore roads for 8 years with out a driving licence Kumaraswamy enquired the officers who were present Wheather a licence could be issued to him atleast now? . on being told that physically challenged are not issued driving licence Kumaraswamy helped Timmappa by Getting him a self employment.\n" +
                "\n" +
                "Parimala from Bangalore was in tears. she had invested rupess 2.5 lakhs in a pvt firm that eventually went bankrupt, her husband needs an heart surgery and she had no money at hand Kumaraswamy directed officials to sanction money from CM relief fund for treatment of parimala’s husband.\n" +
                "\n" +
                "Mr.Kumaraswamy was moved when he received a petition from ramesh seeking help from government to help him to pay the school fees for his two children. He was employed in a security agency and was earning 2500 rupees. Ramesh said he paid 900 as house rent 400 for his bus pass and with remaining amount he took care of his family Kumaraaswamy took some money from his pocket and gave it to ramesh.\n" +
                "\n" +
                "Kumaraswamy personally called the director of sanjay Gandhi hospital and directed him to provide free treatment to arif who has suffered a spinal injury after he fell from the First floor of his house.\n" +
                "\n" +
                "A 24 year old construction labour got a pat off his back by Kumaraswamy. Nagraj who hails from Tumkur has been working as a construction labour since he was 14. To provide better education for his five siblings Nagraj is the only bread winner of his family comprising four sister and a brother they lost both their parents 10 years ago and since then Nagraj has been working to feed the family. Nagraj requested CM for the better job as he was finding too difficult to bear the education Kumaraswamy called up Tumkur deputy commissioner over phone and directed him to help Nagraj finding a job and also he was been assured for a house by Kumaraswamy.\n" +
                "\n" +
                "Nagesh a daily wage worker from Moledoddi in Channapatna taluk came with his three handicapped children the CM ordered to social welfare department officers to sanction an auto rickshaw loan to Nagesh and release a monthly allowance to all the three handicapped children.\n" +
                "\n" +
                "Beemanna a poor farmer from Bangalore rural was not physically challenged all his life. His physical challenge had began three months ago when he was bed-ridden due to a fractured leg and subsequent infection. Beemanna who stretched his injured leg with an external fixture was a sight of distress at Janata darshana he was reimbursed the cost of rupees 25,000 for his implants from CM relief fund towards diet medicine cost that would run into lakhs of rupees was waived off by the sanjay Gandhi hospital accident care and research center.Beemanna was treated at the Victoria hospital where he under went an Orthopedic procedure called interlocking nailing of the fractured bone, to his bad luck he had developed infection and the implants had to be removed he was put on an external Fixture and was back in bed “Beemanna met CM when his saving were exhausted and a major surgery with the intersection of titanium implants was difficult with out external help Beemanna said he would have continued to live as disabled man if not for the immediate sanction of money by the Chief Minister. After two weeks Beemanna was discharged from the hospital and he is back on his foot now.\n" +
                "\n" +
                "In another incidence vidyamani a young mother of two deserted by her husband fainted even as CM handed over a check of rupees 25000 to her and promised her a house at her hometown.\n" +
                "\n" +
                "Manjula complained about police inaction in a dowry case she claimed her estranged husband had married another women and recently attempted to abduct her from her parents house ,Kumaraswamy ordered the DCP (crime) to take immediate action.\n" +
                "\n" +
                "When Five year old Inchara who had a tumour approached Kumaraswamy he ensured that medical aid of Rupess 1 lakh was given to her.\n" +
                "\n" +
                "Chandan an 8 year old boy with a hole in his heart approached Kumaraswamy moved by his condition called up his cardiologist brother-in-law, Director of jayadev institute of cardiology Mr Manjunath and arranged for a surgery to be done at rupees 30,000.\n" +
                "\n" +
                "A young mother from kadur who lost her husband had approached the CM in one of his Janata darshan in chickmagalore met him again in Bangalore. The CM sanctioned Rupees 40000 loan to her to setup a tailoring business.\n" +
                "\n" +
                "The aggrieved father shantappa B koranna narrated to Kumaraswamy how he has been running pillar-to- post to get the compensation sanctioned his son a KPTCL employee was electrocuted while on duty. Stating that it was a serious issue Kumaraswamy asked law minister Basavaraj B Horatti to followup with KPTCL officials and get the compensation sanctioned.\n" +
                "\n" +
                "Nagamani from kariyana katte village in chamrajnagar taluk was thrilled when Kumaraswamy told her that a government order regarding her employment in Revenue Department would be issued. Nagamani who had applied for a job on compassionate grounds has been pursuing the application for past three years following the dismiss of her father from service in the Revenue Department the government decided to give the job to her brother. But even before the order was received nagamani’s brother was stuck by brain haemorrage following this she approached the government seeking the job but the authorities said that she was not eiligble later she started knocking the door in the pervious Janata darshana she was assured of job by CM.\n" +
                "\n" +
                "The crowd and the CM were amused at the friendly approach of a 10 year old boy sukesh from saliqrama. Sukesh who was successful in getting some assistance from the CM during Janatadarshana program at udupi, again approached the CM seeking help for setting up business. When Mr. Kumaraswamy asked him whether he could give him 3000 sukesh straight away told him the amount was not enough then the boy asked for rupees 10,000 .the CM smilingly conceded the demand. Before he left the place Sukesh told ChiefMinister “Barla”.\n" +
                "\n" +
                "Earlier following the compliants from a group of women from Lavanya Slum under Bharathi nagar Police station limits that some Rowdy elements were threatening them. the ChiefMinister directed additional commissioner Gopal B Hosur to arrest the culprits.The Rowdy elements allegedly burnt down a large number of huts in this locality. Kumaraswamy also asked the residents to comeback and meet him if the action is not taken.\n" +
                "\n");
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
