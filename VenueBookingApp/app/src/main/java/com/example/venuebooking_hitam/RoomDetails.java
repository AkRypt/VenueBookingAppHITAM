package com.example.venuebooking_hitam;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class RoomDetails extends AppCompatActivity {

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");
    private DatabaseReference delRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private FirebaseUser mUser;
    private List<String> adminUids = new ArrayList<>();

    private ListView roomBookingsList;
    private List<String> parentNodes = new ArrayList<>();
    private List<SingleBookingItem> singleBookingItemList = new ArrayList<>();
    private RoomBookingsAdapter roomBookingsAdapter;

    private ImageView roomPic;
    private TextView roomName;
    private ProgressBar loading;

    private String roomNo, venueName, venuePic;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_details);

        roomName = findViewById(R.id.roomName);
        roomPic = findViewById(R.id.roomPic);
        Button roomBookBtn = findViewById(R.id.roomBookBtn);
        roomNo = getIntent().getStringExtra("roomNo");
        loading = findViewById(R.id.loading);
        loading.setVisibility(View.VISIBLE);

        roomBookingsList = findViewById(R.id.roomBookingsList);
        roomBookingsAdapter = new RoomBookingsAdapter(this, R.layout.single_room_booking, singleBookingItemList);
        roomBookingsList.setAdapter(roomBookingsAdapter);
        singleBookingItemList.clear();
        roomBookingsAdapter.notifyDataSetChanged();

        mUser = mAuth.getCurrentUser();

        roomBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RoomDetails.this, RoomBookingPage.class);
                intent.putExtra("roomNo", roomNo);
                startActivity(intent);
                finish();
            }
        });

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot ds) {
                for (DataSnapshot d2 : ds.child("admins").getChildren()) {
                    adminUids.add(d2.child("uid").getValue(String.class));
                }
                ds = ds.child("venues").child(roomNo);
                venueName = ds.child("venueName").getValue(String.class);
                venuePic = ds.child("venuePic").getValue(String.class);
                // Setting room image
                roomName.setText(venueName);
                Picasso.get().load(venuePic).fit().into(roomPic);


                if (ds.child("bookings").exists()) {
                    for (DataSnapshot ds1 : ds.child("bookings").getChildren()) {
                        String name = ds1.child("bookerName").getValue(String.class);
                        String role = ds1.child("bookerRole").getValue(String.class);
                        String num = ds1.child("bookerNumber").getValue(String.class);
                        String dept = ds1.child("bookerDept").getValue(String.class);
                        String purpose = ds1.child("bookerPurpose").getValue(String.class);
                        String date = ds1.child("bookerDate").getValue(String.class);
                        String time1 = ds1.child("bookerTime1").getValue(String.class);
                        String time2 = ds1.child("bookerTime2").getValue(String.class);

                        singleBookingItemList.add(new SingleBookingItem(name, dept, role, num, purpose, date, time1, time2));
                        parentNodes.add(ds1.getKey());
                        roomBookingsAdapter.notifyDataSetChanged();
                    }
                }
                loading.setVisibility(View.GONE);

            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        roomBookingsList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int pos, long l) {
                if (adminUids.contains(mUser.getUid())) {
                    new AlertDialog.Builder(RoomDetails.this)
                            .setIcon(R.drawable.warning)
                            .setTitle("Cancel Booking?")
                            .setMessage("Are you sure you want to delete this booking?")
                            .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    delRef = delRef.child("venues").child(roomNo).child("bookings").child(parentNodes.get(pos));
                                    delRef.removeValue();
                                    Toast.makeText(RoomDetails.this, "Successfully Deleted", Toast.LENGTH_SHORT).show();
                                    finish();
                                    startActivity(getIntent());
                                }
                            })
                            .setNegativeButton("No", null)
                            .show();
                }
                return false;
            }
        });
    }

    // Action Bar Search View
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_menu, menu);
        final MenuItem item = menu.findItem(R.id.search);

        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                RoomBookingsAdapter bookingsAdapterx = (RoomBookingsAdapter) roomBookingsList.getAdapter();
                Filter filter = bookingsAdapterx.getFilter();
                filter.filter(s);
                return true;
            }
        });
        return true;
    }
}