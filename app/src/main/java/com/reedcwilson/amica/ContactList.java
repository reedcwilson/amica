package com.reedcwilson.amica;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.reedcwilson.amica.model.Contact;
import com.reedcwilson.amica.model.Favorite;
import com.reedcwilson.amica.model.Interaction;
import com.reedcwilson.amica.model.InteractionType;
import com.reedcwilson.amica.model.Job;
import com.reedcwilson.amica.model.LovedOne;

import java.util.AbstractMap;
import java.util.Arrays;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.reedcwilson.amica.extra.IntentStrings.EXTRA_MESSAGE;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ContactList.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ContactList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ContactList extends Fragment {
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PAGE = "ARG_PAGE";

    private int page;
    private RecyclerView.Adapter adapter;
    private OnFragmentInteractionListener mListener;

    public ContactList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param page the tab page number.
     * @return A new instance of fragment ContactList.
     */
    public static ContactList newInstance(int page) {
        ContactList fragment = new ContactList();
        Bundle args = new Bundle();
        args.putInt(ARG_PAGE, page);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            page = getArguments().getInt(ARG_PAGE);
        }
    }

    private List<Contact> getContacts() {
        return Arrays.asList(
                Contact.builder()
                        .name("Britney Wilson")
                        .birthday(new GregorianCalendar(1988, 6, 14).getTime())
                        .notes("She is pretty and awesome! Great mom and wife! She was born in American Fork, Utah and has 4 brothers. Her dad is a second grade teacher and her mom worked for more than 20 years in lunch rooms at high schools. She likes art and does painting and other art pieces that we put in our home. We got married in 2011 in the Salt Lake City temple. She first went to college at UVU and then transferred to Utah State. After a year and a half there, she went on a mission to L.A. After her mission we met and got married. She worked for a little under a year and then went to BYU where she finished her degree in Family Life Studies.")
                        .lovedOnes(Arrays.asList(
                                LovedOne.builder()
                                        .name("Azure Roman")
                                        .birthday(new GregorianCalendar(2016, 2, 11).getTime())
                                        .build(),
                                LovedOne.builder()
                                        .name("Remy Gray")
                                        .birthday(new GregorianCalendar(2018, 10, 6).getTime())
                                        .build()
                        ))
                        .jobs(Arrays.asList(
                                Job.builder()
                                        .company("doTERRA")
                                        .build(),
                                Job.builder()
                                        .company("VIPKid")
                                        .startDate(new GregorianCalendar(2017, 11, 10).getTime())
                                        .build()
                        ))
                        .favorites(Arrays.asList(
                                Favorite.builder()
                                        .key("Candy")
                                        .value("Lindt Lindor")
                                        .build(),
                                Favorite.builder()
                                        .key("Person")
                                        .value("Reed Wilson")
                                        .build()
                        ))
                        .interactions(Arrays.asList(
                                Interaction.builder()
                                        .date(new Date())
                                        .type(InteractionType.TEXT)
                                        .notes("talked about books")
                                        .build(),
                                Interaction.builder()
                                        .date(new Date())
                                        .type(InteractionType.OTHER)
                                        .build()
                        ))
                        .todos(Arrays.asList("get reimbursement receipt"))
                        .build(),
                Contact.builder().name("Don Wilson").build(),
                Contact.builder().name("Gary Wilson").build(),
                Contact.builder().name("Ryan Wilson").build(),
                Contact.builder().name("Ben Wilson").build(),
                Contact.builder().name("Micaela Wilson").build(),
                Contact.builder().name("Wes Moss").build(),
                Contact.builder().name("Billy Hiatt").build(),
                Contact.builder().name("Austen Sweeten").build(),
                Contact.builder().name("Devin Duncan").build()
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contact_list, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.contacts_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        final List<Contact> contacts = getContacts();
        adapter = new ContactsListAdapter(contacts);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity().getApplicationContext(), recyclerView, new RecyclerTouchListener.ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Intent intent = new Intent(getActivity(), DisplayContactActivity.class);
                Gson gson = new Gson();
                intent.putExtra(Contact.class.toString(), gson.toJson(contacts.get(position)));
                startActivity(intent);
            }

            @Override
            public void onLongClick(View view, int position) {

            }
        }));
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
