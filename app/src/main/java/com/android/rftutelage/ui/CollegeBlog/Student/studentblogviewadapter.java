package com.android.rftutelage.ui.CollegeBlog.Student;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.rftutelage.R;
import com.bumptech.glide.Glide;
import com.mikhaellopez.circularimageview.CircularImageView;

import java.util.ArrayList;


public class studentblogviewadapter extends RecyclerView.Adapter<studentblogviewadapter.ViewHolder>{
    Context context;
    ArrayList<studentblogdata> datalist;
    private studentblogviewadapter.OnNoteListener mOnnotelistener;

    public studentblogviewadapter(Context context, ArrayList<studentblogdata> datalist, studentblogviewadapter.OnNoteListener mOnnotelistener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = mOnnotelistener;
    }

    @NonNull
    @Override
    public studentblogviewadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_student_blog_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull studentblogviewadapter.ViewHolder holder, int position) {

        studentblogdata table = datalist.get(position);
        holder.csbvname.setText(table.getName());
        // holder.cvlocation.setText(table.getLocation());
        holder.csbvdate.setText(table.getDate());
        holder.csbvdepartment.setText(table.getDepartmentandrollno());
        holder.csbvtitle.setText(table.getTitle());
        holder.csbvdescription.setText(table.getDecsription());
        Glide.with(context).load(table.getStudentphoto()).placeholder(R.drawable.diamondshape).into(holder.csbvphoto);
        Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.csbvimage);

    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView csbvname, csbvdepartment, csbvdate, csbvtitle, csbvdescription;
        CircularImageView csbvphoto;
        ImageView csbvimage;
        studentblogviewadapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, studentblogviewadapter.OnNoteListener onNoteListener) {
            super(itemView);
            csbvname = (TextView) itemView.findViewById(R.id.csbvname);
            csbvdepartment = (TextView) itemView.findViewById(R.id.csbvdepartment);
            csbvdate = (TextView) itemView.findViewById(R.id.csbvdate);
            csbvtitle = (TextView) itemView.findViewById(R.id.csbvtitle);
            csbvdate = (TextView) itemView.findViewById(R.id.csbvdate);
            csbvdescription = (TextView) itemView.findViewById(R.id.csbvdescrption);
            csbvimage = (ImageView) itemView.findViewById(R.id.csbvimage);
            csbvphoto = (CircularImageView) itemView.findViewById(R.id.csbvphoto);
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
    public void filterList(ArrayList<studentblogdata> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }
}
