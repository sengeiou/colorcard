package com.color.card.util;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yqy
 * @date on 2018/4/4
 * @describe TODO
 * @org xxd.smartstudy.com
 * @email yeqingyu@innobuddy.com
 */
public class HandleUtil {
    public static String handleData(){
        Map<String, String[]> map = new HashMap<String, String[]>();
        map.put("_api", new String[]{"pd.general.detection.event.data.upload"});
        map.put("_sy", new String[]{"mapi||1||2||3||4||chenjunqi.com:8080"});
        map.put("_v", new String[]{"1.0"});
        map.put("_t", new String[]{"1522830409521"});
        map.put("_appId", new String[]{"1"});
        map.put("_se", new String[]{"e26999eab977412183b443b336ff4523"});

        map.put("eventId", new String[]{"58"});
        map.put("detectionType", new String[]{"urine"});
        map.put("valueLevel", new String[]{"偏高"});
        map.put("dateTime", new String[]{"1522830409521"});
        map.put("value", new String[]{"16.3"});

        String at = TokenUtil.token(TokenUtil.getParameterMap2(map), "pudong*mapi");
        map.put("_at", new String[]{at});


        StringBuilder builder = new StringBuilder();
        builder.append("curl -d \"");
        for (Map.Entry<String, String[]> entry : map.entrySet()) {
            builder.append("&");
            builder.append(entry.getKey());
            builder.append("=");
            builder.append(entry.getValue()[0]);
        }
        builder.delete(9, 10);
        builder.append("\" ");
        builder.append("http://139.224.237.182:8080/mapi");
        return builder.toString();
    }
}
