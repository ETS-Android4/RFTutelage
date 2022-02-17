package com.android.rftutelage.Subjects.Attendance;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Assignment.assignment_data;
import com.android.rftutelage.Subjects.Marks.marks_data;
import com.android.rftutelage.Subjects.Marks.marks_data_adapter;

import java.util.ArrayList;

public class attendance_data_adapter extends RecyclerView.Adapter<attendance_data_adapter.ViewHolder> {
    Context context;
    ArrayList<attendance_data> datalist;

    public attendance_data_adapter(Context context, ArrayList<attendance_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public attendance_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_attendance_view, parent, false);
        attendance_data_adapter.ViewHolder viewHolder = new attendance_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull attendance_data_adapter.ViewHolder holder, int position) {

        attendance_data table = datalist.get(position);
        holder.catvdate.setText(table.getDate());
        if(table.getAttendance().equals("PRESENT")) {
            holder.catvattendance.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.catvattendance.setText(table.getAttendance());
        }else if(table.getAttendance().equals("ABSENT")){
            holder.catvattendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.catvattendance.setText(table.getAttendance());
        }

        holder.catvtime.setText(table.getTime());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView catvdate;
        Button catvattendance, catvtime;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            catvdate = (TextView) itemView.findViewById(R.id.catvdate);
            catvattendance = (Button) itemView.findViewById(R.id.catvattendance);
            catvtime = (Button) itemView.findViewById(R.id.catvtime);
        }
    }

    public void filterList(ArrayList<attendance_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }

}

