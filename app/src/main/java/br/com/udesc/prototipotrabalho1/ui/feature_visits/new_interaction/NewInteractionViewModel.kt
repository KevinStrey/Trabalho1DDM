package br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewInteractionViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewInteractionUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: NewInteractionEvent) {
        when (event) {
            is NewInteractionEvent.OnInteractionTypeChanged -> {
                _uiState.update { it.copy(interactionType = event.type) }
            }
            is NewInteractionEvent.OnDateChanged -> {
                // Limita o input a 8 caracteres numéricos
                if (event.date.length <= 8) {
                    _uiState.update { it.copy(interactionDate = event.date.filter { char -> char.isDigit() }) }
                }
            }
            is NewInteractionEvent.OnSummaryChanged -> {
                _uiState.update { it.copy(interactionSummary = event.summary) }
            }
            is NewInteractionEvent.OnNextStepsChanged -> {
                _uiState.update { it.copy(nextSteps = event.steps) }
            }
        }
    }
}

data class NewInteractionUiState(
    val interactionType: String = "",
    val interactionDate: String = "",
    val interactionSummary: String = "",
    val nextSteps: String = ""
)

sealed interface NewInteractionEvent {
    data class OnInteractionTypeChanged(val type: String) : NewInteractionEvent
    data class OnDateChanged(val date: String) : NewInteractionEvent
    data class OnSummaryChanged(val summary: String) : NewInteractionEvent
    data class OnNextStepsChanged(val steps: String) : NewInteractionEvent
}

class NewInteractionViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewInteractionViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewInteractionViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}