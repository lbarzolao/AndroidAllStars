/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrixsf.allstars.networking.retrofit.api;

import com.belatrixsf.allstars.entities.Category;
import com.belatrixsf.allstars.entities.Employee;
import com.belatrixsf.allstars.networking.retrofit.requests.AuthenticationRequest;
import com.belatrixsf.allstars.networking.retrofit.responses.AuthenticationResponse;
import com.belatrixsf.allstars.networking.retrofit.responses.SearchEmployeeResponse;

import java.util.List;

<<<<<<< HEAD
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
=======
import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
>>>>>>> develop

/**
 * Created by gyosida on 4/11/16.
 */
public interface EmployeeAPI {

    @POST(ServerPaths.EMPLOYEE_AUTHENTICATE)
    Call<AuthenticationResponse> authenticate(@Body AuthenticationRequest request);

    @GET(ServerPaths.EMPLOYEE_BY_ID)
    Call<Employee> getEmployee(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

    @GET(ServerPaths.EMPLOYEE_LIST)
    Call<SearchEmployeeResponse> getEmployeeSearchList(@Query(ServerPaths.SEARCH_TERM) String searchTerm, @Query(ServerPaths.PAGE) Integer page);

    @GET(ServerPaths.RANKING_LIST)
    Call<List<Employee>> getRankingList(@Path(ServerPaths.KIND) String kind, @Path(ServerPaths.QUANTITY) int quantity);

    @GET(ServerPaths.EMPLOYEE_CATEGORIES)
    Call<List<Category>> getEmployeeCategories(@Path(ServerPaths.EMPLOYEE_ID) int employeeId);

}
