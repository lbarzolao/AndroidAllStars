package com.belatrixsf.allstars.networking.retrofit.api;

import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarKeywordListResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarsResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarAPI {

    @GET(ServerPaths.EMPLOYEE_SUBCATEGORY_LIST)
    Call<StarSubCategoryResponse> getEmployeeSubCategoriesStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @POST(ServerPaths.STAR_EMPLOYEE)
    Call<StarResponse> star(@Path(ServerPaths.FROM_EMPLOYEE) int fromEmployeeId, @Path(ServerPaths.TO_EMPLOYEE) int toEmployeeId, @Body StarRequest request);

    @GET(ServerPaths.STARS_BY_EMPLOYEE_AND_SUBCATEGORY)
    Call<StarsResponse> getStars(@Path(ServerPaths.EMPLOYEE_ID) int employeeId, @Path(ServerPaths.SUBCATEGORY_ID) int subcategoryId, @Query(ServerPaths.QUERY_PAGE) Integer page);

    @GET(ServerPaths.STARS_BY_KEYWORD)
    Call<StarKeywordListResponse> getStarKeywordList(@Path(ServerPaths.KEYWORD_ID) int keywordId, @Query(ServerPaths.QUERY_PAGE) Integer page);

}
