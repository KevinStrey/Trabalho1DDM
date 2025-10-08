package br.com.udesc.prototipotrabalho1.data.repository

import br.com.udesc.prototipotrabalho1.R
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementação "falsa" (fake) do FamilyRepository.
 * O campo de endereço foi removido dos dados mocados.
 */
class FakeFamilyRepositoryImpl : FamilyRepository {

    // ATUALIZAÇÃO: O campo de endereço foi removido de cada família.
    private val sampleFamilies = listOf(
        Family(1, "Família Silva", R.drawable.avatar_1),
        Family(2, "Família Oliveira", R.drawable.avatar_2),
        Family(3, "Família Santos", R.drawable.avatar_3),
        Family(4, "Família Pereira", R.drawable.avatar_4),
        Family(5, "Família Costa", R.drawable.avatar_5)
    )

    override fun getFamilies(): Flow<List<Family>> {
        return flowOf(sampleFamilies)
    }

    override suspend fun getFamilyById(id: Int): Family? {
        return sampleFamilies.find { it.id == id }
    }
}