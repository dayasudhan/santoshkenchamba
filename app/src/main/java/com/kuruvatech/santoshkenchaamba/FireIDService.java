package com.kuruvatech.santoshkenchaamba;

/**
 * Created by gagan on 11/6/2017.
 */
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;

/**
 * Created by francesco on 13/09/16.
 */
public class FireIDService extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        String tkn = FirebaseInstanceId.getInstance().getToken();


    }
}
