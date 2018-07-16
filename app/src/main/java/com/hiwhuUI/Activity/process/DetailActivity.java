package com.hiwhuUI.Activity.process;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.ybq.android.spinkit.SpinKitView;
import com.github.ybq.android.spinkit.SpriteFactory;
import com.github.ybq.android.spinkit.Style;
import com.github.ybq.android.spinkit.sprite.Sprite;
import com.hiwhu.hiwhuclient.R;

public class DetailActivity extends AppCompatActivity implements Colors {

    public static Activity detailActivity;

    @SuppressWarnings("WeakerAccess")
    public static void start(Context context, int position) {
        Intent intent = new Intent(context, DetailActivity.class);
        intent.putExtra("position", position);
        context.startActivity(intent);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(0);
        viewPager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return Style.values().length;
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                @SuppressLint("InflateParams") View view = LayoutInflater.from(container.getContext()).inflate(R.layout.item_pager, null);

                SpinKitView spinKitView = (SpinKitView) view.findViewById(R.id.spin_kit);
                Style style = Style.values()[position];
                Sprite drawable = SpriteFactory.create(style);
                spinKitView.setIndeterminateDrawable(drawable);
                container.addView(view);

                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                int color = (int) ArgbEvaluator.getInstance().evaluate(positionOffset,
                        colors[position % colors.length],
                        colors[(position + 1) % colors.length]);
                getWindow().getDecorView().setBackgroundColor(color);
            }

            @Override
            public void onPageSelected(int position) {
                getWindow().getDecorView().setBackgroundColor(colors[position % colors.length]);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        viewPager.setCurrentItem(getIntent().getIntExtra("position", 0));

        detailActivity = this;
    }

    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }
}

