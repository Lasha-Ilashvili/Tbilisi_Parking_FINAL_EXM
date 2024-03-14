package com.example.tbilisi_parking_final_exm.domain.usecase.cards

import com.example.tbilisi_parking_final_exm.presentation.model.cards.Card
import javax.inject.Inject

class GetCardsUseCase @Inject constructor(

) {
    operator fun invoke() =
        listOf(
            Card(
                title = "buy card"
            ),
            Card(
                title = "one day",
                zonalCard = Card.ZonalCard(
                    period = "1 day",
                    price = 20
                )
            ),
            Card(
                title = "one week",
                zonalCard = Card.ZonalCard(
                    period = "1 week",
                    price = 100
                )
            ),
            Card(
                title = "one month",
                zonalCard = Card.ZonalCard(
                    period = "1 month",
                    price = 300
                )
            ),
            Card(
                title = "six months", zonalCard = Card.ZonalCard(
                    period = "6 months",
                    price = 500
                )
            ),
            Card(
                title = "one year", zonalCard = Card.ZonalCard(
                    period = "1 year",
                    price = 800
                )
            )
        )
}