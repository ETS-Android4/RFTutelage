package com.android.rftutelage.ui.CollegeBlog.College;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.Calendar.calendarevents;
import com.android.rftutelage.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class collegeblogviewadapter extends RecyclerView.Adapter<collegeblogviewadapter.ViewHolder> {
    Context context;
    ArrayList<collegeblogdata> datalist;
    private OnNoteListener mOnnotelistener;

    public collegeblogviewadapter(Context context, ArrayList<collegeblogdata>  datalist, OnNoteListener onNoteListener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = onNoteListener;
    }

    @NonNull
    @Override
    public collegeblogviewadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_college_blog_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull collegeblogviewadapter.ViewHolder holder, int position) {

        collegeblogdata table = datalist.get(position);
        holder.ccbvtitle.setText(table.getBlogtitle());
       // holder.cvlocation.setText(table.getLocation());
        holder.ccbvdate.setText(table.getBlogdate());
        holder.ccbvdescription.setText(table.getBlogdescription());
        Glide.with(context).load(table.getBlogimage()).placeholder(R.drawable.diamondshape).into(holder.ccbvimage);

    }


    @Override
    public int getItemCount() {
       return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView ccbvtitle,ccbvdate,ccbvdescription;
        ImageView ccbvimage;
        OnNoteListener onNoteListener;
        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListener) {
            super(itemView);
            ccbvtitle = (TextView)itemView.findViewById(R.id.ccbvtitle);
            ccbvdate= (TextView)itemView.findViewById(R.id.ccbvdate);
            ccbvdescription = (TextView)itemView.findViewById(R.id.ccbvdescrption);
            ccbvimage = (ImageView)itemView.findViewById(R.id.ccbvimage);
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
    public void filterList(ArrayList<collegeblogdata> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}
