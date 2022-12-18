package id.ac.umn.iot;

import com.google.gson.*;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    public static Retrofit retrofit;

    public static Retrofit getRetrofitClient(String baseUrl){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        if(retrofit==null){
            Gson gson = new GsonBuilder().setLenient().create();
            retrofit = new Retrofit.Builder().
                    baseUrl(baseUrl).
                    client(client).
                    addConverterFactory(GsonConverterFactory.
                            create(gson)).
                    build();
        }
        return retrofit;
    }}
