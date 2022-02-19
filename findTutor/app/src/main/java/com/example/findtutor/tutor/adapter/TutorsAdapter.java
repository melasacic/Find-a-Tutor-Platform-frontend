package com.example.findtutor.tutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.findtutor.R;
import com.example.findtutor.tutor.model.Tutors;

import java.util.List;

public class TutorsAdapter<T> extends ArrayAdapter<Tutors> {
    private List<Tutors> tutors;
    private Context context;
    private View view;
    private ViewGroup viewGroup;



    public TutorsAdapter(@NonNull Context context, @NonNull List<Tutors> tutors) {
        super(context,0, tutors);
        this.tutors=tutors;
        this.context=context;
    }

    @Override
    public int getCount() {
        int size=tutors.size();
        return  size;
    }

    @Override
    public Tutors getItem(int position) {
        return tutors.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Tutors tutor = getItem(position);

       if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.get_tutor_listview, parent, false);
        }

        TextView tutorFirstName = (TextView) convertView.findViewById(R.id.TutorListViewTextView1);
        TextView tutorLastName = (TextView) convertView.findViewById(R.id.TutorListViewTextView2);

       // Tutors tutors=(Tutors) getItem(position);

        tutorFirstName.setText(tutor.firstName);
        tutorLastName.setText(tutor.lastName);


        return convertView;

    }
}
