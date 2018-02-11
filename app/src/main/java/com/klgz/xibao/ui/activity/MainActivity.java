package com.klgz.xibao.ui.activity;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bigkoo.convenientbanner.ConvenientBanner;
import com.bigkoo.convenientbanner.holder.CBViewHolderCreator;
import com.bigkoo.convenientbanner.holder.Holder;
import com.bumptech.glide.Glide;
import com.klgz.library.util.SystemBarHelper;
import com.klgz.library.widgets.swipeback.SwipeBackActivity;
import com.klgz.xibao.ItemFragment;
import com.klgz.xibao.R;
import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItem;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends SwipeBackActivity {
    @BindView(R.id.banner) ConvenientBanner mBanner;
    @BindView(R.id.toolbar) Toolbar mToolbar;
    @BindView(R.id.tabs) SmartTabLayout mTabs;
    @BindView(R.id.appbar) public AppBarLayout mAppbar;
    @BindView(R.id.tabs_viewpager) ViewPager mTabsViewpager;
//    @BindView(R.id.navigation_view) NavigationView mNavigationView;
//    @BindView(R.id.drawerlayout) DrawerLayout mDrawerlayout;
    private List<String> localImages = new ArrayList<>();
    private List<String> titles = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        setSwipeBackEnable(false);
        initStatusbar();
        initConvenientBanner(mBanner);
        initAppBar();
        initViewPagerAndTabs();
    }

    private void initViewPagerAndTabs() {
        titles.add("精品");
        titles.add("新品");
        titles.add("折扣");
        titles.add("清单");
        titles.add("体验");
        FragmentPagerItems pages = new FragmentPagerItems(this);
        for (String title : titles) {
            pages.add(FragmentPagerItem.of(title, ItemFragment.class));
        }
        FragmentPagerItemAdapter adapter = new FragmentPagerItemAdapter(
                getSupportFragmentManager(), pages);
        mTabsViewpager.setAdapter(adapter);
        mTabs.setViewPager(mTabsViewpager);
//        mTabsViewpager.setAdapter(new TabFragmentPagerAdapter(getSupportFragmentManager()));
    }

    private void initStatusbar() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT){
//            SystemBarHelper.setPadding(this, mNavigationView.getHeaderView(0));
            SystemBarHelper.immersiveStatusBar(this);
            SystemBarHelper.setHeightAndPadding(this, mToolbar);
        }

    }

    private void initAppBar() {
        mAppbar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
                double alpha = Math.abs(verticalOffset) * 1.0 / (appBarLayout.getHeight() - mTabs.getHeight()-mToolbar.getHeight());
                if (alpha>1.0){
                    alpha=1.0;
                }
                mToolbar.setBackgroundColor(Color.argb((int) (alpha * 255), 255, 64, 129));
//                System.out.println("Color.argb:"+Color.argb((int) (alpha * 255), 255, 64, 129));
            }
        });
    }

    private void initConvenientBanner(ConvenientBanner mConvenientBanner) {
        localImages.add("http://e.hiphotos.baidu.com/zhidao/pic/item/7a899e510fb30f248ecac3b2c995d143ad4b032a.jpg");
        localImages.add("http://pic.qqkw.net/uploads/allimg/150316/1-1503161J910.jpg");
        localImages.add("http://e.hiphotos.baidu.com/zhidao/pic/item/cb8065380cd79123531900e6a8345982b3b780a3.jpg");
        localImages.add("http://pic25.nipic.com/20121126/668573_145418157171_2.jpg");
        localImages.add("http://img1.3lian.com/img13/c3/39/d/8.jpg");

        mConvenientBanner.startTurning(4000);
        //自定义你的Holder，实现更多复杂的界面，不一定是图片翻页，其他任何控件翻页亦可。
        mConvenientBanner.setPages(
                new CBViewHolderCreator<LocalImageHolderView>() {
                    @Override
                    public LocalImageHolderView createHolder() {
                        return new LocalImageHolderView();
                    }
                }, localImages)
                //设置两个点图片作为翻页指示器，不设置则没有指示器，可以根据自己需求自行配合自己的指示器,不需要圆点指示器可用不设
//                .setPageIndicator(new int[]{R.mipmap.ic_launcher, R.mipmap.ic_launcher_round})
                //设置指示器的方向
                .setPageIndicatorAlign(ConvenientBanner.PageIndicatorAlign.ALIGN_PARENT_RIGHT);
        //设置翻页的效果，不需要翻页效果可用不设
        //.setPageTransformer(Transformer.DefaultTransformer);    集成特效之后会有白屏现象，新版已经分离，如果要集成特效的例子可以看Demo的点击响应。
//        mConvenientBanner.setManualPageable(false);//设置不能手动影响

    }

    public class LocalImageHolderView implements Holder<String> {
        private ImageView imageView;

        @Override
        public View createView(Context context) {
            imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            return imageView;
        }

        @Override
        public void UpdateUI(Context context, final int position, String url) {
            Glide.with(MainActivity.this)
                    .load(url)
                    .centerCrop()
                    .placeholder(R.mipmap.ic_launcher)
                    .into(imageView);
        }
    }

}
