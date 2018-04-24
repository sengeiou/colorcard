package com.color.card.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.InputType;
import android.text.TextUtils;
import android.transition.Transition;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.sdk.android.oss.ClientConfiguration;
import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.common.OSSLog;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.color.card.MainActivity;
import com.color.card.R;
import com.color.card.entity.BindInfo;
import com.color.card.entity.TeamEntity;
import com.color.card.entity.UserAvatarInfo;
import com.color.card.mvp.contract.MyFragmentContract;
import com.color.card.mvp.presenter.MyFragmentPresenter;
import com.color.card.ui.activity.ClipPictureActivity;
import com.color.card.ui.activity.SelectMyPhotoActivity;
import com.color.card.ui.adapter.base.CommonAdapter;
import com.color.card.ui.adapter.base.ViewHolder;
import com.color.card.ui.adapter.wrapper.HeaderAndFooterWrapper;
import com.color.card.ui.adapter.wrapper.LoadMoreWrapper;
import com.color.card.ui.base.UIFragment;
import com.color.card.ui.widget.ClipImageLayout;
import com.color.card.ui.widget.LoadMoreRecyclerView;
import com.color.card.ui.widget.NoScrollLinearLayoutManager;
import com.color.card.ui.widget.decoration.HorizontalDividerItemDecoration;
import com.color.card.util.Config;
import com.color.card.util.DateUtil;
import com.color.card.util.DensityUtils;
import com.color.card.util.DisplayImageUtils;
import com.color.card.util.ImageService;
import com.color.card.util.OssService;
import com.color.card.util.PhotoUtil;
import com.color.card.util.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import card.color.basemoudle.util.ParameterUtils;
import card.color.basemoudle.util.SPCacheUtils;

/**
 * @author yqy
 * @date on 2018/4/7
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class MyFragment extends UIFragment<MyFragmentContract.Presenter> implements MyFragmentContract.View {
    private LoadMoreRecyclerView rv_team;

    private LoadMoreWrapper<TeamEntity.User> loadMoreWrapper;

    List<TeamEntity.User> teamEntityList;

    private NoScrollLinearLayoutManager mLayoutManager;

    private CommonAdapter<TeamEntity.User> mAdapter;

    private HeaderAndFooterWrapper mHeader;
    private File photoSaveFile;// 保存文件夹
    private String photoSaveName = null;// 图片名

    private View headView;

    private SwipeRefreshLayout srlt_qa;

    private int mPage = 0;

    private MainActivity mainActivity;

    private ImageView iv_avatar;

    private TextView tv_name;

    private TextView tv_shenfen;

    private TextView tvInvisite;

    private TextView tv_have_no;

    private LinearLayout ll_info;

    private String mobile = "";

    private TextView tv_duizhang;

    private File photoFile;

    String AK = "LTAIix5fXPbUi6Zj";

    String SK = "TT3icsKAIiOrPyZd0YGblqTSOlQPiC";

    String editBucketName = "kiro-oss-prd";

    private ImageService imageService;

    private OSS oss;

    private OssService ossService;

    private String imgEndpoint = "http://oss-cn-beijing.aliyuncs.com";

    private final String mBucket = Config.bucket;

    private String mRegion = "";//杭州

    private TextView tv_count;

    private String userId;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mainActivity = (MainActivity) activity;
        EventBus.getDefault().register(this);
    }

    @Override
    public void onUserInvisible() {
        if (srlt_qa != null && srlt_qa.isRefreshing()) {
            srlt_qa.setRefreshing(false);
        }
        mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#2ECE63"));
        mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#123D57"));
        super.onUserInvisible();
    }

    @Override
    public void onFirstUserVisible() {
        super.onFirstUserVisible();
        mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#2ECE63"));
        mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#123D57"));
        if (srlt_qa != null && srlt_qa.isRefreshing()) {
            srlt_qa.setRefreshing(false);
        }
        super.onFirstUserInvisible();
    }

    @Override
    public void onUserVisible() {
        super.onUserVisible();
        mainActivity.getLl_title().setBackgroundColor(Color.parseColor("#2ECE63"));
        mainActivity.getTv_qick_check().setTextColor(Color.parseColor("#123D57"));
    }

    @Override
    protected View getLayoutView() {
        return mActivity.getLayoutInflater().inflate(R.layout.fragment_my, null);
    }

    @Override
    protected void initView(View rootView) {
        userId = SPCacheUtils.get("userId", "").toString();
        rv_team = (LoadMoreRecyclerView) rootView.findViewById(R.id.rv_my_team);
        srlt_qa = rootView.findViewById(R.id.srlt_qa);
        srlt_qa.setColorSchemeColors(getResources().getColor(R.color.app_main_color));
        mLayoutManager = new NoScrollLinearLayoutManager(getActivity());
        mLayoutManager = new NoScrollLinearLayoutManager(getActivity());
        mLayoutManager.setScrollEnabled(true);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rv_team.setLayoutManager(mLayoutManager);
        rv_team.addItemDecoration(new HorizontalDividerItemDecoration.Builder(getActivity())
            .size(DensityUtils.dip2px(0.5f)).margin(DensityUtils.dip2px(16), 0).colorResId(R.color.bg_home_search).build());
        initAdapter();
        initRegion();
        initHeaderAndFooter();
        presenter.getBind();
        srlt_qa.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if (!TextUtils.isEmpty(mobile)) {
                    mPage = 0;
                    presenter.getUserList(mPage + "", mobile, ParameterUtils.PULL_DOWN);
                } else {
                    srlt_qa.setRefreshing(false);
                }
            }
        });
        ossService = initOSS(Config.endpoint, Config.bucket);
        //设置上传的callback地址，目前暂时只支持putObject的回调
        ossService.setCallbackAddress(Config.callbackAddress);

        //图片服务和OSS使用不同的endpoint，但是可以共用SDK，因此只需要初始化不同endpoint的OssService即可
        imageService = new ImageService(initOSS(imgEndpoint, mBucket));

        ossService.setBucketName(mBucket);
        String newOssEndpoint = getOssEndpoint();
        String newImageEndpoint = getImgEndpoint();
        Log.d(newOssEndpoint, newImageEndpoint);

        OSSCredentialProvider credentialProvider;
        credentialProvider = new OSSPlainTextAKSKCredentialProvider(AK, SK);
        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次
        oss = new OSSClient(mActivity, newOssEndpoint, credentialProvider, conf);
        imageService = new ImageService(initOSS(newImageEndpoint, mBucket));
        ossService.initOss(oss);
    }


    private void initAdapter() {
        teamEntityList = new ArrayList<>();
        mAdapter = new CommonAdapter<TeamEntity.User>(getActivity(), R.layout.item_user_info, teamEntityList) {
            @Override
            protected void convert(ViewHolder holder, TeamEntity.User user, int position) {
                holder.setPersonImageUrl(R.id.iv_avatar, user.getAvatar(), true);
                holder.setText(R.id.tv_name, user.getRealname());
                holder.setText(R.id.tv_time, DateUtil.getStringDate(user.getCreatedTime()));
            }
        };
    }


    private void initHeaderAndFooter() {
        headView = getActivity().getLayoutInflater().inflate(R.layout.layout_my_header, null, false);
        iv_avatar = headView.findViewById(R.id.iv_avatar);
        tv_name = headView.findViewById(R.id.tv_name);
        tv_shenfen = headView.findViewById(R.id.tv_shenfen);
        tvInvisite = headView.findViewById(R.id.invisite);
        tv_count = headView.findViewById(R.id.tv_count);
        tv_have_no = headView.findViewById(R.id.tv_have_no);
        tv_duizhang = headView.findViewById(R.id.tv_duizhang);
        ll_info = headView.findViewById(R.id.ll_info);
        mHeader = new HeaderAndFooterWrapper(mAdapter);
        mHeader.addHeaderView(headView);
        loadMoreWrapper = new LoadMoreWrapper<>(mHeader);
        rv_team.setAdapter(loadMoreWrapper);
        rv_team.setFocusable(false);
        DisplayImageUtils.displayCircleImage(getActivity(), SPCacheUtils.get("avatar", "").toString(), iv_avatar);
        if (TextUtils.isEmpty(SPCacheUtils.get("nickname", "").toString())) {
            tv_name.setText("xxx");
        } else {
            tv_name.setText(SPCacheUtils.get("nickname", "").toString());
        }
        tv_count.setText(SPCacheUtils.get("count", "0").toString());
        rv_team.SetOnLoadMoreLister(new LoadMoreRecyclerView.OnLoadMoreListener() {
            @Override
            public void OnLoad() {
                if (srlt_qa.isRefreshing()) {
                    rv_team.loadComplete(true);
                    return;
                }
                if (!TextUtils.isEmpty(mobile)) {
                    mPage = mPage + 1;
                    presenter.getUserList(mPage + "", mobile, ParameterUtils.PULL_UP);
                } else {
                    rv_team.loadComplete(true);
                    srlt_qa.setRefreshing(false);
                }
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.invisite:
                showInputDialog("phone");
                break;

            case R.id.iv_avatar:
                Intent intent = new Intent(mActivity, SelectMyPhotoActivity.class);
                intent.putExtra("singlePic", true);
                startActivityForResult(intent, ParameterUtils.REQUEST_CODE_CHANGEPHOTO);
                break;

            case R.id.tv_name:
                showInputDialog("name");
                break;

            default:
                break;
        }
    }


    @Override
    protected void initEvent() {
        tvInvisite.setOnClickListener(this);
        tv_name.setOnClickListener(this);
        iv_avatar.setOnClickListener(this);
    }

    @Override
    public MyFragmentContract.Presenter initPresenter() {
        return new MyFragmentPresenter(this);
    }

    @Override
    public void showTip(String message) {
        if (presenter != null) {
            srlt_qa.setRefreshing(false);
            rv_team.loadComplete(true);
//            ToastUtils.shortToast(mActivity, message);
        }
    }

    @Override
    public void bindHave(BindInfo bindInfo) {
        if (bindInfo.isStatus() == false) {
            tv_have_no.setVisibility(View.VISIBLE);
            tvInvisite.setVisibility(View.VISIBLE);
            ll_info.setVisibility(View.GONE);
        } else {
            tv_duizhang.setText(bindInfo.getCreator().getRealname());
            presenter.getUserList(mPage + "", bindInfo.getCreator().getMobile(), ParameterUtils.PULL_DOWN);
            tv_have_no.setVisibility(View.GONE);
            tvInvisite.setVisibility(View.GONE);
            ll_info.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void agentSuccess(String id, String moblie) {
        presenter.bindAgent(id, moblie);
    }

    @Override
    public void bindAgentSuccess(String mobile) {
        this.mobile = mobile;
        ToastUtils.shortToast(getActivity(), "绑定成功");
        tv_have_no.setVisibility(View.GONE);
        tvInvisite.setVisibility(View.GONE);
        ll_info.setVisibility(View.VISIBLE);
    }

    @Override
    public void getUserListSuccess(List<TeamEntity.User> users, int request_state) {
        if (presenter != null) {
            mLayoutManager.setScrollEnabled(true);
            int len = users.size();
            if (request_state == ParameterUtils.PULL_DOWN) {
                //下拉刷新
                if (len <= 0) {
                    rv_team.loadComplete(true);
                    mLayoutManager.setScrollEnabled(false);
                }
                teamEntityList.clear();
                teamEntityList.addAll(users);
                srlt_qa.setRefreshing(false);
                loadMoreWrapper.notifyDataSetChanged();
            } else if (request_state == ParameterUtils.PULL_UP) {
                //上拉加载
                if (len <= 0) {
                    //没有更多内容
                    if (mPage > 1) {
                        mPage = mPage - 1;
                    }
                    rv_team.loadComplete(false);
                } else {
                    rv_team.loadComplete(true);
                    teamEntityList.addAll(users);
                    loadMoreWrapper.notifyDataSetChanged();
                }
            }
        }
        users = null;
    }

    @Override
    public void updateUserInfoSuccess() {
        ToastUtils.shortToast(mActivity, "数据更新成功");
    }


    private void showInputDialog(final String flag) {
    /*@setView 装入一个EditView
     */
        final AlertDialog.Builder inputDialog =
            new AlertDialog.Builder(getActivity());
        final View view = View.inflate(getActivity(), R.layout.dialog_layout, null);
        TextView tv_ttitle = view.findViewById(R.id.tv_ttitle);
        TextView tv_cancle = view.findViewById(R.id.tv_cancle);
        TextView tv_sure = view.findViewById(R.id.tv_sure);
        final EditText etPhone = view.findViewById(R.id.et_phone);
        if (flag.equals("name")) {
            tv_ttitle.setText("请填写名字");
            etPhone.setHint("请填写名字");
            etPhone.setInputType(InputType.TYPE_CLASS_TEXT);
        } else {
            tv_ttitle.setText("请填写邀请人手机号");
            etPhone.setInputType(InputType.TYPE_CLASS_PHONE);
            etPhone.setHint("请填写邀请人手机号");
        }
        inputDialog.setView(view);
        final AlertDialog dia = inputDialog.show();
        tv_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dia.dismiss();
            }
        });
        tv_sure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (flag.equals("name")) {
                    SPCacheUtils.put("nickname", etPhone.getText().toString());

                    presenter.updateUserInfo(userId, etPhone.getText().toString(), "", "");
                    tv_name.setText(etPhone.getText().toString());
                } else {
                    presenter.getAgent(etPhone.getText().toString());
                }
                dia.dismiss();
            }
        });
    }

    public OssService initOSS(String endpoint, String bucket) {
        OSSCredentialProvider credentialProvider;

        credentialProvider = new OSSPlainTextAKSKCredentialProvider(AK, SK);

        ClientConfiguration conf = new ClientConfiguration();
        conf.setConnectionTimeout(15 * 1000); // 连接超时，默认15秒
        conf.setSocketTimeout(15 * 1000); // socket超时，默认15秒
        conf.setMaxConcurrentRequest(5); // 最大并发请求书，默认5个
        conf.setMaxErrorRetry(2); // 失败后最大重试次数，默认2次

        OSS oss = new OSSClient(mActivity, endpoint, credentialProvider, conf);
        // 设置URL过期时间为10年  3600l* 1000*24*365*10
        Date expiration = new Date(System.currentTimeMillis() + 3600L * 1000 * 24 * 365 * 10);
        OSSLog.enableLog();
        return new OssService(oss, editBucketName);

    }


    protected String getOssEndpoint() {
        String ossEndpoint = "";
        if (mRegion.equals("杭州")) {
            ossEndpoint = "http://oss-cn-hangzhou.aliyuncs.com";
        } else if (mRegion.equals("青岛")) {
            ossEndpoint = "http://oss-cn-qingdao.aliyuncs.com";
        } else if (mRegion.equals("北京")) {
            ossEndpoint = "http://oss-cn-beijing.aliyuncs.com";
        } else if (mRegion.equals("深圳")) {
            ossEndpoint = "http://oss-cn-shenzhen.aliyuncs.com";
        } else if (mRegion.equals("美国")) {
            ossEndpoint = "http://oss-us-west-1.aliyuncs.com";
        } else if (mRegion.equals("上海")) {
            ossEndpoint = "http://oss-cn-shanghai.aliyuncs.com";
        } else {
            new AlertDialog.Builder(mActivity).setTitle("错误的区域").setMessage(mRegion).show();
        }
        return ossEndpoint;
    }

    protected void initRegion() {
        if (TextUtils.isEmpty(Config.endpoint)) {
            return;
        }
        if (Config.endpoint.contains("oss-cn-hangzhou")) {
            mRegion = "杭州";
            imgEndpoint = getImgEndpoint();
        } else if (Config.endpoint.contains("oss-cn-qingdao")) {
            mRegion = "青岛";
            imgEndpoint = getImgEndpoint();
        } else if (Config.endpoint.contains("oss-cn-beijing")) {
            mRegion = "北京";
            imgEndpoint = getImgEndpoint();
        } else if (Config.endpoint.contains("oss-cn-shenzhen")) {
            mRegion = "深圳";
            imgEndpoint = getImgEndpoint();
        } else if (Config.endpoint.contains("oss-us-west-1")) {
            mRegion = "美国";
            imgEndpoint = getImgEndpoint();
        } else if (Config.endpoint.contains("oss-cn-shanghai")) {
            mRegion = "上海";
            imgEndpoint = getImgEndpoint();
        } else {
            Toast.makeText(mActivity, "错误的区域", Toast.LENGTH_SHORT).show();
//            new AlertDialog.Builder(AuthTestActivity.this).setTitle("错误的区域").setMessage(mRegion).show();
        }
    }

    protected String getImgEndpoint() {
        String imgEndpoint = "";
        if (mRegion.equals("杭州")) {
            imgEndpoint = "http://img-cn-hangzhou.aliyuncs.com";
        } else if (mRegion.equals("青岛")) {
            imgEndpoint = "http://img-cn-qingdao.aliyuncs.com";
        } else if (mRegion.equals("北京")) {
            imgEndpoint = "http://img-cn-beijing.aliyuncs.com";
        } else if (mRegion.equals("深圳")) {
            imgEndpoint = "http://img-cn-shenzhen.aliyuncs.com";
        } else if (mRegion.equals("美国")) {
            imgEndpoint = "http://img-us-west-1.aliyuncs.com";
        } else if (mRegion.equals("上海")) {
            imgEndpoint = "http://img-cn-shanghai.aliyuncs.com";
        } else {
            new AlertDialog.Builder(mActivity).setTitle("错误的区域").setMessage(mRegion).show();
            imgEndpoint = "";
        }
        return imgEndpoint;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case ParameterUtils.REQUEST_CODE_CHANGEPHOTO:
                final String temppath = data.getStringExtra("path");
                try {
                    Bitmap bitmap = PhotoUtil.getBitmapFormUri(mActivity, Uri.parse(temppath));
                    File file = PhotoUtil.saveBitmapFile(bitmap, temppath);
                    String objectName = file.getName() + ".jpg";
                    ossService.asyncPutImage(objectName, file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                DisplayImageUtils.downloadImageFile(mActivity.getApplicationContext(), temppath, new SimpleTarget<File>(100, 100) {
                    @Override
                    public void onResourceReady(File resource, GlideAnimation<? super File> glideAnimation) {
                        photoFile = resource;
                        DisplayImageUtils.displayPersonRes(mActivity, resource, iv_avatar);
                    }
                });
                break;

            case ParameterUtils.REQUEST_CODE_CAMERA:
                String path_capture = photoSaveFile.getAbsolutePath() + "/" + photoSaveName;
                Intent toClipImage = new Intent(mActivity.getApplicationContext(), ClipPictureActivity.class);
                toClipImage.putExtra("path", path_capture);
                toClipImage.putExtra("clipType", ClipImageLayout.SQUARE);
                startActivityForResult(toClipImage, ParameterUtils.REQUEST_CODE_CLIP_OVER);
                break;
            default:
                break;
        }
    }


    @Override
    public void onDetach() {
        super.onDetach();
        EventBus.getDefault().unregister(this);
    }


    @Subscribe(threadMode = ThreadMode.MAIN) //在ui线程执行
    public void onDataSynEvent(UserAvatarInfo userAvatarInfo) {
        if (userAvatarInfo != null) {
            String url = null;
            try {
                url = oss.presignConstrainedObjectURL("kiro-oss-prd", userAvatarInfo.getKey(), 30 * 60);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            Log.w("kim", "--->" + url);
            SPCacheUtils.put("avatar", url);
            presenter.updateUserInfo(userId, "", "", url);
        }
    }
}
