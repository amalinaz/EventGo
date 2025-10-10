package com.example.projecteventgo.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.projecteventgo.Event;
import com.example.projecteventgo.R;
import com.example.projecteventgo.Section;

import java.util.ArrayList;

public class TransactionActivity extends AppCompatActivity {
    ImageView transactionBannerIV;
    TextView transactionNameTV, transactionDateTV,transactionTicketTV, transactionPriceTV, transactionTotalPriceTV;
    Button transactionPaymentBtn;
    Spinner transactionPaymentSP;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_transaction);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        transactionBannerIV = findViewById(R.id.transactionBannerIV);
        transactionNameTV = findViewById(R.id.transactionNameTV);
        transactionDateTV = findViewById(R.id.transactionDateTV);
        transactionPriceTV = findViewById(R.id.transactionPriceTV);
        transactionTicketTV = findViewById(R.id.transactionTicketTV);
        transactionTotalPriceTV = findViewById(R.id.transactionTotalTV);
        transactionPaymentBtn = findViewById(R.id.transactionPaymentBtn);
        transactionPaymentSP = findViewById(R.id.transactionPaymentSP);

        // Tambahkan metode pembayaran
        ArrayList<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("Kartu Kredit");
        paymentMethods.add("Transfer Bank");
        paymentMethods.add("Dompet Digital");

        // Set adapter untuk spinner metode pembayaran
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, paymentMethods);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        transactionPaymentSP.setAdapter(adapter);

        // Event listener untuk spinner
        transactionPaymentSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Tidak ada aksi spesifik, hanya mengganti opsi
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                Toast.makeText(TransactionActivity.this, "Metode pembayaran belum dipilih", Toast.LENGTH_SHORT).show();
            }
        });

        Log.d("TransactionActivity", "Checking if data exists...");

        // Terima intent untuk data konser atau event
        Intent intent = getIntent();
        String dataType = intent.getStringExtra("data_type");
        Log.d("TransactionActivity", "Intent dataType: " + dataType);

        Event event = (Event) intent.getSerializableExtra("data");
        Section section = (Section) intent.getSerializableExtra("section_data");

        if (event == null || section == null) {
            Log.e("TransactionActivity", "Event or Section data is null!");
            return;
        }

        // Log data untuk memastikan penerimaan
        Log.d("TransactionActivity", "Event name: " + event.getName());
        Log.d("TransactionActivity", "Event date: " + event.getStart_date());
        Log.d("TransactionActivity", "Event pic: " + event.getPic());
        Log.d("TransactionActivity", "Section name: " + section.getName());
        Log.d("TransactionActivity", "Section price: " + section.getPrice());
        Log.d("TransactionActivity", "Section tickets: " + section.getSelectedTickets());

        // Assign ke TextView
        transactionNameTV.setText(event.getName());
        transactionDateTV.setText(event.getStart_date());
        transactionPriceTV.setText(String.valueOf(section.getPrice()));
        transactionTicketTV.setText(String.valueOf(section.getSelectedTickets()));
        transactionTotalPriceTV.setText(String.valueOf(section.getTotalPrice()));

        int imageResId = getResources().getIdentifier(event.getPic(), "drawable", getPackageName());

        // Load gambar jika ada URL
        Glide.with(this).load(imageResId).into(transactionBannerIV);

    }
}