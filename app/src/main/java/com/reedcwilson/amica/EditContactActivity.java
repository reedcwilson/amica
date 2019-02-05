package com.reedcwilson.amica;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.reedcwilson.amica.model.Contact;
import com.reedcwilson.amica.model.Favorite;
import com.reedcwilson.amica.model.Interaction;
import com.reedcwilson.amica.model.Job;
import com.reedcwilson.amica.model.LovedOne;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

import static com.reedcwilson.amica.Constants.DATE_FORMAT;
import static java.util.Calendar.DATE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

public class EditContactActivity extends AppCompatActivity
        implements KeyValueFragment.KeyValueDialogListener {

    private Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_contact);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Gson gson = new Gson();
        contact = gson.fromJson(intent.getStringExtra(Contact.class.toString()), Contact.class);

        TextView nameView = findViewById(R.id.name);
        nameView.setText(contact.getName());

        Date birthday = contact.getBirthday();
        final Calendar myCalendar = Calendar.getInstance();
        EditText birthdayEditText = findViewById(R.id.birthday);
        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel(birthdayEditText, myCalendar);
        };
        birthdayEditText.setOnClickListener(v -> new DatePickerDialog(EditContactActivity.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)).show());
        myCalendar.setTime(birthday);
        updateLabel(birthdayEditText, myCalendar);

        String notes = contact.getNotes();
        TextView notesView = findViewById(R.id.notes);
        if (notes != null) {
            notesView.setText(notes);
            notesView.setMovementMethod(new ScrollingMovementMethod());
        } else {
            notesView.setVisibility(View.GONE);
        }

        if (contact.getLovedOnes() != null) {
            ListView lovedOnesList = findViewById(R.id.loved_ones_list);
            lovedOnesList.setAdapter(new ArrayAdapter<LovedOne>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contact.getLovedOnes()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    List<LovedOne> list = contact.getLovedOnes();
                    text1.setText(list.get(position).getName());
                    Date birthday = list.get(position).getBirthday();
                    if (birthday != null) {
                        text2.setText(formatBirthday(birthday));
                    }
                    return view;
                }
            });
            ListUtils.setDynamicHeight(lovedOnesList);
        }

        if (contact.getJobs() != null) {
            ListView jobsList = findViewById(R.id.jobs_list);
            jobsList.setAdapter(new ArrayAdapter<Job>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contact.getJobs()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    List<Job> list = contact.getJobs();
                    text1.setText(list.get(position).getCompany());
                    Date startDate = list.get(position).getStartDate();
                    if (startDate != null) {
                        text2.setText(DATE_FORMAT.format(startDate));
                    }
                    return view;
                }
            });
            ListUtils.setDynamicHeight(jobsList);
        }

        if (contact.getFavorites() != null) {
            ListView favoritesList = findViewById(R.id.favorites_list);
            favoritesList.setAdapter(new ArrayAdapter<Favorite>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contact.getFavorites()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    List<Favorite> list = contact.getFavorites();
                    text1.setText(list.get(position).getKey());
                    text2.setText(list.get(position).getValue());
                    return view;
                }
            });
            ListUtils.setDynamicHeight(favoritesList);
        }

        if (contact.getInteractions() != null) {
            ListView interactionsList = findViewById(R.id.interactions_list);
            interactionsList.setAdapter(new ArrayAdapter<Interaction>(this, android.R.layout.simple_list_item_2, android.R.id.text1, contact.getInteractions()) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    TextView text1 = view.findViewById(android.R.id.text1);
                    TextView text2 = view.findViewById(android.R.id.text2);

                    List<Interaction> list = contact.getInteractions();
                    text1.setText(String.format(
                            Locale.US,
                            "%s - %s",
                            list.get(position).getType().toString().toLowerCase(),
                            DATE_FORMAT.format(list.get(position).getDate())
                    ));
                    String notes = list.get(position).getNotes();
                    if (notes != null) {
                        text2.setText(notes);
                    }
                    return view;
                }
            });
            ListUtils.setDynamicHeight(interactionsList);
        }

        if (contact.getTodos() != null) {
            ListView todoList = findViewById(R.id.todo_list);
            todoList.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, contact.getTodos()));
            ListUtils.setDynamicHeight(todoList);
        }
    }

    private void updateLabel(EditText editText, Calendar myCalendar) {
        editText.setText(DATE_FORMAT.format(myCalendar.getTime()));
    }

    @Override
    public void onKeyValueSet(KeyValueFragment dialog, KeyValueDialogType type, CrudType operation) {
        switch (type) {
            case LOVED_ONE:
//                contact.getLovedOnes().add(LovedOne.builder()
//                        .name(dialog.getKey())
//                        .birthday(new GregorianCalendar(dialog.getValue()))
//                        .build());
                break;
            case JOB:
                break;
            case FAVORITE:
                break;
        }
    }

    private static class ListUtils {
        static void setDynamicHeight(ListView mListView) {
            ListAdapter mListAdapter = mListView.getAdapter();
            if (mListAdapter == null) {
                // when adapter is null
                return;
            }
            int height = 0;
            int desiredWidth = View.MeasureSpec.makeMeasureSpec(mListView.getWidth(), View.MeasureSpec.UNSPECIFIED);
            for (int i = 0; i < mListAdapter.getCount(); i++) {
                View listItem = mListAdapter.getView(i, null, mListView);
                listItem.measure(desiredWidth, View.MeasureSpec.UNSPECIFIED);
                height += listItem.getMeasuredHeight();
            }
            ViewGroup.LayoutParams params = mListView.getLayoutParams();
            params.height = height + (mListView.getDividerHeight() * (mListAdapter.getCount() - 1));
            mListView.setLayoutParams(params);
            mListView.requestLayout();
        }
    }

    private String formatBirthday(Date birthday) {
        return String.format(
            Locale.US,
            "%s  (%d)",
            DATE_FORMAT.format(birthday),
            getAge(birthday, new Date())
        );
    }

    private static int getAge(Date first, Date last) {
        Calendar a = getCalendar(first);
        Calendar b = getCalendar(last);
        int diff = b.get(YEAR) - a.get(YEAR);
        if (a.get(MONTH) > b.get(MONTH) ||
                (a.get(MONTH) == b.get(MONTH) && a.get(DATE) > b.get(DATE))) {
            diff--;
        }
        return diff;
    }

    private static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.US);
        cal.setTime(date);
        return cal;
    }
}
