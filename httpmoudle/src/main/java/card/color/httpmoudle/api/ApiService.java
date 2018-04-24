package card.color.httpmoudle.api;

import java.util.Map;

import card.color.basemoudle.util.ConstantUtils;
import card.color.basemoudle.util.HttpUrlUtils;
import card.color.basemoudle.util.ParameterUtils;
import card.color.httpmoudle.entity.ResponseInfo;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface ApiService {

    /**
     * 获取验证码
     * @param
     * @return
     */
    @POST(HttpUrlUtils.URL_COMMON)
    Observable<ResponseInfo> getApiRequset(@QueryMap Map<String, String> params);

    @POST(HttpUrlUtils.URL_COMMON)
    Observable<ResponseInfo> getApiRequset2(@QueryMap Map<String, String[]> params);

    @GET(HttpUrlUtils.URL_COMMON)
    Observable<ResponseInfo> getApiRequsetByGet(@QueryMap Map<String, Object> params);

}
