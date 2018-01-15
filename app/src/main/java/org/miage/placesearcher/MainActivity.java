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

import com.squareup.otto.Subscribe;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
        PlaceSearchService.INSTANCE.searchPlacesFromAdress("Place du Commerce");
        EventBusManager.BUS.register(this);
    }

    @Override
    protected void onPause() {
        EventBusManager.BUS.unregister(this);
        super.onPause();
    }

    @Subscribe
    public void searchResult(SearchResultEvent event){
        final List<Place> listItem = new ArrayList<Place>();
        //Création des items
        for(Place place : event.getPlaces()) {
            listItem.add(place);
        }
        ArrayAdapter adapter = new PlaceAdapter(this, listItem);
        mListeView.setAdapter(adapter);
    }
}
