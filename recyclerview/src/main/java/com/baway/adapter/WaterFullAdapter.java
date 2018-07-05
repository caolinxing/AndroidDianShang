package com.baway.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.baway.holder.SecondHolder;
import com.baway.recyclerview.R;
import com.baway.recyclerview.SecondActivity;

import java.util.List;

public class WaterFullAdapter extends RecyclerView.Adapter<SecondHolder>{
    private Context context;
    private List<String> list;
    List<Integer> heightList;
    public WaterFullAdapter(Context context, List<String> list) {
        this.context = context;
        this.list = list;
        //随机300-400的高度
        for (int i = 0; i <list.size() ; i++) {
            heightList.add((int)(Math.random()*100+300));
        }
    }

    @NonNull
    @Override
    public SecondHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SecondHolder(View.inflate(context, R.layout.first_item,null));
    }

    @Override
    public void onBindViewHolder(@NonNull SecondHolder holder, int position) {
        //动态修改高度
        ViewGroup.LayoutParams params = holder.textView.getLayoutParams();
        params.height = heightList.get(position);
        holder.textView.setText(list.get(position));
        holder.textView.setBackgroundColor(Color.rgb((int)Math.random()*100,(int)Math.random()*100,(int)Math.random()*100));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
