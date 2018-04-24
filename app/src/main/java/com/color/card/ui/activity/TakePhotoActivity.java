package com.color.card.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.text.Html;
import android.util.Log;
import android.view.Display;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.color.card.R;
import com.color.card.mvp.base.BasePresenter;
import com.color.card.ui.base.BaseActivity;
import com.color.card.util.PhotoUtil;
import com.color.card.util.ToastUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TakePhotoActivity extends BaseActivity implements SurfaceHolder.Callback,
        View.OnClickListener, Camera.PictureCallback {
    private SurfaceView mSurfaceView;

    private TextView mTvCountDown;

    private SurfaceHolder mHolder;

    private Camera mCamera;

    private ImageView cancel;

    private Handler mHandler = new Handler();

    private int mCurrentTimer = 10;

    private boolean mIsSurfaceCreated = false;
    private boolean mIsTimerRunning = false;
    private TextView count_down;
    private ProgressDialog mProgressDialog;
    String filePath = "";

    //  private static final int CAMERA_ID = 1; //前置摄像头
    private static final String TAG = TakePhotoActivity.class.getSimpleName();

    private static final int CAMERA_ID = 0; //后置摄像头

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initEvent();
        if (PackageManager.PERMISSION_GRANTED == ContextCompat.checkSelfPermission(this, android.Manifest.permission.RECORD_AUDIO)) {
            ToastUtils.shortToast(this,"没有拍照权限");
            finish();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopPreview();
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }


    @Override
    public void initEvent() {
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
        cancel.setOnClickListener(this);
    }

    @Override
    public void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
        mTvCountDown = (TextView) findViewById(R.id.count_down);
        count_down = findViewById(R.id.count_down);
        cancel = findViewById(R.id.cancel);
        getHeadView().setVisibility(View.GONE);
        if (!mIsTimerRunning) {
            mIsTimerRunning = true;
            mHandler.post(timerRunnable);
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        mIsSurfaceCreated = true;
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {
        startPreview();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        mIsSurfaceCreated = false;
    }


    private void startPreview() {
        if (mCamera != null || !mIsSurfaceCreated) {
            Log.d(TAG, "startPreview will return");
            return;
        }
//        int cameraNums = Camera.getNumberOfCameras();
        mCamera = Camera.open(CAMERA_ID);


        Camera.Parameters parameters = mCamera.getParameters();

        int width = getResources().getDisplayMetrics().widthPixels;
        int height = getResources().getDisplayMetrics().heightPixels;
        Camera.Size size = getBestPreviewSize(width, height, parameters);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//获取窗口的管理器
        Display display = wm.getDefaultDisplay();//获得窗口里面的屏幕

        if (size != null) {
            //设置预览分辨率
            parameters.setPreviewSize(size.width, size.height);
            //设置保存图片的大小
//            parameters.setPictureSize(size.width, size.height);
        }

        //自动对焦
        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_AUTO);
        parameters.setPreviewFrameRate(20);

        //设置相机预览方向
        mCamera.setDisplayOrientation(90);

        mCamera.setParameters(parameters);


        try {
            mCamera.setPreviewDisplay(mHolder);
        } catch (Exception e) {
            Log.d(TAG, e.getMessage());
        }

        mCamera.startPreview();
    }


    private void stopPreview() {
        //释放Camera对象
        if (mCamera != null) {
            try {
                mCamera.setPreviewDisplay(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            } catch (Exception e) {
                Log.e(TAG, e.getMessage());
            }
        }
    }

    private Camera.Size getBestPreviewSize(int width, int height,
                                           Camera.Parameters parameters) {
        Camera.Size result = null;

        for (Camera.Size size : parameters.getSupportedPreviewSizes()) {
            if (size.width <= width && size.height <= height) {
                if (result == null) {
                    result = size;
                } else {
                    int resultArea = result.width * result.height;
                    int newArea = size.width * size.height;

                    if (newArea > resultArea) {
                        result = size;
                    }
                }
            }
        }

        return result;
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.start:
                break;

            case R.id.cancel:
                finish();
                break;
            default:
                break;
        }
    }


    private Runnable timerRunnable = new Runnable() {
        @Override
        public void run() {
            if (mCurrentTimer > 0) {
                String time = "倒计时<font color='#FF1300'>" + mCurrentTimer + "</font>秒";
                mTvCountDown.setText(Html.fromHtml(time));
                mCurrentTimer--;
                mHandler.postDelayed(timerRunnable, 1000);
            } else {
                mTvCountDown.setText("");
                mTvCountDown.setBackground(null);
                mCamera.takePicture(null, null, null, TakePhotoActivity.this);
                playSound();
                mIsTimerRunning = false;
                mCurrentTimer = 10;
                mProgressDialog = ProgressDialog.show(TakePhotoActivity.this, null, "正在获取数据");
            }
        }
    };


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {

        try {
            filePath = Environment.getExternalStorageDirectory() + File.separator +
                    System.currentTimeMillis() + ".png";

//            filePath= Environment.getExternalStorageDirectory()
//                    + File.separator + Environment.DIRECTORY_DCIM
//                    +File.separator+"Camera"+File.separator;
            FileOutputStream fos = new FileOutputStream(new File(filePath));

            //旋转角度，保证保存的图片方向是对的
            Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
            Matrix matrix = new Matrix();
            matrix.setRotate(90);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0,
                    bitmap.getWidth(), bitmap.getHeight(), matrix, true);
            bitmap.compress(Bitmap.CompressFormat.PNG, 100, fos);
            fos.flush();
            fos.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        mCamera.startPreview();
        Intent intent = new Intent();
        intent.putExtra("filePath", filePath);
        intent.setClass(TakePhotoActivity.this, ResultDetailActivity.class);
        startActivity(intent);
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
        finish();
    }


    /**
     * 播放系统拍照声音
     */
    public void playSound() {
        MediaPlayer mediaPlayer = null;
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

        if (volume != 0) {
            if (mediaPlayer == null) {
                mediaPlayer = MediaPlayer.create(this,
                        Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            }
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }
    }


}
