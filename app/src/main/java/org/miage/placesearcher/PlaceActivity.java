package org.miage.placesearcher;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by utilisateur on 08/01/2018.
 */

public class PlaceActivity extends AppCompatActivity {

    private int PHOTO = 1;

    @BindView(R.id.textView)
    TextView textView;

    @BindView(R.id.google)
    Button boutonGoogle;

    @BindView(R.id.send)
    Button boutonPartage;

    @BindView(R.id.images)
    Button boutonImage;

    @BindView(R.id.imageView)
    ImageView image;

    String pNamePlace;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_activity);
        ButterKnife.bind(this);

        pNamePlace = getIntent().getStringExtra("street");

        textView.setText(pNamePlace);
    }

    @OnClick(R.id.textView)
    public void clickedOnTextField() {
        PlaceActivity.this.finish();
    }

    @OnClick(R.id.google)
    public void clickedOnButton() {
        Uri uri = Uri.parse("https://www.google.fr/search?q=" + pNamePlace);
        Intent launcherBrowser = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(launcherBrowser);
    }

    @OnClick(R.id.send)
    public void clickedPartage() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, pNamePlace);
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }

    @OnClick(R.id.images)
    public void clickedImage() {
        Intent photoIntent = new Intent(Intent.ACTION_PICK);
        photoIntent.setType("image/*");
        startActivityForResult(photoIntent, PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == PHOTO) {
            if (resultCode == RESULT_OK) {
                Uri selectedImage = data.getData();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImage);
                    image.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
