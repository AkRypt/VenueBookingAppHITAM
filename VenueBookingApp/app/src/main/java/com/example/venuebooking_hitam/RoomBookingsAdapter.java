package com.example.venuebooking_hitam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class RoomBookingsAdapter extends BaseAdapter implements Filterable {

    private TextView name, role, num, dept, date, time;
    private String timesuff1, timesuff2;

    private Context context;
    private List<SingleBookingItem> singleMemberItem = new ArrayList<SingleBookingItem>();
    private List<SingleBookingItem> mOrig;


    public RoomBookingsAdapter(@NonNull Context context, int resource, @NonNull List<SingleBookingItem> objects) {
        super();
        this.context = context;
        this.singleMemberItem = objects;
    }


    @Override
    public int getCount() { return singleMemberItem.size(); }
    @Override
    public Object getItem(int i) { return singleMemberItem.get(i); }
    @Override
    public long getItemId(int i) { return i; }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        SingleBookingItem currentMemberItem = (SingleBookingItem) getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.single_room_booking, parent, false);
        }

        name = listItemView.findViewById(R.id.bookerName);
        name.setText(currentMemberItem.getBookerName());

        role = listItemView.findViewById(R.id.bookerRole);
        role.setText(currentMemberItem.getBookerRole());

        num = listItemView.findViewById(R.id.bookerNumber);
        num.setText(currentMemberItem.getBookerNumber());

        dept = listItemView.findViewById(R.id.bookerDept);
        dept.setText(currentMemberItem.getBookerDept());

        date = listItemView.findViewById(R.id.bookerDate);
        date.setText(currentMemberItem.getBookerDate());

        time = listItemView.findViewById(R.id.bookerTime);
        switch (currentMemberItem.getBookerTime1()) {
            case "1":
                timesuff1 = "st";
                break;
            case "2":
                timesuff1 = "nd";
                break;
            case "3":
                timesuff1 = "rd";
                break;
            default:
                timesuff1 = "th";
                break;
        }

        switch (currentMemberItem.getBookerTime2()) {
            case "1":
                timesuff2 = "st";
                break;
            case "2":
                timesuff2 = "nd";
                break;
            case "3":
                timesuff2 = "rd";
                break;
            default:
                timesuff2 = "th";
                break;
        }

        time.setText("From "+currentMemberItem.getBookerTime1()+timesuff1+" Hour To "+currentMemberItem.getBookerTime2()+timesuff2+" Hour");

        return listItemView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SingleBookingItem> results = new ArrayList<>();
                if (mOrig == null)
                    mOrig = singleMemberItem;
                if (charSequence != null) {
                    if (mOrig != null && mOrig.size() > 0) {
                        for (final SingleBookingItem g : mOrig) {
                            if (g.getBookerDate().toLowerCase().contains(charSequence.toString()) ||
                                    g.getBookerTime1().toLowerCase().contains(charSequence.toString()) ||
                                    g.getBookerName().toLowerCase().contains(charSequence.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                singleMemberItem = (ArrayList<SingleBookingItem>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
