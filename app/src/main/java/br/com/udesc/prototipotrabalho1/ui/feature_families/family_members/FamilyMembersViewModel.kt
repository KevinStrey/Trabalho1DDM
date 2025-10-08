package br.com.udesc.prototipotrabalho1.ui.feature_families.family_members

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamilyByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * ViewModel para a FamilyMembersScreen.
 *
 * Responsável por carregar os detalhes de uma família específica.
 */
class FamilyMembersViewModel(
    private val familyId: Int, // Recebe o ID da família a ser carregada
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamilyMembersUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadFamilyDetails()
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
}

/**
 * Representa o estado visual da FamilyMembersScreen.
 */
data class FamilyMembersUiState(
    val isLoading: Boolean = true,
    val family: Family? = null
)

/**
 * Fábrica para criar o FamilyMembersViewModel.
 *
 * É mais complexa porque, além do UseCase, ela precisa passar o 'familyId' para o ViewModel.
 */
class FamilyMembersViewModelFactory(
    private val familyId: Int,
    private val getFamilyByIdUseCase: GetFamilyByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FamilyMembersViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FamilyMembersViewModel(familyId, getFamilyByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}