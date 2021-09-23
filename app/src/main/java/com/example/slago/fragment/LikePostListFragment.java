package com.example.slago.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.slago.R;

public class LikePostListFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater , ViewGroup container, Bundle savedInstanceState){
        View view=LayoutInflater.from(getContext()).inflate(R.layout.fragment_likepostlist,container,false);
        return view;
    }
}
