package com.example.week5daily3homeassignment.managers;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;

import java.util.ArrayList;
import java.util.List;

public class ContactsManager {

    Context context;
    IContactManager iContactManager;

    public ContactsManager(Context context) {
        this.context = context;
        this.iContactManager = (IContactManager) context;
    }

    public void getContacts() {

        Uri contactUri = ContactsContract.Contacts.CONTENT_URI;

        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;
        String HAS_EMAIL = "has_email";


        Cursor contactCursor = context.getContentResolver().query(
                contactUri, null, null, null, null
        );

        List<Contact> contactList = new ArrayList<>();

        while(contactCursor.moveToNext()) {

            String contactName = contactCursor.getString(contactCursor.getColumnIndex(DISPLAY_NAME));

            int hasEmailColumnIndex = contactCursor.getColumnIndex(HAS_PHONE_NUMBER);
            int has_phone = contactCursor.getInt(hasEmailColumnIndex);

            if(has_phone > 0) {

                String contactEmail = "";

                Uri emailUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
                String EMAIL = ContactsContract.CommonDataKinds.Email.ADDRESS;
                String contactId = contactCursor.getString(contactCursor.getColumnIndex(ContactsContract.Contacts._ID));

                Cursor emailCursor = context.getContentResolver().query(
                        emailUri,
                        null,
                          ContactsContract.CommonDataKinds.Email.CONTACT_ID
                        + " = " + contactId,
                        null,
                        null
                );

                if (emailCursor.moveToNext()) {

                    String email = emailCursor.getString(emailCursor.getColumnIndex(EMAIL));

                    contactEmail = email;

                    Contact contact = new Contact(contactName, contactEmail);
                    contactList.add(contact);
                }


            }
        }

        iContactManager.getContacts(contactList);
    }

    public interface IContactManager {
        void getContacts(List<Contact> contacts);
    }
}
