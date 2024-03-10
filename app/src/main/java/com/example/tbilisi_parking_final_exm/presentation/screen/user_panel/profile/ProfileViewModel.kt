package com.example.tbilisi_parking_final_exm.presentation.screen.user_panel.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.domain.usecase.profile.GetProfileUseCase
import com.example.tbilisi_parking_final_exm.presentation.event.profile.ProfileEvent
import com.example.tbilisi_parking_final_exm.presentation.mapper.profile.toPresenter
import com.example.tbilisi_parking_final_exm.presentation.state.profile.ProfileState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val getProfile: GetProfileUseCase
): ViewModel() {

//    private val _logInState = MutableStateFlow(LogInState())
//    val logInState: SharedFlow<LogInState> = _logInState.asStateFlow()

    private val _profileState = MutableStateFlow(ProfileState())
    val profileState: SharedFlow<ProfileState> = _profileState.asStateFlow()

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.FetchUserProfile -> fetchUserProfile()
            is ProfileEvent.ResetErrorMessage -> updateErrorMessage(message = null)
        }
    }

    private fun fetchUserProfile() {
        viewModelScope.launch {
            getProfile.invoke().collect{
                println(it)
                when(it) {
                    is Resource.Loading -> _profileState.update { currentState ->
                        currentState.copy(
                            isLoading = it.loading
                        )
                    }

                    is Resource.Error -> updateErrorMessage(it.errorMessage)

                    is Resource.Success -> _profileState.update { currentState ->
                        currentState.copy(
                            profile = it.data.toPresenter()
                        )
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(message: String?) {
        _profileState.update {currentState ->
            currentState.copy(
                errorMessage = message
            )

        }
    }


}