package org.miage.placesearcher;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by utilisateur on 08/01/2018.
 */

public class PlaceAdapter extends ArrayAdapter<Place> {

    @BindView(R.id.rue)
    TextView rue;

    @BindView(R.id.codePostal)
    TextView codePostal;

    @BindView(R.id.ville)
    TextView ville;

    @BindView(R.id.image)
    ImageView image;

    public PlaceAdapter(@NonNull Context context, List<Place> places) {
        super(context, -1, places);
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View actualView = convertView;
        if(convertView == null) {
            LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            actualView = inflater.inflate(R.layout.place_adapter, parent, false);
        }

        ButterKnife.bind(this, actualView);

        String nomRue = getItem(position).getStreet();
        if(nomRue.contains("1"))
            image.setImageResource(R.drawable.road);
        else
            image.setImageResource(R.drawable.house);

        rue.setText(getItem(position).getStreet());
        codePostal.setText(getItem(position).getZipCode());
        ville.setText(getItem(position).getCity());
        return actualView;
    }


}
