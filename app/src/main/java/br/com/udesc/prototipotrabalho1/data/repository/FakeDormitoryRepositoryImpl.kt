package br.com.udesc.prototipotrabalho1.data.repository

import br.com.udesc.prototipotrabalho1.domain.model.Dormitory
import br.com.udesc.prototipotrabalho1.domain.model.Geolocation
import br.com.udesc.prototipotrabalho1.domain.repository.DormitoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementação "falsa" (fake) do DormitoryRepository que usa uma lista em memória.
 */
class FakeDormitoryRepositoryImpl : DormitoryRepository {

    // Nossa "tabela" de domicílios em memória, com um dado de exemplo.
    private val dormitories = mutableListOf(
        Dormitory(
            id = 1,
            familyId = 1, // Associado à Família Silva
            address = "Rua das Flores, 123, Bairro Jardim, Cidade Exemplo",
            geolocation = Geolocation(latitude = -27.59537, longitude = -48.54802) // Exemplo: Florianópolis
        )
    )

    /**
     * Adiciona um novo domicílio. Se já existir um para a mesma família, ele é substituído.
     */
    override suspend fun addDormitory(dormitory: Dormitory) {
        // Remove qualquer domicílio existente para esta família para garantir um por família.
        dormitories.removeAll { it.familyId == dormitory.familyId }

        val newId = (dormitories.maxOfOrNull { it.id } ?: 0) + 1
        dormitories.add(dormitory.copy(id = newId))
    }

    /**
     * Retorna um domicílio filtrado pelo ID da família.
     */
    override fun getDormitoryByFamilyId(familyId: Int): Flow<Dormitory?> {
        val dormitory = dormitories.find { it.familyId == familyId }
        return flowOf(dormitory)
    }
}