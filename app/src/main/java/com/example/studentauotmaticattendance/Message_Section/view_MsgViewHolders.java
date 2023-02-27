package com.example.studentauotmaticattendance.Message_Section;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentauotmaticattendance.R;

public class view_MsgViewHolders extends RecyclerView.ViewHolder implements View.OnClickListener {
    public TextView mname,mmessage;
    public view_MsgViewHolders(@NonNull View itemView) {
        super(itemView);
        mname=itemView.findViewById(R.id.name);
        mmessage=itemView.findViewById(R.id.message);
    }

    @Override
    public void onClick(View v) {

    }
}
