package com.example.tbilisi_parking_final_exm.domain.usecase.cards

import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(

) {
    operator fun invoke() =
        listOf(
            Card(
                title = "one day",
                zonalCard = Card.ZonalCard(
                    period = "1 day",
                    price = 20
                )
            ),
            Card(
                title = "imperdiet",
                zonalCard = Card.ZonalCard(
                    period = "veritus",
                    price = 7840
                )
            ),
            Card(
                title = "solet",
                zonalCard = Card.ZonalCard(
                    period = "partiendo",
                    price = 8666
                )
            ),
            Card(
                title = "vix", zonalCard = Card.ZonalCard(
                    period = "laudem",
                    price = 12948
                )
            ),
            Card(
                title = "lobortis", zonalCard = Card.ZonalCard(
                    period = "intellegebat",
                    price = 4943
                )
            )
        )
}