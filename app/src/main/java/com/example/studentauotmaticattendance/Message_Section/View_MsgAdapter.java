package com.example.studentauotmaticattendance.Message_Section;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentauotmaticattendance.R;

import java.util.List;

public class View_MsgAdapter extends RecyclerView.Adapter<view_MsgViewHolders> {
    private List<viewobject> viewobjectList;
    private Context context;

    public  View_MsgAdapter(List<viewobject>viewobjectList,Context context)
    {
        this.context=context;
        this.viewobjectList=viewobjectList;
    }

    @NonNull
    @Override
    public view_MsgViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_msg_item,null,false);
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        view_MsgViewHolders rcv=new view_MsgViewHolders((layoutView));
        return rcv;
    }

    @Override
    public void onBindViewHolder(@NonNull view_MsgViewHolders holder, int position) {
        holder.mname.setText(viewobjectList.get(position).getName());
        holder.mmessage.setText(viewobjectList.get(position).getMessage());
    }


    @Override
    public int getItemCount() {
        return viewobjectList.size();
    }
}
