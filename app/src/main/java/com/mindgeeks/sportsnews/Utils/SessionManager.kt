package com.mindgeeks.sportsnews.Utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log

class SessionManager (context: Context){
    private lateinit var context : Context
    private lateinit var sp : SharedPreferences
    private lateinit var editor : SharedPreferences.Editor

    init {
        this.context =  context
        sp =context.getSharedPreferences(Constants.CRICKET_NEWS_SP,0)
        editor = sp.edit()
    }

    fun InitializeValue(key : String , value : String)
    {
        Log.e("SessionManager","vale for $key is saved $value")
        editor.putString(key.toString(),value.toString())
        editor.commit()
    }


    fun Initialize_Current_no(C_no: Int) {
        editor.putInt(Constants.CURRENT_NO, C_no)
        Log.e("sessionManager", "Current_no is initialize by$C_no")
        editor.commit()
    }

    fun Initialize_Last_no(L_no: Int) {
        editor.putInt(Constants.LAST_NO, L_no)
        Log.e("sessionManager", "Last_no is initialize by$L_no")
        editor.commit()
    }
    fun Get_Current_id(): Int {
        val value = sp.getInt(Constants.CURRENT_NO, 0)
        Log.e("SessionManager", "chceking valus of current_id  is $value")
        return value
    }

    fun Get_Last_id(): Int {
        val value = sp.getInt(Constants.LAST_NO, 0)
        Log.e("SessionManager", "chceking valus of last  is $value")
        return value
    }


    fun GetValue(Key : String):String?{
        val result = sp.getString(Key,"null")
        Log.e("SesseionManager","fetching vale for $Key is $result")
        return result
    }

    fun Sign() {
        editor.putBoolean(Constants.LOGIN_STATE, true)
        Log.e("SessionManager", "Signin")
        editor.commit()
    }

    fun CheckLogin():Boolean
    {
        return if (sp.contains(Constants.LOGIN_STATE)) {
            Log.e("SessionManager", "chceking Singin ans is " + true)
            true
        } else {
            Log.e("SessionManager", "chceking Singin ans is " + false)
            false
        }
    }

    fun LogOut()
    {
        editor.clear()
        editor.commit()
    }
}