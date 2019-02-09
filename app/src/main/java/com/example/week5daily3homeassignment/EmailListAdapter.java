package com.example.week5daily3homeassignment;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.week5daily3homeassignment.managers.Contact;

import java.util.ArrayList;
import java.util.List;

public class EmailListAdapter extends BaseAdapter {

    private Context mContext;
    private List<Contact> contactList;

    public EmailListAdapter(Context mContext, List<Contact> contactList) {
        this.mContext = mContext;
        if(contactList != null) {
            this.contactList = contactList;
        } else {
            this.contactList = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return contactList.size();
    }

    @Override
    public Object getItem(int position) {
        return contactList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = View.inflate(mContext, R.layout.sample_email, null);
        TextView contactNameTextView = view.findViewById(R.id.contactNameTextViewId);
        TextView contactEmailTextView = view.findViewById(R.id.contactEmailTextViewId);

        contactNameTextView.setText(contactList.get(position).getName());
        contactEmailTextView.setText(contactList.get(position).getEmail());

        return view;
    }
}
