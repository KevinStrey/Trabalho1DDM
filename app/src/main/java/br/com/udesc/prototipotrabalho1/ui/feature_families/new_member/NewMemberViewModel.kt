package br.com.udesc.prototipotrabalho1.ui.feature_families.new_member

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewMemberViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewMemberUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: NewMemberEvent) {
        when (event) {
            is NewMemberEvent.OnFullNameChanged -> _uiState.update { it.copy(fullName = event.name) }
            is NewMemberEvent.OnBirthDateChanged -> _uiState.update { it.copy(birthDate = event.date) }
            is NewMemberEvent.OnKinshipChanged -> _uiState.update { it.copy(kinship = event.kinship) }
            is NewMemberEvent.OnHealthInfoChanged -> _uiState.update { it.copy(healthInfo = event.info) }
        }
    }
}

data class NewMemberUiState(
    val fullName: String = "",
    val birthDate: String = "",
    val kinship: String = "",
    val healthInfo: String = ""
)

sealed interface NewMemberEvent {
    data class OnFullNameChanged(val name: String) : NewMemberEvent
    data class OnBirthDateChanged(val date: String) : NewMemberEvent
    data class OnKinshipChanged(val kinship: String) : NewMemberEvent
    data class OnHealthInfoChanged(val info: String) : NewMemberEvent
}

class NewMemberViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewMemberViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewMemberViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}