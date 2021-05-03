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
import ru.geekbrains.kotlin.viewmodel.MainViewModel

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        val observer = Observer<Any> { renderData(it) }
        // связка с жизненным циклом вьюхи(!) фрагмента MainFragment
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
    }

    // renderData() вызывается Observer'ом при изменении данных LiveData
    private fun renderData(data: Any) {
        Toast.makeText(context, "Даннные загружены", Toast.LENGTH_LONG).show()
    }
}