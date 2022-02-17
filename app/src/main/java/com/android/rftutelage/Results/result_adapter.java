package com.android.rftutelage.Results;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.Attendance.attendance_adapter;
import com.android.rftutelage.Attendance.attendance_datas;
import com.android.rftutelage.R;

import java.util.ArrayList;

public class result_adapter extends RecyclerView.Adapter<result_adapter.ViewHolder> {
    Context context;
    ArrayList<results_data> datalist;

    public result_adapter(Context context, ArrayList<results_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public result_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_result_view, parent, false);
        result_adapter.ViewHolder viewHolder = new result_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull result_adapter.ViewHolder holder, int position) {

        results_data table = datalist.get(position);
        holder.cresult_view_subject.setText(table.getPaper_name());
        holder.cresult_view_total.setText(table.getTotal_marks());
        holder.cresult_view_obtained.setText(table.getObtained_marks());
        holder.cresult_view_grade.setText(table.getGrade());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cresult_view_subject, cresult_view_total, cresult_view_obtained, cresult_view_grade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cresult_view_subject = (TextView) itemView.findViewById(R.id.cresult_view_subject);
            cresult_view_total = (TextView) itemView.findViewById(R.id.cresult_view_total);
            cresult_view_obtained = (TextView) itemView.findViewById(R.id.cresult_view_obtained);
            cresult_view_grade = (TextView) itemView.findViewById(R.id.cresult_view_grade);

        }
    }

    public void filterList(ArrayList<results_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}

