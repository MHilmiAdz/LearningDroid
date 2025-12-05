package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnMoveActivity: Button = findViewById(R.id.btn_move_activity)
        btnMoveActivity.setOnClickListener(this)

        val btnMovewithData: Button = findViewById(R.id.btn_move_activity_data)
        btnMovewithData.setOnClickListener(this)

        val btnMovewithObject: Button = findViewById(R.id.btn_move_activity_object)
        btnMovewithObject.setOnClickListener(this)

        val btnMovewithObjectSimple: Button = findViewById(R.id.btn_move_activity_object_simple)
        btnMovewithObjectSimple.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
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
        }
    }
}