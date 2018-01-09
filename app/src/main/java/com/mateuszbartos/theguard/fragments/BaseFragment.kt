package com.mateuszbartos.theguard.fragments

import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.support.v7.app.ActionBar
import android.support.v7.app.AppCompatActivity
import android.view.View
import rx.subscriptions.CompositeSubscription

open class BaseFragment : Fragment() {
    protected val subscription = CompositeSubscription()
    private var actionBar: ActionBar? = null

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val activity = activity as AppCompatActivity
        actionBar = activity.supportActionBar
        actionBar?.show()
    }

    override fun onDestroyView() {
        if (subscription.hasSubscriptions()) {
            subscription.clear()
        }
        super.onDestroyView()
    }

    protected fun setActionBarTitle(title: String) {
        actionBar?.setTitle(title)
    }

    protected fun setActionBarTitle(@StringRes titleRes: Int) {
        setActionBarTitle(getString(titleRes))
    }

    protected fun hideActionBar() {
        actionBar?.hide()
    }

    protected fun showBackButtonInActionBar() {
        actionBar?.setDisplayHomeAsUpEnabled(true)
    }
}