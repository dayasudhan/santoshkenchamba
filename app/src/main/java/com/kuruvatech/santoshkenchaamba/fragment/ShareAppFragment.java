package com.kuruvatech.santoshkenchaamba.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.kuruvatech.santoshkenchaamba.R;
import com.kuruvatech.santoshkenchaamba.utils.Constants;


public class ShareAppFragment extends Fragment {

    Button btnshareApp;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_share_app, container, false);
//        ((MainActivity) getActivity())
//                .setActionBarTitle("Invite Friends");
        btnshareApp= (Button) view.findViewById(R.id.invite_button);
        btnshareApp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.setType("text/plain");
                sendIntent.putExtra(Intent.EXTRA_SUBJECT, Constants.INVITE_SUBJECT);
                sendIntent.putExtra(Intent.EXTRA_TEXT, Constants.INVITE_TEXT);
                startActivity(Intent.createChooser(sendIntent, "Share link!"));
                }
        });
        return view;
    }
    @Override
    public void onResume() {
        super.onResume();
    }
}
