package br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Interaction
import br.com.udesc.prototipotrabalho1.domain.usecase.AddInteractionUseCase
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException

@RequiresApi(Build.VERSION_CODES.O)
class NewInteractionViewModel(
    private val familyId: Int,
    private val addInteractionUseCase: AddInteractionUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewInteractionUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEvent = MutableSharedFlow<UiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: NewInteractionEvent) {
        when (event) {
            is NewInteractionEvent.OnInteractionTypeChanged -> _uiState.update { it.copy(interactionType = event.type) }
            is NewInteractionEvent.OnDateChanged -> {
                _uiState.update { it.copy(interactionDate = event.date) }
            }
            is NewInteractionEvent.OnSummaryChanged -> _uiState.update { it.copy(interactionSummary = event.summary) }
            is NewInteractionEvent.OnNextStepsChanged -> _uiState.update { it.copy(nextSteps = event.steps) }
            NewInteractionEvent.OnSaveClicked -> saveInteraction()
        }
    }

    private fun saveInteraction() {
        viewModelScope.launch {
            try {
                // Validação da data
                val date = try {
                    val formatter = DateTimeFormatter.ofPattern("ddMMyyyy")
                    LocalDate.parse(uiState.value.interactionDate, formatter)
                } catch (e: DateTimeParseException) {
                    _uiEvent.emit(UiEvent.ShowSnackbar("Data inválida. Use o formato DDMMAAAA."))
                    return@launch
                }

                addInteractionUseCase(
                    Interaction(
                        id = 0, // ID será gerado pelo repositório
                        familyId = familyId,
                        type = uiState.value.interactionType,
                        date = date,
                        summary = uiState.value.interactionSummary,
                        nextSteps = uiState.value.nextSteps
                    )
                )
                _uiEvent.emit(UiEvent.SaveSuccess)
            } catch (e: IllegalArgumentException) {
                _uiEvent.emit(UiEvent.ShowSnackbar(e.message ?: "Erro desconhecido"))
            }
        }
    }
}

data class NewInteractionUiState(
    val interactionType: String = "",
    val interactionDate: String = "", // Armazenado como string DDMMAAAA
    val interactionSummary: String = "",
    val nextSteps: String = ""
)

sealed interface NewInteractionEvent {
    data class OnInteractionTypeChanged(val type: String) : NewInteractionEvent
    data class OnDateChanged(val date: String) : NewInteractionEvent
    data class OnSummaryChanged(val summary: String) : NewInteractionEvent
    data class OnNextStepsChanged(val steps: String) : NewInteractionEvent
    object OnSaveClicked : NewInteractionEvent
}

sealed interface UiEvent {
    data class ShowSnackbar(val message: String) : UiEvent
    object SaveSuccess : UiEvent
}


@RequiresApi(Build.VERSION_CODES.O)
class NewInteractionViewModelFactory(
    private val familyId: Int,
    private val addInteractionUseCase: AddInteractionUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewInteractionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewInteractionViewModel(familyId, addInteractionUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}