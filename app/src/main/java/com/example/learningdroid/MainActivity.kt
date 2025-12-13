package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inpnumber: EditText
    private lateinit var tvResult: TextView

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            "Result: $selectedValue".also { tvResult.text = it }
        }
    }

    private val buyingLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        // IMPROVEMENT: Check for RESULT_OK to ensure the user actually clicked "Buy"
        // and didn't just press the Back button.
        if (result.resultCode == RESULT_OK && result.data != null) {
            // Retrieve the specific string sent from ScrollingActivity
            val purchasedItem = result.data?.getStringExtra(ScrollingActivity.BUYING_KEY)

            // Display dynamic message
            "Thanks for buying: $purchasedItem".also { tvResult.text = it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inpnumber = findViewById(R.id.editTextPhone)
        tvResult = findViewById(R.id.tv_result)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener(this)

        val btnMovewithData: Button = findViewById(R.id.btn_move_activity_data)
        btnMovewithData.setOnClickListener(this)

        val btnMovewithObject: Button = findViewById(R.id.btn_move_activity_object)
        btnMovewithObject.setOnClickListener(this)

        val btnMovewithObjectSimple: Button = findViewById(R.id.btn_move_activity_object_simple)
        btnMovewithObjectSimple.setOnClickListener(this)

        val btnDialNumber: Button = findViewById(R.id.btn_dial_number)
        btnDialNumber.setOnClickListener(this)

        val btnMoveForResult: Button = findViewById(R.id.btn_move_for_result)
        btnMoveForResult.setOnClickListener(this)

        val btnOtherMainActivity: Button = findViewById(R.id.btn_other_main_activity)
        btnOtherMainActivity.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btn_move_activity -> {
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btn_move_activity_data -> {
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Android Passing Data")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 2000)
                startActivity(moveWithDataIntent)
            }

            R.id.btn_move_activity_object -> {
                val persona = Persona(
                    "LearningDroid",
                    200,
                    "Learning new Things",
                    "person@example.com",
                )
                val moveWithObjectIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithObjectIntent.putExtra(MoveWithObjectActivity.EXTRA_PERSONA, persona)
                startActivity(moveWithObjectIntent)
            }

            R.id.btn_move_activity_object_simple -> {
                val simplePersona = SimplePersona(
                    "LearningDroid",
                    200,
                    170,
                    70
                )
                val moveWithParcelizeIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithParcelizeIntent.putExtra(MoveWithObjectActivity.SIMPLE_PERSONA, simplePersona)
                startActivity(moveWithParcelizeIntent)
            }

            R.id.btn_dial_number -> {
                val phoneNumber = inpnumber.text.toString().trim()

                val dialString = if (phoneNumber.isNotEmpty()) {
                    "tel:$phoneNumber"
                } else {
                    "tel:0"
                }
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, dialString.toUri())
                startActivity(dialPhoneIntent)
            }

            R.id.btn_move_for_result -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }

            R.id.btn_other_main_activity -> {
                val scrollingActivityIntent = Intent(this@MainActivity, ScrollingActivity::class.java)
                buyingLauncher.launch(scrollingActivityIntent)
            }
        }
    }
}
