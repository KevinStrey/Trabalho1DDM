package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.repository.InteractionRepository

/**
 * Caso de Uso para obter a lista de interações de uma família específica.
 */
class GetInteractionsByFamilyIdUseCase(
    private val interactionRepository: InteractionRepository
) {
    operator fun invoke(familyId: Int) = interactionRepository.getInteractionsByFamilyId(familyId)
}