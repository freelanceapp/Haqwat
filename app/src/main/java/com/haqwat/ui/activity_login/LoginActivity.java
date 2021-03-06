package com.haqwat.ui.activity_login;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.haqwat.R;
import com.haqwat.databinding.ActivityLoginBinding;
import com.haqwat.language.Language;
import com.haqwat.models.AccountsModel;
import com.haqwat.models.LoginModel;
import com.haqwat.models.UserModel;
import com.haqwat.mvp.login_mvp.LoginPresenter;
import com.haqwat.mvp.login_mvp.LoginView;
import com.haqwat.preferences.Preferences;
import com.haqwat.share.Common;
import com.haqwat.ui.activity_complete_sign_up.CompleteSignUpActivity;
import com.haqwat.ui.activity_confirm_code.ConfirmCodeActivity;
import com.haqwat.ui.activity_forget_password.ForgetPasswordActivity;
import com.haqwat.ui.activity_home.HomeActivity;
import com.haqwat.ui.activity_sign_up.SignUpActivity;

import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity implements LoginView {
    private ActivityLoginBinding binding;
    private LoginModel loginModel;
    private LoginPresenter presenter;
    private Preferences preferences;
    private ProgressDialog dialog;
    private AccountsModel accountsModel;


    @Override
    protected void attachBaseContext(Context newBase) {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        getDataFromIntent();
        initView();
    }

    private void getDataFromIntent() {
        Intent intent = getIntent();
        accountsModel = (AccountsModel) intent.getSerializableExtra("account");

    }

    private void initView() {
        dialog = Common.createProgressDialog(this, getString(R.string.wait));
        dialog.setCanceledOnTouchOutside(false);

        preferences = Preferences.getInstance();
        loginModel = new LoginModel();
        binding.setModel(loginModel);
        presenter = new LoginPresenter(this, this);


        binding.tvSignUp.setOnClickListener(view -> {
            Intent intent = new Intent(this, SignUpActivity.class);
            startActivity(intent);
            finish();

        });

        binding.btnLogin.setOnClickListener(view -> {
            presenter.isDataValid(loginModel);
        });


        if (accountsModel != null) {
            loginModel.setEmail(accountsModel.getEmail());
            loginModel.setPassword(accountsModel.getPassword());
            presenter.isDataValid(loginModel);
        }

        binding.tvForgetPassword.setOnClickListener(view -> {
            Intent intent = new Intent(this, ForgetPasswordActivity.class);
            startActivityForResult(intent,100);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==RESULT_OK){
            navigateToHomeActivity();
        }
    }

    @Override
    public void onSuccess(UserModel userModel) {
        if (userModel.getHas_haqawat_subscribe().equals("no")) {
            navigateToCompleteSignUp(userModel);

        } else {
            preferences.create_update_userdata(this, userModel);
            AccountsModel model = new AccountsModel(loginModel.getEmail(), loginModel.getPassword());
            model.setLoggedIn(true);
            preferences.createAccount(this, model);
            navigateToHomeActivity();
        }

    }

    private void navigateToHomeActivity() {
        Intent intent = new Intent(this, HomeActivity.class);
        startActivity(intent);
        finish();
    }

    private void navigateToCompleteSignUp(UserModel userModel) {
        Intent intent = new Intent(this, CompleteSignUpActivity.class);
        intent.putExtra("data", userModel);
        startActivity(intent);
    }

    @Override
    public void navigateToConfirmCode(UserModel userModel) {
        Intent intent = new Intent(this, ConfirmCodeActivity.class);
        intent.putExtra("data", userModel);
        startActivity(intent);
        finish();
    }

    @Override
    public void navigateToSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showProgressBar() {
        dialog.show();
    }

    @Override
    public void hideProgressBar() {
        dialog.dismiss();
    }
}