package com.newversions;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yunqin.bearmall.R;
import com.yunqin.bearmall.base.BaseActivity;

import java.io.Serializable;
import java.util.ArrayList;

public class ImageLookActivity extends BaseActivity {

    private ArrayList<String> images = new ArrayList<>();
    private ViewPager mViewPager;
    private TextView number;
    private int pos;

    @Override
    public int layoutId() {
        return R.layout.new_activity_image_look;
    }

    @Override
    public void init() {
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

        mViewPager = findViewById(R.id.view_pager);
        number = findViewById(R.id.number);

        initViewPagerData();
    }


    public void initViewPagerData() {
//        images = (ArrayList<String>) getIntent().getSerializableExtra("images");
        images = getIntent().getStringArrayListExtra("images");
        pos = getIntent().getIntExtra("pos", -1);
        number.setText(pos + 1 + "/" + images.size());
        mViewPager.setAdapter(adapter);
        mViewPager.setCurrentItem(pos);
        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int arg0) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                pos = position;
                number.setText(pos + 1 + "/" + images.size());
            }
        });

    }


    PagerAdapter adapter = new PagerAdapter() {
        @Override
        public int getCount() {
            return images.size();
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object o) {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imageView = new ImageView(ImageLookActivity.this);
//            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            Glide.with(ImageLookActivity.this).load(images.get(position)).into(imageView);
            imageView.setOnClickListener(view -> ImageLookActivity.this.finish());
            container.addView(imageView);
            return imageView;
        }
    };


    public static void startImageActivity(Context context, ArrayList<String> images, int i) {
        Intent intent = new Intent(context, ImageLookActivity.class);
//        intent.putExtra("images", (Serializable) images);
        intent.putStringArrayListExtra("images", images);
        intent.putExtra("pos", i);
        context.startActivity(intent);
    }

}
