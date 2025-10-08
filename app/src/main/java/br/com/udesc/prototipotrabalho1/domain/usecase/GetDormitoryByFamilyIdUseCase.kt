package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.repository.DormitoryRepository

/**
 * Caso de Uso para obter o domicílio de uma família específica.
 */
class GetDormitoryByFamilyIdUseCase(
    private val dormitoryRepository: DormitoryRepository
) {
    operator fun invoke(familyId: Int) = dormitoryRepository.getDormitoryByFamilyId(familyId)
}