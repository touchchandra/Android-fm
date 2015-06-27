package com.poliveira.apps.allindiafms;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


/**
 * A fragment with a Google +1 button.
 * Activities that contain this fragment must implement the
 * {@link com.poliveira.apps.allindiafms.MySlideTabFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link com.poliveira.apps.allindiafms.MySlideTabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MySlideTabFragment extends Fragment {

    private static String ARG_PARAM1 = "ARG_PARAM1";
    private static String ARG_PARAM2 = "ARG_PARAM2";

    private String mParam1, mParam2;
    private OnFragmentInteractionListener mListener;

    static final String LOG_TAG = "SlidingTabsBasicFrag";
    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;


    public static MySlideTabFragment newInstance(String param1, String param2) {
        MySlideTabFragment fragment = new MySlideTabFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public MySlideTabFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_slide_tab, container, false);

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        //mViewPager.setAdapter(new SamplePagerAdapter());
        mViewPager.setAdapter(new SlidingFragmentPagerAdapter(getFragmentManager()));

        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        //mSlidingTabLayout.setDistributeEvenly(true);
        mSlidingTabLayout.setViewPager(mViewPager);


    }


    @Override
    public void onResume() {
        super.onResume();

    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        public void onFragmentInteraction(Uri uri);
    }

    class SamplePagerAdapter extends PagerAdapter {

        String[] title = {"Today", "Daily", "Settings", "Profile"};

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            //return 10;
            return 4;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(ViewGroup, int)} is the
         * same object as the {@link View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }

        /**
         * Return the title of the item at {@code position}. This is important as what this method
         * returns is what is displayed in the {@link SlidingTabLayout}.
         * <p/>
         * Here we construct one using the position value, but for real application the title should
         * refer to the item's contents.
         */
        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
            //return "Item " + (position + 1);
        }

        /**
         * Instantiate the {@link View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            if (position == 0) {

            }

            View view = getActivity().getLayoutInflater().inflate(R.layout.pager_item, container, false);
            // Add the newly created View to the ViewPager
            container.addView(view);

            // Retrieve a TextView from the inflated View, and update it's text
            TextView title = (TextView) view.findViewById(R.id.item_title);
            title.setText(String.valueOf(position + 1));

            Log.i(LOG_TAG, "instantiateItem() [position: " + position + "]");

            // Return the View
            return view;
        }

        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
            Log.i(LOG_TAG, "destroyItem() [position: " + position + "]");
        }

    }

    class SlidingFragmentPagerAdapter extends FragmentStatePagerAdapter {

        String[] title = {"ALL FM", "FAVOURITE", "POPULAR"};

        public SlidingFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            Fragment fragment = new FragmentHelp();
            switch (position) {
                case 0:
                    fragment = FragmentFmList.newInstance(AppConst.ARG_ALL_FM, "NONE");
                    break;
                case 1:
                    fragment = FragmentFmList.newInstance(AppConst.ARG_FAVOURITE_FM, "NONE");
                    break;
                case 2:
                    fragment = FragmentFmList.newInstance(AppConst.ARG_RECENT_FM, "NONE");
                    break;
            }


            return fragment;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return title[position];
            //return "Item " + (position + 1);
        }


        @Override
        public int getCount() {
            return 3;
        }
    }
}
