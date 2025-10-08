package br.com.udesc.prototipotrabalho1.domain.repository

import br.com.udesc.prototipotrabalho1.domain.model.Interaction
import kotlinx.coroutines.flow.Flow

/**
 * Interface (Contrato) que define as operações de dados para a entidade Interação.
 */
interface InteractionRepository {

    /**
     * Adiciona uma nova interação à fonte de dados.
     */
    suspend fun addInteraction(interaction: Interaction)

    /**
     * Retorna um Flow contendo a lista de todas as interações de uma família específica.
     */
    fun getInteractionsByFamilyId(familyId: Int): Flow<List<Interaction>>
}