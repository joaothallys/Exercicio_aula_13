package com.example.myapplication_13;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
public class DataEntryAdapter extends RecyclerView.Adapter<DataEntryAdapter.ViewHolder> {

    private List<DataEntry> dataEntries;

    public DataEntryAdapter(List<DataEntry> dataEntries) {
        this.dataEntries = dataEntries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_data_entry, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DataEntry dataEntry = dataEntries.get(position);
        holder.textViewData.setText(dataEntry.getData());
        holder.textViewDateTime.setText(dataEntry.getDateTime());
    }

    @Override
    public int getItemCount() {
        return dataEntries.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textViewData;
        public TextView textViewDateTime;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewData = itemView.findViewById(R.id.textViewData);
            textViewDateTime = itemView.findViewById(R.id.textViewDateTime);
        }
    }
}