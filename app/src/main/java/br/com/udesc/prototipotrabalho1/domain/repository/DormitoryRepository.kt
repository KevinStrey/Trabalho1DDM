package br.com.udesc.prototipotrabalho1.domain.repository

import br.com.udesc.prototipotrabalho1.domain.model.Dormitory
import kotlinx.coroutines.flow.Flow

/**
 * Interface (Contrato) que define as operações de dados para a entidade Domicílio.
 */
interface DormitoryRepository {

    /**
     * Adiciona um novo domicílio à fonte de dados.
     */
    suspend fun addDormitory(dormitory: Dormitory)

    /**
     * Retorna um Flow contendo o domicílio de uma família específica.
     * Um Flow é usado para que a UI possa reagir a futuras atualizações no domicílio.
     */
    fun getDormitoryByFamilyId(familyId: Int): Flow<Dormitory?>
}