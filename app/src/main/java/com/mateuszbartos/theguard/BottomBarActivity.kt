package com.mateuszbartos.theguard

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.MenuItem
import com.aurelhubert.ahbottomnavigation.AHBottomNavigation
import kotlinx.android.synthetic.main.activity_with_action_bar.*

abstract class BottomBarActivity : AppCompatActivity() {

    @LayoutRes
    protected fun getLayoutId(): Int {
        return R.layout.activity_with_action_bar
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutId())
        setSupportActionBar(toolbar)
    }

    protected fun prepareBottomBar() {
        val accentColor = ContextCompat.getColor(this, R.color.colorPrimary)
        val backgroundColor = ContextCompat.getColor(this, R.color.colorBackground)
        val inactiveColor = ContextCompat.getColor(this, R.color.colorTabIconInactive)

        bottomNavigation.accentColor = accentColor
        bottomNavigation.defaultBackgroundColor = backgroundColor
        bottomNavigation.inactiveColor = inactiveColor
        bottomNavigation.titleState = AHBottomNavigation.TitleState.ALWAYS_SHOW

        setBottomBarItems()
        setBottomBarListeners()
    }

    protected fun addFragment(fragment: Fragment, addToBackStack: Boolean, tag: String) {
        var transaction = supportFragmentManager
                .beginTransaction()
                .replace(R.id.container, fragment, tag)

        if (addToBackStack) {
            transaction = transaction.addToBackStack(tag)
        }

        transaction.commit()
    }

    protected fun openFragment(fragment: Fragment, tag: String) {
        if (supportFragmentManager.findFragmentByTag(tag) == null) {
            addFragment(fragment, true, tag)
        } else {
            supportFragmentManager.popBackStack(tag, 0)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (supportFragmentManager.backStackEntryCount == 0) {
            finish()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return if (item.itemId == android.R.id.home) {
            onBackPressed()
            true
        } else {
            super.onOptionsItemSelected(item)
        }
    }

    protected abstract fun setBottomBarItems()

    protected abstract fun setBottomBarListeners()
}
