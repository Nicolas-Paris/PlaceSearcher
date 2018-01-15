package org.miage.placesearcher;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.liste) ListView mListeView;

    MediaPlayer player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final List<Place> listItem = new ArrayList<Place>();
        //Création des items
        for(int i = 0; i < 50; i++) {
            listItem.add(new Place("Street" + (i+1), "44000", "Nantes"));
        }
        ArrayAdapter adapter = new PlaceAdapter(this, listItem);

        mListeView.setAdapter(adapter);
        mListeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                // Méthode qui joue du son quand on clic sur un item
                AssetFileDescriptor son = null;
                try {
                    son = getAssets().openFd("click.mp3");
                    player = new MediaPlayer();
                    player.setDataSource(son.getFileDescriptor(), son.getStartOffset(), son.getLength());
                    player.prepare();
                    player.start();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent seePlace = new Intent(MainActivity.this, PlaceActivity.class);
                Place place = (Place) parent.getItemAtPosition(position);
                seePlace.putExtra("street", place.getStreet());
                startActivity(seePlace);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        AsyncTask<String, Void, Response> asyncTask = new AsyncTask<String, Void, Response>() {
            @Override
            protected Response doInBackground(String... strings) {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request request = new Request.Builder().url(strings[0])
                        .build();
                try {
                    Response response = okHttpClient.newCall(request).execute();
                    return response;
                } catch(IOException e) {
                    Log.e("ERROR BITCH", e.getMessage());
                }
                return null;
            }

            @Override
            protected void onPostExecute(Response response) {
                super.onPostExecute(response);

                Log.d("Test", response.body().toString());
            }
        };

        asyncTask.execute("http://api.adresse.data.gouv.fr/search/?q=Place%20du%20commerce");


    }


}
