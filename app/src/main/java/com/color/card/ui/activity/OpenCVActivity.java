//package com.color.card.ui.activity;
//
//import android.app.Activity;
//import android.graphics.Bitmap;
//import android.graphics.BitmapFactory;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.View;
//import android.widget.Button;
//import android.widget.ImageView;
//
//
//import com.color.card.R;
//
//import org.opencv.android.BaseLoaderCallback;
//import org.opencv.android.LoaderCallbackInterface;
//import org.opencv.android.OpenCVLoader;
//import org.opencv.android.Utils;
//import org.opencv.core.Mat;
//import org.opencv.core.MatOfRect;
//import org.opencv.core.Point;
//import org.opencv.core.Rect;
//import org.opencv.core.Scalar;
//import org.opencv.imgproc.Imgproc;
//
//import java.io.IOException;
//
//import static org.opencv.imgcodecs.Imgcodecs.imread;
//import static org.opencv.imgproc.Imgproc.CV_HOUGH_GRADIENT;
//import static org.opencv.imgproc.Imgproc.GaussianBlur;
//
//public class OpenCVActivity extends Activity {
//
//    private Button btn;
//    private ImageView img;
//
//    private Bitmap srcBitmap;
//    private Bitmap grayBitmap;
//    Bitmap we;
//
//    Mat img1;
//    private static boolean flag = true;
//    private static boolean isFirst = true;
//    private static final String TAG = "gao_chun";
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_opencv);
//
//        img = (ImageView) findViewById(R.id.img);
//        btn = (Button) findViewById(R.id.btn);
//        btn.setOnClickListener(new ProcessClickListener());
//    }
//
//
//    @Override
//    protected void onResume() {
//        super.onResume();
//        //load OpenCV engine and init OpenCV library
//       /* OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, getApplicationContext(), mLoaderCallback);
//        Log.i(TAG, "onResume sucess load OpenCV...");*/
//
//
//        if (!OpenCVLoader.initDebug()) {
//            Log.d(TAG, "Internal OpenCV library not found. Using OpenCV Manager for initialization");
//            OpenCVLoader.initAsync(OpenCVLoader.OPENCV_VERSION_3_2_0, this, mLoaderCallback);
//        } else {
//            Log.d(TAG, "OpenCV library found inside package. Using it!");
//            mLoaderCallback.onManagerConnected(LoaderCallbackInterface.SUCCESS);
//        }
//    }
//
//
//    //OpenCV库加载并初始化成功后的回调函数
//    private BaseLoaderCallback mLoaderCallback = new BaseLoaderCallback(this) {
//
//        @Override
//        public void onManagerConnected(int status) {
//            switch (status) {
//                case BaseLoaderCallback.SUCCESS:
//                    Log.i(TAG, "成功加载");
//                    break;
//                default:
//                    super.onManagerConnected(status);
//                    Log.i(TAG, "加载失败");
//                    break;
//            }
//        }
//    };
//
////    public void cicle(){
////        Mat srcImage = imread("yuan.png");
////        Mat midImage, dstImage;//临时变量和目标图的定义
////
////        //【2】显示原始图
////        imshow("【原始图】", srcImage);
////
////        //【3】转为灰度图，进行图像平滑
////        Imgproc.cvtColor(srcImage, midImage, Imgproc.CV_BGR2GRAY);//转化边缘检测后的图为灰度图
////        GaussianBlur(midImage, midImage, Size(9, 9), 2, 2);
////
////        //【4】进行霍夫圆变换
////        vector<Vec3f> circles;//保存矢量
////        HoughCircles(midImage, circles, CV_HOUGH_GRADIENT, 1.5, 10, 200, 100, 0, 0);
////
////        //【5】依次在图中绘制出圆
////        for (size_t i = 0; i < circles.size(); i++)
////        {
////            Point center(cvRound(circles[i][0]), cvRound(circles[i][1]));
////            int radius = Imgproc.cvRound(circles[i][2]);
////            //绘制圆心
////            circle(srcImage, center, 3, Scalar(0, 255, 0), -1, 8, 0);
////            //绘制圆轮廓
////            circle(srcImage, center, radius, Scalar(155, 50, 255), 3, 8, 0);
////            //Scalar(55,100,195)参数中G、B、R颜色值的数值，得到想要的颜色
////        }
////
////        //【6】显示效果图
////        imshow("【效果图】", srcImage);
////
////        waitKey(0);
////    }
//
//
//    public void procSrc2Gray() {
//        Mat rgbMat = new Mat();
//        Mat grayMat = new Mat();
//        srcBitmap = BitmapFactory.decodeResource(getResources(), R.drawable.niao);
//        grayBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.RGB_565);
//        Utils.matToBitmap( rgbMat,srcBitmap);//convert original bitmap to Mat, R G B.
//        Imgproc.cvtColor(rgbMat, grayMat, Imgproc.COLOR_RGB2GRAY);//rgbMat to gray grayMat
//        Utils.matToBitmap(grayMat, grayBitmap); //convert mat to bitmap
//        Log.i(TAG, "procSrc2Gray sucess...");
//        int radius = 60;
//        Log.w("kim", "--->" + rgbMat.cols() / 2);
//        Point center = new Point(rgbMat.cols() / 2, rgbMat.rows() / 2);
//        Imgproc.circle(rgbMat, center, radius, new Scalar(0, 200, 100), 2, 8, 0);
////        try {
////
////            img1=Utils.loadResource(this, R.drawable.niao);
////            Mat grayImage= new Mat();
////            Imgproc.cvtColor(img1,grayImage,Imgproc.COLOR_RGB2GRAY);//转化为灰度图
////            MatOfRect circles = new MatOfRect();
////            Imgproc.HoughCircles(grayImage, circles, Imgproc.CV_HOUGH_GRADIENT,  1.2, 50,200,100,0,0);
////            Log.w("kim", "circles.cols() " + circles.cols());
////            Rect[] facesArray = circles.toArray();
////            for (int i = 0; i < facesArray.length; i++) {
////                //绘制识别出人脸的位置上的绿色方框
////                Imgproc.circle(img1, facesArray[i].tl(), (int) ((facesArray[i].width+facesArray[i].height)*0.25), new Scalar(105,193,0), 3);
////                Utils.matToBitmap(img1, we);
////            }
//
//
//    }
////        HoughCircles(grayImage,circles,HOUGH_GRADIENT,1,10,200,100,0,0);
//
//    // returns number of circular objects found
//
////            Mat hh=circles.clone();
////            Imgproc.cvCircle(src1,cvPoint(150,150),100,CV_RGB(0,0,255),5);
//
//
////            Imgproc.circle(img1,new Point(150,150),100,new Scalar(0,0,255),5);
//
//
//
//
//
//public class ProcessClickListener implements View.OnClickListener {
//
//    @Override
//    public void onClick(View v) {
//
//        if (isFirst) {
//            procSrc2Gray();
//            isFirst = false;
//        }
//
//        if (flag) {
//            img.setImageBitmap(srcBitmap);
//            btn.setText("查看原图");
//            flag = false;
//        } else {
//            img.setImageBitmap(grayBitmap);
//            btn.setText("灰度化");
//            flag = true;
//        }
//    }
//}
//}
