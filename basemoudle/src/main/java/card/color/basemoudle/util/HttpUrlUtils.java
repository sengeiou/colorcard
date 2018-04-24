package card.color.basemoudle.util;

import android.util.DebugUtils;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

public class HttpUrlUtils {

    //    public static final String URL_KEY_SECRET = "poiDGVe391";
    public static final String URL_KEY_SECRET = "pudong*mapi";
    /**************************** api ***************************/
    public static final String URL_COMMON = "mapi";

    public static final String URL_PHONE_CODE = "pd.general.verify.sms.send";  //获取验证码
    public static final String URL_PHONE_CODE_CHECK = "pd.general.verify.sms.check";  //验证码检测
    public static final String URL_USER_CREATE = "pd.general.user.create";//创建用户
    public static final String URL_USER_LOGIN = "pd.general.user.login";//用户登录
    public static final String URL_CREAT_EVENT = "pd.general.detection.event.create";//创建容器
    public static final String URL_POST_CHECK_DATA = "pd.general.detection.event.data.upload";//提交检测数据
    public static final String URL_USER_DATA = "pd.general.detection.event.data.list";//用户的测试数据

    public static final String URL_USER_PROFILE = "pd.general.user.profile.get";
    public static final String URL_RECENT_CHECK = "pd.general.detection.event.data.getLatest";
    public static final String URL_CHECK_COUNT = "pd.general.detection.event.data.count";
    public static final String URL_CHECK_BIND = "pd.general.agent.bind.status";
    public static final String URL_GET_AGENT = "pd.general.agent.get";
    public static final String URL_BIND_AGENT = "pd.general.agent.bind";
    public static final String URL_LOGIN_AUTOMATIC = "pd.general.user.login";
    public static final String URL_USER_LIST = "pd.general.agent.list";
    public static final String URL_UPDATE_USER = "pd.general.user.profile.update";
    public static final String URL_POST_TAG = "pd.general.detection.event.data.upload.tag";

    /*********获取api接口url***********/
    public static String getBaseUrl() {
        String SERVER = "http://139.224.237.182:8080/";
        return SERVER;
    }


    public static Map<String, String> getCommonMap() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("_appId", "1");
        params.put("_t", String.valueOf(System.currentTimeMillis()));
        params.put("_v", "1.0");
        params.put("_sy", DeviceUtils.getUniquePsuedoID() + "||3||2||3||4||chenjunqi.com:8080");
        return params;
    }


    public static Map<String, String> getCommonMap2() {
        Map<String, String> params = new HashMap<String, String>();
        params.put("_appId", "1");
        params.put("_t", String.valueOf(System.currentTimeMillis()));
        params.put("_v", "2.0");
        params.put("_sy", DeviceUtils.getUniquePsuedoID() + "||3||2||3||4||chenjunqi.com:8080");
        return params;
    }
}
