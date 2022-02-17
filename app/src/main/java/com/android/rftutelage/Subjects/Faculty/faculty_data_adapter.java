package com.android.rftutelage.Subjects.Faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.android.rftutelage.Subjects.subjectadapter;
import com.android.rftutelage.Subjects.subjects;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;

public class faculty_data_adapter extends RecyclerView.Adapter<faculty_data_adapter.ViewHolder> {
    Context context;
    ArrayList<facultydata> datalist;
    private faculty_data_adapter.OnNoteListener mOnnotelistener;

    public faculty_data_adapter(Context context, ArrayList<facultydata> datalist, faculty_data_adapter.OnNoteListener onNoteListener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = onNoteListener;
    }

    @NonNull
    @Override
    public faculty_data_adapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_faculty_data_view, parent, false);
        faculty_data_adapter.ViewHolder viewHolder = new faculty_data_adapter.ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull faculty_data_adapter.ViewHolder holder, int position) {

        facultydata table = datalist.get(position);
        holder.cfdvname.setText(table.getFaculty_name());
        holder.cfdvpost.setText(table.getPost());
        holder.cfdvqualification.setText(table.getQualification());
        holder.cfdvareaofexpertise.setText(table.getArea_of_expertise());
        holder.cfdvemail.setText(table.getEmail());
        holder.cfdvphonenumber.setText(table.getPhone_no());

        if (table.getFaculty_image().equals("null")) {

        } else {
            holder.cfdvphoto.setVisibility(View.VISIBLE);
            Glide.with(context).load(table.getFaculty_image()).placeholder(R.drawable.diamondshape).into(holder.cfdvphoto);
        }
        if (table.getAcademic_experience().equals("null")) {

        } else {
            holder.cfdvacademicexperience.setText(table.getAcademic_experience());
        }
        if (table.getCourses_taught().equals("null")) {

        } else {
            //holder.csvfaculty3.setVisibility(View.VISIBLE);
            holder.cfdvcoursestaught.setText(table.getCourses_taught());
        }
        //Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.cvimage);
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cfdvname,cfdvpost,cfdvqualification,cfdvcoursestaught,cfdvareaofexpertise,cfdvemail,cfdvphonenumber,cfdvacademicexperience;
        CircularImageView cfdvphoto;
        ImageView cfdvphonecall;
        faculty_data_adapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, faculty_data_adapter.OnNoteListener onNoteListener) {
            super(itemView);
            cfdvname = (TextView) itemView.findViewById(R.id.cfdvname);
            cfdvpost = (TextView) itemView.findViewById(R.id.cfdvpost);
            cfdvqualification = (TextView) itemView.findViewById(R.id.cfdvqualification);
            cfdvcoursestaught = (TextView) itemView.findViewById(R.id.cfdvcoursestaught);
            cfdvareaofexpertise = (TextView) itemView.findViewById(R.id.cfdvareaofexpertise);
            cfdvacademicexperience = (TextView) itemView.findViewById(R.id.cfdvacademicexperience);
            cfdvemail = (TextView) itemView.findViewById(R.id.cfdvemail);
            cfdvphonenumber = (TextView) itemView.findViewById(R.id.cfdvphone_number);
            cfdvphoto = (CircularImageView) itemView.findViewById(R.id.cfdvphoto);
            cfdvphonecall = (ImageView) itemView.findViewById(R.id.cfdvphonecall);
            this.onNoteListener = onNoteListener;
            cfdvphonecall.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClicked(getAdapterPosition());

        }
    }

    public interface OnNoteListener {
        void onNoteClicked(int position);
    }
}