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
    private val familyId: Int, // Para saber a qual família o membro pertence
    private val addMemberUseCase: AddMemberUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewMemberUiState())
    val uiState = _uiState.asStateFlow()

    // Canal para emitir eventos únicos, como "salvo com sucesso"
    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()


    fun onEvent(event: NewMemberEvent) {
        when (event) {
            is NewMemberEvent.OnFullNameChanged -> _uiState.update { it.copy(fullName = event.name) }
            is NewMemberEvent.OnBirthDateChanged -> _uiState.update { it.copy(birthDate = event.date) }
            is NewMemberEvent.OnKinshipChanged -> _uiState.update { it.copy(kinship = event.kinship) }
            is NewMemberEvent.OnHealthInfoChanged -> _uiState.update { it.copy(healthInfo = event.info) }
            is NewMemberEvent.OnPhotoSelected -> _uiState.update { it.copy(photoUri = event.uri) }
            is NewMemberEvent.OnAddMemberClicked -> saveMember()
        }
    }

    private fun saveMember() {
        viewModelScope.launch {
            try {
                addMemberUseCase(
                    Member(
                        id = 0, // O ID será gerado pelo repositório
                        familyId = familyId,
                        name = uiState.value.fullName,
                        birthDate = uiState.value.birthDate,
                        kinship = uiState.value.kinship,
                        healthInfo = uiState.value.healthInfo,
                        photoUri = uiState.value.photoUri
                    )
                )
                // Emite um evento de sucesso para a UI
                _uiEvent.emit(UiEvent.SaveSuccess)
            } catch (e: IllegalArgumentException) {
                // Emite um evento de erro se a validação do UseCase falhar
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Erro desconhecido"))
            }
        }
    }
}

// O estado da UI agora inclui a URI da foto
data class NewMemberUiState(
    val fullName: String = "",
    val birthDate: String = "",
    val kinship: String = "",
    val healthInfo: String = "",
    val photoUri: String? = null
)

// Os eventos agora incluem a seleção de foto e o clique no botão de salvar
sealed interface NewMemberEvent {
    data class OnFullNameChanged(val name: String) : NewMemberEvent
    data class OnBirthDateChanged(val date: String) : NewMemberEvent
    data class OnKinshipChanged(val kinship: String) : NewMemberEvent
    data class OnHealthInfoChanged(val info: String) : NewMemberEvent
    data class OnPhotoSelected(val uri: String) : NewMemberEvent
    object OnAddMemberClicked : NewMemberEvent
}

// Eventos que o ViewModel pode enviar para a UI (ex: navegar, mostrar snackbar)
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