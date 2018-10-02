package app.plusContacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import app.plusContacts.R;

// Provide a reference to the views for each data item
// Complex data items may need more than one view per item, and
// you provide access to all the views for a data item in a view holder
public class LineViewHolder extends RecyclerView.ViewHolder {
    public TextView placeName;
    public TextView placeDescrition;
    public ImageButton callButton;

    public LineViewHolder(View itemView) {
        super(itemView);
        placeName = (TextView) itemView.findViewById(R.id.place_name);
        placeDescrition = (TextView) itemView.findViewById(R.id.place_description);
        callButton = (ImageButton) itemView.findViewById(R.id.call_button);
    }
}