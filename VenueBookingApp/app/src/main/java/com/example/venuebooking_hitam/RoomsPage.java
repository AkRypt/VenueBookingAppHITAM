package com.example.venuebooking_hitam;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RoomsPage extends AppCompatActivity {

    private DatabaseReference dbRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");

    private ListView roomsList;
    private List<String> venues = new ArrayList<>();
    private List<SingleRoomName> venueNames = new ArrayList<>();
    private SingleRoomNameAdapter singleRoomNameAdapter;

    private ProgressBar roomload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rooms_page);

        roomsList = findViewById(R.id.roomsListView);
        roomload = findViewById(R.id.roomsLoading);
        roomload.setVisibility(View.VISIBLE);
        singleRoomNameAdapter = new SingleRoomNameAdapter(this, R.layout.single_room_name, venueNames);
        roomsList.setAdapter(singleRoomNameAdapter);
        venueNames.clear();
        singleRoomNameAdapter.notifyDataSetChanged();

        dbRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataSnapshot = dataSnapshot.child("venues");

                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    venues.add(ds.getKey());
                    venueNames.add(new SingleRoomName(ds.child("venueName").getValue(String.class)));
                    singleRoomNameAdapter.notifyDataSetChanged();
                }
                roomload.setVisibility(View.GONE);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        roomsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(RoomsPage.this, RoomDetails.class);
                intent.putExtra("roomNo", venues.get(i));
                startActivity(intent);
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
                SingleRoomNameAdapter bookingsAdapterx = (SingleRoomNameAdapter) roomsList.getAdapter();
                Filter filter = bookingsAdapterx.getFilter();
                filter.filter(s);
                return true;
            }
        });
        return true;
    }
}
