package br.com.udesc.prototipotrabalho1.domain.repository

import br.com.udesc.prototipotrabalho1.domain.model.Visit
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Interface (Contrato) que define as operações de dados para a entidade Visita.
 */
interface VisitRepository {

    /**
     * Retorna um Flow contendo a lista de todas as visitas para uma data específica.
     */
    fun getVisitsByDate(date: LocalDate): Flow<List<Visit>>

    /**
     * Busca e retorna uma única visita pelo seu ID.
     */
    suspend fun getVisitById(id: Int): Visit?
}