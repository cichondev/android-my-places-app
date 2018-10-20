package app.plusContacts.view;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import app.plusContacts.R;
import app.plusContacts.domain.PlaceBasic;
import app.plusContacts.listener.OnClickPlaceBasic;

import java.util.LinkedList;

public class AdapterListResults extends RecyclerView.Adapter<LineViewHolder> {
    private LinkedList<PlaceBasic> dataset;

    public AdapterListResults() {
        this.dataset = new LinkedList<>();
    }

    @Override
    public LineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_line, parent, false);

        CardView cardPlace = view.findViewById(R.id.card_place_basic);
        cardPlace.setOnClickListener(new OnClickPlaceBasic());

        return new LineViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LineViewHolder holder, int position) {
        holder.setValues(dataset.get(position));
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void insertItem(PlaceBasic item) {
        dataset.add(item);
        notifyItemInserted(getItemCount());
    }

    public void setItems(LinkedList items) {
        dataset = items;
        notifyDataSetChanged();
    }

    public void clearDataSet(String item) {
        dataset = new LinkedList<>();
        notifyDataSetChanged();
    }
}