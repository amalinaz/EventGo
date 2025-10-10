package com.example.projecteventgo.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projecteventgo.R;
import com.example.projecteventgo.SliderItem;

import java.util.ArrayList;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolder> {
private ArrayList<SliderItem> sliderItems;
private ViewPager2 viewPager2;
private Context context;

public SliderAdapter(ArrayList<SliderItem> sliderItems, ViewPager2 viewPager2) {
    this.sliderItems = sliderItems;
    this.viewPager2 = viewPager2;
}

@NonNull
@Override
public SliderAdapter.SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    context = parent.getContext();
    return new SliderViewHolder(LayoutInflater.from(context).inflate(R.layout.slider_item_container, parent, false));
}
@Override
public void onBindViewHolder(@NonNull SliderAdapter.SliderViewHolder holder, int position) {
    holder.setImage(sliderItems.get(position));
}

@Override
public int getItemCount() {
    return sliderItems.size();
}

public class SliderViewHolder extends RecyclerView.ViewHolder {
    private ImageView imageView;

    public SliderViewHolder(@NonNull View itemView) {
        super(itemView);
        imageView = itemView.findViewById(R.id.imageSlide);
    }

    void setImage(SliderItem sliderItems) {
        imageView.setImageResource(sliderItems.getImageResource()); // Gambar dari drawable
    }
}
    }