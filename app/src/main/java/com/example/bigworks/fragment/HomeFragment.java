package com.example.bigworks.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.bigworks.R;
import com.example.bigworks.achievement.AchievementPageActivity;
import com.example.bigworks.more.MorePageActivity;
import com.example.bigworks.persondata.PersonDataPageActivity;

public class HomeFragment extends Fragment {
    @Override
    public View onCreateView( LayoutInflater inflater,  ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_homepage,container,false);
    }
    private void binActionForElement(){
        Activity mainActivity=getActivity();
        //个人信息按钮
        TextView textView=mainActivity.findViewById(R.id.homepage_persondata);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), PersonDataPageActivity.class);
                startActivity(intent);
            }
        });
        //成就按钮跳转
        textView=mainActivity.findViewById(R.id.homepage_achive);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), AchievementPageActivity.class);
                startActivity(intent);
            }
        });
        //更多按钮跳转
        textView=mainActivity.findViewById(R.id.homepage_more);
        textView.setClickable(true);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(getContext(), MorePageActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binActionForElement();
    }

}
