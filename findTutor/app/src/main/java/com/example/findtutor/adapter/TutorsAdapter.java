package com.example.findtutor.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.findtutor.R;
import com.example.findtutor.model.Tutors;

import java.util.ArrayList;

public class TutorsAdapter<T> extends ArrayAdapter<Tutors> {


    public TutorsAdapter(Context context, ArrayList<Tutors> tutors){
        super(context,0,tutors);
    }

    public View getView(int position, View convertView, ViewGroup parent){

        Tutors tutor = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate
                    (R.layout.get_tutor_listview, parent, false);
        }

        TextView tutorFirstName = (TextView) convertView.findViewById(R.id.TutorListViewTextView1);
        TextView tutorLastName = (TextView) convertView.findViewById(R.id.TutorListViewTextView2);

        tutorFirstName.setText(tutor.firstName);
        tutorLastName.setText(tutor.lastName);


        return convertView;

    }
}
