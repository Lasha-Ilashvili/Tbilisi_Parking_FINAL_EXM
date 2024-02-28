package com.example.tbilisi_parking_final_exm.presentation.mapper.sign_up

import com.example.tbilisi_parking_final_exm.domain.model.sign_up.SignUpDomain
import com.example.tbilisi_parking_final_exm.presentation.model.sign_up.SignUp


fun SignUpDomain.toPresentation() = SignUp(
    email = email,
    password = password
)