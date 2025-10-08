package br.com.udesc.prototipotrabalho1.ui.feature_families.new_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Member
import br.com.udesc.prototipotrabalho1.domain.usecase.AddMemberUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewMemberViewModel(
    private val familyId: Int,
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewMemberUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun onEvent(event: NewMemberEvent) {
        when (event) {
            is NewMemberEvent.OnFullNameChanged -> _uiState.update { it.copy(fullName = event.name) }
            is NewMemberEvent.OnBirthDateChanged -> _uiState.update { it.copy(birthDate = event.date) }
            is NewMemberEvent.OnKinshipChanged -> {
                // Ao selecionar um item, atualiza o valor e fecha o menu
                _uiState.update { it.copy(kinship = event.kinship, isKinshipDropdownExpanded = false) }
            }
            is NewMemberEvent.OnHealthInfoChanged -> _uiState.update { it.copy(healthInfo = event.info) }
            is NewMemberEvent.OnPhotoSelected -> _uiState.update { it.copy(photoUri = event.uri) }
            is NewMemberEvent.OnAddMemberClicked -> saveMember()
            // Eventos para controlar a visibilidade do dropdown
            is NewMemberEvent.OnKinshipDropdownDismiss -> {
                _uiState.update { it.copy(isKinshipDropdownExpanded = false) }
            }
            is NewMemberEvent.OnKinshipDropdownClicked -> {
                _uiState.update { it.copy(isKinshipDropdownExpanded = true) }
            }
        }
    }

    private fun saveMember() {
        viewModelScope.launch {
            try {
                addMemberUseCase(
                    Member(
                        id = 0,
                        familyId = familyId,
                        name = uiState.value.fullName,
                        birthDate = uiState.value.birthDate,
                        kinship = uiState.value.kinship,
                        healthInfo = uiState.value.healthInfo,
                        photoUri = uiState.value.photoUri
                    )
                )
                _uiEvent.emit(UiEvent.SaveSuccess)
            } catch (e: IllegalArgumentException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Erro desconhecido"))
            }
        }
    }
}

data class NewMemberUiState(
    val fullName: String = "",
    val birthDate: String = "",
    val kinship: String = "",
    val healthInfo: String = "",
    val photoUri: String? = null,
    val isKinshipDropdownExpanded: Boolean = false // <-- ADICIONADO: controla o estado do menu
)

sealed interface NewMemberEvent {
    data class OnFullNameChanged(val name: String) : NewMemberEvent
    data class OnBirthDateChanged(val date: String) : NewMemberEvent
    data class OnKinshipChanged(val kinship: String) : NewMemberEvent
    data class OnHealthInfoChanged(val info: String) : NewMemberEvent
    data class OnPhotoSelected(val uri: String) : NewMemberEvent
    object OnAddMemberClicked : NewMemberEvent
    object OnKinshipDropdownClicked : NewMemberEvent
    object OnKinshipDropdownDismiss : NewMemberEvent
}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    object SaveSuccess : UiEvent
}


class NewMemberViewModelFactory(
    private val familyId: Int,
    private val addMemberUseCase: AddMemberUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewMemberViewModel(familyId, addMemberUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}