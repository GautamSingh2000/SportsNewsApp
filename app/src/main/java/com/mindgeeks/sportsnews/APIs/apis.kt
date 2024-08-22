package com.mindgeeks.sportsnews.APIs

import com.mindgeeks.sportsnews.models.RequestModels.DateWiseUpdateRequest
import com.mindgeeks.sportsnews.models.RequestModels.GetLeagDataRequest
import com.mindgeeks.sportsnews.models.RequestModels.GetLiveMatchDataRequest
import com.mindgeeks.sportsnews.models.RequestModels.GetPlayerDetailRequest
import com.mindgeeks.sportsnews.models.RequestModels.GoogleSigninRequest
import com.mindgeeks.sportsnews.models.RequestModels.HomeFragmentRequest
import com.mindgeeks.sportsnews.models.RequestModels.LoginWithEmailrequest
import com.mindgeeks.sportsnews.models.RequestModels.OpenAppRequest
import com.mindgeeks.sportsnews.models.RequestModels.PlayerSearchRequest
import com.mindgeeks.sportsnews.models.RequestModels.ProfileREquest
import com.mindgeeks.sportsnews.models.ResponseModel.DateWiseUpdateResponse
import com.mindgeeks.sportsnews.models.ResponseModel.GetLeagueDataResponse
import com.mindgeeks.sportsnews.models.ResponseModel.GetLiveMatchDataResponse
import com.mindgeeks.sportsnews.models.ResponseModel.GetPlayerDetailResponse
import com.mindgeeks.sportsnews.models.ResponseModel.GoogleSigninResponse
import com.mindgeeks.sportsnews.models.ResponseModel.HomeFragmentResponse
import com.mindgeeks.sportsnews.models.ResponseModel.LoginWithEmailResponse
import com.mindgeeks.sportsnews.models.ResponseModel.OpenAppResponse
import com.mindgeeks.sportsnews.models.ResponseModel.PlayerSearchResponse
import com.mindgeeks.sportsnews.models.ResponseModel.ProfileResponse
import com.mindgeeks.sportsnews.models.ResponseModel.SearchResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface apis {

    @POST("appOpen")
    fun OpenAppAPI(@Body openAppRequest: OpenAppRequest): Call<OpenAppResponse>

    @POST("home")
    fun GetHomeFragmentData(@Body homeFragmentRequest: HomeFragmentRequest): Call<HomeFragmentResponse>

    @POST("leagueData")
    fun GetLeagueData(@Body getLeagueDataRequest: GetLeagDataRequest): Call<GetLeagueDataResponse>

    @POST("liveMatchData")
    fun GetLiveMatchData(@Body getLiveMatchDataRequest: GetLiveMatchDataRequest): Call<GetLiveMatchDataResponse>

    @POST("search")
    fun Search(@Query("query") search : String): Call<SearchResponse>

    @POST("datewiseUpdates")
    fun DateWiseUpdate(@Body dateWiseUpdateResponse: DateWiseUpdateRequest): Call<DateWiseUpdateResponse>

    @POST("playerData")
    fun GetPlayerDetail(@Body getPlayerDetailRequest: GetPlayerDetailRequest): Call<GetPlayerDetailResponse>

    @POST("playerSearch")
    fun PlayerSearch(@Body playerSearchRequest: PlayerSearchRequest): Call<PlayerSearchResponse>

    @POST("userDetail")
    fun GoogleSignIn(@Body googleSigninRequest: GoogleSigninRequest): Call<GoogleSigninResponse>

    @POST("defaultUser")
    fun LoginWithEmail(@Body loginWithEmailrequest: LoginWithEmailrequest): Call<LoginWithEmailResponse>


    @POST("profile")
    fun GetProfile(@Body profileREquest: ProfileREquest): Call<ProfileResponse>
}