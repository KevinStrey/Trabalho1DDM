package br.com.udesc.prototipotrabalho1.domain.repository

import br.com.udesc.prototipotrabalho1.domain.model.Family
import kotlinx.coroutines.flow.Flow

/**
 * Interface (Contrato) que define as operações de dados para a entidade Família.
 *
 * A camada de domínio depende desta abstração, não de uma implementação concreta.
 * Isso nos permite trocar a fonte de dados (de uma lista "fake" para uma API, por exemplo)
 * sem precisar alterar os UseCases ou ViewModels.
 */
interface FamilyRepository {

    /**
     * Retorna um Flow contendo a lista de todas as famílias.
     * O uso de Flow permite que a UI reaja automaticamente a mudanças nos dados.
     */
    fun getFamilies(): Flow<List<Family>>

    /**
     * Busca e retorna uma única família pelo seu ID.
     * É uma função suspensa (suspend) pois pode envolver uma operação de I/O (rede/banco de dados)
     * que não deve bloquear a thread principal.
     */
    suspend fun getFamilyById(id: Int): Family?
}