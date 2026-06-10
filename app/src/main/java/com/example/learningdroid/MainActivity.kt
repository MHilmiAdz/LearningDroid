package com.example.learningdroid

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.net.toUri
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.ViewModelProvider
import com.example.learningdroid.alarmmanager.AlarmManagerActivity
import com.example.learningdroid.apicall.APIQuoteActivity
import com.example.learningdroid.appbar.AppBarActivity
import com.example.learningdroid.asynchronous.AsynchronousActivity
import com.example.learningdroid.bottomnav.BottomNavActivity
import com.example.learningdroid.broadcast.BroadcastActivity
import com.example.learningdroid.customview.CustomViewActivity
import com.example.learningdroid.databinding.ActivityMainBinding
import com.example.learningdroid.datapass.MoveActivity
import com.example.learningdroid.datapass.MoveForResultActivity
import com.example.learningdroid.datapass.MoveWithDataActivity
import com.example.learningdroid.datapass.MoveWithObjectActivity
import com.example.learningdroid.datapass.Persona
import com.example.learningdroid.datapass.SimplePersona
import com.example.learningdroid.drawer.DrawerActivity
import com.example.learningdroid.fileaccess.ReadWriteActivity
import com.example.learningdroid.fragment.FlexFragments
import com.example.learningdroid.frost.ChatSessionActivity
import com.example.learningdroid.likesapp.LikesAppActivity
import com.example.learningdroid.livedata.LiveDataActivity
import com.example.learningdroid.mainmenu.MainMenuActivity
import com.example.learningdroid.navigation.NavigationActivity
import com.example.learningdroid.notesapp.MyNotesApp
import com.example.learningdroid.recycle.RecycleActivity
import com.example.learningdroid.recycle.ScrollingActivity
import com.example.learningdroid.restoreview.RestoReviewActivity
import com.example.learningdroid.roomnote.ui.main.RoomNotesActivity
import com.example.learningdroid.searchbar.SearchBarActivity
import com.example.learningdroid.settingpref.MainViewModel
import com.example.learningdroid.settingpref.SettingPref
import com.example.learningdroid.settingpref.SettingPreferenceActivity
import com.example.learningdroid.settingpref.ViewModelFactory
import com.example.learningdroid.settingpref.dataStore
import com.example.learningdroid.sharedpref.SharedPrefActivity
import com.example.learningdroid.simplenotif.SimpleNotifActivity
import com.example.learningdroid.tablayout.TabLayoutActivity
import com.example.learningdroid.ticketing.TicketingActivity
import com.example.learningdroid.viewmodel.ViewModelActivity
import com.example.learningdroid.webview.WebViewActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val resultLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == MoveForResultActivity.RESULT_CODE && result.data != null) {
            val selectedValue = result.data?.getIntExtra(MoveForResultActivity.EXTRA_SELECTED_VALUE, 0)
            binding.tvResult.text = getString(R.string.resultValue, selectedValue)
        }
    }

    private val buyingLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK && result.data != null) {
            val purchasedItem = result.data?.getStringExtra(ScrollingActivity.BUYING_KEY)
            binding.tvResult.text = getString(R.string.thanksforbuying, purchasedItem)
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
        setupThemeSwitch()
        setupButtons()
    }

    private fun setupThemeSwitch() {
        val pref = SettingPref.getInstance(application.dataStore)
        val mainViewModel = ViewModelProvider(this, ViewModelFactory(pref))[MainViewModel::class.java]

        mainViewModel.getThemeSettings().observe(this) { isDarkModeActive ->
            val mode = if (isDarkModeActive) AppCompatDelegate.MODE_NIGHT_YES
            else AppCompatDelegate.MODE_NIGHT_NO
            AppCompatDelegate.setDefaultNightMode(mode)
            binding.switchTheme.isChecked = isDarkModeActive
        }

        binding.switchTheme.setOnClickListener {
            val isChecked = binding.switchTheme.isChecked
            mainViewModel.saveThemeSetting(isChecked)
        }
    }

    private fun setupButtons() {
        val simpleNavMap = mapOf(
            binding.btnMoveActivity          to MoveActivity::class.java,
            binding.btnToRecycleActivity     to RecycleActivity::class.java,
            binding.btnToFragmentsActivity   to FlexFragments::class.java,
            binding.btnToNavigationActivity  to NavigationActivity::class.java,
            binding.btnToAppBarActivity      to AppBarActivity::class.java,
            binding.btnToSearchBarActivity   to SearchBarActivity::class.java,
            binding.btnToDrawerActivity      to DrawerActivity::class.java,
            binding.btnToBottomNavActivity   to BottomNavActivity::class.java,
            binding.btnTpTabLayoutActivity   to TabLayoutActivity::class.java,
            binding.btnToAsynchronousActivity to AsynchronousActivity::class.java,
            binding.btnToAPIActivity         to APIQuoteActivity::class.java,
            binding.btnToFrostActivity       to ChatSessionActivity::class.java,
            binding.btnToRestoReviewActivity to RestoReviewActivity::class.java,
            binding.btnToViewModelActivity   to ViewModelActivity::class.java,
            binding.btnToLiveDataActivity    to LiveDataActivity::class.java,
            binding.btnToReadWriteActivity   to ReadWriteActivity::class.java,
            binding.btnToSharedPrefActivity  to SharedPrefActivity::class.java,
            binding.btnToSettingPreferenceActivity to SettingPreferenceActivity::class.java,
            binding.btnToNotesApp            to MyNotesApp::class.java,
            binding.btnToRoomNotes           to RoomNotesActivity::class.java,
            binding.btnToBradcastActivity    to BroadcastActivity::class.java,
            binding.btnToSimpleNotifActivity to SimpleNotifActivity::class.java,
            binding.btnToAlarmManagerActivity to AlarmManagerActivity::class.java,
            binding.btnToCustomViewActivity to CustomViewActivity::class.java,
            binding.btnToTicketingActivity to TicketingActivity::class.java,
            binding.btnToLikesAppActivity to LikesAppActivity::class.java,
            binding.btnToWebViewActivity to WebViewActivity::class.java,
            binding.btnToMainMenuActivity to MainMenuActivity::class.java,
        )

        simpleNavMap.forEach { (button, activity) ->
            button.setOnClickListener { startActivity(Intent(this, activity)) }
        }

        // Special cases with extras or launchers
        binding.btnMoveWithData.setOnClickListener {
            startActivity(Intent(this, MoveWithDataActivity::class.java).apply {
                putExtra(MoveWithDataActivity.EXTRA_NAME, "Android Passing Data")
                putExtra(MoveWithDataActivity.EXTRA_AGE, 2000)
            })
        }

        binding.btnMoveWithObject.setOnClickListener {
            startActivity(Intent(this, MoveWithObjectActivity::class.java).apply {
                putExtra(MoveWithObjectActivity.EXTRA_PERSONA,
                    Persona("LearningDroid", 200, "Learning new Things", "person@example.com"))
            })
        }

        binding.btnMoveParcelize.setOnClickListener {
            startActivity(Intent(this, MoveWithObjectActivity::class.java).apply {
                putExtra(MoveWithObjectActivity.SIMPLE_PERSONA,
                    SimplePersona("LearningDroid", 200, 170, 70))
            })
        }

        binding.btnDialNumber.setOnClickListener {
            val phone = binding.etInput.text.toString().trim().ifEmpty { "0" }
            startActivity(Intent(Intent.ACTION_DIAL, "tel:$phone".toUri()))
        }

        binding.btnMoveResult.setOnClickListener {
            resultLauncher.launch(Intent(this, MoveForResultActivity::class.java))
        }

        binding.btnToScrollActivity.setOnClickListener {
            buyingLauncher.launch(Intent(this, ScrollingActivity::class.java))
        }
    }
}