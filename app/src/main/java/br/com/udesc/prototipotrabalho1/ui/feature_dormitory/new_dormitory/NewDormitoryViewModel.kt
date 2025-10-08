package br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Dormitory
import br.com.udesc.prototipotrabalho1.domain.model.Family
import br.com.udesc.prototipotrabalho1.domain.usecase.AddDormitoryUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamiliesUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewDormitoryViewModel(
    private val addDormitoryUseCase: AddDormitoryUseCase,
    private val getFamiliesUseCase: GetFamiliesUseCase // Adicionado
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewDormitoryUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    init {
        loadFamilies()
    }

    private fun loadFamilies() {
        getFamiliesUseCase().onEach { families ->
            _uiState.update { it.copy(allFamilies = families) }
        }.launchIn(viewModelScope)
    }

    fun onEvent(event: NewDormitoryEvent) {
        when (event) {
            is NewDormitoryEvent.OnAddressChanged -> _uiState.update { it.copy(address = event.address) }
            is NewDormitoryEvent.OnFamilySelected -> {
                _uiState.update { it.copy(selectedFamily = event.family, isFamilyDropdownExpanded = false) }
            }
            is NewDormitoryEvent.OnFamilyDropdownClicked -> {
                _uiState.update { it.copy(isFamilyDropdownExpanded = true) }
            }
            is NewDormitoryEvent.OnFamilyDropdownDismiss -> {
                _uiState.update { it.copy(isFamilyDropdownExpanded = false) }
            }
            is NewDormitoryEvent.OnSaveClicked -> saveDormitory()
        }
    }

    private fun saveDormitory() {
        viewModelScope.launch {
            val selectedFamilyId = uiState.value.selectedFamily?.id
            if (selectedFamilyId == null) {
                _uiEvent.emit(UiEvent.ShowSnackbar("Por favor, selecione uma família."))
                return@launch
            }

            try {
                addDormitoryUseCase(
                    Dormitory(
                        id = 0,
                        familyId = selectedFamilyId,
                        address = uiState.value.address,
                        geolocation = null
                    )
                )
                _uiEvent.emit(UiEvent.SaveSuccess)
            } catch (e: IllegalArgumentException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Erro ao salvar."))
            }
        }
    }
}

data class NewDormitoryUiState(
    val allFamilies: List<Family> = emptyList(),
    val selectedFamily: Family? = null,
    val isFamilyDropdownExpanded: Boolean = false,
    val address: String = ""
)

sealed interface NewDormitoryEvent {
    data class OnAddressChanged(val address: String) : NewDormitoryEvent
    data class OnFamilySelected(val family: Family) : NewDormitoryEvent
    object OnFamilyDropdownClicked : NewDormitoryEvent
    object OnFamilyDropdownDismiss : NewDormitoryEvent
    object OnSaveClicked : NewDormitoryEvent
}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    object SaveSuccess : UiEvent
}

class NewDormitoryViewModelFactory(
    private val addDormitoryUseCase: AddDormitoryUseCase,
    private val getFamiliesUseCase: GetFamiliesUseCase // Adicionado
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewDormitoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewDormitoryViewModel(addDormitoryUseCase, getFamiliesUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}