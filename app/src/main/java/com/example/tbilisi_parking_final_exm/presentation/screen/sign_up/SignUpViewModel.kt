package com.example.tbilisi_parking_final_exm.presentation.screen.sign_up

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.repository.SignUpRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val signUpRepository: SignUpRepository
) : ViewModel() {

    init {
        viewModelScope.launch {
            signUpRepository.signUp(
                email = "eve.holt@reqres.in",
                password = "dapibus",
                personalNumber = 1302,
                firstName = "Morton Massey",
                lastName = "Clara Britt",
                phoneNumber = 1749
            ).collect {
                if (it is Resource.Success) {
                    d("ShowData", "${it.data.first} ${it.data.second}")
                }
            }
        }
    }

    fun setUp() {

    }
}