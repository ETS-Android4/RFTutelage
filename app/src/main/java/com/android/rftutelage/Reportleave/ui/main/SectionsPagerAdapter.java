package com.android.rftutelage.Reportleave.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.rftutelage.R;
import com.android.rftutelage.Reportleave.History.Fragment_history;
import com.android.rftutelage.Reportleave.Report_Leave.Fragment_report_leave;
import com.android.rftutelage.ui.CollegeBlog.College.FragmentCollegeBlogView;
import com.android.rftutelage.ui.CollegeBlog.Faculty.FragmentFacultyBlogView;
import com.android.rftutelage.ui.CollegeBlog.Student.FragmentStudentBlogView;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_6, R.string.tab_text_7};
    private final Context mContext;

    public SectionsPagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new Fragment_report_leave();
                break;
            case 1:
                fragment = new Fragment_history();
                break;
        }
        return fragment;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);
    }

    @Override
    public int getCount() {
        // Show 2 total pages.
        return 2;
    }
}