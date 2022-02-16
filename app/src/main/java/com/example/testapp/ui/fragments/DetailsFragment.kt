package com.example.testapp.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.testapp.R
import com.example.testapp.databinding.DetailsFragmentBinding
import com.example.testapp.viewModel.DetailsViewModel
import com.example.testapp.viewModel.models.UserVM
import com.squareup.picasso.Picasso
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailsFragment : Fragment() {
    private var vb: DetailsFragmentBinding? = null
    private val detailsViewModel: DetailsViewModel by viewModel()
    private var user: UserVM? = null

    companion object {
        const val userExtra = "userExtra"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        vb = DetailsFragmentBinding.inflate(inflater, container, false)
        return vb?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        user = requireArguments().getParcelable(userExtra)
        initBtns()
        fillFields()
        loadAvatar()
    }

    private fun loadAvatar() {
        Picasso.get().load(user?.avatar)
            .error(R.drawable.error_load)
            .into(vb?.userAvatar)
    }

    private fun fillFields() {
         user?.first_name?.apply {
            vb?.userFirstNameET?.setText(this)
        }
        user?.last_name?.apply {
            vb?.userSecondNameET?.setText(this)
        }
    }

    private fun initBtns() {
        vb?.deleteBtn?.setOnClickListener {
            user?.apply {
                detailsViewModel.deleteUser(this)
                findNavController().popBackStack()
            }
        }
        vb?.editBtn?.setOnClickListener {
            user?.apply {
                 vb?.userFirstNameET?.text?.toString()?.apply {
                    user?.first_name = this
                }
                vb?.userSecondNameET?.text?.toString()?.apply {
                    user?.last_name = this
                }
                detailsViewModel.updateUser(this)
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        vb = null
    }
}