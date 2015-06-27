package com.poliveira.apps.allindiafms;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import java.util.ArrayList;

/**
 * Created by i80429 on 2/20/2015.
 */
public class AdapterFMList extends RecyclerView.Adapter<AdapterFMList.FMViewHolder> {

    public ArrayList<FM> fmList;
    ImageLoader imageLoader = AppController.getInstance().getImageLoader();
    private ProgressBar playSeekBar;
    private MediaPlayer player;
    Context ctx;


    public AdapterFMList(Context ctx, ArrayList<FM> contactList) {
        this.fmList = contactList;
        this.ctx = ctx;
    }

    @Override
    public int getItemCount() {
        return fmList.size();
    }

    @Override
    public void onBindViewHolder(final FMViewHolder contactViewHolder, final int i) {
        final FM ci = fmList.get(i);
        contactViewHolder.fmid = ci.fmid;
        contactViewHolder.name.setText(ci.name);
        contactViewHolder.url.setText(ci.url);
        contactViewHolder.logo.setImageUrl("http://png-3.findicons.com/files/icons/2145/private_stock/128/ps_vudu_logo.png", imageLoader);
        contactViewHolder.logo.setErrorImageResId(R.drawable.ic_launcher);
        //contactViewHolder.like.setText(ci.favourite);
        contactViewHolder.favourite = ci.favourite;
        contactViewHolder.like.setImageResource(ci.favourite.equalsIgnoreCase("Y") ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);
        contactViewHolder.like.setOnClickListener(new View.OnClickListener() {
                                                      @Override
                                                      public void onClick(View v) {
                                                          Toast.makeText(ctx, ci.fmid, Toast.LENGTH_LONG).show();
                                                          ci.favourite = ci.favourite.equalsIgnoreCase("Y") ? "N" : "Y";

                                                          contactViewHolder.like.setImageResource(ci.favourite.equalsIgnoreCase("Y") ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);

                                                          FMDao fmDAO = new FMDao(ctx);
                                                          fmDAO.open();
                                                          fmList.get(i).favourite = ci.favourite;
                                                          fmDAO.setFavourite(ci.fmid, ci.favourite);
                                                          fmDAO.close();
                                                      }
                                                  }
        );
        //contactViewHolder.like.setImageResource(ci.favourite.equalsIgnoreCase("Y") ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);
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
            Intent fmIntent = new Intent(view.getContext(), ActivityFM.class);
            fmIntent.putExtra(ActivityFM.param_fmlist, fmList);
            view.getContext().startActivity(fmIntent);
        }
    }

    public static class FMViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected String fmid;
        protected NetworkImageView logo;
        protected TextView name;
        protected TextView url;
        protected TextView slogan;
        protected ImageButton like;
        protected String favourite;
        Context context;
        public IMyViewHolderClicks mListener;
        protected int position;

        public FMViewHolder(View v, IMyViewHolderClicks listener) {
            super(v);
            mListener = listener;
            context = v.getContext();
            logo = (NetworkImageView) v.findViewById(R.id.logo);
            name = (TextView) v.findViewById(R.id.title);
            url = (TextView) v.findViewById(R.id.url);
            like = (ImageButton) v.findViewById(R.id.like);

            like.setOnClickListener(this);
            v.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
/*            position = getPosition();
            if (v instanceof ImageButton) {
                //mListener.onButtonClick((Button) v, position);
                Toast.makeText(v.getContext(), fmid + " :" + name.getText(), Toast.LENGTH_LONG).show();
                FMDao fmDAO = new FMDao(context);
                fmDAO.open();
                favourite = favourite.equalsIgnoreCase("Y") ? "N" : "Y";
                fmDAO.setFavourite(fmid, favourite);
                fmDAO.close();
                //like.setText(flag);
                like.setImageResource(favourite.equalsIgnoreCase("Y") ? R.drawable.ic_favorite_black_24dp : R.drawable.ic_favorite_border_black_24dp);


            } else {
                mListener.onViewClick(v, position);
            }*/
        }

        public static interface IMyViewHolderClicks {
            public void onButtonClick(Button button, int position);

            public void onViewClick(View view, int position);
        }
    }
}