package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var inpnumber: TextView
    private lateinit var tvResult: TextView
    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue =
                result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            "Result: $selectedValue".also { tvResult.text = it }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        inpnumber = findViewById(R.id.editTextPhone)

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

        tvResult = findViewById(R.id.tv_result)
    }

    override fun onClick(v: View) {
        when (v?.id) {
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
                val phoneNumber = if(inpnumber.text.isNotEmpty()){
                    inpnumber.text.toString()
                } else {
                    "0"
                }
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, "tel:$phoneNumber".toUri())
                startActivity(dialPhoneIntent)
            }

            R.id.btn_move_for_result -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }
        }
    }
}