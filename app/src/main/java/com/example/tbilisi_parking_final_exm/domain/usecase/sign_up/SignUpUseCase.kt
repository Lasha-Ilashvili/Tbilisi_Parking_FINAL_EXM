package com.example.tbilisi_parking_final_exm.domain.usecase.sign_up

import com.example.tbilisi_parking_final_exm.domain.model.sign_up.GetUser
import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {
    suspend operator fun invoke(getUser: GetUser) =
        signUpRepository.signUp(getUser = getUser)
}