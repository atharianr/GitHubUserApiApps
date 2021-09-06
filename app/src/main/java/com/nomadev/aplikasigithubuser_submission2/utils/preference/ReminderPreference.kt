package com.nomadev.aplikasigithubuser_submission2.utils.preference

import android.content.Context
import com.nomadev.aplikasigithubuser_submission2.domain.model.ReminderModel

class ReminderPreference(context: Context) {
    companion object {
        const val PREFS_NAME = "reminder_pref"
        private const val IS_REMINDER = "is_reminder"
    }

    private val preference = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setReminder(data: ReminderModel) {
        val editor = preference.edit()
        editor.putBoolean(IS_REMINDER, data.isReminder)
        editor.apply()
    }

    fun getReminder(): ReminderModel {
        val model = ReminderModel()
        model.isReminder = preference.getBoolean(IS_REMINDER, false)
        return model
    }
}