package site.linkway.slago.postpage;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class TabFragmentPagerAdapter extends FragmentStateAdapter {
    private List<Fragment> mlist;

    public TabFragmentPagerAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle,List<Fragment> list) {
        super(fragmentManager, lifecycle);
        this.mlist=list;
    }


    @NonNull
    @NotNull
    @Override
    public Fragment createFragment(int position) {
        return mlist.get(position);
    }

    @Override
    public int getItemCount() {
        return mlist.size();
    }
}
