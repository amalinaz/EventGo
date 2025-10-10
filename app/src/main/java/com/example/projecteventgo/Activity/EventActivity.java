package com.example.projecteventgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.projecteventgo.Adapter.SectionAdapter;
import com.example.projecteventgo.Event;
import com.example.projecteventgo.R;
import com.example.projecteventgo.Section;

import java.util.ArrayList;

public class EventActivity extends AppCompatActivity {


    ImageView pic, sectionPic, EAbackIV;
    TextView name, artist, city, venue, startDate, endDate;
    RecyclerView sectionRV;
    Button sectionPaymentBtn;
    TextView ticketCount;
    ArrayList<Section> sectionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_event);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.EventActivity), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        pic = findViewById(R.id.EApicIV);
        name = findViewById(R.id.EAnameTV);
        artist = findViewById(R.id.EAartistTV);
        city = findViewById(R.id.EAcityTV);
        venue = findViewById(R.id.EAvenueTV);
        startDate = findViewById(R.id.EAstartdateTV);
        endDate = findViewById(R.id.EAenddateTV);
        sectionPic = findViewById(R.id.EAsectionpicIV);
        sectionRV = findViewById(R.id.EAsectionRV);
        EAbackIV = findViewById(R.id.EAbackIV);

        Intent getIntent = getIntent();

        Event event = (Event) getIntent.getSerializableExtra("data");
        if (event != null) {
            int imageResId = getResources().getIdentifier(event.getPic(), "drawable", getPackageName());
            Glide.with(this)
                    .load(imageResId)
                    .into(pic);

            EAbackIV.setOnClickListener(view -> {
                Intent backIntent = new Intent(this, MainActivity.class);
                startActivity(backIntent);
            });
            name.setText(event.getName());
            artist.setText(event.getArtist());
            city.setText(event.getCity());
            venue.setText(event.getVenue());
            startDate.setText(event.getStart_date());
            endDate.setText(event.getEnd_date());
            int imageResId2 = getResources().getIdentifier(event.getSection_pic(), "drawable", getPackageName());
            Glide.with(this)
                    .load(imageResId2)
                    .into(sectionPic);

            sectionList = new ArrayList<>(event.getSection());

            SectionAdapter adapter = new SectionAdapter(sectionList, event, section -> {
                updateTotalPrice();  // Update total price setiap kali ada perubahan pada section
            });

            sectionRV.setAdapter(adapter);
            sectionRV.setLayoutManager(new LinearLayoutManager(this));
        }

    }

    private void updateTotalPrice() {
        int totalPrice = 0;
        for (Section section : sectionList) {
            totalPrice += section.getSelectedTickets() * section.getPrice();
        }

        TextView totalPriceTextView = findViewById(R.id.sectionPriceTV);
        totalPriceTextView.setText("Total: Rp " + totalPrice);
    }

}