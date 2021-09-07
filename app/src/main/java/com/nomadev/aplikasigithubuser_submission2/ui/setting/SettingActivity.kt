package com.nomadev.aplikasigithubuser_submission2.ui.setting

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivitySettingBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ReminderModel
import com.nomadev.aplikasigithubuser_submission2.utils.preference.ReminderPreference
import com.nomadev.aplikasigithubuser_submission2.utils.receiver.AlarmReceiver

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding
    private lateinit var reminderModel: ReminderModel
    private lateinit var alarmReceiver: AlarmReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.title = getString(R.string.settings)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val reminderPreference = ReminderPreference(this)
        binding.switchReminder.isChecked = reminderPreference.getReminder().isReminder

        alarmReceiver = AlarmReceiver()
        binding.switchReminder.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(
                    this,
                    "RepeatingAlarm",
                    "09:00",
                    "Gitify Daily Reminder"
                )
            } else {
                saveReminder(false)
                alarmReceiver.cancelAlarm(this)
            }
        }
    }

    private fun saveReminder(b: Boolean) {
        val reminderPreference = ReminderPreference(this)
        reminderModel = ReminderModel()
        reminderModel.isReminder = b
        reminderPreference.setReminder(reminderModel)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}