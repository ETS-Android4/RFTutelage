package com.android.rftutelage.Subjects;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.rftutelage.R;

import java.util.ArrayList;

public class semsterGVadapter extends ArrayAdapter<semestermodel> {
    public semsterGVadapter(@NonNull Context context, ArrayList<semestermodel> semestermodelArrayList) {
        super(context, 0, semestermodelArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listitemView = convertView;
        if (listitemView == null) {
            // Layout Inflater inflates each item to be displayed in GridView.
            listitemView = LayoutInflater.from(getContext()).inflate(R.layout.semesternumber, parent, false);
        }
        semestermodel courseModel = getItem(position);
        TextView courseTV = listitemView.findViewById(R.id.idTVSemester);
        courseTV.setText(courseModel.getSemster());
        return listitemView;
    }
}
