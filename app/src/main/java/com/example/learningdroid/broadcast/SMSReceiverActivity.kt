package com.example.learningdroid.broadcast

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.R
import com.example.learningdroid.databinding.ActivitySmsrecieverBinding

class SMSReceiverActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SMS_NO = "extra_sms_no"
        const val EXTRA_SMS_MESSAGE = "extra_sms_message"
    }

    lateinit var binding: ActivitySmsrecieverBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySmsrecieverBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.rootContainer) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        title = getString(R.string.incoming_message)
        binding.btnClose.setOnClickListener {
            finish()
        }
        val senderNo = intent.getStringExtra(EXTRA_SMS_NO)
        val senderMessage = intent.getStringExtra(EXTRA_SMS_MESSAGE)
        binding.tvFrom.text = getString(R.string.from, senderNo)
        binding.tvMessage.text = senderMessage
    }
}