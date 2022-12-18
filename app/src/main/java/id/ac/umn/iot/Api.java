package id.ac.umn.iot;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface Api {

//    @Headers("Secret-key:$2a$10$WxkkTylkdR7NwGSoPwrfy.Odxtj7MR2vDtYZBp9cOd0SaYGVRmhOm")
    @GET(".")
    Call<GetDataApi> getDataApi();

    @GET(".")
    Call<GetTokenDevice> tokenDeviceRequest();

}
