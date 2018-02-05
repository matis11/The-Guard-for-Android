package com.mateuszbartos.theguard.activities

import android.os.Bundle
import android.preference.PreferenceManager
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import com.mateuszbartos.theguard.R
import com.mateuszbartos.theguard.api.ApiClient
import com.mateuszbartos.theguard.api.ApiService
import com.mateuszbartos.theguard.fragments.CamerasFragment
import com.mateuszbartos.theguard.fragments.SensorsFragment
import com.mateuszbartos.theguard.models.ApiDto
import kotlinx.android.synthetic.main.activity_with_action_bar.*
import java.util.*



class MainActivity : BottomBarActivity() {

    companion object {
        private const val RC_SIGN_IN = 123
        private const val KEY_TOKEN = "token"
    }

    private enum class BottomBarItems {
        CAMERAS, SENSORS, ALERTS, SETTINGS
    }

    private val bottomBarItems = ArrayList<BottomBarItems>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ApiClient.initApi()
        prepareBottomBar()
        openFragment(CamerasFragment.newInstance(), CamerasFragment.TAG)
    }

    override fun onResume() {
        super.onResume()

        val user = FirebaseAuth.getInstance().currentUser
        if (user == null) {
            val providers = Arrays.asList(AuthUI.IdpConfig.Builder(AuthUI.EMAIL_PROVIDER).build())

            startActivityForResult(
                    AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(providers)
                            .build(),
                    RC_SIGN_IN)
        } else {
            user.getIdToken(true)
                    .addOnCompleteListener {
                        val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
                        sharedPreferences.edit().putString(KEY_TOKEN, it.result.token).apply()
                    }
        }
    }

    override fun setBottomBarItems() {
        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.tab_cameras), R.drawable.ic_videocam))
        bottomBarItems.add(BottomBarItems.CAMERAS)

        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.tab_sensors), R.drawable.ic_sensor))
        bottomBarItems.add(BottomBarItems.SENSORS)

        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.tab_alerts), R.drawable.ic_warning))
        bottomBarItems.add(BottomBarItems.ALERTS)

        bottomNavigation.addItem(AHBottomNavigationItem(getString(R.string.tab_settings), R.drawable.ic_settings))
        bottomBarItems.add(BottomBarItems.SETTINGS)
    }

    override fun setBottomBarListeners() {
        bottomNavigation.setOnTabSelectedListener { position, wasSelected ->
            if (wasSelected) {
                return@setOnTabSelectedListener false
            }

            val currentItem = this.bottomBarItems[position]
            when (currentItem) {
                MainActivity.BottomBarItems.CAMERAS -> {
                    addFragment(CamerasFragment.newInstance(), false, CamerasFragment.TAG)
                }

                MainActivity.BottomBarItems.SENSORS -> {
                    addFragment(SensorsFragment.newInstance(), false, SensorsFragment.TAG)
                }

                MainActivity.BottomBarItems.ALERTS -> {
//                    addFragment(PartnersFragment.newInstance(eventInfo), false, PartnersFragment.TAG)
                }

                MainActivity.BottomBarItems.SETTINGS -> {
//                    addFragment(CaseFragment.newInstance(), false, CaseFragment.TAG)
                }

                else -> return@setOnTabSelectedListener false
            }
            true
        }
    }
}