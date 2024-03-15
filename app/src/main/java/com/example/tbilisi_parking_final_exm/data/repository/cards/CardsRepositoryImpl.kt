package com.example.tbilisi_parking_final_exm.data.repository.cards

import com.example.tbilisi_parking_final_exm.data.common.HandleResponse
import com.example.tbilisi_parking_final_exm.data.common.Resource
import com.example.tbilisi_parking_final_exm.data.mapper.base.asResource
import com.example.tbilisi_parking_final_exm.data.mapper.cards.toDomain
import com.example.tbilisi_parking_final_exm.data.service.cards.CardsService
import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard
import com.example.tbilisi_parking_final_exm.domain.repository.cards.CardsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CardsRepositoryImpl @Inject constructor(
    private val handleResponse: HandleResponse,
    private val cardsService: CardsService
) : CardsRepository {

    override suspend fun getCards(): Flow<Resource<List<GetCard>>> {
        return handleResponse.safeApiCall {
            cardsService.getCards()
        }.asResource {
            it.map {dto->
                dto.toDomain()
            }
        }
    }
}