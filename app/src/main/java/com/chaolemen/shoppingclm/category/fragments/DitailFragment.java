package com.chaolemen.shoppingclm.category.fragments;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaolemen.httplibrary.client.HttpClient;
import com.chaolemen.httplibrary.utils.JsonUtils;
import com.chaolemen.httplibrary.utils.LogUtils;
import com.chaolemen.shoppingclm.R;
import com.chaolemen.shoppingclm.app.BaseApp;
import com.chaolemen.shoppingclm.category.bean.CategoryDitail;
import com.chaolemen.shoppingclm.category.parmesan.DetailParmesan;
import com.chaolemen.shoppingclm.category.viewGroup.XCFlowLayout;
import com.chaolemen.shoppingclm.net.CGHttpCallBack;
import com.google.gson.JsonElement;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.android.schedulers.AndroidSchedulers;

import android.graphics.Color;
import android.os.Bundle;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.TextView;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 */
public class DitailFragment extends Fragment {
    @BindView(R.id.tv_ditail_xuan)
    TextView mTvDitailXuan;
    @BindView(R.id.iv_ditail_more)
    ImageView mIvDitailMore;
    private int position;

    @BindView(R.id.banner_ditail)
    Banner bannerDitail;
    @BindView(R.id.tv_ditail_title)
    TextView tvDitailTitle;
    @BindView(R.id.tv_ditail_money)
    TextView tvDitailMoney;
    @BindView(R.id.tv_ditail_select)
    TextView tvDitailSelect;
    Unbinder unbinder;
    //    private String[] mNames;
    private CategoryDitail beans;
    private int money;
    private int code = 1;
    private String pei;
    private String ban;
    private int id = -1;

    public DitailFragment(int position) {
        // Required empty public constructor
        this.position = position;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ditail, container, false);
        unbinder = ButterKnife.bind(this, view);

        initData();
        return view;
    }

    private void initData() {
        DetailParmesan detailParmesan = new DetailParmesan();
        detailParmesan.setGoodsId(position);
        String toJson = JsonUtils.classToJson(detailParmesan);

        new HttpClient.Builder()
                .setApiUrl("goods/getGoodsDetail")
                .post()
                .setJsonBody(toJson, true)
                .build()
                .request(new CGHttpCallBack<CategoryDitail>() {
                    @Override
                    public void onError(String message, int code) {
                        LogUtils.e(message + code);
                    }

                    @Override
                    public void cancle() {

                    }

                    @Override
                    public void onSuccess(CategoryDitail categoryDitail) {
                        LogUtils.e("详情" + categoryDitail.toString());
                        initView(categoryDitail);

                    }

                    @Override
                    public CategoryDitail convert(JsonElement result) {
                        return JsonUtils.jsonToClass(result, CategoryDitail.class);
                    }
                });

    }

    private void initView(CategoryDitail bean) {

        tvDitailTitle.setText(bean.getGoodsDesc() + "");
        String price = bean.getGoodsDefaultPrice();
        int parseInt = Integer.parseInt(price);
        money = parseInt / 100;
        tvDitailMoney.setText("￥" + money + ".00");
        tvDitailSelect.setText(bean.getGoodsDefaultSku() + "");

        ArrayList<String> images = new ArrayList<>();
        String goodsBanner = bean.getGoodsBanner();
        String[] split = goodsBanner.split(",");
        for (int i = 0; i < split.length; i++) {
            images.add(split[i]);
        }

        //设置图片加载器
        bannerDitail.setImageLoader(new GlideImageLoader());
        //设置图片集合
        bannerDitail.setImages(images);
        //设置轮播时间
        bannerDitail.setDelayTime(2000);
        //设置指示器位置（当banner模式中有指示器时）
        bannerDitail.setIndicatorGravity(BannerConfig.RIGHT);
        //banner设置方法全部调用完毕时最后调用
        bannerDitail.start();

        EventBus.getDefault().postSticky(bean);

        beans = bean;
//        mNames = new String[]{
//               bean.getGoodsDefaultPrice()
//        };
    }

    @OnClick({R.id.tv_ditail_xuan, R.id.tv_ditail_select, R.id.iv_ditail_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ditail_xuan:
                break;
            case R.id.tv_ditail_select:
                initPop();
                break;
            case R.id.iv_ditail_more:
                initPop();
                break;
        }
    }

    private void initPop() {
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.fragment_ditail_pop, null);
        final PopupWindow popupWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, 1300);
        popupWindow.setBackgroundDrawable(new ColorDrawable());
        popupWindow.setOutsideTouchable(true);
        popupWindow.showAtLocation(view, Gravity.BOTTOM, 0, 0);
        alpha(0.5f);

        popupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                alpha(1);
            }
        });

        ImageView img = view.findViewById(R.id.iv_pop_img);
        TextView title = view.findViewById(R.id.tv_pop_title);
        TextView text = view.findViewById(R.id.tv_pop_text);
        ImageView close = view.findViewById(R.id.iv_pop_close);
        Button add = view.findViewById(R.id.btn_pop_add);
        Button jian = view.findViewById(R.id.btn_pop_jian);
        Button jia = view.findViewById(R.id.btn_pop_jia);
        final TextView codes = view.findViewById(R.id.tv_pop_code);

        jia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                code++;
                codes.setText(code + "");
                tvDitailSelect.setText(ban+","+pei+","+code+"件");
            }
        });

        jian.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (code > 1) {
                    code--;
                    codes.setText(code + "");
                    tvDitailSelect.setText(ban+","+pei+","+code+"件");
                } else {
                    Toast.makeText(getActivity(), "已到最小选中数量，不能再减少", Toast.LENGTH_SHORT).show();
                }
            }
        });

        codes.setText(code + "");

        Glide.with(getActivity()).load(beans.getGoodsDefaultIcon()).into(img);
        title.setText("￥" + money + ".00");
        text.setText("商品编号：" + beans.getGoodsCode());

        /**
         * 点击加入购物车
         *
         * 关闭popupWindow
         */
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventBus.getDefault().postSticky(code);
                popupWindow.dismiss();
            }
        });

        //点击关闭popupWindow
        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });


        List<CategoryDitail.GoodsSkuBean> goodsSku = beans.getGoodsSku();

        for (int i = 0; i < goodsSku.size(); i++) {
            CategoryDitail.GoodsSkuBean goodsSkuBean = goodsSku.get(i);
            if (goodsSkuBean.getId() == 1) {
                initChildViews(view, goodsSkuBean);
            } else if (goodsSkuBean.getId() == 2) {
                initChildViewss(view, goodsSkuBean);
            }
        }

    }

    private void initChildViews(View root, CategoryDitail.GoodsSkuBean goodsSkuBean) {
        // TODO Auto-generated method stub

        final SparseArray<Boolean> sparseArray = new SparseArray<>();
        List<String> skuContent = goodsSkuBean.getSkuContent();

        XCFlowLayout mFlowLayout = root.findViewById(R.id.flowlayout);
        ViewGroup.MarginLayoutParams lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < skuContent.size(); i++) {
            sparseArray.put(i, false);
            final TextView view = new TextView(getActivity());
            ban = skuContent.get(i);
            view.setText(ban);
            view.setTextColor(Color.WHITE);
            view.setBackgroundColor(Color.RED);

            if (i==0){
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
            }

            final int finalI = i;
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    sparseArray.put(finalI,true);
                    id = finalI;
                    LogUtils.e("===========================");
                    view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
                }
            });
            if (sparseArray.get(i)){
                tvDitailSelect.setText(ban+","+pei+","+code+"件");
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.text_bg));
            } else {
                view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            }

            view.setSelected(false);
            mFlowLayout.addView(view, lp);
        }
    }

    private void initChildViewss(View root, CategoryDitail.GoodsSkuBean goodsSkuBean) {
        // TODO Auto-generated method stub


        List<String> skuContent = goodsSkuBean.getSkuContent();

        XCFlowLayout mFlowLayouts = root.findViewById(R.id.flowlayout_peizhi);
        ViewGroup.MarginLayoutParams lp = new MarginLayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 5;
        lp.rightMargin = 5;
        lp.topMargin = 5;
        lp.bottomMargin = 5;
        for (int i = 0; i < skuContent.size(); i++) {
            TextView view = new TextView(getActivity());
            pei = skuContent.get(i);
            view.setText(pei);
            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    tvDitailSelect.setText(ban+","+pei+","+code+"件");
                }
            });
            view.setTextColor(Color.WHITE);
            view.setBackgroundDrawable(getResources().getDrawable(R.drawable.textview_bg));
            mFlowLayouts.addView(view, lp);
        }
    }

    public void alpha(float alpha) {
        WindowManager.LayoutParams attributes = getActivity().getWindow().getAttributes();
        attributes.alpha = alpha;
        getActivity().getWindow().setAttributes(attributes);
    }

    public class GlideImageLoader extends ImageLoader {

        @Override
        public void displayImage(Context context, Object path, ImageView imageView) {
            Glide.with(context).load(path).into(imageView);
        }

        //提供createImageView 方法，如果不用可以不重写这个方法，主要是方便自定义ImageView的创建
        @Override
        public ImageView createImageView(Context context) {
            //使用fresco，需要创建它提供的ImageView，当然你也可以用自己自定义的具有图片加载功能的ImageView
            return new ImageView(context);
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

}
