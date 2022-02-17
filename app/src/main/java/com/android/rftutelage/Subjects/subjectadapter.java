package com.android.rftutelage.Subjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.Calendar.calendarevents;
import com.android.rftutelage.Calendar.calendarviewadapter;
import com.android.rftutelage.R;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class subjectadapter extends RecyclerView.Adapter<subjectadapter.ViewHolder> {
    Context context;
    ArrayList<subjects> datalist;
    private subjectadapter.OnNoteListener mOnnotelistener;

    public subjectadapter(Context context, ArrayList<subjects> datalist, subjectadapter.OnNoteListener onNoteListener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = onNoteListener;
    }

    @NonNull
    @Override
    public subjectadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_subject_view, parent, false);
        subjectadapter.ViewHolder viewHolder = new subjectadapter.ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull subjectadapter.ViewHolder holder, int position) {

        subjects table = datalist.get(position);
        holder.csv_subjectname.setText(table.getPaper_name());
        // holder.cvlocation.setText(table.getLocation());
        if(table.getFaculty1_photo().equals("null")) {

        }else{
            holder.csvfaculty1.setVisibility(View.VISIBLE);
            Glide.with(context).load(table.getFaculty1_photo()).placeholder(R.drawable.diamondshape).into(holder.csvfaculty1);
        }
        if(table.getFaculty2_photo().equals("null")) {

        }else{
            holder.csvfaculty2.setVisibility(View.VISIBLE);
            Glide.with(context).load(table.getFaculty2_photo()).placeholder(R.drawable.diamondshape).into(holder.csvfaculty2);
        }
        if(table.getFaculty3_photo().equals("null")) {

        }else {
            holder.csvfaculty3.setVisibility(View.VISIBLE);
            Glide.with(context).load(table.getFaculty3_photo()).placeholder(R.drawable.diamondshape).into(holder.csvfaculty3);
        }
        //Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.cvimage);

    }


    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView csv_subjectname;
        CircularImageView csvfaculty1,csvfaculty2,csvfaculty3;
        subjectadapter.OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, subjectadapter.OnNoteListener onNoteListener) {
            super(itemView);
            csv_subjectname = (TextView)itemView.findViewById(R.id.csvsubject_name);
            csvfaculty1 = (CircularImageView) itemView.findViewById(R.id.csvfaculty1);
            csvfaculty2 = (CircularImageView) itemView.findViewById(R.id.csvfaculty2);
            csvfaculty3 = (CircularImageView) itemView.findViewById(R.id.csvfaculty3);
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
