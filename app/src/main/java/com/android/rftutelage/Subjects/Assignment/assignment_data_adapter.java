package com.android.rftutelage.Subjects.Assignment;

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

public class assignment_data_adapter extends RecyclerView.Adapter<assignment_data_adapter.ViewHolder> {
    Context context;
    ArrayList<assignment_data> datalist;

    public assignment_data_adapter(Context context, ArrayList<assignment_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public assignment_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_assignment_view, parent, false);
        assignment_data_adapter.ViewHolder viewHolder = new assignment_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull assignment_data_adapter.ViewHolder holder, int position) {

        assignment_data table = datalist.get(position);
        holder.cavassignmnetname.setText(table.getActivity_name());
        holder.cavdate.setText(table.getDate());
        if(table.getAttendance().equals("DONE")) {
            holder.cavattendance.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.cavattendance.setText(table.getAttendance());
        }else{
            holder.cavattendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.cavattendance.setText(table.getAttendance());
        }
        holder.cavgrade.setText(table.getGrading()+" / "+table.getTotal_grading());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cavassignmnetname, cavdate;
        Button cavattendance, cavgrade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cavassignmnetname = (TextView) itemView.findViewById(R.id.cavassignment_name);
            cavdate = (TextView) itemView.findViewById(R.id.cavdate);
            cavattendance = (Button) itemView.findViewById(R.id.cavattendance);
            cavgrade = (Button) itemView.findViewById(R.id.cavgrade);
        }
    }

    public void filterList(ArrayList<assignment_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}
