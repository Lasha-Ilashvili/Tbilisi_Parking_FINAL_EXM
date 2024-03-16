package com.example.tbilisi_parking_final_exm.domain.usecase.cards

import com.example.tbilisi_parking_final_exm.domain.model.cards.GetCard
import com.example.tbilisi_parking_final_exm.domain.repository.cards.CardsRepository
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(
    private val cardsRepository: CardsRepository
) {

//    suspend operator fun invoke() =
//        cardsRepository.getCards()


    operator fun invoke() = flow<List<GetCard>> {
        listOf(
            GetCard(
                title = "buy card"
            ),
            GetCard(
                title = "one day",
                zonalCard = GetCard.GetZonalCard(
                    period = "1 day",
                    price = (20),
                    backgroundColor = "#219BCC"
                )
            ),
            GetCard(
                title = "one week",
                zonalCard = GetCard.GetZonalCard(
                    period = "1 week",
                    price = 100,
                    backgroundColor = "#03DAC6"
                )
            ),
            GetCard(
                title = "one month",
                zonalCard = GetCard.GetZonalCard(
                    period = "1 month",
                    price = 300,
                    backgroundColor = "#605A7C"
                )
            ),
            GetCard(
                title = "six months", zonalCard = GetCard.GetZonalCard(
                    period = "6 months",
                    price = 500,
                    backgroundColor = "#E38568"
                )
            ),
            GetCard(
                title = "one year", zonalCard = GetCard.GetZonalCard(
                    period = "1 year",
                    price = 800,
                    backgroundColor = "#68E387"
                )
            )
        )
    }
}