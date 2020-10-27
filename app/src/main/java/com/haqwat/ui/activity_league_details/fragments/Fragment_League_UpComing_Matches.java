package com.haqwat.ui.activity_league_details.fragments;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.haqwat.R;
import com.haqwat.databinding.FragmentLeagueUpcomingMatchesBinding;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;

public class Fragment_League_UpComing_Matches extends Fragment {
    private FragmentLeagueUpcomingMatchesBinding binding;
    private LeagueDetailsActivity activity;

    public static Fragment_League_UpComing_Matches newInstance(){
        return new Fragment_League_UpComing_Matches();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_league_upcoming_matches,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (LeagueDetailsActivity) getActivity();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }
}
