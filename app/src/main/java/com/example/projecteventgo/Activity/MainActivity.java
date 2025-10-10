package com.example.projecteventgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import com.example.projecteventgo.Adapter.EventAdapter;
import com.example.projecteventgo.Adapter.SliderAdapter;
import com.example.projecteventgo.Event;
import com.example.projecteventgo.R;
import com.example.projecteventgo.SliderItem;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ViewPager2 viewPagerSlider;
    ArrayList<Event> listEvent;
    EventAdapter eventAdapter;
    RecyclerView mainEventRV;
    ImageView mainProfileIV;
    ProgressBar mainEventPB;
    private ValueEventListener eventValueEventListener;

    FirebaseDatabase database = FirebaseDatabase.getInstance("https://eventgo-17464-default-rtdb.asia-southeast1.firebasedatabase.app/");
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.Login), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        FirebaseApp.initializeApp(this);
        viewPagerSlider = findViewById(R.id.mainBannerVP);
        initBanner();
        events();
        mainProfileIV = findViewById(R.id.mainProfileIV);
        mainProfileIV.setOnClickListener(view -> {
            FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
            // Cek apakah user sudah login
            if (firebaseAuth.getCurrentUser() != null) {
                // User sudah login -> Pindah ke halaman Profile
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
            } else {

                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
            }
        });
    }

    private void banners(ArrayList<SliderItem> items) {
        viewPagerSlider.setAdapter(new SliderAdapter(items, viewPagerSlider));

        // Konfigurasi tambahan untuk ViewPager2
        viewPagerSlider.setClipToPadding(false);
        viewPagerSlider.setClipChildren(false);
        viewPagerSlider.setOffscreenPageLimit(4);
        viewPagerSlider.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);

        // Page Transformer untuk efek margin antar slide
        CompositePageTransformer compositePageTransformer = new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(40));
        viewPagerSlider.setPageTransformer(compositePageTransformer);

        Handler handler = new Handler();
        Runnable sliderRunnable = new Runnable() {
            int currentPage = 0;

            @Override
            public void run() {
                if (currentPage == items.size() - 1) {
                    // Back to the first page
                    viewPagerSlider.setCurrentItem(0, true);
                    currentPage = 0;
                } else {
                    // Go to the next page
                    viewPagerSlider.setCurrentItem(++currentPage, true);
                }
                handler.postDelayed(this, 3000); // Slide every 3 seconds
            }
        };

        viewPagerSlider.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrollStateChanged(int state) {
                super.onPageScrollStateChanged(state);
                if (state == ViewPager2.SCROLL_STATE_DRAGGING) {
                    handler.removeCallbacks(sliderRunnable); // Pause when the user is dragging
                } else if (state == ViewPager2.SCROLL_STATE_IDLE) {
                    handler.postDelayed(sliderRunnable, 3000); // Resume after idle state
                }
            }
        });

        handler.postDelayed(sliderRunnable, 3000); // Start the auto-slide when the activity is created


    }

    private void initBanner() {
        ArrayList<SliderItem> items = new ArrayList<>();
        items.add(new SliderItem(R.drawable.banner1));
        items.add(new SliderItem(R.drawable.banner2));
        items.add(new SliderItem(R.drawable.banner3));
        items.add(new SliderItem(R.drawable.banner4));

        banners(items);
    }

    private void events() {
        //concert
        databaseReference = database.getReference("concert");
        listEvent = new ArrayList<>();

        mainEventPB = findViewById(R.id.mainEventPB);
        mainEventPB.setVisibility(View.VISIBLE); // Show ProgressBar initially

        mainEventRV = findViewById(R.id.mainEventRV);
        eventAdapter = new EventAdapter(listEvent, MainActivity.this);
        mainEventRV.setAdapter(eventAdapter);
        mainEventRV.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));

        eventValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listEvent.clear(); // Clear previous data

                if (snapshot.exists()) {
                    // Loop through data and add each event
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        Event event = dataSnapshot.getValue(Event.class);
                        if (event != null) {
                            listEvent.add(event);
                            Log.d("FirebaseData", "Event added: " + event.getName());
                        }
                    }

                    eventAdapter.notifyDataSetChanged(); // Notify adapter to refresh data
                }

                mainEventPB.setVisibility(View.GONE); // Hide ProgressBar once data is loaded


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("FirebaseError", error.getMessage());
                mainEventPB.setVisibility(View.GONE); // Hide ProgressBar in case of error
            }
        };

        // Attach listener to Firebase database reference
        databaseReference.addValueEventListener(eventValueEventListener);
    }

    DatabaseReference concertRef;
    DatabaseReference eventRef;

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (eventValueEventListener != null && eventRef != null) {
            eventRef.removeEventListener(eventValueEventListener);
        }
    }
}