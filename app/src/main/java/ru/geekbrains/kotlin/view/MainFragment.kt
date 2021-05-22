package ru.geekbrains.kotlin.view

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import ru.geekbrains.kotlin.R

import ru.geekbrains.kotlin.databinding.MainFragmentBinding
import ru.geekbrains.kotlin.model.Weather
import ru.geekbrains.kotlin.viewmodel.AppState
import ru.geekbrains.kotlin.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private var _binding: MainFragmentBinding? = null
    private val binding
       // геттер переменной binding
       get(): MainFragmentBinding = _binding!!

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        //return inflater.inflate(R.layout.main_fragment, container, false)
        _binding = MainFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // привязка viewModel к жизненному циклу фрагмента MainFragment
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observer = Observer<AppState> { state: AppState -> renderData(state) }
        // подписываемся на изменения LiveData<AppState>
        // связка с жизненным циклом вьюхи(!) фрагмента MainFragment
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getWeather()
    }

    // renderData() вызывается Observer'ом при изменении данных LiveData
    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Loading -> {
                binding.loadingLayout.visibility = View.VISIBLE // отображаем прогрессбар
                Toast.makeText(context, "Загрузка данных.", Toast.LENGTH_SHORT).show()
            }
            is AppState.Success -> {
                binding.loadingLayout.visibility = View.GONE // скрываем прогрессбар
                val weatherData = appState.weatherData
                setData(weatherData)
                Toast.makeText(context, "Даннные загружены.", Toast.LENGTH_LONG).show()
            }
            is AppState.Error -> {
                binding.loadingLayout.visibility = View.GONE // скрываем прогрессбар
                Toast.makeText(context, "Сбой при загрузке данных.", Toast.LENGTH_SHORT).show()
                //viewModel.getWeather()
            }
        }
    }

    private fun setData(weatherData: Weather) {
        binding.cityName.text = weatherData.city.name
        binding.cityCoordinates.text = String.format(getString(R.string.city_coordinates), weatherData.city.lat, weatherData.city.lon)
        binding.temperatureValue.text = weatherData.temperature.toString()
        binding.feelsLikeValue.text = weatherData.feelsLike.toString()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}