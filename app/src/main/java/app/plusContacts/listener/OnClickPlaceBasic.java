package app.plusContacts.listener;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import app.plusContacts.R;
import app.plusContacts.view.PlaceDetailsActivity;

public class OnClickPlaceBasic implements View.OnClickListener {

    @Override
    public void onClick(View view) {
        String placeID = ((TextView)view.findViewById(R.id.place_id)).getText().toString();

        Intent it = new Intent(view.getContext(), PlaceDetailsActivity.class);
        it.putExtra("place_id", placeID);
        ((Activity)view.getContext()).startActivityForResult(it, 1);
    }
}
