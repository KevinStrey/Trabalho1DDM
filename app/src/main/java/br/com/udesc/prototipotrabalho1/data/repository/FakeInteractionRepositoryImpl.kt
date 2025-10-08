package br.com.udesc.prototipotrabalho1.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.udesc.prototipotrabalho1.domain.model.Interaction
import br.com.udesc.prototipotrabalho1.domain.repository.InteractionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

/**
 * Implementação "falsa" (fake) do InteractionRepository que usa uma lista em memória.
 */
@RequiresApi(Build.VERSION_CODES.O)
class FakeInteractionRepositoryImpl : InteractionRepository {

    // Nossa "tabela" de interações em memória, com alguns dados de exemplo.
    private val interactions = mutableListOf(
        Interaction(
            id = 1,
            familyId = 1, // Associada à Família Silva
            type = "Visita domiciliar",
            date = LocalDate.of(2024, 7, 20),
            summary = "Realizada visita de rotina. Aferida pressão arterial do Sr. José.",
            nextSteps = "Agendar retorno em 1 mês para nova aferição."
        ),
        Interaction(
            id = 2,
            familyId = 1, // Associada à Família Silva
            type = "Contato telefônico",
            date = LocalDate.of(2024, 7, 15),
            summary = "Contato realizado para orientações sobre medicação da Sra. Maria.",
            nextSteps = "Nenhum."
        ),
        Interaction(
            id = 3,
            familyId = 2, // Associada à Família Oliveira
            type = "Visita domiciliar",
            date = LocalDate.of(2025, 10, 8),
            summary = "Realizada visita de rotina.",
            nextSteps = "."
        ),
        Interaction(
            id = 4,
            familyId = 3, // Associada à Família Santos
            type = "Outro",
            date = LocalDate.of(2025, 10, 9),
            summary = "Enviada carta com orientações sobre vacinação",
            nextSteps = "Nenhum."
        )
    )

    /**
     * Adiciona uma nova interação à lista, simulando a geração de um ID automático.
     */
    override suspend fun addInteraction(interaction: Interaction) {
        val newId = (interactions.maxOfOrNull { it.id } ?: 0) + 1
        interactions.add(interaction.copy(id = newId))
    }

    /**
     * Retorna um Flow contendo as interações filtradas pelo ID da família.
     */
    override fun getInteractionsByFamilyId(familyId: Int): Flow<List<Interaction>> {
        val familyInteractions = interactions.filter { it.familyId == familyId }
        return flowOf(familyInteractions)
    }
}