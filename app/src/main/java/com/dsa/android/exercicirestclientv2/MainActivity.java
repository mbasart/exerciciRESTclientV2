package com.dsa.android.exercicirestclientv2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import retrofit2.Call;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "Tracks";

    private Retrofit retrofit;

    private RecyclerView recyclerView;
    private MyAdapter myAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
        List<Tracks> input = new ArrayList<>();
        myAdapter = new MyAdapter(input);
        recyclerView.setAdapter(myAdapter);
        recyclerView.setHasFixedSize(true);
        GridLayoutManager layoutManager = new GridLayoutManager(this,1);
        recyclerView.setLayoutManager(layoutManager);

        retrofit = new Retrofit.Builder()
                .baseUrl("https://147.83.7.155:8080/swagger/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        obtenerDatos();
    }

    private void obtenerDatos(){
        RestAPI rest = retrofit.create(RestAPI.class);
        rest.obtenerListaTracks();
        Call<LlistaTracks> llistaTracksCall = rest.obtenerListaTracks();

        llistaTracksCall.enqueue(new Callback<LlistaTracks>() {
            @Override
            public void onResponse(Call<LlistaTracks> call, Response<LlistaTracks> response) {
                if(response.isSuccessful()){
                    LlistaTracks llistaTracks = response.body();
                    List<Tracks> tracks = llistaTracks.getResults();

                    myAdapter.afegirLlistaTracks(tracks);

                    //aixo es per veure-ho per consola
                    /*for(int i=0; i < tracks.size();i++){
                        Tracks tk = tracks.get(i);
                        Log.i(TAG,"Song: "+ tk.getTitle());
                    }*/
                }else{
                    Log.e(TAG,"OnResponse: "+ response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<LlistaTracks> call, Throwable t) {
                Log.e(TAG, "OnFailure: "+ t.getMessage());
            }
        });
    }
}
