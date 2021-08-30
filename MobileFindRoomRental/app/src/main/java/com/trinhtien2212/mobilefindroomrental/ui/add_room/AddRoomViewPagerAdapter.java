package com.trinhtien2212.mobilefindroomrental.ui.add_room;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class AddRoomViewPagerAdapter extends FragmentStateAdapter {


    public AddRoomViewPagerAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new AddRoomAddressFragment();

            case 1: return new AddRoomInfoFragment();

            case 2: return new AddRoomUtilityFragment();


        }
        return  new AddRoomAddressFragment();
    }

    @Override
    public int getItemCount() {
        return 3;
    }


}
