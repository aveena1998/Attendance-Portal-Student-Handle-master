package com.example.studentauotmaticattendance.Teacher_Section.View_Teacher;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.studentauotmaticattendance.R;

import java.util.List;

public class view_TeacherAdapter extends RecyclerView.Adapter<view_TeacherViewHolder> {
    private List<viewteacherobject> viewteacherobjectList;
    private Context context;


    public view_TeacherAdapter(List<viewteacherobject> viewteacherobjectList, Context context)
    {
        this.viewteacherobjectList = viewteacherobjectList;
        this.context=context;
    }
    @NonNull
    @Override
    public view_TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View layoutView= LayoutInflater.from(parent.getContext()).inflate(R.layout.view_teacher_item,null,false);
        RecyclerView.LayoutParams lp =new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutView.setLayoutParams(lp);
        view_TeacherViewHolder rcv=new view_TeacherViewHolder((layoutView));
        return rcv;

    }

    @Override
    public void onBindViewHolder(@NonNull view_TeacherViewHolder holder, int position) {
        holder.mname.setText(viewteacherobjectList.get(position).getName());
        if(!viewteacherobjectList.get(position).getProfilepic().equals("default"))
        {
            Glide.with(context).load(viewteacherobjectList.get(position).getProfilepic()).into(holder.mprofilepic);
        }else{
            Glide.with(context).load(R.mipmap.profile).into(holder.mprofilepic);
        }
        holder.mdepartment.setText(viewteacherobjectList.get(position).getDepartment());
        holder.mphone.setText(viewteacherobjectList.get(position).getPhone());
        holder.memail.setText(viewteacherobjectList.get(position).getEmail());

    }

    @Override
    public int getItemCount() {
        return viewteacherobjectList.size();
    }
}
