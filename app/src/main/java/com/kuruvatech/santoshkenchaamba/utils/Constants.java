package com.kuruvatech.santoshkenchaamba.utils;


public class Constants {

    public static final String LOCALHOST = "http://192.168.1.104:3000";

    public static final String RELEASE_URL = "http://chunavane.herokuapp.com";
    public static final String DEBUG_URL = "http://chunavane.herokuapp.com";

    public static final String MAIN_URL = DEBUG_URL;
    public static final String USERNAME = "santoshkenchamba";
    public static final String PARTY = "bjp";
    public static final String GET_FEEDS_URL = MAIN_URL + "/v2/feed/info/";
    public static final String GET_IMAGES_URL = MAIN_URL + "/v1/feed/images/";
    public static final String GET_VIDEOS_URL = MAIN_URL + "/v1/feed/videos/";
    public static final String GET_MANIFESTO_URL = MAIN_URL + "/v1/feed/manifesto/" + PARTY;
    public static final String POST_LETTER_URL = MAIN_URL + "/v1/candidate/suggestion/" +USERNAME;


    public static final String FIREBASE_APP = "https://project-8598805513533999178.firebaseio.com";
    //To store the firebase id in shared preferences
    public static final String UNIQUE_ID = "uniqueid";
    public static final String INVITE_TEXT = "Invite BJP workers, fans, to this app" +

            "Download this android App: https://play.google.com/store/apps/details?id=com.kuruvatech.santoshkenchaamba";
    public static final String INVITE_SUBJECT = "Santhosh Kenchaamba App";


    public static final String SECUREKEY_KEY = "securekey";
    public static final String VERSION_KEY = "version";
    public static final String CLIENT_KEY = "client";

    public static final String SECUREKEY_VALUE = "EjR7tUPWx7WhsVs9FuVO6veFxFISIgIxhFZh6dM66rs";
    public static final String VERSION_VALUE = "1";
    public static final String CLIENT_VALUE = "bhoomika";


    public static final int TITLE_TEXT_COLOR_RED = 00;
    public static final int TITLE_TEXT_COLOR_GREEN = 177;
    public static final int TITLE_TEXT_COLOR_BLUE = 106;


}
