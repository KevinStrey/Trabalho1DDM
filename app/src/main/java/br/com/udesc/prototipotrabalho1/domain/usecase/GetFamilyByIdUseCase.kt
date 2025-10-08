package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository

/**
 * Caso de Uso para obter uma única família pelo seu ID.
 */
class GetFamilyByIdUseCase(
    private val familyRepository: FamilyRepository
) {
    suspend operator fun invoke(id: Int): Family? {
        return familyRepository.getFamilyById(id)
    }
}