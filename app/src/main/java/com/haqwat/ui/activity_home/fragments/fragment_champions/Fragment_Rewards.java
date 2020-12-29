package com.haqwat.ui.activity_home.fragments.fragment_champions;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.haqwat.R;
import com.haqwat.adapters.RewardAdapter;
import com.haqwat.models.RewardModel;
import com.haqwat.adapters.TopChampionAdapter;
import com.haqwat.databinding.FragmentRewardBinding;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_reward_mvp.FragmentRewardPresenter;
import com.haqwat.mvp.fragment_reward_mvp.FragmentRewardView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentChampionView;
import com.haqwat.mvp.fragment_top_champion_mvp.FragmentTopChampionPresenter;
import com.haqwat.preferences.Preferences;
import com.haqwat.ui.activity_home.HomeActivity;

import java.util.ArrayList;
import java.util.List;

public class Fragment_Rewards extends Fragment implements FragmentRewardView {
    private FragmentRewardBinding binding;
    private List<RewardModel> rewardModelList;
    private RewardAdapter adapter;
    private HomeActivity activity;
    private FragmentRewardPresenter presenter;
    private Preferences preferences;
    private UserModel userModel;

    public static Fragment_Rewards newInstance(){
        return new Fragment_Rewards();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_reward,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView() {
        activity = (HomeActivity) getActivity();
        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);
        rewardModelList = new ArrayList<>();
        binding.progBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.recView.setLayoutManager(new GridLayoutManager(activity,3));
        adapter = new RewardAdapter(rewardModelList,activity);
        binding.recView.setAdapter(adapter);
        presenter = new FragmentRewardPresenter(this,activity);
        presenter.getReward(userModel);

    }

    @Override
    public void onSuccess(List<RewardModel> rewardModelList) {
        this.rewardModelList.clear();
        this.rewardModelList.addAll(rewardModelList);
        if (this.rewardModelList.size()>0){
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            binding.tvNote.setVisibility(View.VISIBLE);

        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.tvNote.setVisibility(View.GONE);

        }
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        binding.progBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        binding.progBar.setVisibility(View.GONE);

    }
}
