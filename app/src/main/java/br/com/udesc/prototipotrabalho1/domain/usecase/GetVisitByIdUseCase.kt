package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.model.Visit
import br.com.udesc.prototipotrabalho1.domain.repository.VisitRepository

/**
 * Caso de Uso para obter os detalhes de uma única visita pelo seu ID.
 */
class GetVisitByIdUseCase(
    private val visitRepository: VisitRepository
) {
    suspend operator fun invoke(id: Int): Visit? {
        return visitRepository.getVisitById(id)
    }
}