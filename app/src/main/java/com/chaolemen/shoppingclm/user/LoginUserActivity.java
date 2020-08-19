package com.chaolemen.shoppingclm.user;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chaolemen.httplibrary.utils.LogUtils;
import com.chaolemen.mvplibrary.base.BaseMvpActivity;
import com.chaolemen.shoppingclm.R;
import com.chaolemen.shoppingclm.user.bean.LoginBean;
import com.chaolemen.shoppingclm.user.contract.LoginContract;
import com.chaolemen.shoppingclm.user.parmesan.LoginParmesan;
import com.chaolemen.shoppingclm.user.presenter.LoginPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginUserActivity extends BaseMvpActivity<LoginContract.View, LoginPresenter> implements LoginContract.View {
    @BindView(R.id.toolbar_login)
    Toolbar mToolbarLogin;
    @BindView(R.id.iv_login_finish)
    ImageView mIvLoginFinish;
    @BindView(R.id.tv_login_register)
    TextView mTvLoginRegister;
    @BindView(R.id.et_login_user)
    EditText mEtLoginUser;
    @BindView(R.id.et_login_pass)
    EditText mEtLoginPass;
    @BindView(R.id.linear_login)
    LinearLayout mLinearLogin;
    @BindView(R.id.btn_login)
    Button mBtnLogin;
    @BindView(R.id.tv_login_slip)
    TextView mTvLoginSlip;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView();
//    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_user_login;
    }

    @Override
    protected void initData() {

        mToolbarLogin.setTitle("登录");

        TextView childAt = (TextView) mToolbarLogin.getChildAt(0);
        childAt.getLayoutParams().width = LinearLayout.LayoutParams.MATCH_PARENT;
        childAt.setGravity(Gravity.CENTER);
    }

    @Override
    protected LoginPresenter initPresenter() {
        return new LoginPresenter();
    }

    @Override
    public void onSuccess(LoginBean loginBean) {
        String userName = loginBean.getUserName();
        Toast.makeText(this, userName+"登录成功", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFailItem(String error) {
        LogUtils.e(error);
    }

    @Override
    public void onCancal() {
        mPresenter.onCancal();
    }


    @OnClick({R.id.iv_login_finish, R.id.tv_login_register, R.id.btn_login, R.id.tv_login_slip})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_login_finish:
                finish();
                break;
            case R.id.tv_login_register:
                Intent intent = new Intent(LoginUserActivity.this, RegisterActivity.class);
                startActivityForResult(intent, 200);
                break;
            case R.id.btn_login:
                String user = mEtLoginUser.getText().toString().trim();
                String pass = mEtLoginPass.getText().toString().trim();
                LoginParmesan loginParmesan = new LoginParmesan();
                loginParmesan.setMobile(user);
                loginParmesan.setPwd(pass);
                loginParmesan.setPushId("140fe1da9e25c9cc321");
                mPresenter.getDataLogin(loginParmesan);
                break;
            case R.id.tv_login_slip:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200 && resultCode == 300) {
            String user = data.getStringExtra("user");
            mEtLoginUser.setText(user);
        }
    }
}
