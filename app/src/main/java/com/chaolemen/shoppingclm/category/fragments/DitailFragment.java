package com.chaolemen.shoppingclm.category.fragments;


import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.ArrayMap;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.chaolemen.httplibrary.client.HttpClient;
import com.chaolemen.httplibrary.utils.JsonUtils;
import com.chaolemen.httplibrary.utils.LogUtils;
import com.chaolemen.shoppingclm.R;
import com.chaolemen.shoppingclm.category.bean.CategoryDitail;
import com.chaolemen.shoppingclm.category.parmesan.DetailParmesan;
import com.chaolemen.shoppingclm.net.CGHttpCallBack;
import com.google.gson.JsonElement;
import com.youth.banner.Banner;
import com.youth.banner.BannerConfig;
import com.youth.banner.loader.ImageLoader;
import com.zhy.view.flowlayout.FlowLayout;
import com.zhy.view.flowlayout.TagAdapter;
import com.zhy.view.flowlayout.TagFlowLayout;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import ren.qinc.numberbutton.NumberButton;

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
    private BottomDialog bottomDialog;


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
    }

    @OnClick({R.id.tv_ditail_xuan, R.id.tv_ditail_select, R.id.iv_ditail_more})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_ditail_xuan:
                break;
            case R.id.tv_ditail_select:
                bottomDialog = new BottomDialog(getActivity(),beans);
                bottomDialog.show();
                break;
            case R.id.iv_ditail_more:
                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN,sticky = true)
    public void eventBusSku(String str){
        List<String> skuEvent = bottomDialog.getSkuEvent();
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < skuEvent.size(); i++) {
            stringBuilder.append(skuEvent.get(i));
            if (i!=skuEvent.size()-1){
                stringBuilder.append(",");
            }
        }

        tvDitailSelect.setText(stringBuilder.toString()+bottomDialog.numberButton.getNumber()+"件");
    }

    //低部弹出Dialog,动态添加控件到ViewGroup
    class BottomDialog extends BottomSheetDialog implements View.OnClickListener {
        List<Map<TagFlowLayout,List<String>>> skuStringList = new ArrayList<>();
        public NumberButton numberButton;
        private List<CategoryDitail.GoodsSkuBean> goodsSkuBeans;
        public BottomDialog( final Context context, CategoryDitail categoryDitail) {
            super(context);
            this.goodsSkuBeans = goodsSkuBeans;
            setContentView(R.layout.layout_sku_pop);
            findViewById(R.id.mCloseIv).setOnClickListener(this);
            ImageView imageView = findViewById(R.id.mGoodsIconIv);
            Glide.with(context).load(categoryDitail.getGoodsDefaultIcon()).into(imageView);
            numberButton = findViewById(R.id.mSkuCountBtn);
            //数量默认选中1
            numberButton.setCurrentNumber(1);

            EditText editText = numberButton.findViewById(R.id.text_count);
            editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    EventBus.getDefault().postSticky("");
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });

            //父控件
            ViewGroup viewGroup = findViewById(R.id.mSkuView);
            View inflate = null;
            for (CategoryDitail.GoodsSkuBean goodsSkuBean : goodsSkuBeans) {
                Map<TagFlowLayout,List<String>> skuStringMap = new ArrayMap<>();
                //初始化一组数据
                inflate =  LayoutInflater.from(getActivity()).inflate(R.layout.layout_sku_view, null, false);
                TagFlowLayout tagFlowLayout = inflate.findViewById(R.id.mSkuContentView);
                TextView textView = inflate.findViewById(R.id.mSkuTitleTv);
                textView.setText(goodsSkuBean.getGoodsSkuTitle());
                List<String> skuContent = goodsSkuBean.getSkuContent();
                tagFlowLayout.setAdapter(new TagAdapter<String>(skuContent) {
                    @Override
                    public View getView(com.zhy.view.flowlayout.FlowLayout parent, int position, String s) {
                        TextView textView1 = (TextView) LayoutInflater.from(context)
                                .inflate(R.layout.layout_sku_item, parent, false);
                        textView1.setText(s);
                        return textView1;
                    }
                });

                tagFlowLayout.getAdapter().setSelectedList(0);
                tagFlowLayout.setOnTagClickListener(new TagFlowLayout.OnTagClickListener() {
                    @Override
                    public boolean onTagClick(View view, int position, FlowLayout parent) {
                        EventBus.getDefault().post("");
                        return true;
                    }
                });

                skuStringMap.put(tagFlowLayout,skuContent);
                skuStringList.add(skuStringMap);
                //将一组数据添加到父控件当中
                viewGroup.addView(inflate);
            }
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mCloseIv:
                    bottomDialog.dismiss();
                    break;
            }
        }

        public List<String> getSkuEvent(){
            List<String> skuCount = new ArrayList<>();
            for (Map<TagFlowLayout, List<String>> listMap : skuStringList) {
                Set<Map.Entry<TagFlowLayout, List<String>>> entrySet = listMap.entrySet();
                for (Map.Entry<TagFlowLayout, List<String>> entry : entrySet) {
                    TagFlowLayout tagFlowLayout = entry.getKey();
                    List<String> value = entry.getValue();
                    skuCount.add(value.get(tagFlowLayout.getSelectedList().iterator().next()));
                }
            }
            return skuCount;
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
