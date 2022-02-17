package com.android.rftutelage.Calendar;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Objects;

import static java.util.Objects.*;

public class calendarviewadapter extends RecyclerView.Adapter<calendarviewadapter.ViewHolder> {
    Context context;
    ArrayList<calendarevents> calendareventsdata;
    private OnNoteListener mOnnotelistener;

    public calendarviewadapter(Context context, ArrayList<calendarevents> calendareventsdata,OnNoteListener onNoteListener) {
        this.context = context;
        this.calendareventsdata = calendareventsdata;
        this.mOnnotelistener = onNoteListener;
    }

    @NonNull
    @Override
    public calendarviewadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.calendar_event_items, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull calendarviewadapter.ViewHolder holder, int position) {

        calendarevents table = calendareventsdata.get(position);
        holder.cvtitle.setText(table.getTitle());
       // holder.cvlocation.setText(table.getLocation());
        holder.cvdate.setText(table.getDate());
        holder.cvtime.setText(table.getTime());
        //Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.cvimage);

    }


    @Override
    public int getItemCount() {
       return calendareventsdata.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cvtitle,cvlocation,cvdate,cvtime;
        ImageView cvimage;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            cvtitle = (TextView)itemView.findViewById(R.id.cvtitle);
            cvdate = (TextView)itemView.findViewById(R.id.cvdate);
            cvtime = (TextView)itemView.findViewById(R.id.cvtime);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClicked(getAdapterPosition());

        }
    }

    public interface OnNoteListener{
        void onNoteClicked(int position);
    }
}
