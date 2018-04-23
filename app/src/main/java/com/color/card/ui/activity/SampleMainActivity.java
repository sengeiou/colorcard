/*
 * Copyright (C) 2014 Fonpit AG
 *
 * License
 * -------
 * Creative Commons Attribution 2.5 License:
 * http://creativecommons.org/licenses/by/2.5/
 *
 * Thanks
 * ------
 * Simon Oualid - For creating java-colorthief
 * available at https://github.com/soualid/java-colorthief
 *
 * Lokesh Dhakar - for the original Color Thief javascript version
 * available at http://lokeshdhakar.com/projects/color-thief/
 *
 */

package com.example.yqy.myapplication.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.yqy.myapplication.MMCQ;
import com.example.yqy.myapplication.R;
import com.example.yqy.myapplication.ui.widget.ClipImageLayout;
import com.example.yqy.myapplication.util.ActionSheetDialog;
import com.example.yqy.myapplication.util.DisplayImageUtils;
import com.example.yqy.myapplication.util.HSVUtil;
import com.example.yqy.myapplication.util.ParameterUtils;
import com.example.yqy.myapplication.util.PhotoUtil;
import com.example.yqy.myapplication.util.SDCardUtils;
import com.example.yqy.myapplication.util.Utils;
import com.soundcloud.android.crop.Crop;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * @author <a href="mailto:henrique.rocha@androidpit.de">Henrique Rocha</a>
 */
public class SampleMainActivity extends Activity {
    private String path;
    private ImageView ivIcon;
    private int r;
    private int g;
    private int b;
    private LinearLayout llDominantColor;
    private TextView tvDistance;

    private File photoFile;
    private File photoSaveFile;// 保存文件夹
    private String photoSaveName = null;// 图片名
    private String selected_path = null;

    /**
     * 标准主颜色 r=113 g=185 b=166
     * r=108 g=196 b=172
     *
     * @param savedInstanceState
     */


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        ivIcon = findViewById(R.id.iv_icon);

        llDominantColor = findViewById(R.id.dominant_color);

        tvDistance = findViewById(R.id.tv_distance);

        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.drawable.photo1);

        if (icon != null) {
            showMainColor(icon);
        }
        findViewById(R.id.bt_jump).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                options();
                Intent intent = new Intent(SampleMainActivity.this, SelectMyPhotoActivity.class);
                intent.putExtra("singlePic", true);
                startActivityForResult(intent, ParameterUtils.REQUEST_CODE_CHANGEPHOTO);
            }
        });
    }


    protected void options() {
        ActionSheetDialog mDialog = new ActionSheetDialog(this).builder();
        mDialog.setTitle("选择");
        mDialog.setCancelable(false);
        mDialog.addSheetItem("拍照", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtil.photograph(SampleMainActivity.this);
            }
        }).addSheetItem("从相冊选取", ActionSheetDialog.SheetItemColor.Blue, new ActionSheetDialog.OnSheetItemClickListener() {
            @Override
            public void onClick(int which) {
                PhotoUtil.selectPictureFromAlbum(SampleMainActivity.this);
            }
        }).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//
//        if (requestCode == Crop.REQUEST_PICK && resultCode == RESULT_OK) {
//            beginCrop(data.getData());
//        } else if (requestCode == Crop.REQUEST_CROP) {
//            handleCrop(resultCode, data);
//        }

        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ParameterUtils.REQUEST_CODE_CHANGEPHOTO:
                if ("from_capture".equals(data.getStringExtra("flag_from"))) {
                    photoSaveName = System.currentTimeMillis() + ".png";
                    photoSaveFile = SDCardUtils.getFileDirPath("Xxd" + File.separator + "pictures");// 存放照片的文件夹
                    Utils.startActionCapture(SampleMainActivity.this, new File(photoSaveFile.getAbsolutePath(), photoSaveName), ParameterUtils.REQUEST_CODE_CAMERA);
                }
                if ("from_album".equals(data.getStringExtra("flag_from"))) {
                    selected_path = data.getStringExtra("path");
                    Intent toClipImage = new Intent(SampleMainActivity.this, ClipPictureActivity.class);
                    toClipImage.putExtra("path", selected_path);
                    toClipImage.putExtra("clipType", ClipImageLayout.SQUARE);
                    this.startActivityForResult(toClipImage, ParameterUtils.REQUEST_CODE_CLIP_OVER);
                }
                break;
            case ParameterUtils.REQUEST_CODE_CAMERA:
                String path_capture = photoSaveFile.getAbsolutePath() + "/" + photoSaveName;
                Intent toClipImage = new Intent(getApplicationContext(), ClipPictureActivity.class);
                toClipImage.putExtra("path", path_capture);
                toClipImage.putExtra("clipType", ClipImageLayout.SQUARE);
                startActivityForResult(toClipImage, ParameterUtils.REQUEST_CODE_CLIP_OVER);
                break;
            case ParameterUtils.REQUEST_CODE_CLIP_OVER:
                final String temppath = data.getStringExtra("path");
                DisplayImageUtils.downloadImageFile(getApplicationContext(), temppath, new SimpleTarget<File>() {

                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        photoFile = resource;
                        DisplayImageUtils.displayPersonRes(SampleMainActivity.this, resource, ivIcon);
                        Bitmap bitmap = BitmapFactory.decodeFile(resource.getPath());
                        showMainColor(bitmap);
                        tvDistance.setText(HSVUtil.distanceOf(getBZHSVUtil(), new HSVUtil(171, 87, 206)) + "");


                    }
                });
                break;

            default:
                break;
        }

//        if (resultCode == PhotoUtil.NONE) {
//            return;
//        }
//        // 拍照
//        if (requestCode == PhotoUtil.PHOTOGRAPH) {
//            // 设置文件保存路径这里放在跟文件夹下
//            File picture = null;
//            if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
//                picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
//                if (!picture.exists()) {
//                    picture = new File(Environment.getExternalStorageDirectory() + PhotoUtil.imageName);
//                }
//            } else {
//                picture = new File(this.getFilesDir() + PhotoUtil.imageName);
//                if (!picture.exists()) {
//                    picture = new File(this.getFilesDir() + PhotoUtil.imageName);
//                }
//            }
//
//            path = PhotoUtil.getPath(this);// 生成一个地址用于存放剪辑后的图片
//            if (TextUtils.isEmpty(path)) {
//                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
//                return;
//            }
//            Uri imageUri = Uri.fromFile(new File(path));
//            PhotoUtil.startPhotoZoom(this, Uri.fromFile(picture), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
//        }
//
//        if (data == null) {
//            return;
//        }
//
//        // 读取相冊缩放图片
//        if (requestCode == PhotoUtil.PHOTOZOOM) {
//
//            path = PhotoUtil.getPath(this);// 生成一个地址用于存放剪辑后的图片
//            if (TextUtils.isEmpty(path)) {
//                Log.e(TAG, "随机生成的用于存放剪辑后的图片的地址失败");
//                return;
//            }
//            Uri imageUri = Uri.fromFile(new File(path));
//            PhotoUtil.startPhotoZoom(this, data.getData(), PhotoUtil.PICTURE_HEIGHT, PhotoUtil.PICTURE_WIDTH, imageUri);
//        }
//        // 处理结果
//        if (requestCode == PhotoUtil.PHOTORESOULT) {
        /**
         * 在这里处理剪辑结果。能够获取缩略图，获取剪辑图片的地址。得到这些信息能够选则用于上传图片等等操作
         * */
//            Bitmap bitmap2 = PhotoUtil.convertToBitmap(path,10,10);
//            if (bitmap2 != null) {
//                ivIcon.setImageBitmap(bitmap2);
//                showMainColor(bitmap2);
//            }

//            ivIcon.setImageDrawable(null);
//            Log.w("kim","--->"+path);
//            Log.w("kim","---->"+PhotoUtil.getImageContentUri(this,path));
//            ivIcon.setImageBitmap(PhotoUtil.convertToBitmap(path,300,300));
//            ivIcon.setImageURI(PhotoUtil.getImageContentUri(this,path));
//            try {
//                showMainColor(PhotoUtil.converToBitmapByUri(this,PhotoUtil.getImageContentUri(this,path)));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }

//        }
        super.

                onActivityResult(requestCode, resultCode, data);

    }


    private void showMainColor(Bitmap bitmap) {
        if (bitmap == null || bitmap.equals("")) {
            Toast.makeText(this, "截图失败，请重新截图", Toast.LENGTH_LONG).show();
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

        Log.w("kim", "r-->" + dominantColor[0] + "====" + "g---->" + dominantColor[1] + "====" + "b--->" + dominantColor[2]);
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


    private void beginCrop(Uri source) {
        Uri destination = Uri.fromFile(new File(getCacheDir(), "cropped"));
        Crop.of(source, destination).asSquare().start(this);
    }

    private void handleCrop(int resultCode, Intent result) {
//        int hsv=Color.HSVToColor()
        if (resultCode == RESULT_OK) {
            ivIcon.setImageDrawable(null);
            ivIcon.setImageURI(Crop.getOutput(result));
            try {
                showMainColor(PhotoUtil.converToBitmapByUri(this, Crop.getOutput(result)));
                Log.w("kim","h--->"+getBZHSVUtil().getH()+"s---->"+getBZHSVUtil().getS()+"v--->"+getBZHSVUtil().getV());
                tvDistance.setText(HSVUtil.distanceOf(getBZHSVUtil(), new HSVUtil(171, 89, 206)) + "");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (resultCode == Crop.RESULT_ERROR) {
            Toast.makeText(this, Crop.getError(result).getMessage(), Toast.LENGTH_SHORT).show();
        }
    }


    private HSVUtil getBZHSVUtil() {
        HSVUtil hsvUtil = new HSVUtil();
        Drawable background = llDominantColor.getBackground();
        ColorDrawable colorDrawable = (ColorDrawable) background;
        int color = colorDrawable.getColor();
        float red = (color & 0xff0000) >> 16;
        float green = (color & 0x00ff00) >> 8;
        float blue = (color & 0x0000ff);
        hsvUtil.RGBtoHSV(red, green, blue);
        return hsvUtil;
    }


    private BitmapFactory.Options getBitmapOption(int inSampleSize) {
        System.gc();
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPurgeable = true;
        options.inSampleSize = inSampleSize;
        return options;
    }
}
