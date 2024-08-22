package com.mindgeeks.sportsnews.view_models

import android.content.Context
 
 
import android.util.Log
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.ui.unit.dp
  
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mindgeeks.sportsnews.models.RequestModels.DateWiseUpdateRequest
import com.mindgeeks.sportsnews.models.RequestModels.GetLeagDataRequest
import com.mindgeeks.sportsnews.models.RequestModels.GetPlayerDetailRequest
import com.mindgeeks.sportsnews.models.RequestModels.GoogleSigninRequest
import com.mindgeeks.sportsnews.models.RequestModels.LoginWithEmailrequest
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
import com.mindgeeks.sportsnews.R
import com.mindgeeks.sportsnews.Repos.Repositoy
import com.mindgeeks.sportsnews.Components.customDialog
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ViewModel_Main(val context : Context) : ViewModel() {

    private var repo: Repositoy = Repositoy(context = context)


    private val tag = "ViewModel"
    private val ongoingCalls = mutableListOf<Call<*>>()
    private lateinit var NoDataFoundDialog: customDialog
    // we create the mutable list of Call because Call is interface of Retofit lib and with this
    //I can use cancel function to cancel Network call relate to that object .

    private var openAppResponse = MutableLiveData<OpenAppResponse>()
    private var homeFragmentResponse = MutableLiveData<HomeFragmentResponse>()
    private var getLiveMatchDataResponse = MutableLiveData<GetLiveMatchDataResponse>()
    private var searchResponse = MutableLiveData<SearchResponse>()
    private var dateWiseUpdateREsponse = MutableLiveData<DateWiseUpdateResponse>()
    private var platerDetailResponse = MutableLiveData<GetPlayerDetailResponse>()
    private var playerSearchResponse = MutableLiveData<PlayerSearchResponse>()
    private var GoogleSignedResposne = MutableLiveData<GoogleSigninResponse>()
    private var GetLeagueDataResponse = MutableLiveData<GetLeagueDataResponse>()
    private var LoginWithEmailResponse = MutableLiveData<LoginWithEmailResponse>()
    private var ProfileResponse = MutableLiveData<ProfileResponse>()

    private fun <T> enqueueCall(call: Call<T>, liveData: MutableLiveData<T>) {
        ongoingCalls.add(call)
        call.enqueue(object : Callback<T> {
            override fun onResponse(call: Call<T>, response: Response<T>) {
                if(response.isSuccessful)
                {
                    Log.e(tag, "in Enquecall ${response.body()}")
                    if (response.body() != null) {
                        liveData.value = response.body()
                    } else {
                        val padding = PaddingValues(
                            start = 0.dp,
                            top = 0.dp,
                            end = 0.dp,
                            bottom = 0.dp
                        )
                        NoDataFoundDialog = customDialog(
                            context = context,
                            Title1 = "Data Not Found",
                            Title2 = "",
                            cancelable = false,
                            animationID = R.raw.duck_walking_with_bat,
                            padding = padding,
                            repeat = false
                        )
                    }
                } else {
                        val padding = PaddingValues(
                            start = 50.dp,
                            top = 55.dp,
                            end = 50.dp,
                            bottom = 10.dp
                        )
                        NoDataFoundDialog = customDialog(
                            context = context,
                            Title1 = "Something Went Wrong !",
                            Title2 = "Restart This App Or Try Again Later",
                            cancelable = false,
                            Error = " Error : ${response.message()}",
                            animationID = R.raw.something_went_wrong,
                            padding = padding,
                            repeat = false
                        )
                }
                ongoingCalls.remove(call)
            }

            override fun onFailure(call: Call<T>, t: Throwable) {
                Log.e(tag, "in onFail of GetLeagueData ${t.message}")

                if(t.message.equals("Unable to resolve host \"cricnews.app\": No address associated with hostname"))
                {
                    val padding = PaddingValues(
                        start = 60.dp,
                        top = 70.dp,
                        end = 60.dp,
                        bottom = 80.dp
                    )

                    NoDataFoundDialog = customDialog(
                        context = context,
                        Title1 = "Not Connected To Internet !!",
                        Title2 = "Your phone is not connected to the internet.\nPlease check !!",
                        cancelable = false,
                        Error = " Error : ${t.message}",
                        animationID = R.raw.nointernet,
                        padding = padding,
                        repeat = true
                    )
                }else{
                    val padding = PaddingValues(
                        start = 50.dp,
                        top = 55.dp,
                        end = 50.dp,
                        bottom = 10.dp
                    )

                    NoDataFoundDialog = customDialog(
                        context = context,
                        Title1 = "Something Went Wrong !",
                        Title2 = "Restart This App Or Try Again Later",
                        cancelable = false,
                        Error = " Error : ${t.message}",
                        animationID = R.raw.something_went_wrong,
                        padding = padding,
                        repeat = false
                    )
                }
            }
        })
        ongoingCalls.remove(call)
    }

    fun ProfileDetail(request: ProfileREquest): MutableLiveData<ProfileResponse> {
        val call = repo.ProfileResponse(request)
        enqueueCall(call, ProfileResponse)
        return ProfileResponse
    }

    fun LoginWithEmail(request: LoginWithEmailrequest): MutableLiveData<LoginWithEmailResponse> {
        val call = repo.LoginWithEmail(request)
        enqueueCall(call, LoginWithEmailResponse)
        return LoginWithEmailResponse
    }

    fun GetLeagueData(request: GetLeagDataRequest): MutableLiveData<GetLeagueDataResponse> {
        val call = repo.GetLeagueData(request)
        enqueueCall(call, GetLeagueDataResponse)
        return GetLeagueDataResponse
    }

    fun GoogleSignin(request: GoogleSigninRequest): MutableLiveData<GoogleSigninResponse> {
        val call = repo.GoogleSingin(request)
        enqueueCall(call, GoogleSignedResposne)
        return GoogleSignedResposne
    }

    fun PlayerSearch(request: PlayerSearchRequest): MutableLiveData<PlayerSearchResponse> {
        val call = repo.PlayerSearch(request)
        enqueueCall(call, playerSearchResponse)
        return playerSearchResponse
    }

    fun GetPlayerDetail(request: GetPlayerDetailRequest): MutableLiveData<GetPlayerDetailResponse> {
        val call = repo.GetPlayerDetail(request)
        enqueueCall(call, platerDetailResponse)
        return platerDetailResponse
    }

    fun DateWiseUpdate(request: DateWiseUpdateRequest): MutableLiveData<DateWiseUpdateResponse> {
        val call = repo.DateWiseUpdate(request)
        enqueueCall(call, dateWiseUpdateREsponse)
        return dateWiseUpdateREsponse
    }

    fun Search(query: String): MutableLiveData<SearchResponse> {
        val call = repo.Search(query)
        enqueueCall(call, searchResponse)
        return searchResponse
    }

    fun getLiveMatchData(
        deviceId: String,
        securityToken: String,
        versionName: String,
        versionCode: String,
        matchId: String,
        compId: String
    ): MutableLiveData<GetLiveMatchDataResponse> {
        val call = repo.GetLiveMatchData(deviceId, securityToken, versionName, versionCode, matchId, compId)
        enqueueCall(call, getLiveMatchDataResponse)
        return getLiveMatchDataResponse
    }

    fun getHomeFragmentData(
        userId: String,
        securityToken: String,
        versionName: String,
        versionCode: String
    ): MutableLiveData<HomeFragmentResponse> {
        val call = repo.GetHomeFragmentData(userId, securityToken, versionName, versionCode)
        enqueueCall(call, homeFragmentResponse)
        return homeFragmentResponse
    }

    fun OpenAppAPI(
        userId: String,
        securityToken: String,
        versionName: String,
        versionCode: String
    ): MutableLiveData<OpenAppResponse> {
        val call = repo.OpenAppAPI(userId, securityToken, versionName, versionCode)
        enqueueCall(call, openAppResponse)
        return openAppResponse
    }

    override fun onCleared() {
        super.onCleared()
        for (call in ongoingCalls) {
            call.cancel()
        }
        ongoingCalls.clear()
    }
}
