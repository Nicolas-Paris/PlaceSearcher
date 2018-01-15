package org.miage.placesearcher;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by utilisateur on 08/01/2018.
 */

public class PlaceActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);
        ButterKnife.bind(this);

        textView.setText(getIntent().getStringExtra("street"));
    }

    @OnClick(R.id.textView)
    public void clickedOnTextField() {
        PlaceActivity.this.finish();
    }
}
