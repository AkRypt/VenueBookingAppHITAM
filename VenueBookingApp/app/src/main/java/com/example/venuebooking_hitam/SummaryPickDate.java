package com.example.venuebooking_hitam;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.DatePicker;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
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
import java.util.Calendar;
import java.util.List;

public class SummaryPickDate extends AppCompatActivity {

    private DatabaseReference summRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");

    private TextView summaryPickDate;
    private String roomNoPick;
    private ProgressBar summLoading;

    private ListView summaryRoomsList;
    private List<String> summVenues = new ArrayList<>();
    private List<SingleRoomName> summVenueNames = new ArrayList<>();
    private SingleRoomNameAdapter singleRoomNameAdapter;

    // For Date
    private Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.summary_pick);

        summaryPickDate = findViewById(R.id.summaryPickDate);
        summaryRoomsList = findViewById(R.id.summaryPickListView);
        summLoading = findViewById(R.id.summarPickLoading);
        summLoading.setVisibility(View.VISIBLE);

        singleRoomNameAdapter = new SingleRoomNameAdapter(this, R.layout.single_room_name, summVenueNames);
        summaryRoomsList.setAdapter(singleRoomNameAdapter);
        summVenueNames.clear();
        singleRoomNameAdapter.notifyDataSetChanged();

        summRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child("venues");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    summVenues.add(ds.getKey());
                    summVenueNames.add(new SingleRoomName(ds.child("venueName").getValue(String.class)));
                    singleRoomNameAdapter.notifyDataSetChanged();
                }
                summLoading.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        summaryPickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        SummaryPickDate.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String datex = day+"/"+month+"/"+year;
                        summaryPickDate.setText(datex);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        summaryRoomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (!summaryPickDate.getText().toString().equals("Choose a Date")) {
                    roomNoPick = summVenues.get(i);
                    goToSummaryPage();
                } else {
                    Toast.makeText(SummaryPickDate.this, "Please choose a date", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void goToSummaryPage() {
        Intent intent = new Intent(SummaryPickDate.this, SummaryPage.class);
        intent.putExtra("summaryVenue", roomNoPick);
        intent.putExtra("summaryDate", summaryPickDate.getText().toString());
        startActivity(intent);
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
                SingleRoomNameAdapter bookingsAdapterx = (SingleRoomNameAdapter) summaryRoomsList.getAdapter();
                Filter filter = bookingsAdapterx.getFilter();
                filter.filter(s);
                return true;
            }
        });
        return true;
    }
}
