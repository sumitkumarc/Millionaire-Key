package com.maker.millionairekey.Rest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetroClient {
//    public static final String BASE_URL = "http://mlmapi.havlogic.com/";
    public static final String BASE_URL = "http://api.millionairekey.in/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit == null) {

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(MyJsonConverter.create(gson))
                    .build();
        }
        return retrofit;
    }
}
