package app.plusContacts.listener;

import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import app.plusContacts.service.GooglePlacesSearch;

import java.io.UnsupportedEncodingException;

public class OnKeySearch implements View.OnKeyListener {

    @Override
    public boolean onKey(View view, int i, KeyEvent keyEvent) {
        EditText etSearch = (EditText) view;
        String searchValue = etSearch.getText().toString();

        if (keyEvent.getKeyCode() != 66) { //ENTER
            return false;
        }
        try {
            new GooglePlacesSearch(view.getContext())
                    .setKey("AIzaSyA72IYrfMQxUZiOxupbVzwPuhAdJrS6y9A")   //todo colocar em arquivo de configuração...
                    .setLocation(
                            "-19.50737292516664",
                            "-40.6131935119629")                //todo  pegar do GPS
                    .setQuery(searchValue)
                    .setRadius("100")                                  //todo colocar em arquivo de configuração...
                    .performRequest();
        } catch (UnsupportedEncodingException e) {
            Log.e("OnKeySearch", e.getMessage());
        }
        return true;
    }
}
