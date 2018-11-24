package io.github.gubarsergey.stratosphericbaloon.ui.admin.addlaunch

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.api.launch.entitiy.AddLaunchModel
import io.github.gubarsergey.stratosphericbaloon.db.launches.LaunchesRepository
import io.github.gubarsergey.stratosphericbaloon.db.launches.realm.LaunchMapper
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_add_launch.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast

class AddLaunchActivity : AppCompatActivity(), AnkoLogger {

    private val disposable = CompositeDisposable()
    private val launchesRepository by lazy {
        LaunchesRepository(this, (application as App).getRetrofit(), LaunchMapper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_launch)
        add_launch_button.setOnClickListener {
            val model = AddLaunchModel(
                add_launch_name_edit_text.text.toString(),
                "2018-01-01 00:00:00.000000",
                add_launch_longitude_edit_text.text.toString().toDouble(),
                add_launch_latitude_edit_text.text.toString().toDouble()
            )
            launchesRepository.addLaunch(model)
                .subscribe({
                    info("Added launch $it")
                    finish()
                }) {
                    info("Launch add failed $it")
                    toast("Launch add failed!")
                }
        }
    }

    override fun onStop() {
        super.onStop()
        disposable.dispose()
    }
}
