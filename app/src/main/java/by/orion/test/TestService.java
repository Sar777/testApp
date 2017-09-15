package by.orion.test;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface TestService {

    @GET("list.php")
    Call<List<String>> images();
}
