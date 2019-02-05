package com.reedcwilson.amica;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import static com.reedcwilson.amica.Constants.ADD_CONTACT;

public class MainActivity extends AppCompatActivity
        implements ContactList.OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Get the ViewPager and set it's PagerAdapter so that it can display items
        ViewPager viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new ContactListFragmentPagerAdapter(getSupportFragmentManager(), MainActivity.this));

        // Give the TabLayout the ViewPager
        TabLayout tabLayout = findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
    }

    public void addContact(View view) {
//        Toast.makeText(this, "This is a test", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, ADD_CONTACT);

//        Intent intent = new Intent(this, DisplayContactActivity.class);
//        EditText editText = (EditText) findViewById(R.id.nameText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ADD_CONTACT) {
            if (resultCode == RESULT_OK) {
                Uri contactData = data.getData();
                String number = "";
                try (Cursor cursor = getContentResolver().query(contactData, null, null, null, null)) {
                    cursor.moveToFirst();
                    String hasPhone = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                    String name = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY));
                    Toast.makeText(this, name, Toast.LENGTH_SHORT).show();
//                    if (hasPhone.equals("1")) {
//                        Cursor phones = getContentResolver().query
//                                (ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
//                                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID
//                                                + " = " + contactId, null, null);
//                        while (phones.moveToNext()) {
//                            number = phones.getString(phones.getColumnIndex
//                                    (ContactsContract.CommonDataKinds.Phone.NUMBER)).replaceAll("[-() ]", "");
//                        }
//                        phones.close();
//                        //Do something with number
//                    } else {
//                        Toast.makeText(getApplicationContext(), "This contact has no phone number", Toast.LENGTH_LONG).show();
//                    }
                }
            }
        }
    }

    @Override
    public void onFragmentInteraction(Uri uri) {
        // you can leave it empty
    }
}
