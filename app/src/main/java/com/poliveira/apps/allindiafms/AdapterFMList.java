package com.poliveira.apps.allindiafms;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by i80429 on 2/20/2015.
 */
public class AdapterFMList extends RecyclerView.Adapter<AdapterFMList.FMViewHolder> {

    private ArrayList<FM> fmList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ProgressBar playSeekBar;
    private MediaPlayer player;
    private boolean isPlaying = false;

    public AdapterFMList(ArrayList<FM> contactList) {
        this.fmList = contactList;
    }

    @Override
    public int getItemCount() {
        return fmList.size();
    }

    @Override
    public void onBindViewHolder(FMViewHolder contactViewHolder, int i) {
        FM ci = fmList.get(i);
        contactViewHolder.name.setText(ci.name);
        contactViewHolder.url.setText(ci.url);
        contactViewHolder.logo.setImageUrl(ci.imageurl, imageLoader);
        contactViewHolder.logo.setErrorImageResId(R.drawable.ic_launcher);
    }

    @Override
    public FMViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View itemView = LayoutInflater.
                from(viewGroup.getContext()).
                inflate(R.layout.fm_list_row, viewGroup, false);

        return new FMViewHolder(itemView, new MyViewHolderListner());
    }

    public class MyViewHolderListner implements FMViewHolder.IMyViewHolderClicks {

        @Override
        public void onButtonClick(Button button, int position) {
            Toast.makeText(button.getContext(), position + "Button name:" + button.getResources().getResourceEntryName(button.getId()), Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onViewClick(View view, int position) {
            Toast.makeText(view.getContext(), Boolean.toString(isPlaying) + position + "name:" + view.getResources().getResourceEntryName(view.getId()), Toast.LENGTH_SHORT).show();
            //initializeMediaPlayer(fmList.get(position).url);
            //startPlaying();
            //ArrayList<FM> fmSelected = new ArrayList<FM>();
            //fmSelected.add(fmList.get(position));
            //view.getContext().startService(MediaService.createPlaylistIntent(view.getContext(), fmList));
            //view.getContext().startService(MediaService.createSinglePlayIntent(view.getContext(), fmList.get(position)));
            Intent fmIntent = new Intent(view.getContext(), ActivityFM.class);
            fmIntent.putExtra(ActivityFM.param_fmlist, fmList);
            view.getContext().startActivity(fmIntent);
        }
    }

    public static class FMViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected NetworkImageView logo;
        protected TextView name;
        protected TextView url;
        protected TextView slogan;
        protected Button like;
        Context context;
        public IMyViewHolderClicks mListener;
        protected int positioin;

        public FMViewHolder(View v, IMyViewHolderClicks listner) {
            super(v);
            mListener = listner;
            context = v.getContext();
            logo = (NetworkImageView) v.findViewById(R.id.logo);
            name = (TextView) v.findViewById(R.id.title);
            url = (TextView) v.findViewById(R.id.url);
            like = (Button) v.findViewById(R.id.like);

            like.setOnClickListener(this);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            positioin = getPosition();
            if (v instanceof Button) {
                //mListener.onButtonClick((Button) v, positioin);
            } else {
                mListener.onViewClick(v, positioin);
            }
        }

        public static interface IMyViewHolderClicks {
            public void onButtonClick(Button button, int positioin);

            public void onViewClick(View view, int positioin);
        }
    }

    protected void startPlaying() {

        //playSeekBar.setVisibility(View.VISIBLE);

        player.prepareAsync();

        player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            public void onPrepared(MediaPlayer mp) {
                player.start();
            }
        });


    }

    private void stopPlaying() {
        if (player.isPlaying()) {
            player.stop();
            player.release();
        }
        //playSeekBar.setVisibility(View.INVISIBLE);
    }

    private void initializeMediaPlayer(String url) {
        player = new MediaPlayer();
        player.reset();
        try {
            player.setDataSource(url);
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        player.setOnBufferingUpdateListener(new MediaPlayer.OnBufferingUpdateListener() {

            public void onBufferingUpdate(MediaPlayer mp, int percent) {
                //playSeekBar.setSecondaryProgress(percent);
                Log.i("Buffering", "" + percent);
            }
        });
    }
}