package com.android.rftutelage.Attendance;

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
import java.util.ArrayList;

public class attendance_adapter extends RecyclerView.Adapter<attendance_adapter.ViewHolder> {
    Context context;
    ArrayList<attendance_datas> datalist;

    public attendance_adapter(Context context, ArrayList<attendance_datas> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public attendance_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_main_attendance_view, parent, false);
        attendance_adapter.ViewHolder viewHolder = new attendance_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull attendance_adapter.ViewHolder holder, int position) {

        attendance_datas table = datalist.get(position);
        holder.cmav_paper_name.setText(table.getPaper_name());
        holder.cmav_date.setText(table.getDate());
        if(table.getAttendance().equals("PRESENT")) {
            holder.cmav_attendance.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.cmav_attendance.setText(table.getAttendance());
        }else{
            holder.cmav_attendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.cmav_attendance.setText(table.getAttendance());
        }
        holder.cmav_time.setText(table.getTime());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cmav_paper_name, cmav_date;
        Button cmav_time, cmav_attendance;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cmav_paper_name = (TextView) itemView.findViewById(R.id.cmavpaper_name);
            cmav_date = (TextView) itemView.findViewById(R.id.cmav_date);
            cmav_attendance = (Button) itemView.findViewById(R.id.cmav_attendance);
            cmav_time = (Button) itemView.findViewById(R.id.cmav_time);
        }
    }

    public void filterList(ArrayList<attendance_datas> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}

