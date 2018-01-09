package com.mateuszbartos.theguard

import android.os.Bundle
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem
import kotlinx.android.synthetic.main.activity_with_action_bar.*
import java.util.ArrayList

class MainActivity : BottomBarActivity() {

    private enum class BottomBarItems {
        CAMERAS, SENSORS, ALERTS, SETTINGS
    }

    private val bottomBarItems = ArrayList<BottomBarItems>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prepareBottomBar()

//        openFragment(RoomsPreviewFragment.newInstance(eventInfo.id(), eventInfo.name()), RoomsPreviewFragment.TAG)
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
//        bottomNavigation.setOnTabSelectedListener { position, wasSelected ->
//            if (wasSelected) {
//                return@setOnTabSelectedListener false
//            }
//
//            val currentItem = this.bottomBarItems[position]
//            when (currentItem) {
//                MainActivity.BottomBarItems.CAMERAS -> {
//                    addFragment(RoomsPreviewFragment.newInstance(eventInfo.id(), eventInfo.name()), false, RoomsPreviewFragment.TAG)
//                }
//
//                MainActivity.BottomBarItems.SENSORS -> {
//                    addFragment(AgendaFragment.newInstance(eventInfo), false, AgendaFragment.TAG)
//                }
//
//                MainActivity.BottomBarItems.ALERTS -> {
//                    addFragment(PartnersFragment.newInstance(eventInfo), false, PartnersFragment.TAG)
//                }
//
//                MainActivity.BottomBarItems.SETTINGS -> {
//                    addFragment(CaseFragment.newInstance(), false, CaseFragment.TAG)
//                }
//
//                else -> return@setOnTabSelectedListener false
//            }
//            true
//        }
    }
}