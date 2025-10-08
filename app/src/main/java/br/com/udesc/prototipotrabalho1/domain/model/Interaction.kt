package br.com.udesc.prototipotrabalho1.domain.model

import java.time.LocalDate

/**
 * Modelo de negócio puro, representando a entidade Interação.
 *
 * @param id Identificador único da interação.
 * @param familyId Identificador da família à qual a interação está associada.
 * @param type O tipo de interação (ex: "Visita domiciliar", "Contato telefônico").
 * @param date A data em que a interação ocorreu.
 * @param summary Um resumo do que foi discutido ou observado.
 * @param nextSteps Ações de acompanhamento ou próximos passos a serem tomados.
 */
data class Interaction(
    val id: Int,
    val familyId: Int,
    val type: String,
    val date: LocalDate,
    val summary: String,
    val nextSteps: String
)