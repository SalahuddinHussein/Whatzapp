package com.smiledwatermelon.whatzapp;

/**
 * Created by salahuddin on 3/4/18.
 */

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;



public class ChatListAdapter extends BaseAdapter {


    private Activity mActivity;
    private DatabaseReference mDatabaseReference;
    private String mDisplayName;
    private ArrayList<DataSnapshot> mDataSnapshots;

    private ChildEventListener mListener=new ChildEventListener() {
        @Override
        public void onChildAdded(DataSnapshot dataSnapshot, String s) {

            mDataSnapshots.add(dataSnapshot);
            notifyDataSetChanged();
        }

        @Override
        public void onChildChanged(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onChildRemoved(DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(DataSnapshot dataSnapshot, String s) {

        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }
    };


    public ChatListAdapter(Activity activity, DatabaseReference databaseReference, String displayName) {
        mActivity = activity;
        mDatabaseReference = databaseReference.child("messages");
        mDisplayName = displayName;

        mDatabaseReference.addChildEventListener(mListener);

        mDataSnapshots=new ArrayList<>();


    }

    static class ViewHolder{
        TextView authorName;
        TextView body;
        LinearLayout.LayoutParams mParams;

    }

    @Override
    public int getCount() {
        return mDataSnapshots.size();
    }

    @Override
    public InstantMessage getItem(int position) {

        DataSnapshot snapshot=mDataSnapshots.get(position);

        return snapshot.getValue(InstantMessage.class);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if(convertView==null){

            LayoutInflater inflater= (LayoutInflater) mActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView=inflater.inflate(R.layout.chat_msg_row,parent,false);

            final ViewHolder holder=new ViewHolder();
            holder.authorName=convertView.findViewById(R.id.author);
            holder.body=convertView.findViewById(R.id.message);

            holder.mParams= (LinearLayout.LayoutParams) holder.authorName.getLayoutParams();
            convertView.setTag(holder);

        }

        final InstantMessage instantMessage=getItem(position);
        final ViewHolder holder= (ViewHolder) convertView.getTag();

        String author=instantMessage.getAuthor();
        holder.authorName.setText(author);
        String msg=instantMessage.getMessage();
        holder.body.setText(msg);

        return convertView;
    }
    public void cleanUp(){
        mDatabaseReference.removeEventListener(mListener);
    }
}
