package com.example.venuebooking_hitam;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class SummaryPage extends AppCompatActivity {

    private String summaryVenue, summaryDate;
    private ProgressBar summLoading;
    private TextView summPageVenue, summPageDate, slot1, slot2, slot3, slot4, slot5, slot6, slot7;
    private List<TextView> textViews = new ArrayList<>();

    private DatabaseReference sumRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_page);

        summaryVenue = getIntent().getStringExtra("summaryVenue");
        summaryDate = getIntent().getStringExtra("summaryDate");

        summPageVenue = findViewById(R.id.summaryPageVenue);
        summPageDate = findViewById(R.id.summaryPageDate);
        summPageDate.setText(summaryDate);
        slot1 = findViewById(R.id.slot1);
        slot2 = findViewById(R.id.slot2);
        slot3 = findViewById(R.id.slot3);
        slot4 = findViewById(R.id.slot4);
        slot5 = findViewById(R.id.slot5);
        slot6 = findViewById(R.id.slot6);
        slot7 = findViewById(R.id.slot7);
        summLoading = findViewById(R.id.summLoading);
        summLoading.setVisibility(View.VISIBLE);

        textViews.add(0, slot1);
        textViews.add(1, slot1);textViews.add(2, slot2);textViews.add(3, slot3);
        textViews.add(4, slot4);textViews.add(5, slot5);textViews.add(6, slot6);
        textViews.add(7, slot7);

        sumRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                summPageVenue.setText(dataSnapshot.child("venues").child(summaryVenue).child("venueName").getValue(String.class));
                dataSnapshot = dataSnapshot.child("venues").child(summaryVenue).child("bookings");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    if (ds.child("bookerDate").getValue(String.class).equals(summaryDate)) {
                        for (int ind = Integer.parseInt(ds.child("bookerTime1").getValue(String.class));
                             ind<= Integer.parseInt(ds.child("bookerTime2").getValue(String.class)); ind++) {
                            textViews.get(ind).setTextColor(Color.RED);
                            textViews.get(ind).setText(ds.child("bookerName").getValue(String.class));
                        }
                    }
                }
                summLoading.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });
    }

    public void goToTimeSlot(View view) {
        Intent intent = new Intent(SummaryPage.this, RoomDetails.class);
        intent.putExtra("roomNo", summaryVenue);
        startActivity(intent);
    }
}
