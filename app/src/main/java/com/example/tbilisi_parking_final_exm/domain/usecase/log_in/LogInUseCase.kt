package com.example.tbilisi_parking_final_exm.domain.usecase.log_in

import com.example.tbilisi_parking_final_exm.domain.model.log_in.GetUser
import com.example.tbilisi_parking_final_exm.domain.repository.log_in.LogInRepository
import javax.inject.Inject

class LogInUseCase @Inject constructor(
    private val logInRepository: LogInRepository
) {
    suspend operator fun invoke(email: String, password: String) = logInRepository.logIn(GetUser(email, password))
}