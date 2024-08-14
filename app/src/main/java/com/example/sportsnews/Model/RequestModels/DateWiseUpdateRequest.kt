package com.mindgeeks.sportsnews.Model.RequestModels

data class DateWiseUpdateRequest(val userId: String,
                                 val securityToken: String,
                                 val versionName: String,
                                 val versionCode: String,
                                 val dateFrom : String,
                                 val dateTo : String )
