package br.com.udesc.prototipotrabalho1.data.repository

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.udesc.prototipotrabalho1.domain.model.Visit
import br.com.udesc.prototipotrabalho1.domain.repository.VisitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import java.time.LocalDate

/**
 * Implementação "falsa" (fake) do VisitRepository que usa dados mocados em memória.
 * A anotação `@RequiresApi` é necessária porque estamos usando `LocalDate`.
 */
@RequiresApi(Build.VERSION_CODES.O)
class FakeVisitRepositoryImpl : VisitRepository {

    // Lista de dados mocados para as visitas
    private val sampleVisits = listOf(
        Visit(
            id = 1,
            familyId = 1,
            familyName = "Família Silva",
            address = "Rua das Flores, 123",
            date = LocalDate.of(2025, 10, 8)
        ),
        Visit(
            id = 2,
            familyId = 2,
            familyName = "Família Oliveira",
            address = "Avenida Central, 456",
            date = LocalDate.of(2025, 10, 8)
        ),
        Visit(
            id = 3,
            familyId = 3,
            familyName = "Família Santos",
            address = "Travessa da Paz, 789",
            date = LocalDate.of(2025, 10, 9)
        )
    )

    /**
     * Retorna uma lista de visitas filtrada pela data.
     */
    override fun getVisitsByDate(date: LocalDate): Flow<List<Visit>> {
        val visitsForDate = sampleVisits.filter { it.date == date }
        return flowOf(visitsForDate)
    }

    /**
     * Busca uma visita específica pelo seu ID.
     */
    override suspend fun getVisitById(id: Int): Visit? {
        return sampleVisits.find { it.id == id }
    }
}