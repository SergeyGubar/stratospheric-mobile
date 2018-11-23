package io.github.gubarsergey.stratosphericbaloon.ui.settings

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.helper.SharedPrefHelper
import io.github.gubarsergey.stratosphericbaloon.ui.login.LoginActivity
import kotlinx.android.synthetic.main.fragment_settings.*
import org.jetbrains.anko.support.v4.intentFor

class SettingsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_settings, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        log_out_button.setOnClickListener {
            SharedPrefHelper.cleanCurrentUser(notNullContext)
            startActivity(intentFor<LoginActivity>())
            activity?.finish()
        }
    }
}