package com.haqwat.ui.activity_home.fragments;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.util.Log;
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
import com.haqwat.adapters.BestLeagueAdapter;
import com.haqwat.adapters.BestTeamAdapter;
import com.haqwat.adapters.FavoriteTeamAdapter;
import com.haqwat.adapters.HomeJoinedTeamAdapter;
import com.haqwat.databinding.FragmentHomeBinding;
import com.haqwat.models.BestThreeLeagueModel;
import com.haqwat.models.BestThreeTeamModel;
import com.haqwat.models.HomeJoinedTeamsModel;
import com.haqwat.models.HomeModel;
import com.haqwat.models.TeamOrderModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.fragment_home_mvp.FragmentHomePresenter;
import com.haqwat.mvp.fragment_home_mvp.FragmentHomeView;
import com.haqwat.preferences.Preferences;
import com.haqwat.tags.Tags;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_league_details.LeagueDetailsActivity;
import com.haqwat.ui.activity_matches.MatchesActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class Fragment_Home extends Fragment implements FragmentHomeView {
    private FragmentHomeBinding binding;
    private HomeActivity activity;
    private FragmentHomePresenter presenter;
    private Preferences preferences;
    private UserModel userModel;
    private List<HomeJoinedTeamsModel> homeJoinedTeamsModelList;
    private List<BestThreeLeagueModel> bestThreeLeagueModelList;
    private BestLeagueAdapter bestLeagueAdapter;
    private HomeJoinedTeamAdapter  adapter;
    private List<BestThreeTeamModel> bestThreeTeamModelList;
    private BestTeamAdapter bestTeamAdapter;



    public static Fragment_Home newInstance(){
        return new Fragment_Home();
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_home,container,false);
        initView();
        return binding.getRoot();
    }

    private void initView()
    {
        bestThreeTeamModelList = new ArrayList<>();
        bestThreeLeagueModelList = new ArrayList<>();
        homeJoinedTeamsModelList = new ArrayList<>();
        activity = (HomeActivity) getActivity();

        preferences = Preferences.getInstance();
        userModel = preferences.getUserData(activity);

        binding.recView.setLayoutManager(new LinearLayoutManager(activity));
        adapter = new HomeJoinedTeamAdapter(homeJoinedTeamsModelList,activity);
        binding.recView.setAdapter(adapter);

        binding.recViewBestLeague.setLayoutManager(new GridLayoutManager(activity,3));
        bestLeagueAdapter = new BestLeagueAdapter(bestThreeLeagueModelList,activity);
        binding.recViewBestLeague.setAdapter(bestLeagueAdapter);


        binding.recViewBestRecommended.setLayoutManager(new LinearLayoutManager(activity));
        bestTeamAdapter = new BestTeamAdapter(bestThreeTeamModelList,activity);
        binding.recViewBestRecommended.setAdapter(bestTeamAdapter);



        presenter = new FragmentHomePresenter(this,activity);
        presenter.getData(userModel);
        presenter.getBestLeague();
        presenter.getBestTeams();



        binding.img1.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.champions_league), Tags.CHAMPIONS_LEAGUE);
        });


        binding.img2.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.egyptian_league),Tags.EGYPTIAN_LEAGUE);
        });
        binding.img3.setOnClickListener(view -> {

            navigateToLeagueDetailsActivity(getString(R.string.saudi_league),Tags.SAUDI_LEAGUE);
        });
        binding.img4.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.french_league),Tags.FRANCE_LEAGUE);
        });
        binding.img5.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.bundesliga),Tags.JERMAN_LEAGUE);
        });
        binding.img6.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.italian_league),Tags.SERIA_LEAGUE);
        });
        binding.img7.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.la_liga),Tags.LA_LEAGUE);
        });
        binding.img8.setOnClickListener(view -> {
            navigateToLeagueDetailsActivity(getString(R.string.premier_league),Tags.PREMIER_LEAGUE);
        });

        binding.btnUpComingMatches.setOnClickListener(view -> {
            navigateToMatchesActivity(0);
        });

        binding.btnPreviousMatches.setOnClickListener(view -> {
            navigateToMatchesActivity(1);
        });
        binding.progBarLoadAverageRate.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarLoadRate.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarLoadTeam.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarBestLeague.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);
        binding.progBarBestRecommended.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(activity,R.color.colorPrimaryDark), PorterDuff.Mode.SRC_IN);

    }

    private void navigateToMatchesActivity(int pos) {
        Intent intent = new Intent(activity, MatchesActivity.class);
        intent.putExtra("data",pos);
        startActivity(intent);

    }
    private void navigateToLeagueDetailsActivity(String name,String id)
    {
        Intent intent = new Intent(activity, LeagueDetailsActivity.class);
        intent.putExtra("data",name);
        intent.putExtra("id",id);
        startActivity(intent);

    }

    @Override
    public void hideLoadRate() {
        binding.progBarLoadRate.setVisibility(View.GONE);
        binding.progBarLoadTeam.setVisibility(View.GONE);

    }

    @Override
    public void hideLoadAverageRate() {
        binding.progBarLoadAverageRate.setVisibility(View.GONE);
        binding.progBarLoadTeam.setVisibility(View.GONE);

    }

    @Override
    public void showProgressBestLeague() {
        binding.progBarBestLeague.setVisibility(View.VISIBLE);
        bestThreeLeagueModelList.clear();
        bestLeagueAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgressBestLeague() {
        binding.progBarBestLeague.setVisibility(View.GONE);

    }

    @Override
    public void onDataSuccess(HomeModel homeModel) {
        activity.updateGiftCount(homeModel.getAllowable_leagues_count());
        binding.progBarAverageRate.setVisibility(View.VISIBLE);
        binding.progBarYourRate.setVisibility(View.VISIBLE);
        binding.progBarAverageRate.setProgress((int) homeModel.getAllUsersRate());
        binding.tvAverageRate.setText(String.format(Locale.ENGLISH,"%s%s",homeModel.getAllUsersRate(),"%"));
        binding.progBarYourRate.setProgress((int) homeModel.getCurrentUserRate());
        binding.tvYourRate.setText(String.format(Locale.ENGLISH,"%s%s",homeModel.getCurrentUserRate(),"%"));
        homeJoinedTeamsModelList.addAll(homeModel.getList_of_leagues_with_teams());
        adapter.notifyDataSetChanged();

        try {
            binding.nestedScrollView.getParent().requestChildFocus(binding.nestedScrollView,binding.nestedScrollView);

        }catch (Exception e){

        }


    }

    @Override
    public void onBestLeagueSuccess(List<BestThreeLeagueModel> data) {
        if (data.size()>0){
            binding.tvNoDataBestLeague.setVisibility(View.GONE);
            bestThreeLeagueModelList.addAll(data);
            bestLeagueAdapter.notifyDataSetChanged();
        }else {
            binding.tvNoDataBestLeague.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void onBestTeamsSuccess(List<BestThreeTeamModel> data) {
        if (data.size()>0){
            Log.e("ddd","ddd");
            binding.tvNoDataBestRecommended.setVisibility(View.GONE);
            bestThreeTeamModelList.addAll(data);
            bestTeamAdapter.notifyDataSetChanged();
        }else {
            binding.tvNoDataBestRecommended.setVisibility(View.VISIBLE);

        }
    }

    @Override
    public void showProgressBestTeam() {
        binding.progBarBestRecommended.setVisibility(View.VISIBLE);
        bestThreeTeamModelList.clear();
        bestTeamAdapter.notifyDataSetChanged();
    }

    @Override
    public void hideProgressBestTeam() {
        binding.progBarBestRecommended.setVisibility(View.GONE);

    }


    @Override
    public void onFailed(String msg) {
        Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
    }
}
