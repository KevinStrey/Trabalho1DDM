package br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Visit
import br.com.udesc.prototipotrabalho1.domain.usecase.GetVisitByIdUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class VisitReportViewModel(
    private val visitId: Int,
    private val getVisitByIdUseCase: GetVisitByIdUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(VisitReportUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadVisitDetails()
    }

    private fun loadVisitDetails() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true) }
            val visit = getVisitByIdUseCase(visitId)
            _uiState.update {
                it.copy(
                    isLoading = false,
                    visit = visit
                )
            }
        }
    }

    fun onEvent(event: VisitReportEvent) {
        when(event) {
            is VisitReportEvent.OnObservationsChanged -> _uiState.update { it.copy(observations = event.text) }
            is VisitReportEvent.OnFollowUpChanged -> _uiState.update { it.copy(followUp = event.text) }
            is VisitReportEvent.OnHealthAdviceToggled -> _uiState.update { it.copy(healthAdviceChecked = event.isChecked) }
            is VisitReportEvent.OnVaccinationToggled -> _uiState.update { it.copy(vaccinationChecked = event.isChecked) }
            is VisitReportEvent.OnMedicineDistributionToggled -> _uiState.update { it.copy(medicineDistributionChecked = event.isChecked) }
        }
    }
}

data class VisitReportUiState(
    val isLoading: Boolean = true,
    val visit: Visit? = null,
    val observations: String = "",
    val followUp: String = "",
    val healthAdviceChecked: Boolean = true,
    val vaccinationChecked: Boolean = false,
    val medicineDistributionChecked: Boolean = false
)

sealed interface VisitReportEvent {
    data class OnObservationsChanged(val text: String) : VisitReportEvent
    data class OnFollowUpChanged(val text: String) : VisitReportEvent
    data class OnHealthAdviceToggled(val isChecked: Boolean) : VisitReportEvent
    data class OnVaccinationToggled(val isChecked: Boolean) : VisitReportEvent
    data class OnMedicineDistributionToggled(val isChecked: Boolean) : VisitReportEvent
}


@RequiresApi(Build.VERSION_CODES.O)
class VisitReportViewModelFactory(
    private val visitId: Int,
    private val getVisitByIdUseCase: GetVisitByIdUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VisitReportViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return VisitReportViewModel(visitId, getVisitByIdUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}