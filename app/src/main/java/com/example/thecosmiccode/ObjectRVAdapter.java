package com.example.thecosmiccode;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecosmiccode.model.Object;

import java.util.ArrayList;

public class ObjectRVAdapter extends RecyclerView.Adapter<ObjectRVAdapter.ObjectViewHolder> {

    private ArrayList<Object> objects;

    ObjectRVAdapter(ArrayList<Object> objects) {
        this.objects = objects;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @NonNull
    @Override
    public ObjectViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.object_item, viewGroup, false);
        ObjectViewHolder holder = new ObjectViewHolder(v);
        return holder;
    }

    @Override
    public void onBindViewHolder(ObjectViewHolder holder, int position) {
        Object object = objects.get(position);

        for (int i = 0; i < objects.size(); i++) {
            System.out.println("OBJECT\n");
            System.out.println(objects.get(i));
        }

        int[] pictures = new int[]{R.drawable.picture1,
                R.drawable.picture2,
                R.drawable.picture3,
                R.drawable.picture4,
                R.drawable.picture5,
                R.drawable.picture6,
                R.drawable.picture7,
                R.drawable.picture8,
                R.drawable.picture9,
                R.drawable.picture10
        };

        holder.picture.setImageResource(pictures[(int) (Math.random() * 10)]);
        holder.name.setText(object.getName());
        holder.weight.setText(String.valueOf(object.getWeight()));
        holder.cost.setText(String.valueOf(object.getCost()));
    }

    @Override
    public int getItemCount() {
        return objects.size();
    }

    public static class ObjectViewHolder extends RecyclerView.ViewHolder {

        TextView name;
        ImageView picture;
        TextView weight;
        TextView cost;

        ObjectViewHolder(View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            picture = itemView.findViewById(R.id.picture);
            weight = itemView.findViewById(R.id.weight);
            cost = itemView.findViewById(R.id.cost);
        }
    }
}
