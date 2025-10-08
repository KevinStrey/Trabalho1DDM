package br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class NewDormitoryViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(NewDormitoryUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: NewDormitoryEvent) {
        when (event) {
            is NewDormitoryEvent.OnFamilySelected -> _uiState.update { it.copy(selectedFamily = event.family) }
            is NewDormitoryEvent.OnHousingTypeChanged -> _uiState.update { it.copy(housingType = event.type) }
            is NewDormitoryEvent.OnSanitationChanged -> _uiState.update { it.copy(sanitationConditions = event.conditions) }
            is NewDormitoryEvent.OnPeoplePerRoomChanged -> _uiState.update { it.copy(peoplePerRoom = event.count) }
        }
    }
}

data class NewDormitoryUiState(
    val selectedFamily: String = "",
    val housingType: String = "",
    val sanitationConditions: String = "",
    val peoplePerRoom: String = ""
)

sealed interface NewDormitoryEvent {
    data class OnFamilySelected(val family: String) : NewDormitoryEvent
    data class OnHousingTypeChanged(val type: String) : NewDormitoryEvent
    data class OnSanitationChanged(val conditions: String) : NewDormitoryEvent
    data class OnPeoplePerRoomChanged(val count: String) : NewDormitoryEvent
}

class NewDormitoryViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewDormitoryViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewDormitoryViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}