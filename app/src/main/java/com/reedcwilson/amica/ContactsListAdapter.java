package com.reedcwilson.amica;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.reedcwilson.amica.model.Contact;
import com.reedcwilson.amica.model.Interaction;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

class ContactsListAdapter extends RecyclerView.Adapter<ContactsListAdapter.ViewHolder> {

    private List<Contact> contacts;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name, date;
        public ViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.name);
            date = v.findViewById(R.id.last_contact_date);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public ContactsListAdapter(List<Contact> contacts) {
        this.contacts = contacts;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public ContactsListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_list_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Contact contact = contacts.get(position);
        holder.name.setText(contact.getName());
        List<Interaction> interactions = contact.getInteractions();
        Interaction interaction = interactions != null && interactions.size() > 0 ? interactions.get(interactions.size() - 1) : null;
        if (interaction != null) {
            DateFormat dateFormat = new SimpleDateFormat("d MMM, yy", Locale.US);
            String interactionDateStr = dateFormat.format(interaction.getDate());
            String interactionTypeStr = interaction.getType().toString().toLowerCase();
            holder.date.setText(String.format("%s - %s", interactionTypeStr, interactionDateStr));
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return contacts.size();
    }
}
