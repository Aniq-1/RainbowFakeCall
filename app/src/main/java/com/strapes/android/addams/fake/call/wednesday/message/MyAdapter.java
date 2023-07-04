package com.strapes.android.addams.fake.call.wednesday.message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;


import com.strapes.android.addams.fake.call.R;

import java.util.List;

public class MyAdapter extends PagerAdapter {

    List<Integer> list;
    MyAdapter(List<Integer> list){

        this.list=list;
    }


    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view==object;



    }

    @Override
    public int getCount() {
        return 3;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view= LayoutInflater.from(container.getContext()).inflate(R.layout.imagelayout,container,false);
        ImageView imageView=view.findViewById(R.id.htu_image);

        imageView.setImageResource((list.get(position)));
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }
}
