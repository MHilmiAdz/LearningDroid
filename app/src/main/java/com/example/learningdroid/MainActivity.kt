package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.learningdroid.databinding.ActivityMainBinding
import com.example.learningdroid.datapass.MoveActivity
import com.example.learningdroid.datapass.MoveForResultActivity
import com.example.learningdroid.datapass.MoveWithDataActivity
import com.example.learningdroid.datapass.MoveWithObjectActivity
import com.example.learningdroid.datapass.Persona
import com.example.learningdroid.datapass.SimplePersona
import com.example.learningdroid.fragment.FlexFragments
import com.example.learningdroid.navigation.NavigationActivity
import com.example.learningdroid.recycle.RecycleActivity
import com.example.learningdroid.recycle.ScrollingActivity

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var etInput: EditText
    private lateinit var tvResult: TextView
    private lateinit var binding: ActivityMainBinding

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        etInput = binding.etInput
        tvResult = binding.tvResult

        binding.btnMoveActivity.setOnClickListener(this)
        binding.btnMoveWithData.setOnClickListener(this)
        binding.btnMoveWithObject.setOnClickListener(this)
        binding.btnMoveParcelize.setOnClickListener(this)
        binding.btnDialNumber.setOnClickListener(this)
        binding.btnMoveResult.setOnClickListener(this)
        binding.btnToScrollActivity.setOnClickListener(this)
        binding.btnToRecycleActivity.setOnClickListener(this)
        binding.btnToFragmentsActivity.setOnClickListener(this)
        binding.btnToNavigationActivity.setOnClickListener(this)

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

            R.id.btnToRecycleActivity -> {
                val recycleActivityIntent = Intent(this@MainActivity, RecycleActivity::class.java)
                startActivity(recycleActivityIntent)
            }

            R.id.btnToFragmentsActivity -> {
                val flexFragmentsIntent = Intent(this@MainActivity, FlexFragments::class.java)
                startActivity(flexFragmentsIntent)
            }

            R.id.btnToNavigationActivity -> {
                val navigationActivityIntent = Intent(this@MainActivity, NavigationActivity::class.java)
                startActivity(navigationActivityIntent)
            }
        }
    }
}