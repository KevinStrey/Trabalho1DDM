package br.com.udesc.prototipotrabalho1.ui.feature_families.family_list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamiliesUseCase
import kotlinx.coroutines.flow.*

/**
 * ViewModel para a FamiliesScreen.
 *
 * Responsável por manter o estado da UI, processar eventos do usuário
 * e interagir com a camada de domínio (UseCases).
 */
class FamiliesViewModel(
    private val getFamiliesUseCase: GetFamiliesUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(FamiliesUiState())
    val uiState: StateFlow<FamiliesUiState> = _uiState.asStateFlow()

    private var allFamilies: List<Family> = emptyList()

    init {
        loadFamilies()
    }

    fun onEvent(event: FamiliesEvent) {
        when (event) {
            is FamiliesEvent.SearchQueryChanged -> {
                _uiState.update { it.copy(searchQuery = event.query) }
                filterFamilies(event.query)
            }
        }
    }

    private fun loadFamilies() {
        getFamiliesUseCase()
            .onStart { _uiState.update { it.copy(isLoading = true) } }
            .onEach { families ->
                allFamilies = families
                _uiState.update {
                    it.copy(
                        families = families,
                        isLoading = false
                    )
                }
            }
            .launchIn(viewModelScope)
    }

    private fun filterFamilies(query: String) {
        val filteredList = if (query.isBlank()) {
            allFamilies
        } else {
            allFamilies.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.address.contains(query, ignoreCase = true)
            }
        }
        _uiState.update { it.copy(families = filteredList) }
    }
}

/**
 * Representa todo o estado visual da FamiliesScreen.
 */
data class FamiliesUiState(
    val families: List<Family> = emptyList(),
    val searchQuery: String = "",
    val isLoading: Boolean = false
)

/**
 * Representa as ações que o usuário pode realizar na tela.
 */
sealed interface FamiliesEvent {
    data class SearchQueryChanged(val query: String) : FamiliesEvent
}

/**
 * Fábrica (Factory) para criar o FamiliesViewModel.
 *
 * Necessária para a injeção de dependência manual, pois ensina o sistema a criar
 * um ViewModel que tem dependências (neste caso, o GetFamiliesUseCase).
 */
class FamiliesViewModelFactory(
    private val getFamiliesUseCase: GetFamiliesUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FamiliesViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FamiliesViewModel(getFamiliesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}