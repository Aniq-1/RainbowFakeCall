package com.strapes.android.addams.fake.call.wednesday.message.adapter;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import com.strapes.android.addams.fake.call.wednesday.message.activities.fragments.FirstFragment;
import com.strapes.android.addams.fake.call.wednesday.message.activities.fragments.SecondFragment;
import com.strapes.android.addams.fake.call.wednesday.message.activities.fragments.ThirdFragment;


public class MyViewPagerAdapter extends FragmentStateAdapter {

    public MyViewPagerAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new FirstFragment();
            case 1: return new SecondFragment();
            case 2: return new ThirdFragment();
            default: return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
