package com.reedcwilson.amica;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class ContactListFragmentPagerAdapter extends FragmentPagerAdapter {
    private final int PAGE_COUNT = 4;
    private String tabTitles[] = new String[] { "A", "B", "C", "D" };
    private Context context;

    public ContactListFragmentPagerAdapter(FragmentManager manager, Context context) {
        super(manager);
        this.context = context;
    }

    @Override
    public Fragment getItem(int i) {
        return ContactList.newInstance(i + 1);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int i) {
        return tabTitles[i];
    }
}
