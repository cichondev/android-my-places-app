package app.plusContacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import app.plusContacts.R;
import app.plusContacts.domain.PlaceBasic;

public class LineViewHolder extends RecyclerView.ViewHolder {
    private TextView placeName;
    private TextView placeDescrition;

    public LineViewHolder(View itemView) {
        super(itemView);
        placeName = (TextView) itemView.findViewById(R.id.place_name);
        placeDescrition = (TextView) itemView.findViewById(R.id.place_description);
    }

    public void setValues(PlaceBasic placeBasic) {
        this.placeName.setText(placeBasic.getName());
        this.placeDescrition.setText(placeBasic.getAddress());
    }
}