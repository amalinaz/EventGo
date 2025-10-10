package com.example.projecteventgo.Adapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projecteventgo.Activity.TransactionActivity;
import com.example.projecteventgo.Event;
import com.example.projecteventgo.R;
import com.example.projecteventgo.Section;

import java.util.List;

public class SectionAdapter extends RecyclerView.Adapter<SectionAdapter.SectionViewHolder>  {

    private List<Section> sectionList;
    private Event event;
    private OnSectionUpdatedListener sectionUpdatedListener;

    public SectionAdapter(List<Section> sectionList,Event event, OnSectionUpdatedListener listener) {
        this.sectionList = sectionList;
        this.event = event;
        this.sectionUpdatedListener = listener;
    }

    public interface OnSectionUpdatedListener {
        void onSectionUpdated(Section section);  // Mengupdate section setelah perubahan tiket
    }

    @Override
    public SectionViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_section, parent, false);
        return new SectionViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(SectionViewHolder holder, int position) {
        Section section = sectionList.get(position);
        holder.bind(section);
    }

    @Override
    public int getItemCount() {
        return sectionList.size();
    }

    // ViewHolder untuk Section
    public class SectionViewHolder extends RecyclerView.ViewHolder {

        private TextView sectionName;
        private TextView price;
        private TextView description;
        private TextView type;
        private TextView ticketCount;
        private Button btnPlus, btnMin, sectionPaymentBtn;

        public SectionViewHolder(View itemView) {
            super(itemView);
            sectionName = itemView.findViewById(R.id.sectionNameTV);
            price = itemView.findViewById(R.id.sectionPriceTV);
            description = itemView.findViewById(R.id.sectionDescTV);
            type = itemView.findViewById(R.id.sectionTypeTV);
            ticketCount = itemView.findViewById(R.id.ticketCount);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            btnMin = itemView.findViewById(R.id.btnMinus);
            sectionPaymentBtn = itemView.findViewById(R.id.sectionPaymentBtn);
        }

        public void bind(Section section) {
            sectionName.setText(section.getName());
            price.setText("Rp " + section.getPrice());
            description.setText(section.getDescription());
            type.setText(section.getSeat_type());
            ticketCount.setText(String.valueOf(section.getSelectedTickets()));
            sectionPaymentBtn.setVisibility(View.GONE);
            // Handle tombol +
            btnPlus.setOnClickListener(v -> {
                if (section.getSelectedTickets() < section.getAvailableTickets()) {
                    section.setSelectedTickets(section.getSelectedTickets() + 1);
                    ticketCount.setText(String.valueOf(section.getSelectedTickets()));
                    updateSectionPaymentBtnVisibility(section);
                    if (sectionUpdatedListener != null) {
                        sectionUpdatedListener.onSectionUpdated(section);
                    }
                }
            });

            // Handle tombol -
            btnMin.setOnClickListener(v -> {
                if (section.getSelectedTickets() > 0) {
                    section.setSelectedTickets(section.getSelectedTickets() - 1);
                    ticketCount.setText(String.valueOf(section.getSelectedTickets()));
                    updateSectionPaymentBtnVisibility(section);
                    if (sectionUpdatedListener != null) {
                        sectionUpdatedListener.onSectionUpdated(section);
                    }
                }
            });
        }

        private void updateSectionPaymentBtnVisibility(Section section) {
            if (section.getSelectedTickets() > 0) {
                sectionPaymentBtn.setVisibility(View.VISIBLE);  // Show button if tickets are selected
            } else {
                sectionPaymentBtn.setVisibility(View.GONE);  // Hide button if no tickets are selected
            }

            // Handle continue payment button click
            sectionPaymentBtn.setOnClickListener(v -> {
                // Handle payment logic for this section
                // This could navigate to another activity or update the main concert details
                // Example:
                Intent intent = new Intent(itemView.getContext(), TransactionActivity.class);
                intent.putExtra("data", event);
                intent.putExtra("section_data", section);
                Log.d("CurrentActivity", "Sending Intent: ");
                Log.d("CurrentActivity", "Event: " + event.getName());
                Log.d("CurrentActivity", "Section: " + section.getName());
                itemView.getContext().startActivity(intent);
            });
        }
    }

}
