package br.com.udesc.prototipotrabalho1.domain.usecase

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.udesc.prototipotrabalho1.domain.model.Interaction
import br.com.udesc.prototipotrabalho1.domain.repository.InteractionRepository

/**
 * Caso de Uso para adicionar uma nova interação.
 */
@RequiresApi(Build.VERSION_CODES.O)
class AddInteractionUseCase(
    private val interactionRepository: InteractionRepository
) {
    /**
     * Executa o caso de uso.
     * @param interaction A nova interação a ser adicionada.
     * @throws IllegalArgumentException se os dados da interação forem inválidos.
     */
    suspend operator fun invoke(interaction: Interaction) {
        // Exemplo de regra de negócio: o tipo e o resumo não podem estar em branco.
        if (interaction.type.isBlank() || interaction.summary.isBlank()) {
            throw IllegalArgumentException("O tipo e o resumo da interação são obrigatórios.")
        }

        interactionRepository.addInteraction(interaction)
    }
}