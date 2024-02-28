package com.example.tbilisi_parking_final_exm.domain.usecase.sign_up

import com.example.tbilisi_parking_final_exm.domain.repository.sign_up.SignUpRepository
import com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up.toDomain
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.User
import javax.inject.Inject

class SignUpUseCase @Inject constructor(
    private val signUpRepository: SignUpRepository
) {

    suspend operator fun invoke(user: User) =
        signUpRepository.signUp(userDomain = user.toDomain())
}