package br.com.udesc.prototipotrabalho1.data

import android.os.Build
import br.com.udesc.prototipotrabalho1.data.repository.FakeFamilyRepositoryImpl
import br.com.udesc.prototipotrabalho1.data.repository.FakeVisitRepositoryImpl
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository
import br.com.udesc.prototipotrabalho1.domain.repository.VisitRepository
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamiliesUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamilyByIdUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetVisitByIdUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetVisitsByDateUseCase

/**
 * Interface para o contêiner de dependências.
 * Foi atualizada para incluir as dependências de Visita.
 */
interface AppContainer {
    val familyRepository: FamilyRepository
    val getFamiliesUseCase: GetFamiliesUseCase
    val getFamilyByIdUseCase: GetFamilyByIdUseCase

    // Dependências de Visita
    val visitRepository: VisitRepository
    val getVisitsByDateUseCase: GetVisitsByDateUseCase
    val getVisitByIdUseCase: GetVisitByIdUseCase
}

/**
 * Implementação do contêiner que cria e gerencia as instâncias das dependências.
 */
class DefaultAppContainer : AppContainer {

    // --- Dependências de Família ---
    override val familyRepository: FamilyRepository by lazy {
        FakeFamilyRepositoryImpl()
    }

    override val getFamiliesUseCase: GetFamiliesUseCase by lazy {
        GetFamiliesUseCase(familyRepository)
    }

    override val getFamilyByIdUseCase: GetFamilyByIdUseCase by lazy {
        GetFamilyByIdUseCase(familyRepository)
    }

    // --- Dependências de Visita ---
    override val visitRepository: VisitRepository by lazy {
        // Adicionamos uma verificação de segurança, pois FakeVisitRepositoryImpl
        // usa APIs a partir do Android Oreo (API 26).
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FakeVisitRepositoryImpl()
        } else {
            // Em um app real, poderíamos fornecer uma implementação alternativa
            // para versões mais antigas do Android ou simplesmente lançar um erro.
            throw IllegalStateException("VisitRepository requires API level 26 or higher.")
        }
    }

    override val getVisitsByDateUseCase: GetVisitsByDateUseCase by lazy {
        GetVisitsByDateUseCase(visitRepository)
    }

    override val getVisitByIdUseCase: GetVisitByIdUseCase by lazy {
        GetVisitByIdUseCase(visitRepository)
    }
}