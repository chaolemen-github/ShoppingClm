package com.chaolemen.shoppingclm.category;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chaolemen.mvplibrary.base.BaseActivity;
import com.chaolemen.shoppingclm.R;
import com.chaolemen.shoppingclm.category.fragments.DitailFragment;
import com.chaolemen.shoppingclm.category.fragments.DitailWebFragment;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DitailActivity extends BaseActivity {

    @BindView(R.id.tabLayout_ditail)
    TabLayout tabLayoutDitail;
    @BindView(R.id.iv_ditail)
    ImageView ivDitail;
    @BindView(R.id.vp_ditail)
    ViewPager vpDitail;
    @BindView(R.id.cl)
    ConstraintLayout mCl;
    @BindView(R.id.tv_ditail_find)
    TextView mTvDitailFind;
    @BindView(R.id.tv_ditail_shopping)
    TextView mTvDitailShopping;
    @BindView(R.id.linear_ditail)
    LinearLayout mLinearDitail;
    @BindView(R.id.tv_ditail_add)
    TextView mTvDitailAdd;


    @Override
    protected int getLayoutId() {
        return R.layout.activity_ditail;
    }

    @Override
    protected void initEvent() {

        int position = getIntent().getIntExtra("position", 0);
        final ArrayList<Fragment> fragments = new ArrayList<>();
        fragments.add(new DitailFragment(position));
        fragments.add(new DitailWebFragment());
        final ArrayList<String> titles = new ArrayList<>();
        titles.add("商品");
        titles.add("详情");

        vpDitail.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int i) {
                return fragments.get(i);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }

            @Nullable
            @Override
            public CharSequence getPageTitle(int position) {
                return titles.get(position);
            }
        });

        tabLayoutDitail.setupWithViewPager(vpDitail);

    }

    @Override
    protected void initData() {

    }


    @OnClick({R.id.tv_ditail_find, R.id.tv_ditail_shopping, R.id.tv_ditail_add,R.id.iv_ditail})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ditail_find:
                break;
            case R.id.tv_ditail_shopping:
                Intent intent = new Intent(DitailActivity.this, ShoppingActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_ditail_add:
                break;
            case R.id.iv_ditail:
                finish();
                break;
        }
    }
}