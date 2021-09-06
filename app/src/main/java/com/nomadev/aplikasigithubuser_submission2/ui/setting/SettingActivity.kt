package com.nomadev.aplikasigithubuser_submission2.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.nomadev.aplikasigithubuser_submission2.R
import com.nomadev.aplikasigithubuser_submission2.databinding.ActivitySettingBinding
import com.nomadev.aplikasigithubuser_submission2.domain.model.ReminderModel
import com.nomadev.aplikasigithubuser_submission2.preference.ReminderPreference
import com.nomadev.aplikasigithubuser_submission2.receiver.AlarmReceiver

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
        if (reminderPreference.getReminder().isReminder) {
            binding.switchReminder.isChecked = true
        } else {
            binding.switchReminder.isChecked = false
        }

        alarmReceiver = AlarmReceiver()
        binding.switchReminder.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                saveReminder(true)
                alarmReceiver.setRepeatingAlarm(
                    this,
                    "RepeatingAlarm",
                    "22:23",
                    "GitHub Daily Reminder"
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