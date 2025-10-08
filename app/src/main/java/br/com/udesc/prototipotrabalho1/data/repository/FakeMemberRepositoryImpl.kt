package br.com.udesc.prototipotrabalho1.data.repository

import br.com.udesc.prototipotrabalho1.domain.model.Member
import br.com.udesc.prototipotrabalho1.domain.repository.MemberRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementação "falsa" (fake) do MemberRepository que usa uma lista em memória.
 */
class FakeMemberRepositoryImpl : MemberRepository {

    // Nossa "tabela" de membros em memória, agora com dados iniciais.
    private val members = mutableListOf(
        Member(
            id = 1,
            familyId = 1, // Pertence à Família Silva
            name = "José Silva",
            birthDate = "15/05/1980",
            kinship = "Chefe da Família",
            healthInfo = "Hipertenso, faz uso de Losartana.",
            photoUri = null
        ),
        Member(
            id = 2,
            familyId = 1, // Pertence à Família Silva
            name = "Maria Silva",
            birthDate = "20/08/1982",
            kinship = "Cônjuge",
            healthInfo = "Diabetes tipo 2.",
            photoUri = null
        ),
        Member(
            id = 3,
            familyId = 1, // Pertence à Família Silva
            name = "Ana Silva",
            birthDate = "10/11/2005",
            kinship = "Filha",
            healthInfo = "Asma, usa bombinha esporadicamente.",
            photoUri = null
        ),
        Member(
            id = 4,
            familyId = 2, // Pertence à Família Oliveira
            name = "Carlos Oliveira",
            birthDate = "01/02/1975",
            kinship = "Chefe da Família",
            healthInfo = "Saudável.",
            photoUri = null
        ),
        Member(
            id = 5,
            familyId = 2, // Pertence à Família Oliveira
            name = "Sofia Oliveira",
            birthDate = "30/03/1978",
            kinship = "Cônjuge",
            healthInfo = "Enxaqueca crônica.",
            photoUri = null
        )
    )

    /**
     * Adiciona um novo membro à lista.
     * A lógica de gerar um novo ID continuará funcionando normalmente.
     */
    override suspend fun addMember(member: Member) {
        val newId = (members.maxOfOrNull { it.id } ?: 0) + 1
        members.add(member.copy(id = newId))
    }

    /**
     * Retorna um Flow contendo os membros filtrados por familyId.
     */
    override fun getMembersByFamilyId(familyId: Int): Flow<List<Member>> {
        val familyMembers = members.filter { it.familyId == familyId }
        return flowOf(familyMembers)
    }

    /**
     * Busca um membro específico pelo seu ID.
     */
    override suspend fun getMemberById(id: Int): Member? {
        return members.find { it.id == id }
    }
}