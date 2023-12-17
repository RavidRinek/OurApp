package com.our.data.base.datasources

import android.content.Context

class Prefs(context: Context) {

    private val sharedPref = context.getSharedPreferences("OurApp.data", Context.MODE_PRIVATE)
    private val editor = sharedPref.edit()

    fun clearSharedPrefs() {
        sharedPref.edit().clear().apply()
    }

    fun removeKey(key: String) {
        editor.remove(key).commit()
    }

    fun removeKeyAsync(key: String) {
        editor.remove(key).apply()
    }

    fun contains(key: String): Boolean {
        return sharedPref.contains(key)
    }

    fun putString(key: String, value: String?) {
        editor.putString(key, value).commit()
    }

    fun putStringAsync(key: String, value: String?) {
        editor.putString(key, value).apply()
    }

    fun getString(key: String, defValue: String? = null): String =
        sharedPref.getString(key, defValue) ?: ""

    fun putStringSet(key: String, value: Set<String>) {
        editor.putStringSet(key, value).commit()
    }

    fun putStringSetAsync(key: String, value: Set<String>) {
        editor.putStringSet(key, value).apply()
    }

    fun getStringSet(key: String, defValue: Set<String>?): Set<String>? =
        sharedPref.getStringSet(key, defValue)

    fun putInt(key: String, value: Int) {
        editor.putInt(key, value).commit()
    }

    fun putIntAsync(key: String, value: Int) {
        editor.putInt(key, value).apply()
    }

    fun getInt(key: String, defValue: Int = 0): Int =
        sharedPref.getInt(key, defValue)

    fun putLong(key: String, value: Long) {
        editor.putLong(key, value).commit()
    }

    fun putLongAsync(key: String, value: Long) {
        editor.putLong(key, value).apply()
    }

    fun getLong(key: String, defValue: Long): Long =
        sharedPref.getLong(key, defValue)

    fun putBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value).commit()
    }

    fun putBooleanAsync(key: String, value: Boolean) {
        editor.putBoolean(key, value).apply()
    }

    fun getBoolean(key: String, defValue: Boolean = false): Boolean =
        sharedPref.getBoolean(key, defValue)

    fun putFloat(key: String, value: Float) {
        editor.putFloat(key, value).commit()
    }

    fun putFloatAsync(key: String, value: Float) {
        editor.putFloat(key, value).apply()
    }

    fun getFloat(key: String, defValue: Float = 0F): Float =
        sharedPref.getFloat(key, defValue)

    companion object {
        @Volatile
        private var instance: Prefs? = null
        fun getInstance(context: Context): Prefs {
            return instance ?: synchronized(this) {
                instance ?: Prefs(context.applicationContext).also { instance = it }
            }
        }

        const val MEMBER_ID: String = "memberId"
        const val STUDENT_ID: String = "studentId"
        const val FCM_TOKEN: String = "firebaseFcmToken"
        const val COMPLETED_TEACHER_PERSONAL_INFO_REGISTRATION = "user_already_post_his_personal_info"
        const val COMPLETED_TEACHER_FULL_REGISTRATION = "completed_both_create_and_update_teacher"
    }
}