package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.repository.VisitRepository
import java.time.LocalDate

/**
 * Caso de Uso para obter a lista de visitas para uma data específica.
 */
class GetVisitsByDateUseCase(
    private val visitRepository: VisitRepository
) {
    operator fun invoke(date: LocalDate) = visitRepository.getVisitsByDate(date)
}