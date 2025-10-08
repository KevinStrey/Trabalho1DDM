package br.com.udesc.prototipotrabalho1.data

import android.os.Build
import androidx.annotation.RequiresApi
import br.com.udesc.prototipotrabalho1.data.repository.FakeFamilyRepositoryImpl
import br.com.udesc.prototipotrabalho1.data.repository.FakeInteractionRepositoryImpl
import br.com.udesc.prototipotrabalho1.data.repository.FakeMemberRepositoryImpl
import br.com.udesc.prototipotrabalho1.data.repository.FakeVisitRepositoryImpl
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository
import br.com.udesc.prototipotrabalho1.domain.repository.InteractionRepository
import br.com.udesc.prototipotrabalho1.domain.repository.MemberRepository
import br.com.udesc.prototipotrabalho1.domain.repository.VisitRepository
import br.com.udesc.prototipotrabalho1.domain.usecase.*

/**
 * Interface para o contêiner de dependências.
 * Foi atualizada para incluir as dependências de Interação.
 */
interface AppContainer {
    // Família
    val familyRepository: FamilyRepository
    val getFamiliesUseCase: GetFamiliesUseCase
    val getFamilyByIdUseCase: GetFamilyByIdUseCase

    // Visita
    val visitRepository: VisitRepository
    val getVisitsByDateUseCase: GetVisitsByDateUseCase
    val getVisitByIdUseCase: GetVisitByIdUseCase

    // Membro
    val memberRepository: MemberRepository
    val addMemberUseCase: AddMemberUseCase
    val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase

    // Interação (Adicionado)
    val interactionRepository: InteractionRepository
    val addInteractionUseCase: AddInteractionUseCase
    val getInteractionsByFamilyIdUseCase: GetInteractionsByFamilyIdUseCase
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
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FakeVisitRepositoryImpl()
        } else {
            throw IllegalStateException("VisitRepository requires API level 26 or higher.")
        }
    }
    override val getVisitsByDateUseCase: GetVisitsByDateUseCase by lazy {
        GetVisitsByDateUseCase(visitRepository)
    }
    override val getVisitByIdUseCase: GetVisitByIdUseCase by lazy {
        GetVisitByIdUseCase(visitRepository)
    }

    // --- Dependências de Membro ---
    override val memberRepository: MemberRepository by lazy {
        FakeMemberRepositoryImpl()
    }
    override val addMemberUseCase: AddMemberUseCase by lazy {
        AddMemberUseCase(memberRepository)
    }
    override val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase by lazy {
        GetMembersByFamilyIdUseCase(memberRepository)
    }

    // --- Dependências de Interação (Adicionado) ---
    override val interactionRepository: InteractionRepository by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            FakeInteractionRepositoryImpl()
        } else {
            throw IllegalStateException("InteractionRepository requires API level 26 or higher.")
        }
    }
    override val addInteractionUseCase: AddInteractionUseCase by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            AddInteractionUseCase(interactionRepository)
        } else {
            throw IllegalStateException("AddInteractionUseCase requires API level 26 or higher.")
        }
    }
    override val getInteractionsByFamilyIdUseCase: GetInteractionsByFamilyIdUseCase by lazy {
        GetInteractionsByFamilyIdUseCase(interactionRepository)
    }
}