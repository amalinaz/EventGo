package com.example.projecteventgo.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projecteventgo.Activity.EventActivity;
import com.example.projecteventgo.Event;
import com.bumptech.glide.Glide;
import com.example.projecteventgo.R;

import java.util.ArrayList;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder>{
    ArrayList<Event> eventList;
    Context ctx;

    public EventAdapter(ArrayList<Event> inputEventList, Context inputCtx) {
        this.eventList = inputEventList;
        this.ctx = inputCtx;

    }


    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(ctx).inflate(R.layout.item_events, parent, false);
        return new EventViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull EventAdapter.EventViewHolder holder, int position) {
        Event event = eventList.get(position);
        String imageName = event.getPic();  // Image name (e.g., "image_name")

        // Get the resource ID dynamically
        int imageResId = holder.itemView.getContext().getResources().getIdentifier(imageName, "drawable", holder.itemView.getContext().getPackageName());

        if (imageResId != 0) {  // If the image exists in drawable
            Glide.with(holder.itemView.getContext())
                    .load(imageResId)  // Use resource ID directly
                    .into(holder.eventImage);
        } else {
            // Fallback if image is not found
            Glide.with(holder.itemView.getContext())
                    .load(R.drawable.tds)  // Default image if the resource is not found
                    .into(holder.eventImage);
        }
        holder.eventDate.setText(event.getStart_date());
        holder.eventName.setText(event.getName());
        Log.d("ConcertAdapter", "Name: " + event.getName() + ", Date: " + event.getStart_date());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    // ViewHolder untuk Concert
    public class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView eventName;
        private TextView eventDate;
        private ImageView eventImage;



        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            eventName = itemView.findViewById(R.id.eventNameTV);
            eventImage = itemView.findViewById(R.id.eventBannerIV);
            eventDate = itemView.findViewById(R.id.eventDateTV);
            itemView.setOnClickListener(this);
        }


        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            Event event = eventList.get(position);
            Intent openDetailAct = new Intent(view.getContext(), EventActivity.class);
            openDetailAct.putExtra("data", event);
            view.getContext().startActivity(openDetailAct);

        }
    }
}
