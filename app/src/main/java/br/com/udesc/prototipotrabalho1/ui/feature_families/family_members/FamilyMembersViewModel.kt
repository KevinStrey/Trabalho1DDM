package br.com.udesc.prototipotrabalho1.ui.feature_families.family_members

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.model.Member
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamilyByIdUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetMembersByFamilyIdUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

/**
 * ViewModel para a FamilyMembersScreen.
 *
 * Responsável por carregar os detalhes de uma família específica e a lista de seus membros.
 */
class FamilyMembersViewModel(
    private val familyId: Int,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase // Adicionado
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamilyMembersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFamilyDetails()
        loadMembers() // Adicionado
    }

    private fun loadFamilyDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val family = getFamilyByIdUseCase(familyId)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    family = family
                )
            }
        }
    }

    // Nova função para carregar os membros
    private fun loadMembers() {
        getMembersByFamilyIdUseCase(familyId)
            .onEach { members ->
                _uiState.update { it.copy(members = members) }
            }
            .launchIn(viewModelScope)
    }
}

/**
 * Representa o estado visual da FamilyMembersScreen.
 * Agora inclui a lista de membros.
 */
data class FamilyMembersUiState(
    val isLoading: Boolean = true,
    val family: Family? = null,
    val members: List<Member> = emptyList() // Adicionado
)

/**
 * Fábrica para criar o FamilyMembersViewModel.
 * Atualizada para receber o novo UseCase.
 */
class FamilyMembersViewModelFactory(
    private val familyId: Int,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase // Adicionado
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FamilyMembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FamilyMembersViewModel(familyId, getFamilyByIdUseCase, getMembersByFamilyIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}