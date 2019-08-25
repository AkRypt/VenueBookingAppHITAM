package com.example.venuebooking_hitam;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class SingleRoomNameAdapter extends BaseAdapter implements Filterable {

    private TextView roomName;

    private Context context;
    private List<SingleRoomName> singleMemberItem = new ArrayList<SingleRoomName>();
    private List<SingleRoomName> mOrig;


    public SingleRoomNameAdapter(@NonNull Context context, int resource, @NonNull List<SingleRoomName> objects) {
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

        SingleRoomName currentMemberItem = (SingleRoomName) getItem(position);

        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(context).inflate(R.layout.single_room_name, parent, false);
        }

        roomName = listItemView.findViewById(R.id.singleRoomTV);
        roomName.setText(currentMemberItem.getRoom());

        return listItemView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                final FilterResults oReturn = new FilterResults();
                final ArrayList<SingleRoomName> results = new ArrayList<>();
                if (mOrig == null)
                    mOrig = singleMemberItem;
                if (charSequence != null) {
                    if (mOrig != null && mOrig.size() > 0) {
                        for (final SingleRoomName g : mOrig) {
                            if (g.getRoom().toLowerCase().contains(charSequence.toString()))
                                results.add(g);
                        }
                    }
                    oReturn.values = results;
                }
                return oReturn;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                singleMemberItem = (ArrayList<SingleRoomName>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }
}
