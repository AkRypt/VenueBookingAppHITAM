package com.example.venuebooking_hitam;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class RoomBookingPage extends AppCompatActivity {

    private DatabaseReference pushRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");
    private DatabaseReference checkHourRef = FirebaseDatabase.getInstance().getReference().child("hitamVenueBooking");

    private EditText entryBookerName, entryBookerRole, entryBookerNumber, entryBookderDept, entryBookerPurpose;
    private TextView entryBookerDate, entryBookerT1, entryBookerT2;
    private Button entryBookBtn;
    private String roomNo;
    private List<String> dateExists = new ArrayList<>();

    private ArrayList<Integer> timeSlotsList = new ArrayList<>();
    private int timeSlotCollide, after4Toggle;
    private ArrayList<Integer> stopTimesList = new ArrayList<>();

    // For Date
    private Calendar calendar = Calendar.getInstance();
    final int year = calendar.get(Calendar.YEAR);
    final int month = calendar.get(Calendar.MONTH);
    final int day = calendar.get(Calendar.DAY_OF_MONTH);


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.room_booking_page);

        after4Toggle = 0;
        roomNo = getIntent().getStringExtra("roomNo");

        entryBookerName = findViewById(R.id.entryBookerName);
        entryBookerRole = findViewById(R.id.entryBookerRole);
        entryBookerNumber = findViewById(R.id.entryBookerNum);
        entryBookderDept = findViewById(R.id.entryBookerDept);
        entryBookerPurpose = findViewById(R.id.entryBookerPurpose);
        entryBookerDate = findViewById(R.id.entryBookerDate);
        entryBookerT1 = findViewById(R.id.entryBookerTime1);
        entryBookerT2 = findViewById(R.id.entryBookerTime2);
        entryBookBtn = findViewById(R.id.entryBookBtn);

        entryBookerDate = findViewById(R.id.entryBookerDate);
        entryBookerDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        RoomBookingPage.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        month = month + 1;
                        String datex = day+"/"+month+"/"+year;
                        entryBookerDate.setText(datex);
                    }
                }, year, month, day);
                datePickerDialog.show();
            }
        });

        pushRef = pushRef.child("venues").child(roomNo).child("bookings");

        pushRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    dateExists.add(ds.child("bookerDate").getValue(String.class));
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });


        // To Pick the start time
        entryBookerT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder startHourDialog = new AlertDialog.Builder(RoomBookingPage.this);
                LayoutInflater inflater1 = RoomBookingPage.this.getLayoutInflater();
                final View dialogView1 = inflater1.inflate(R.layout.choose_hour, null);
                startHourDialog.setView(dialogView1);
                Button h1 = dialogView1.findViewById(R.id.hour1);
                Button h2 = dialogView1.findViewById(R.id.hour2);
                Button h3 = dialogView1.findViewById(R.id.hour3);
                Button h4 = dialogView1.findViewById(R.id.hour4);
                Button h5 = dialogView1.findViewById(R.id.hour5);
                Button h6 = dialogView1.findViewById(R.id.hour6);
                Button h7 = dialogView1.findViewById(R.id.hour7);
                CheckBox c1 = dialogView1.findViewById(R.id.after4Check);
                c1.setChecked(false);

                startHourDialog.setIcon(R.drawable.ic_alarm_clock)
                        .setTitle("Choose Hour/Period")
                        .setNegativeButton("Cancel", null);
                final AlertDialog a1 = startHourDialog.create();
                a1.show();

                h1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("1");
                        a1.dismiss();
                    }
                });
                h2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("2");
                        a1.dismiss();
                    }
                });
                h3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("3");
                        a1.dismiss();
                    }
                });
                h4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("4");
                        a1.dismiss();
                    }
                });
                h5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("5");
                        a1.dismiss();
                    }
                });
                h6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("6");
                        a1.dismiss();
                    }
                });
                h7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT1.setText("7");
                        a1.dismiss();
                    }
                });
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 1;
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(RoomBookingPage.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                entryBookerT1.setText(i+":"+i1);
                            }
                        }, hour, minute, false);
                        mTimePicker.setTitle("Pick Start Time");
                        mTimePicker.show();
                        a1.dismiss();
                    }
                });


            }
        });
        // To Pick end time
        entryBookerT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder startHourDialog = new AlertDialog.Builder(RoomBookingPage.this);
                LayoutInflater inflater1 = RoomBookingPage.this.getLayoutInflater();
                View dialogView1 = inflater1.inflate(R.layout.choose_hour, null);
                startHourDialog.setView(dialogView1);
                Button h1 = dialogView1.findViewById(R.id.hour1);
                Button h2 = dialogView1.findViewById(R.id.hour2);
                Button h3 = dialogView1.findViewById(R.id.hour3);
                Button h4 = dialogView1.findViewById(R.id.hour4);
                Button h5 = dialogView1.findViewById(R.id.hour5);
                Button h6 = dialogView1.findViewById(R.id.hour6);
                Button h7 = dialogView1.findViewById(R.id.hour7);
                CheckBox c1 = dialogView1.findViewById(R.id.after4Check);
                c1.setChecked(false);

                startHourDialog.setIcon(R.drawable.ic_alarm_clock)
                        .setTitle("Choose Hour/Period")
                        .setNegativeButton("Cancel", null);
                final AlertDialog a1 = startHourDialog.create();
                a1.show();

                h1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("1");
                        a1.dismiss();
                    }
                });
                h2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("2");
                        a1.dismiss();
                    }
                });
                h3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("3");
                        a1.dismiss();
                    }
                });
                h4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("4");
                        a1.dismiss();
                    }
                });
                h5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("5");
                        a1.dismiss();
                    }
                });
                h6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("6");
                        a1.dismiss();
                    }
                });
                h7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 0;
                        entryBookerT2.setText("7");
                        a1.dismiss();
                    }
                });
                c1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        after4Toggle = 1;
                        Calendar mcurrentTime = Calendar.getInstance();
                        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                        int minute = mcurrentTime.get(Calendar.MINUTE);
                        TimePickerDialog mTimePicker;
                        mTimePicker = new TimePickerDialog(RoomBookingPage.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                                entryBookerT2.setText(i+":"+i1);
                            }
                        }, hour, minute, false);
                        mTimePicker.setTitle("Pick End Time");
                        mTimePicker.show();
                        a1.dismiss();
                    }
                });
            }
        });

        entryBookerDate.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) { }
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                timeSlotsList.clear();
                checkHourRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        dataSnapshot = dataSnapshot.child("venues").child(roomNo).child("bookings");

                        for (DataSnapshot dsCheck : dataSnapshot.getChildren()) {
                            if (dsCheck.child("bookerDate").getValue(String.class).equals(entryBookerDate.getText().toString())) {
                                if (dsCheck.child("bookerTime1").getValue(String.class).length() == 1) {
                                    for (int i = Integer.parseInt(dsCheck.child("bookerTime1").getValue(String.class));
                                         i <= Integer.parseInt(dsCheck.child("bookerTime2").getValue(String.class));
                                         i++) {
                                        timeSlotsList.add(i);
                                    }
                                }
                            }
                        }
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {}
                });
            }
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        entryBookBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timeSlotCollide = 0;
                for (int i = Integer.parseInt(entryBookerT1.getText().toString());
                     i <= Integer.parseInt(entryBookerT2.getText().toString()); i++) {
                    if (timeSlotsList.contains(i)) {
                        timeSlotCollide = 1;
                    }
                }

                if (entryBookerName.getText().toString().length()>0 && entryBookderDept.getText().toString().length()>0
                        && entryBookerPurpose.getText().length()>0
                        && entryBookerDate.getText().length()>4 && entryBookerT1.getText().length()>0
                        && entryBookerT2.getText().length()>0) {

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
                    Date pastDate = null;
                    try {
                        pastDate = sdf.parse(entryBookerDate.getText().toString());
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Date yesterday = new Date(System.currentTimeMillis() - 1000L * 60L * 60L * 24L);
                    if (pastDate.compareTo(yesterday)<0) {
                        new AlertDialog.Builder(RoomBookingPage.this)
                                .setIcon(R.drawable.warning)
                                .setTitle("Can't Book")
                                .setMessage("The date you've selected is not available for booking as it is in the past. Please choose a valid date.")
                                .setNegativeButton("OK", null)
                                .show();
                    } else {
//                        if (timeSlotsList.contains(Integer.parseInt(entryBookerT1.getText().toString()))
//                                || timeSlotsList.contains(Integer.parseInt(entryBookerT2.getText().toString()))) {
//                            Toast.makeText(RoomBookingPage.this, "This slot is already booked!", Toast.LENGTH_SHORT).show();
//                        } else
                        if (timeSlotCollide == 1) {
                            Toast.makeText(RoomBookingPage.this, "This slot is already booked!", Toast.LENGTH_SHORT).show();
                        } else {
                            if (Integer.parseInt(entryBookerT1.getText().toString()) <= Integer.parseInt(entryBookerT2.getText().toString())) {
                                pushRef.push().setValue(new SingleBookingItem(entryBookerName.getText().toString().trim(),
                                        entryBookerRole.getText().toString().trim(),
                                        entryBookerNumber.getText().toString().trim(),
                                        entryBookderDept.getText().toString().trim(), entryBookerPurpose.getText().toString().trim(),
                                        entryBookerDate.getText().toString().trim(),
                                        entryBookerT1.getText().toString().trim(),
                                        entryBookerT2.getText().toString().trim()));
                                Toast.makeText(RoomBookingPage.this, "Successful", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(RoomBookingPage.this, "Time slots are not valid!", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                } else {
                    Toast.makeText(RoomBookingPage.this, "Please fill in the fields!", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}