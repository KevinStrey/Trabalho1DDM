package br.com.udesc.prototipotrabalho1.data.repository

import br.com.udesc.prototipotrabalho1.R
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

/**
 * Implementação "falsa" (fake) do FamilyRepository que usa dados mocados em memória.
 * Esta classe agora é a única fonte de verdade para os dados das famílias.
 * A criação de sua instância será gerenciada manualmente pelo AppContainer.
 */
class FakeFamilyRepositoryImpl : FamilyRepository {

    private val sampleFamilies = listOf(
        Family(1, "Família Silva", "Rua das Flores, 123", R.drawable.avatar_1),
        Family(2, "Família Oliveira", "Avenida Central, 456", R.drawable.avatar_2),
        Family(3, "Família Santos", "Travessa da Paz, 789", R.drawable.avatar_3),
        Family(4, "Família Pereira", "Rua do Sol, 101", R.drawable.avatar_4),
        Family(5, "Família Costa", "Avenida da Lua, 202", R.drawable.avatar_5)
    )

    override fun getFamilies(): Flow<List<Family>> {
        return flowOf(sampleFamilies)
    }

    override suspend fun getFamilyById(id: Int): Family? {
        return sampleFamilies.find { it.id == id }
    }
}