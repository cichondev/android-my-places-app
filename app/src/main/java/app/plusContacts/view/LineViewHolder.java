package app.plusContacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import app.plusContacts.R;
import app.plusContacts.domain.PlaceBasic;

public class LineViewHolder extends RecyclerView.ViewHolder {
    private TextView placeName;
    private TextView placeDescrition;
    private TextView placeID;

    public LineViewHolder(View itemView) {
        super(itemView);
        placeName = (TextView) itemView.findViewById(R.id.details_name);
        placeDescrition = (TextView) itemView.findViewById(R.id.place_description);
        placeID = (TextView) itemView.findViewById(R.id.place_id);
    }

    public void setValues(PlaceBasic placeBasic) {
        this.placeName.setText(placeBasic.getName());
        this.placeDescrition.setText(placeBasic.getAddress());
        this.placeID.setText(placeBasic.getPalceID());
    }
}