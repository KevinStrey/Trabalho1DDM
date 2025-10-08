package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.model.Dormitory
import br.com.udesc.prototipotrabalho1.domain.repository.DormitoryRepository

/**
 * Caso de Uso para adicionar um novo domicílio.
 */
class AddDormitoryUseCase(
    private val dormitoryRepository: DormitoryRepository
) {
    /**
     * Executa o caso de uso.
     * @param dormitory O novo domicílio a ser adicionado.
     * @throws IllegalArgumentException se o endereço estiver em branco.
     */
    suspend operator fun invoke(dormitory: Dormitory) {
        if (dormitory.address.isBlank()) {
            throw IllegalArgumentException("O endereço não pode estar em branco.")
        }
        dormitoryRepository.addDormitory(dormitory)
    }
}