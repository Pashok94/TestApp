package com.example.testapp.ui.fragments.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testapp.R
import com.example.testapp.databinding.ItemUserBinding
import com.example.testapp.viewModel.models.UserVM
import com.squareup.picasso.Picasso

class UsersAdapter(val openDetailsCallback: (userVM: UserVM) -> Unit) : RecyclerView.Adapter<UsersAdapter.UserViewHolder>() {
    private val users = arrayListOf<UserVM>()

    @SuppressLint("NotifyDataSetChanged")
    fun addUsers(newUsers: List<UserVM>){
        users.clear()
        users.addAll(newUsers)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        return UserViewHolder(
            ItemUserBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
        )
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.onBind(users[position], position)
    }

    override fun getItemCount(): Int {
        return users.size
    }

    inner class UserViewHolder(private val vb: ItemUserBinding) : RecyclerView.ViewHolder(vb.root){
        fun onBind(user: UserVM, position: Int){
            with(vb){
                userFirstName.text = user.first_name
                userSecondName.text = user.last_name
                Picasso.get().load(user.avatar)
                    .error(R.drawable.error_load)
                    .into(userAvatar)
            }
            itemView.setOnClickListener {
                openDetailsCallback.invoke(user)
            }
        }
    }
}