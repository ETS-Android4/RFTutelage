package com.android.rftutelage.Subjects.test;

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
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdata;

import java.util.ArrayList;

public class test_data_adapter extends RecyclerView.Adapter<test_data_adapter.ViewHolder> {
    Context context;
    ArrayList<test_data> datalist;

    public test_data_adapter(Context context, ArrayList<test_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public test_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_test_view, parent, false);
        test_data_adapter.ViewHolder viewHolder = new test_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull test_data_adapter.ViewHolder holder, int position) {

        test_data table = datalist.get(position);
        holder.ctvtestname.setText(table.getActivity_name());
        holder.ctvdate.setText(table.getDate());
        if(table.getAttendance().equals("PRESENT")) {
            holder.ctvattendance.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.ctvattendance.setText(table.getAttendance());
        }else if(table.getAttendance().equals("ABSENT")){
            holder.ctvattendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.ctvattendance.setText(table.getAttendance());
        }
        holder.ctvgrade.setText(table.getGrading()+" / "+table.getTotal_grading());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView ctvtestname, ctvdate;
        Button ctvattendance, ctvgrade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ctvtestname = (TextView) itemView.findViewById(R.id.ctvtest_name);
            ctvdate = (TextView) itemView.findViewById(R.id.ctvdate);
            ctvattendance = (Button) itemView.findViewById(R.id.ctvattendance);
            ctvgrade = (Button) itemView.findViewById(R.id.ctvgrade);
        }
    }

    public void filterList(ArrayList<test_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}