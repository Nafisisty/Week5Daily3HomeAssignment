package com.example.week5daily3homeassignment;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.week5daily3homeassignment.managers.Contact;
import com.example.week5daily3homeassignment.managers.ContactsManager;
import com.example.week5daily3homeassignment.managers.PermissionsManager;

import java.util.List;

public class MainActivity extends AppCompatActivity implements PermissionsManager.IPermissionManager, ContactsManager.IContactManager {

    PermissionsManager permissionsManager;
    ListView listView;
    EmailListAdapter emailListAdapter;
    List<Contact> emailList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        listView = findViewById(R.id.emailListViewId);

        permissionsManager = new PermissionsManager(this, this);
        permissionsManager.checkPermission();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent emailIntent = new Intent(Intent.ACTION_SENDTO);

                Contact contact = emailList.get(position);

                emailIntent.setData(Uri.parse("mailto:" + contact.getEmail()));

                startActivity(emailIntent);

            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        permissionsManager.checkResult(requestCode, permissions, grantResults);
    }

    @Override
    public void getContacts(List<Contact> contacts) {
        for (Contact contact: contacts) {
            Log.d("TAG", "getContacts: " + contact.toString());
        }

        emailList = contacts;

        emailListAdapter = new EmailListAdapter(this, emailList);
        listView.setAdapter(emailListAdapter);

    }

    @Override
    public void onPermissionResult(boolean isGranted) {

        Log.d("TAG", "onPermissionResult: " + (isGranted? "Granted" : "DENIED!!! LOSER"));

        if (isGranted) {

            getContacts();

        }

    }

    public void getContacts() {

        ContactsManager contactsManager = new ContactsManager(this);
        contactsManager.getContacts();

    }
}
