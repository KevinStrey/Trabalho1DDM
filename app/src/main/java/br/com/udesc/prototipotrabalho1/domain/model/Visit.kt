package br.com.udesc.prototipotrabalho1.domain.model

import java.time.LocalDate

/**
 * Modelo de negócio puro, representando a entidade Visita.
 */
data class Visit(
    val id: Int,
    val familyId: Int,
    val familyName: String,
    val address: String,
    val date: LocalDate
)