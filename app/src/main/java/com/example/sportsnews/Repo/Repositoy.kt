package com.mindgeeks.sportsnews.Repo

import android.content.Context
import com.mindgeeks.sportsnews.Model.RequestModels.DateWiseUpdateRequest
import com.mindgeeks.sportsnews.Model.RequestModels.GetLeagDataRequest
import com.mindgeeks.sportsnews.Model.RequestModels.GetLiveMatchDataRequest
import com.mindgeeks.sportsnews.Model.RequestModels.GetPlayerDetailRequest
import com.mindgeeks.sportsnews.Model.RequestModels.GoogleSigninRequest
import com.mindgeeks.sportsnews.Model.RequestModels.HomeFragmentRequest
import com.mindgeeks.sportsnews.Model.RequestModels.LoginWithEmailrequest
import com.mindgeeks.sportsnews.Model.RequestModels.OpenAppRequest
import com.mindgeeks.sportsnews.Model.RequestModels.PlayerSearchRequest
import com.mindgeeks.sportsnews.Model.RequestModels.ProfileREquest
import com.mindgeeks.sportsnews.Model.ResponseModel.DateWiseUpdateResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.GetLeagueDataResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.GetLiveMatchDataResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.GetPlayerDetailResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.GoogleSigninResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.HomeFragmentResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.LoginWithEmailResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.OpenAppResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.PlayerSearchResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.ProfileResponse
import com.mindgeeks.sportsnews.Model.ResponseModel.SearchResponse
import retrofit2.Call

class Repositoy(val context: Context) {
    private val Retrofit_object = com.mindgeeks.sportsnews.API.Retrofit.getInstance().create(com.mindgeeks.sportsnews.API.Apis::class.java)


    fun ProfileResponse(resquest: ProfileREquest): Call<ProfileResponse> {
        return Retrofit_object.GetProfile(resquest)
    }

     fun GetLeagueData(resquest: GetLeagDataRequest): Call<GetLeagueDataResponse> {
         return  Retrofit_object.GetLeagueData(resquest)
     }

    fun LoginWithEmail(resquest: LoginWithEmailrequest): Call<LoginWithEmailResponse> {
        return Retrofit_object.LoginWithEmail(resquest)
    }

    fun GoogleSingin(resquest: GoogleSigninRequest): Call<GoogleSigninResponse> {
        return  Retrofit_object.GoogleSignIn(resquest)
    }


    fun PlayerSearch(resquest: PlayerSearchRequest): Call<PlayerSearchResponse> {
        return  Retrofit_object.PlayerSearch(resquest)
    }

    fun GetPlayerDetail(request: GetPlayerDetailRequest): Call<GetPlayerDetailResponse> {
        return Retrofit_object.GetPlayerDetail(request)
    }

    fun DateWiseUpdate(request: DateWiseUpdateRequest): Call<DateWiseUpdateResponse> {
        return    Retrofit_object.DateWiseUpdate(request)
    }


    fun Search(query: String): Call<SearchResponse> {
        return   Retrofit_object.Search(query)
    }


    fun GetLiveMatchData(
        deviceId: String,
        securityToken: String,
        versionName: String,
        versionCode: String,
        matchId: String,
        compId: String,
    ): Call<GetLiveMatchDataResponse> {
        val request = GetLiveMatchDataRequest(
            deviceId,
            securityToken,
            versionName,
            versionCode,
            matchId,
            compId
        )

        return Retrofit_object.GetLiveMatchData(request)
    }

    fun GetHomeFragmentData(
        userId: String,
        securityToken: String,
        versionName: String,
        versionCode: String,
    ): Call<HomeFragmentResponse> {
        val request = HomeFragmentRequest(
            userId, securityToken, versionName, versionCode
        )
        return Retrofit_object.GetHomeFragmentData(request)
    }

    fun OpenAppAPI(
        userId: String,
        securityToken: String,
        versionName: String,
        versionCode: String,
    ): Call<OpenAppResponse> {
        val request = OpenAppRequest(
            userId = userId,
            securityToken = securityToken,
            versionName = versionName,
            versionCode = versionCode
        )

        return Retrofit_object.OpenAppAPI(request)
    }
}