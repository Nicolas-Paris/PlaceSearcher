package org.miage.placesearcher;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.liste) ListView mListeView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        List<Place> listItem = new ArrayList<Place>();
        //Création des items
        for(int i = 0; i < 50; i++) {
            listItem.add(new Place("Street" + (i+1), "44000", "Nantes"));
        }
        ArrayAdapter adapter = new PlaceAdapter(this, listItem);

        mListeView.setAdapter(adapter);
        mListeView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent seePlace = new Intent(MainActivity.this, PlaceActivity.class);
                startActivity(seePlace);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }


}
