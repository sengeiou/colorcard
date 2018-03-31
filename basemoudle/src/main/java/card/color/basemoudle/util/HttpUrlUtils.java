package card.color.basemoudle.util;

import java.util.HashMap;
import java.util.Map;

public class HttpUrlUtils {

    public static final String URL_KEY_SECRET = "poiDGVe391";
    /**************************** api ***************************/
    public static final String URL_COMMON = "mapi";

    public static final String URL_PHONE_CODE = "pd.general.verify.sms.send";  //获取验证码
    public static final String URL_PHONE_CODE_CHECK = "pd.general.verify.sms.check";  //验证码检测
    public static final String URL_USER_CREATE = "pd.general.user.create";//创建用户
    public static final String URL_USER_LOGIN = "pd.general.user.login";//用户登录


    /*********获取api接口url***********/
    public static String getBaseUrl() {
        String SERVER = "http://139.224.237.182:8080/";
        return SERVER;
    }


    public static Map<String, Object> getCommonMap() {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("_appId", 2);
        params.put("_t", System.currentTimeMillis());
        params.put("_v", "1.0");
        return params;
    }
}
