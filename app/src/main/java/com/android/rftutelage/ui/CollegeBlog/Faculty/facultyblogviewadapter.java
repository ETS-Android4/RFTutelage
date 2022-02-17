package com.android.rftutelage.ui.CollegeBlog.Faculty;

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

public class facultyblogviewadapter extends RecyclerView.Adapter<facultyblogviewadapter.ViewHolder> {
    Context context;
    ArrayList<facultyblogdata> datalist;
    private facultyblogviewadapter.OnNoteListener mOnnotelistener;

    public facultyblogviewadapter(Context context, ArrayList<facultyblogdata> datalist, facultyblogviewadapter.OnNoteListener mOnnotelistener) {
        this.context = context;
        this.datalist = datalist;
        this.mOnnotelistener = mOnnotelistener;
    }

    @NonNull
    @Override
    public facultyblogviewadapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.custom_faculty_blog_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view, mOnnotelistener);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull facultyblogviewadapter.ViewHolder holder, int position) {

        facultyblogdata table = datalist.get(position);
        holder.cfbvname.setText(table.getName());
        // holder.cvlocation.setText(table.getLocation());
        holder.cfbvdate.setText(table.getDate());
        holder.cfbvdepartment.setText(table.getDepartment());
        holder.cfbvtitle.setText(table.getTitle());
        holder.cfbvdescription.setText(table.getDescription());
        Glide.with(context).load(table.getFacultyphoto()).placeholder(R.drawable.diamondshape).into(holder.cfbvphoto);
        Glide.with(context).load(table.getImage()).placeholder(R.drawable.diamondshape).into(holder.cfbvimage);


    }

    @Override
    public int getItemCount() {
        return datalist.size();

    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView cfbvname, cfbvdepartment, cfbvdate, cfbvtitle, cfbvdescription;
        CircularImageView cfbvphoto;
        ImageView cfbvimage;
        facultyblogviewadapter.OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, facultyblogviewadapter.OnNoteListener onNoteListener) {
            super(itemView);
            cfbvname = (TextView) itemView.findViewById(R.id.cfbvname);
            cfbvdepartment = (TextView) itemView.findViewById(R.id.cfbvdepartment);
            cfbvdate = (TextView) itemView.findViewById(R.id.cfbvdate);
            cfbvtitle = (TextView) itemView.findViewById(R.id.cfbvtitle);
            cfbvdate = (TextView) itemView.findViewById(R.id.cfbvdate);
            cfbvdescription = (TextView) itemView.findViewById(R.id.cfbvdescrption);
            cfbvimage = (ImageView) itemView.findViewById(R.id.cfbvimage);
            cfbvphoto = (CircularImageView) itemView.findViewById(R.id.cfbvphoto);
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
    public void filterList(ArrayList<facultyblogdata> filteredList) {
        datalist = filteredList;
        notifyDataSetChanged();
    }

    }

