package com.android.rftutelage.Subjects.Marks;

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

public class marks_data_adapter extends RecyclerView.Adapter<marks_data_adapter.ViewHolder> {
    Context context;
    ArrayList<marks_data> datalist;

    public marks_data_adapter(Context context, ArrayList<marks_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public marks_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_marks_view, parent, false);
        marks_data_adapter.ViewHolder viewHolder = new marks_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull marks_data_adapter.ViewHolder holder, int position) {

        marks_data table = datalist.get(position);
        holder.cmvassignmnetname.setText(table.getActivity_name());
        holder.cmvdate.setText(table.getDate());
        if(table.getAttendance().equals("PRESENT")) {
            holder.cmvattendance.setText(table.getAttendance());
        }else{
            holder.cmvattendance.setBackgroundColor(Color.parseColor("#750000"));
            holder.cmvattendance.setText(table.getAttendance());
        }
        holder.cmvgrade.setText(table.getGrading()+" / "+table.getTotal_grading());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cmvassignmnetname, cmvdate;
        Button cmvattendance, cmvgrade;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cmvassignmnetname = (TextView) itemView.findViewById(R.id.cmvinternalassessment_name);
            cmvdate = (TextView) itemView.findViewById(R.id.cmvdate);
            cmvattendance = (Button) itemView.findViewById(R.id.cmvattendance);
            cmvgrade = (Button) itemView.findViewById(R.id.cmvgrade);
        }
    }
}
