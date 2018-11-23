package io.github.gubarsergey.stratosphericbaloon.ui.weather

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.github.gubarsergey.stratosphericbaloon.App
import io.github.gubarsergey.stratosphericbaloon.R
import io.github.gubarsergey.stratosphericbaloon.extension.notNullContext
import io.github.gubarsergey.stratosphericbaloon.db.weather.WeatherRepository
import io.github.gubarsergey.stratosphericbaloon.extension.makeVisible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_weather.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.support.v4.toast

private const val MAX_NORMAL_HUMIDITY = 5.0
private const val MAX_NORMAL_WIND_SPEED = 4

class WeatherFragment : Fragment(), AnkoLogger {

    private lateinit var disposable: Disposable

    private val weatherRepository by lazy {
        WeatherRepository(
            notNullContext,
            (activity?.application as App).getRetrofit()
        )
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        disposable = weatherRepository
            .getWeather(44.5, 43.7)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .doOnSubscribe {
                toast(getString(R.string.downloading))
            }
            .doOnError {
                toast(getString(R.string.error_network_generic))
            }
            .subscribe({ weatherResponse ->
                info("Got weather response $weatherResponse")
                with (weatherResponse) {
                    weather_description_text_view.text = summary
                    weather_humidity_value_text_value.text = humidity.toString()
                    weather_wind_speed_value_text_view.text = windSpeed.toString()
                    weather_min_temp_value_text_view.text = temperatureLow.toString()
                    weather_max_temp_value_text_view.text = temperatureHigh.toString()
                    val recommendation = if (humidity > MAX_NORMAL_HUMIDITY || windSpeed > MAX_NORMAL_WIND_SPEED) {
                        getString(R.string.bad_weather_conditions)
                    } else {
                        getString(R.string.youre_ready_to_go)
                    }
                    weather_recommendation_text_view.makeVisible()
                    weather_recommendation_text_view.text = recommendation
                }
            }) { ex ->
                info("Failed lodaing weather $ex")
            }
    }
}