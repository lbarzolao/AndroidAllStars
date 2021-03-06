package com.belatrixsf.allstars.services;

import com.belatrixsf.allstars.networking.retrofit.requests.StarRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.StarResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.StarSubCategoryResponse;
import com.belatrixsf.allstars.utils.AllStarsCallback;

/**
 * Created by PedroCarrillo on 4/26/16.
 */
public interface StarService {

    void getEmployeeSubCategoriesStars(int employeeId, AllStarsCallback<StarSubCategoryResponse> callback);

    void star(int fromEmployeeId, int toEmployeeId, StarRequest starRequest, AllStarsCallback<StarResponse> callback);


}
