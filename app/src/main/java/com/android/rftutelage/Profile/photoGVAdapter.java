package com.android.rftutelage.Profile;

    import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

    import com.android.rftutelage.Profile.photo_model;
    import com.android.rftutelage.R;

    import java.util.ArrayList;

    public class photoGVAdapter extends ArrayAdapter<photo_model> {
        public photoGVAdapter(@NonNull Context context, ArrayList<photo_model> photoModelArrayList) {
            super(context, 0, photoModelArrayList);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            View listitemView = convertView;
            if (listitemView == null) {
                // Layout Inflater inflates each item to be displayed in GridView.
                listitemView = LayoutInflater.from(getContext()).inflate(R.layout.card_item, parent, false);
            }
            photo_model photo_model = getItem(position);
            TextView courseTV = listitemView.findViewById(R.id.idTVtext);
            ImageView courseIV = listitemView.findViewById(R.id.idIVphoto);
            courseTV.setText(photo_model.getPhoto_name());
            courseIV.setImageResource(photo_model.getImgid());
            return listitemView;
        }
    }

