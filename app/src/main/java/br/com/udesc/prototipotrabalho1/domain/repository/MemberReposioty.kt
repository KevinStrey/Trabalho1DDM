package br.com.udesc.prototipotrabalho1.domain.repository

import br.com.udesc.prototipotrabalho1.domain.model.Member
import kotlinx.coroutines.flow.Flow

/**
 * Interface (Contrato) que define as operações de dados para a entidade Membro.
 */
interface MemberRepository {

    /**
     * Adiciona um novo membro à fonte de dados.
     * É uma função suspensa (suspend) pois a gravação de dados é uma operação de I/O.
     */
    suspend fun addMember(member: Member)

    /**
     * Retorna um Flow contendo a lista de todos os membros de uma família específica.
     */
    fun getMembersByFamilyId(familyId: Int): Flow<List<Member>>

    /**
     * Busca e retorna um único membro pelo seu ID.
     */
    suspend fun getMemberById(id: Int): Member?
}