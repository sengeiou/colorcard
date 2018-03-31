package com.color.card.ui.activity;

import android.content.Context;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class TakePhotoActivity extends BaseActivity implements SurfaceHolder.Callback,
        View.OnClickListener, Camera.PictureCallback {
    private SurfaceView mSurfaceView;
    //    private ImageView mIvStart;
    private TextView mTvCountDown;

    private SurfaceHolder mHolder;

    private Camera mCamera;

    private Handler mHandler = new Handler();

    private int mCurrentTimer = 180;

    private boolean mIsSurfaceCreated = false;
    private boolean mIsTimerRunning = false;
    private TextView count_down;

    //  private static final int CAMERA_ID = 1; //前置摄像头
    private static final String TAG = TakePhotoActivity.class.getSimpleName();

    private static final int CAMERA_ID = 0; //后置摄像头

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_photo);
        initView();
        initEvent();
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


    public void initEvent() {
        mHolder = mSurfaceView.getHolder();
        mHolder.addCallback(this);
//        mIvStart.setOnClickListener(this);
    }

    @Override
    public void initView() {
        mSurfaceView = (SurfaceView) findViewById(R.id.surface_view);
//        mIvStart = (ImageView) findViewById(R.id.start);
        mTvCountDown = (TextView) findViewById(R.id.count_down);
        count_down = findViewById(R.id.count_down);
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

        mCamera = Camera.open(CAMERA_ID);

        Camera.Parameters parameters = mCamera.getParameters();
        int width = 0;
        int height = 0;

        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//获取窗口的管理器
        Display display = wm.getDefaultDisplay();//获得窗口里面的屏幕

        // 选择合适的预览尺寸
        List<Camera.Size> sizeList = parameters.getSupportedPreviewSizes();

        // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
        if (sizeList.size() > 1) {
            Iterator<Camera.Size> itor = sizeList.iterator();
            while (itor.hasNext()) {
                Camera.Size cur = itor.next();
                if (cur.width >= width
                        && cur.height >= height) {
                    width = cur.width;
                    height = cur.height;
                    break;
                }
            }
        }

        parameters.setPreviewSize(width, height); //获得摄像区域的大小

        Camera.Size size = getBestPreviewSize(width, height, parameters);
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

                mCamera.takePicture(null, null, null, TakePhotoActivity.this);
                playSound();

                mIsTimerRunning = false;
                mCurrentTimer = 180;
            }
        }
    };


    @Override
    public void onPictureTaken(byte[] data, Camera camera) {
        try {
            FileOutputStream fos = new FileOutputStream(new File
                    (Environment.getExternalStorageDirectory() + File.separator +
                            System.currentTimeMillis() + ".png"));

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
    }


    /**
     * 播放系统拍照声音
     */
    public void playSound() {
        MediaPlayer mediaPlayer = null;
        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int volume = audioManager.getStreamVolume(AudioManager.STREAM_NOTIFICATION);

        if (volume != 0) {
            if (mediaPlayer == null)
                mediaPlayer = MediaPlayer.create(this,
                        Uri.parse("file:///system/media/audio/ui/camera_click.ogg"));
            if (mediaPlayer != null) {
                mediaPlayer.start();
            }
        }
    }


}
