package com.example.testapp.ui.fragments.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testapp.R
import com.example.testapp.databinding.MainFragmentBinding
import com.example.testapp.ui.fragments.DetailsFragment
import com.example.testapp.viewModel.MainViewModel
import com.example.testapp.viewModel.models.UserVM
import com.example.testapp.viewModel.utils.AppState
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainFragment : Fragment() {
    private val viewModel: MainViewModel by viewModel()
    private var vb: MainFragmentBinding? = null
    private lateinit var adapter: UsersAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = MainFragmentBinding.inflate(inflater, container, false)
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vb?.main?.setOnRefreshListener {
            viewModel.getUsers()
        }
        initRV()
        initViewModel()
    }

    private fun initRV() {
        adapter = UsersAdapter(openDetailsCallback)
        vb?.mainRV?.layoutManager = LinearLayoutManager(requireContext())
        vb?.mainRV?.adapter = adapter
    }

    private fun initViewModel() {
        val observer = Observer<AppState> { renderData(it) }
        viewModel.getLiveData().observe(viewLifecycleOwner, observer)
        viewModel.getLocalUsers()
    }

    private val openDetailsCallback: (userVM: UserVM) -> Unit = {
        val bundle = Bundle()
        bundle.putParcelable(DetailsFragment.userExtra, it)
        findNavController().navigate(R.id.detailsFragment, bundle)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.Error -> {}
            is AppState.SuccessLoadUsers -> {
                if (appState.users.isEmpty())
                    vb?.listEmptyMessage?.visibility = View.VISIBLE
                else
                    vb?.listEmptyMessage?.visibility = View.INVISIBLE

                vb?.main?.isRefreshing = false
                adapter.addUsers(appState.users)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }
}