package com.android.rftutelage.ui.Meeting;

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
import com.android.rftutelage.Subjects.Assignment.assignment_data_adapter;

import java.util.ArrayList;

public class meeting_data_adapter extends RecyclerView.Adapter<meeting_data_adapter.ViewHolder> {
    Context context;
    ArrayList<meeting_data> datalist;

    public meeting_data_adapter(Context context, ArrayList<meeting_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public meeting_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_meeting_view, parent, false);
        meeting_data_adapter.ViewHolder viewHolder = new meeting_data_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull meeting_data_adapter.ViewHolder holder, int position) {

        meeting_data table = datalist.get(position);
        holder.cmvtitle.setText(table.getTitle());
        holder.cmvdate.setText(table.getDate());
        holder.cmvplace.setText(table.getPlace());
        holder.cmvtime.setText(table.getTime());

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cmvtitle, cmvdate, cmvtime;
        Button cmvplace;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cmvtitle = (TextView) itemView.findViewById(R.id.cmvtitle);
            cmvdate = (TextView) itemView.findViewById(R.id.cmvdate);
            cmvtime = (TextView) itemView.findViewById(R.id.cmvtime);
            cmvplace = (Button) itemView.findViewById(R.id.cmvplace);
        }
    }

    public void filterList(ArrayList<meeting_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}

