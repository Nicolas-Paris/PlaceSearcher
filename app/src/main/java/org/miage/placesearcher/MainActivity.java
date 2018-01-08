package org.miage.placesearcher;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.hello)  TextView hello;
    @BindView(R.id.ratingBar) RatingBar ratingBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Utilisation de la librairie ButterKnife
        ButterKnife.bind(this);

    }

    @Override
    protected void onResume() {
        super.onResume();

        ratingBar.setProgress(ratingBar.getProgress() + 1);
    }

    @OnClick(R.id.hello)
    public void clickedOnTextField() {
            ratingBar.setProgress(ratingBar.getProgress() - 1);
    }

}
