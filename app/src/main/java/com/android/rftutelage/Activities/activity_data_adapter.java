package com.android.rftutelage.Activities;

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

public class activity_data_adapter extends RecyclerView.Adapter<activity_data_adapter.ViewHolder> {
    Context context;
    ArrayList<activity_data> datalist;

    public activity_data_adapter(Context context, ArrayList<activity_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public activity_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_activity_view, parent, false);
        activity_data_adapter.ViewHolder viewHolder = new activity_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull activity_data_adapter.ViewHolder holder, int position) {

        activity_data table = datalist.get(position);
        holder.cacv_activity_name.setText(table.getActivity_name());
        holder.cacvdate.setText(table.getDate());
        if(table.getAttendance().equals("DONE")||table.getAttendance().equals("PRESENT")) {
            holder.cacvattendance.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.cacvattendance.setText(table.getAttendance());
        }else{
            holder.cacvattendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.cacvattendance.setText(table.getAttendance());
        }
        holder.cacvgrade.setText(table.getGrading()+" / "+table.getTotal_grading());
        holder.cacv_subject_name.setText(table.getSubject_name());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cacv_subject_name, cacv_activity_name,cacvdate;
        Button cacvattendance, cacvgrade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cacv_activity_name = (TextView) itemView.findViewById(R.id.cacv_activity_name);
            cacv_subject_name = (TextView) itemView.findViewById(R.id.cacv_subject_name);
            cacvattendance = (Button) itemView.findViewById(R.id.cacvattendance);
            cacvgrade = (Button) itemView.findViewById(R.id.cacvgrade);
            cacvdate = (TextView) itemView.findViewById(R.id.cacvdate);
        }
    }

    public void filterList(ArrayList<activity_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}

