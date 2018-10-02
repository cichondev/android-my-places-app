package app.plusContacts.view;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import app.plusContacts.R;

public class AdapterListResults extends RecyclerView.Adapter<LineViewHolder> {
    private String[] mDataset;

    // Provide a suitable constructor (depends on the kind of dataset)
    public AdapterListResults(String[] myDataset) {
        mDataset = myDataset;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public LineViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new LineViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.result_line, parent, false));
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(LineViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.placeName.setText(mDataset[position]);
        holder.placeDescrition.setText(mDataset[position]);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}