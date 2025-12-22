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
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etInput: EditText
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
        if (result.resultCode == RESULT_OK && result.data != null) {
            val purchasedItem = result.data?.getStringExtra(ScrollingActivity.BUYING_KEY)
            "Thanks for buying: $purchasedItem".also { tvResult.text = it }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<View>(R.id.mainLayout)

        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etInput = findViewById(R.id.etInput)
        tvResult = findViewById(R.id.tvResult)

        val btnMoveActivity: Button = findViewById(R.id.btnMoveActivity)
        btnMoveActivity.setOnClickListener(this)

        val btnMoveWithData: Button = findViewById(R.id.btnMoveWithData)
        btnMoveWithData.setOnClickListener(this)

        val btnMoveWithObject: Button = findViewById(R.id.btnMoveWithObject)
        btnMoveWithObject.setOnClickListener(this)

        val btnMoveParcelize: Button = findViewById(R.id.btnMoveParcelize)
        btnMoveParcelize.setOnClickListener(this)

        val btnDialNumber: Button = findViewById(R.id.btnDialNumber)
        btnDialNumber.setOnClickListener(this)

        val btnMoveResult: Button = findViewById(R.id.btnMoveResult)
        btnMoveResult.setOnClickListener(this)

        val btntoShop: Button = findViewById(R.id.btnToScrollActivity)
        btntoShop.setOnClickListener(this)

    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnMoveActivity -> {
                val moveIntent = Intent(this@MainActivity, MoveActivity::class.java)
                startActivity(moveIntent)
            }

            R.id.btnMoveWithData -> {
                val moveWithDataIntent = Intent(this@MainActivity, MoveWithDataActivity::class.java)
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_NAME, "Android Passing Data")
                moveWithDataIntent.putExtra(MoveWithDataActivity.EXTRA_AGE, 2000)
                startActivity(moveWithDataIntent)
            }

            R.id.btnMoveWithObject -> {
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

            R.id.btnMoveParcelize -> {
                val simplePersona = SimplePersona(
                    "LearningDroid",
                    200,
                    170,
                    70
                )
                // Note: Ensure MoveWithObjectActivity is set up to receive this parcelable
                val moveWithParcelizeIntent = Intent(this@MainActivity, MoveWithObjectActivity::class.java)
                moveWithParcelizeIntent.putExtra(MoveWithObjectActivity.SIMPLE_PERSONA, simplePersona)
                startActivity(moveWithParcelizeIntent)
            }

            R.id.btnDialNumber -> {
                // Updated to use the new etInput variable
                val phoneNumber = etInput.text.toString().trim()

                val dialString = if (phoneNumber.isNotEmpty()) {
                    "tel:$phoneNumber"
                } else {
                    "tel:0"
                }
                val dialPhoneIntent = Intent(Intent.ACTION_DIAL, dialString.toUri())
                startActivity(dialPhoneIntent)
            }

            R.id.btnMoveResult -> {
                val moveForResultIntent = Intent(this@MainActivity, MoveForResultActivity::class.java)
                resultLauncher.launch(moveForResultIntent)
            }

            R.id.btnToScrollActivity -> {
                val scrollingActivityIntent = Intent(this@MainActivity, ScrollingActivity::class.java)
                buyingLauncher.launch(scrollingActivityIntent)
            }
        }
    }
}