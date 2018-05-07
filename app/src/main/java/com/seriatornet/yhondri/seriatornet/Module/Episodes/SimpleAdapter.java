package com.seriatornet.yhondri.seriatornet.Module.Episodes;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.seriatornet.yhondri.seriatornet.Model.DataBase.Episode.Episode;
import com.seriatornet.yhondri.seriatornet.Model.DataBase.Show.Show;
import com.seriatornet.yhondri.seriatornet.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.SimpleViewHolder> {

    private final Context mContext;
    private List<Episode> mData;

    public List<Episode> getmData() {
        return mData;
    }

    public SimpleAdapter(Context context, List<Episode> data) {
        mContext = context;
        if (data == null) {
            mData = new ArrayList<>();
        } else {
            this.mData = data;
        }
    }

    public void add(Episode episode, int position) {
        position = position == -1 ? getItemCount()  : position;
        mData.add(position, episode);
        notifyItemInserted(position);
    }

    public void remove(int position){
        if (position < getItemCount()  ) {
            mData.remove(position);
            notifyItemRemoved(position);
        }
    }

    public static class SimpleViewHolder extends RecyclerView.ViewHolder {
        public final TextView title;

        public SimpleViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.episodeNameTextView);
        }
    }

    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(mContext).inflate(R.layout.row_episode, parent, false);
        return new SimpleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, final int position) {
        Episode episode = mData.get(position);
        holder.title.setText(episode.getNumber() + " - " + episode.getTitle());
        holder.title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(mContext,"Position ="+position,Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public Episode getItemAt(int position) {
        return mData.get(position -1);
    }
}