package com.poliveira.apps.allindiafms;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;

public class ActivityFM extends ActionBarActivity {

    private String TAG = ActivityFM.class.getName();
    private Toolbar mToolbar;
    private ArrayList<FM> fmList;
    public static String param_fmlist = "fm_list_selected";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_fm);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            Log.d(TAG, "Bundle Found");
            fmList = bundle.getParcelableArrayList(param_fmlist);
            Log.d(TAG, "Total FM :" + fmList.size());
        }

        mToolbar = (Toolbar) findViewById(R.id.toolbar_actionbar);
        //mToolbar.getBackground().setAlpha(0);
        //myView = (View) findViewById(R.id.my_view);
        setSupportActionBar(mToolbar);

        //ViewCompat.setElevation(mToolbar,0);
        //ViewCompat.setElevation(myView,10);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        startService(MediaService.createPlaylistIntent(this, fmList));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            getWindow().setStatusBarColor(getResources().getColor(R.color.myPrimaryDarkColor));
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_fragment_fm, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            case R.id.action_settings:
                return true;
        }

        //noinspection SimplifiableIfStatement

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        Button mPlayPause;
        Button mNext;
        Button mPrevious;
        Button mStop;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_fragment_fm, container, false);
            mPlayPause = (Button) rootView.findViewById(R.id.btn_play_pause);
            mNext = (Button) rootView.findViewById(R.id.btn_next);
            mPrevious = (Button) rootView.findViewById(R.id.btn_prev);
            mStop = (Button) rootView.findViewById(R.id.btn_stop);

            setListner(mPlayPause, MediaService.ACTION_PLAY);
            setListner(mNext, MediaService.ACTION_NEXT);
            setListner(mPrevious, MediaService.ACTION_PREV);
            setListner(mStop, MediaService.ACTION_QUIT);
            return rootView;
        }

        public void setListner(final Button pButton, final String pAction) {
            pButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), pButton.getText(), Toast.LENGTH_SHORT).show();
                    broadcastIntent(getActivity(), pAction);
                    if(pAction.equalsIgnoreCase(MediaService.ACTION_QUIT)){
                        getActivity().finish();
                    }
                }
            });
        }

        private static void broadcastIntent(Context ctx, String action) {
            Intent intent = new Intent(action);
            intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            ctx.sendBroadcast(intent);
            //PendingIntent.getBroadcast(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
    }


}
