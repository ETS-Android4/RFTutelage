package com.android.rftutelage.Reportleave.History;

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
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class History_data_adapter extends RecyclerView.Adapter<History_data_adapter.ViewHolder> {
    Context context;
    ArrayList<history_data> datalist;
    private History_data_adapter.OnNoteListener mOnnotelistener;

    public History_data_adapter(Context context, ArrayList<history_data> datalist, History_data_adapter.OnNoteListener mOnnotelistener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = mOnnotelistener;
    }

    @NonNull
    @Override
    public History_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_history_data, parent, false);
        History_data_adapter.ViewHolder viewHolder = new History_data_adapter.ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull History_data_adapter.ViewHolder holder, int position) {

        history_data table = datalist.get(position);
        holder.history_date.setText(table.getDate());
        holder.history_subject.setText(table.getSubject());
        holder.history_duration.setText(table.getDuration());
        holder.history_from_to_date.setText(table.getFrom_date()+" - "+table.getTo_date());
        if(table.getStatus().equals("ACCEPTED")) {
            holder.history_staus.setBackgroundColor(Color.parseColor("#25CC84"));
            holder.history_staus.setText(table.getStatus());
        }else{
            holder.history_staus.setBackgroundColor(Color.parseColor("#750000"));
            holder.history_staus.setText(table.getStatus());
        }

    }

    @Override
    public int getItemCount() {
        return datalist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView history_date,history_subject,history_duration,history_from_to_date;
        Button history_staus;
        History_data_adapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, History_data_adapter.OnNoteListener onNoteListener) {
            super(itemView);
            history_date = (TextView) itemView.findViewById(R.id.history_date);
            history_subject = (TextView) itemView.findViewById(R.id.history_subject);
            history_duration = (TextView) itemView.findViewById(R.id.history_duration);
            history_from_to_date = (TextView) itemView.findViewById(R.id.history_from_to_date);
            history_staus = (Button) itemView.findViewById(R.id.history_status);
            this.onNoteListener = onNoteListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClicked(getAdapterPosition());
        }
    }
    public interface OnNoteListener{
        void onNoteClicked(int position);

    }

    public void filterList(ArrayList<history_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }

}



