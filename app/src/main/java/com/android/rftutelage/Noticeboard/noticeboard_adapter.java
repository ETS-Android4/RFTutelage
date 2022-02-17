package com.android.rftutelage.Noticeboard;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.rftutelage.R;
import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class noticeboard_adapter extends RecyclerView.Adapter<noticeboard_adapter.ViewHolder> {
    Context context;
    ArrayList<noticeboard_data> datalist;

    public noticeboard_adapter(Context context, ArrayList<noticeboard_data> datalist) {
        this.context = context;
        this.datalist = datalist;
    }

    @NonNull
    @Override
    public noticeboard_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_notice_board_view, parent, false);
        noticeboard_adapter.ViewHolder viewHolder = new noticeboard_adapter.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull noticeboard_adapter.ViewHolder holder, int position) {

        noticeboard_data table = datalist.get(position);
        holder.cnbdate.setText(table.getDate());
        Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.cnbimage);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView cnbdate;
        ImageView cnbimage;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cnbdate = (TextView) itemView.findViewById(R.id.cnbdate);
            cnbimage = (ImageView) itemView.findViewById(R.id.cnbimage);
        }
    }

    public void filterList(ArrayList<noticeboard_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}
