package com.color.card.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.color.card.MainActivity;
import com.color.card.R;
import com.color.card.base.tools.SystemBarTintManager;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.ui.adapter.GuidePageAdapter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.util.DensityUtils;

import java.util.ArrayList;
import java.util.List;


/**
 * @author yqy
 * @date on 2018/3/22
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class GuideActivity extends BaseActivity implements ViewPager.OnPageChangeListener {
    private int[] imageIdArray;//图片资源的数组
    private List<View> viewList;//图片资源的集合
    private LinearLayout vg;//放置圆点
    private ImageView[] ivPointArray;

    //最后一页的按钮
    private TextView tv_clickenter;

    private TextView tv_registerr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setChangeStatusTrans(false);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        setHeadVisible(View.GONE);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
//        SPCacheUtils.put("hasGuide", true);
//        initSysBar();
        tv_clickenter =  findViewById(R.id.tv_clickenter);
        tv_registerr = findViewById(R.id.tv_registerr);
        //加载ViewPager
        initViewPager();
        //加载底部圆点
        initPoint();

    }


    /**
     * 初始化状态栏
     */
    private void initSysBar() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            setTranslucentStatus(true);
            tintManager.setStatusBarLightMode(this, true);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(R.color.transparent);
        }
    }

    @Override
    public void initEvent() {
        tv_clickenter.setOnClickListener(this);
        tv_registerr.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clickenter:
                startActivity(new Intent(GuideActivity.this, LoginActivity.class));
                finish();
                break;

            case R.id.tv_registerr:
                startActivity(new Intent(GuideActivity.this, RegisterActivity.class));
                break;
            default:
                break;
        }
    }

    /**
     * 加载底部圆点
     */

    private void initPoint() {
        //这里实例化LinearLayout
        vg = (LinearLayout) findViewById(R.id.guide_ll_point);
        //根据ViewPager的item数量实例化数组
        ivPointArray = new ImageView[viewList.size()];
        //循环新建底部圆点ImageView，将生成的ImageView保存到数组中
        int size = viewList.size();
        //实例化原点View
        ImageView iv_point;
        for (int i = 0; i < size; i++) {
            iv_point = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(DensityUtils.dip2px(8), DensityUtils.dip2px(8));
            params.setMargins(DensityUtils.dip2px(5), 0, DensityUtils.dip2px(5), 0);
            iv_point.setLayoutParams(params);
            //第一个页面需要设置为选中状态，这里采用两张不同的图片
            if (i == 0) {
                iv_point.setBackgroundResource(R.drawable.full_holo);
            } else {
                iv_point.setBackgroundResource(R.drawable.empty_holo);
            }
            ivPointArray[i] = iv_point;
            //将数组中的ImageView加入到ViewGroup
            vg.addView(ivPointArray[i]);
            iv_point = null;
        }
    }

    /**
     * 加载图片ViewPager
     */
    private void initViewPager() {
        ViewPager vp = (ViewPager) findViewById(R.id.guide_vp);
        //实例化图片资源
        imageIdArray = new int[]{R.drawable.img_guide1, R.drawable.img_guide2, R.drawable.img_guide3};
        viewList = new ArrayList<>();
        //获取一个Layout参数，设置为全屏
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        //循环创建View并加入到集合中
        int len = imageIdArray.length;
        ImageView imageView;
        for (int i = 0; i < len; i++) {
            //new ImageView并设置全屏和图片资源
            imageView = new ImageView(this);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(imageIdArray[i]);
            //将ImageView加入到集合中
            viewList.add(imageView);
            imageView = null;
        }
        //View集合初始化好后，设置Adapter
        vp.setAdapter(new GuidePageAdapter(viewList));
        //设置滑动监听
        vp.addOnPageChangeListener(this);
    }


    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        //循环设置当前页的标记图
        int length = imageIdArray.length;
        for (int i = 0; i < length; i++) {
            ivPointArray[position].setBackgroundResource(R.drawable.full_holo);
            if (position != i) {
                ivPointArray[i].setBackgroundResource(R.drawable.empty_holo);
            }
        }
        //判断是否是最后一页，若是则显示按钮
        if (position == imageIdArray.length - 1) {
//            tv_clickenter.setVisibility(View.VISIBLE);
            vg.setVisibility(View.GONE);
        } else {
//            tv_clickenter.setVisibility(View.GONE);
            vg.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


    private void releaseRes() {
        if (imageIdArray != null) {
            imageIdArray = null;
        }
        if (ivPointArray != null) {
            ivPointArray = null;
        }
        if (viewList != null) {
            viewList.clear();
            viewList = null;
        }
        if (vg != null) {
            vg.removeAllViews();
            vg = null;
        }
    }

    @Override
    protected void onDestroy() {
        releaseRes();
        super.onDestroy();
    }
}
