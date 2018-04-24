//package com.color.card.ui.activity;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.os.Bundle;
//import android.os.Environment;
//import android.util.Log;
//import android.view.WindowManager;
//import android.widget.ImageView;
//import android.widget.LinearLayout;
//
//import com.color.card.R;
//
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.CameraBridgeViewBase;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.android.Utils;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.core.Size;
//import org.opencv.imgproc.Imgproc;
//import org.opencv.objdetect.CascadeClassifier;
//
//import java.io.File;
//import java.util.List;
//
///**
// * @author yqy
// * @date on 2018/4/10
// * @describe TODO
// * @org xxd.smartstudy.com
// * @email yeqingyu@innobuddy.com
// */
//public class CicleActivity extends Activity implements CameraBridgeViewBase.CvCameraViewListener2 {
//
//    Mat mRgba;
//    Mat mGray;
//    File mCascadeFile;
//    CascadeClassifier mJavaDetector;
//    CameraBridgeViewBase mOpenCvCameraView;
//    LinearLayout linearLayoutOne;
//    ImageView imageViewOne;
//    int counter = 0;
//    private Bitmap bitmap;
//
//    private ImageView iv;
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case LoaderCallbackInterface.SUCCESS: {
//                    Log.i("OPENCV", "OpenCV loaded successfully");
//                    mOpenCvCameraView.enableView();
//
//                }
//                break;
//                default: {
//                    super.onManagerConnected(status);
//                }
//                break;
//            }
//        }
//    };
//
//    /**
//     * Called when the activity is first created.
//     */
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//
//        if (!OpenCVLoader.initDebug()) {
//            // Handle initialization error
//        }
//
//        super.onCreate(savedInstanceState);
//
//        setContentView(R.layout.activity_coffee);
//
//        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
//
//        mOpenCvCameraView = (CameraBridgeViewBase) findViewById(R.id.fd_activity_surface_view);
//        mOpenCvCameraView.setMaxFrameSize(640, 480);
//        mOpenCvCameraView.setCvCameraViewListener(this);
//
//
//    }
//
//    @Override
//    public void onPause() {
//        super.onPause();
//        if (mOpenCvCameraView != null)
//            mOpenCvCameraView.disableView();
//    }
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //load OpenCV engine and init OpenCV library
//       /* OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, getApplicationContext(), mLoaderCallback);
//        Log.i(TAG, "onResume sucess load OpenCV...");*/
//
////
////        if (!OpenCVLoader.initDebug()) {
////            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
////        } else {
////            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
////        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        mOpenCvCameraView.disableView();
//    }
//
//    @Override
//    public void onCameraViewStarted(int width, int height) {
//        mGray = new Mat();
//        mRgba = new Mat();
//
//    }
//
//    @Override
//    public void onCameraViewStopped() {
//        mGray.release();
//        mRgba.release();
//
//    }
//
//    @Override
//    public Mat onCameraFrame(final CameraBridgeViewBase.CvCameraViewFrame inputFrame) {
//        mRgba = inputFrame.rgba();
//        mGray = inputFrame.gray();
//        Imgproc.cvtColor(mRgba, mGray, Imgproc.COLOR_RGB2GRAY);//转化为灰度图
//        if (counter == 9) {
//            MatOfRect circles = new MatOfRect();
//            Imgproc.HoughCircles(mGray, circles, Imgproc.CV_HOUGH_GRADIENT, 1.2, 50);
//            Log.e("circle check", "circles.cols() " + circles.cols());
////            Utils.matToBitmap(circles.clone(), bitmap);
//
////            if (bitmap != null) {
////                iv.setImageBitmap(bitmap);
////            }
//                int radius = 60;
//                Log.w("kim", "--->" + circles.cols() / 2);
//                Point center = new Point(mRgba.cols() / 2, mRgba.rows() / 2);
//                Imgproc.circle(mRgba, center, radius, new Scalar(0, 200, 100), 2, 8, 0);
////
////  Rect[] facesArray = circles.toArray();
////            for (int i = 0; i < facesArray.length; i++) {
////                //绘制识别出人脸的位置上的绿色方框
//////                    Imgproc.rectangle(mRgba, facesArray[i].tl(), facesArray[i].br(), new Scalar(0, 193, 0), 3);
////                saveImage(mRgba, facesArray[i], String.valueOf(System.currentTimeMillis()));
////                Imgproc.circle(mGray, new org.opencv.core.Point(facesArray[i].x, facesArray[i].y), 3, new Scalar(0, 255, 0), -1, 8, 0);
////                Imgproc.circle(mGray, new org.opencv.core.Point(facesArray[i].x, facesArray[i].y), facesArray[i].width / 2, new Scalar(155, 50, 255), 3, 8, 0);
////            }
//
//        }
//        counterAdder();
//        return mRgba;
//    }
//
//    public void counterAdder() {
//        if (counter > 10) {
//            counter = 0;
//
//        }
//        counter++;
//
//    }
//
//
//    public boolean saveImage(Mat image, Rect rect, String fileName) {
//        try {
//            String PATH = Environment.getExternalStorageDirectory() + "/FaceDetect/" + fileName + ".jpg";
//            // 把检测到的人脸重新定义大小后保存成文件
//            Mat sub = image.submat(rect);
//            Mat mat = new Mat();
//            Size size = new Size(100, 100);
//            Imgproc.resize(sub, mat, size);
//            return true;
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
//
//}