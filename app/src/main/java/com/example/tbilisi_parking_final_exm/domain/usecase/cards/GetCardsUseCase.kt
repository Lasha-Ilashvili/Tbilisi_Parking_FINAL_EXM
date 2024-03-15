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
                    price = (20),
                    backgroundColor = "#219BCC"
                )
            ),
            Card(
                title = "one week",
                zonalCard = Card.ZonalCard(
                    period = "1 week",
                    price = 100,
                    backgroundColor = "#03DAC6"
                )
            ),
            Card(
                title = "one month",
                zonalCard = Card.ZonalCard(
                    period = "1 month",
                    price = 300,
                    backgroundColor = "#605A7C"
                )
            ),
            Card(
                title = "six months", zonalCard = Card.ZonalCard(
                    period = "6 months",
                    price = 500,
                    backgroundColor = "#E38568"
                )
            ),
            Card(
                title = "one year", zonalCard = Card.ZonalCard(
                    period = "1 year",
                    price = 800,
                    backgroundColor = "#68E387"
                )
            )
        )
}