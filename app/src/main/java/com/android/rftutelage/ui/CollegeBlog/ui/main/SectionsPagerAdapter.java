package com.android.rftutelage.ui.CollegeBlog.ui.main;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.android.rftutelage.R;
import com.android.rftutelage.ui.CollegeBlog.College.FragmentCollegeBlogView;
import com.android.rftutelage.ui.CollegeBlog.Faculty.FragmentFacultyBlogView;
import com.android.rftutelage.ui.CollegeBlog.Student.FragmentStudentBlogView;
//import com.android.rftutelage.ui.CollegeBlog.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_3, R.string.tab_text_4,R.string.tab_text_5};
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
                fragment = new FragmentCollegeBlogView();
                break;
            case 1:
                fragment = new FragmentFacultyBlogView();
                break;
            case 2:
                fragment = new FragmentStudentBlogView();
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
        return 3;
    }
}