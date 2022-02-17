package com.android.rftutelage.Subjects.Remarks;

import android.content.Context;
import android.graphics.Color;
import android.os.FileUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.assignment_data;
import com.android.rftutelage.Subjects.Assignment.assignment_data_adapter;

import java.util.ArrayList;

public class remarks_data_adapter extends RecyclerView.Adapter<remarks_data_adapter.ViewHolder> {
    Context context;
    ArrayList<remarks_data> datalist;

    public remarks_data_adapter(Context context, ArrayList<remarks_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public remarks_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_remarks_view, parent, false);
        remarks_data_adapter.ViewHolder viewHolder = new remarks_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull remarks_data_adapter.ViewHolder holder, int position) {

        remarks_data table = datalist.get(position);
        if(table.getPerformance().equals("BAD")){
            holder.crvperformance.setText(table.getPerformance());
            holder.crvprogressbar.setProgress(25);
        }else if(table.getPerformance().equals("AVERAGE")){
            holder.crvperformance.setText(table.getPerformance());
            holder.crvprogressbar.setProgress(50);
        }else if(table.getPerformance().equals("GOOD")){
            holder.crvperformance.setText(table.getPerformance());
            holder.crvprogressbar.setProgress(75);
        }else if(table.getPerformance().equals("EXCELLENT")){
            holder.crvperformance.setText(table.getPerformance());
            holder.crvprogressbar.setProgress(100);
        }

        holder.crvdate.setText(table.getDate());
       // if(table.getAttendance().equals("DONE")) {
       //     holder.cavattendance.setText(table.getAttendance());
       // }else{
       //     holder.cavattendance.setBackgroundColor(Color.parseColor("#750000"));
       //     holder.cavattendance.setText(table.getAttendance());
      //  }
        holder.crvfeedback.setText(table.getFeedback());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView crvperformance, crvdate,crvfeedback ;
        ProgressBar crvprogressbar;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            crvperformance = (TextView) itemView.findViewById(R.id.crvperformance);
            crvdate = (TextView) itemView.findViewById(R.id.crvdate);
            crvfeedback = (TextView) itemView.findViewById(R.id.crvfeedback);
            crvprogressbar = (ProgressBar) itemView.findViewById(R.id.crvprogressbar);
        }
    }

    public void filterList(ArrayList<remarks_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}
