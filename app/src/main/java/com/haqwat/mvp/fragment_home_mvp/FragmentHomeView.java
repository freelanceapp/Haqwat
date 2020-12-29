package com.haqwat.mvp.fragment_home_mvp;

import com.haqwat.models.HomeModel;
import com.haqwat.models.UserModel;

public interface FragmentHomeView {
    void hideLoadRate();
    void hideLoadAverageRate();
    void onDataSuccess(HomeModel homeModel);
    void onFailed(String msg);

}
