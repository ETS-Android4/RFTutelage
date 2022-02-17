package com.android.rftutelage.Staff;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.Faculty.facultydata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogdata;
import com.android.rftutelage.ui.CollegeBlog.Faculty.facultyblogviewadapter;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class staff_data_adapter extends RecyclerView.Adapter<staff_data_adapter.ViewHolder> {
    Context context;
    ArrayList<staff_data> datalist;
    private staff_data_adapter.OnNoteListener mOnnotelistener;

    public staff_data_adapter(Context context, ArrayList<staff_data> datalist, staff_data_adapter.OnNoteListener mOnnotelistener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = mOnnotelistener;
    }

    @NonNull
    @Override
    public staff_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_staff_view, parent, false);
        staff_data_adapter.ViewHolder viewHolder = new staff_data_adapter.ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull staff_data_adapter.ViewHolder holder, int position) {

        staff_data table = datalist.get(position);
        holder.csvname.setText(table.getFaculty_name());
        holder.csvemail.setText(table.getEmail());
        holder.csv_phone_number.setText(table.getPhone_no());
        if (table.getFaculty_image().equals("null")) {

        } else {
            holder.csvimage.setVisibility(View.VISIBLE);
            Glide.with(context).load(table.getFaculty_image()).placeholder(R.drawable.diamondshape).into(holder.csvimage);
        }

    }

    @Override
    public int getItemCount() {
        return datalist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView csvname, csvemail,csv_phone_number;
        CircularImageView csvimage;
        staff_data_adapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, staff_data_adapter.OnNoteListener onNoteListener) {
            super(itemView);
            csvname = (TextView) itemView.findViewById(R.id.csvname);
            csvemail = (TextView) itemView.findViewById(R.id.csv_email);
            csv_phone_number = (TextView) itemView.findViewById(R.id.csv_phone_number);
            csvimage = (CircularImageView) itemView.findViewById(R.id.csvimage);
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

    public void filterList(ArrayList<staff_data> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }

}
