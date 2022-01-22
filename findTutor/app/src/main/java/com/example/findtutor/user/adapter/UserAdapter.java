package com.example.findtutor.user.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.findtutor.R;
import com.example.findtutor.tutor.model.Tutors;
import com.example.findtutor.user.model.User;

import java.util.List;

public class UserAdapter<T> extends ArrayAdapter<User> {
    private List<User> users;
    private Context context;
    private View view;
    private ViewGroup viewGroup;

   /* public TutorsAdapter( List<Tutors> tutors){
        this.tutors=tutors;
    }*/

    public UserAdapter(@NonNull Context context, @NonNull List<User> users) {
        super(context, 0, users);
        this.users = users;
        this.context = context;
    }

    @Override
    public int getCount() {
        int size = users.size();
        return size;
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.get_user_listview, parent, false);
        }

        TextView userFirstName = (TextView) convertView.findViewById(R.id.UserListViewTextView1);
        TextView userLastName = (TextView) convertView.findViewById(R.id.UserListViewTextView2);

        userFirstName.setText(user.firstName);
        userLastName.setText(user.lastName);

        return convertView;

    }
}
