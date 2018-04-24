package com.color.card.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.color.card.R;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.util.BitmapUtils;
import com.color.card.util.MMCQ;
import com.color.card.util.PhotoUtil;
import com.smartstudy.permissions.Permission;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author yqy
 * @date on 2018/4/1
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class ResultActivity extends BaseActivity {
    private ImageView ivIcon;

    private String filePath;

    private LinearLayout llDominantColor;

    private TextView tvDistance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }

    @Override
    public void initView() {
        ivIcon = findViewById(R.id.iv_icon);

        llDominantColor = findViewById(R.id.dominant_color);

        tvDistance = findViewById(R.id.tv_distance);

        filePath = getIntent().getStringExtra("filePath");

        ivIcon.setImageURI(Uri.parse(filePath));

        Bitmap icon = null;
        try {
            icon = PhotoUtil.getBitmapFormUri(ResultActivity.this, Uri.fromFile(new File(filePath)));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (icon != null) {

            showMainColor(BitmapUtils.compressBitmap(icon));
        }
    }

    private void showMainColor(Bitmap bitmap) {
        if (bitmap == null || bitmap.equals("")) {
            Toast.makeText(this, "图片处理失败，请重新测量", Toast.LENGTH_LONG).show();
            finish();
            return;
        }
        List<int[]> result = new ArrayList<>();
        try {
            result = MMCQ.compute(bitmap, 5);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int[] dominantColor = result.get(0);
        findViewById(R.id.dominant_color).setBackgroundColor(
                Color.rgb(dominantColor[0], dominantColor[1], dominantColor[2]));

        LinearLayout palette = (LinearLayout) findViewById(R.id.palette);
        palette.removeAllViews();
        for (int i = 0; i < result.size(); i++) {
            View swatch = new View(this);
            Resources resources = getResources();
            DisplayMetrics displayMetrics = resources.getDisplayMetrics();
            int swatchWidth = (int) (48 * displayMetrics.density + 0.5f);
            int swatchHeight = (int) (48 * displayMetrics.density + 0.5f);
            LinearLayout.LayoutParams layoutParams =
                    new LinearLayout.LayoutParams(swatchWidth, swatchHeight);
            int margin = (int) (4 * displayMetrics.density + 0.5f);
            layoutParams.setMargins(margin, 0, margin, 0);
            swatch.setLayoutParams(layoutParams);

            int[] color = result.get(i);
            int rgb = Color.rgb(color[0], color[1], color[2]);
            swatch.setBackgroundColor(rgb);
            palette.addView(swatch);
        }
        bitmap = null;

    }

}
