package com.example.thecosmiccode;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class VoyageRVAdapter extends RecyclerView.Adapter<VoyageRVAdapter.VoyageViewHolder> {

    private ArrayList<Voyage> voyages;
    private Context context;

    VoyageRVAdapter(ArrayList<Voyage> voyages, Context context) {
        this.voyages = voyages;
        this.context = context;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public VoyageRVAdapter.VoyageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.voyage_item, viewGroup, false);
        VoyageRVAdapter.VoyageViewHolder holder = new VoyageRVAdapter.VoyageViewHolder(v, context);
        return holder;
    }

    @Override
    public void onBindViewHolder(VoyageRVAdapter.VoyageViewHolder holder, int position) {
        Voyage voyage = voyages.get(position);
        holder.currentVoyage = voyage;
        holder.voyageName.setText(voyage.getName());
    }

    @Override
    public int getItemCount() {
        return voyages.size();
    }

    public static class VoyageViewHolder extends RecyclerView.ViewHolder {

        Voyage currentVoyage;
        TextView voyageName;

        VoyageViewHolder(View itemView, final Context context) {
            super(itemView);

            voyageName = itemView.findViewById(R.id.voyageName);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ObjectArchiveActivity.class);
                    intent.putExtra("VOYAGE", currentVoyage);
                    context.startActivity(intent);
                }
            });
        }
    }
}
