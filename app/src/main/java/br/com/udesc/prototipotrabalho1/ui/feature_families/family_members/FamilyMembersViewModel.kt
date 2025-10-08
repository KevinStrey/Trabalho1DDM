package br.com.udesc.prototipotrabalho1.ui.feature_families.family_members

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.model.Interaction
import br.com.udesc.prototipotrabalho1.domain.model.Member
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamilyByIdUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetInteractionsByFamilyIdUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetMembersByFamilyIdUseCase
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class FamilyMembersViewModel(
    private val familyId: Int,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase,
    private val getInteractionsByFamilyIdUseCase: GetInteractionsByFamilyIdUseCase // Adicionado
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamilyMembersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFamilyDetails()
        loadMembers()
        loadInteractions() // Adicionado
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

    private fun loadMembers() {
        getMembersByFamilyIdUseCase(familyId)
            .onEach { members ->
                _uiState.update { it.copy(members = members) }
            }
            .launchIn(viewModelScope)
    }

    // Nova função para carregar as interações
    private fun loadInteractions() {
        getInteractionsByFamilyIdUseCase(familyId)
            .onEach { interactions ->
                _uiState.update { it.copy(interactions = interactions) }
            }
            .launchIn(viewModelScope)
    }
}

data class FamilyMembersUiState(
    val isLoading: Boolean = true,
    val family: Family? = null,
    val members: List<Member> = emptyList(),
    val interactions: List<Interaction> = emptyList() // Adicionado
)

@RequiresApi(Build.VERSION_CODES.O)
class FamilyMembersViewModelFactory(
    private val familyId: Int,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase,
    private val getMembersByFamilyIdUseCase: GetMembersByFamilyIdUseCase,
    private val getInteractionsByFamilyIdUseCase: GetInteractionsByFamilyIdUseCase // Adicionado
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FamilyMembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FamilyMembersViewModel(familyId, getFamilyByIdUseCase, getMembersByFamilyIdUseCase, getInteractionsByFamilyIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}