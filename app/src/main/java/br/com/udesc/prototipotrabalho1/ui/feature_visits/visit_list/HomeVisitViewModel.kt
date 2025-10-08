package br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import br.com.udesc.prototipotrabalho1.domain.model.Visit
import br.com.udesc.prototipotrabalho1.domain.usecase.GetVisitsByDateUseCase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import java.time.LocalDate
import java.time.YearMonth

@OptIn(ExperimentalCoroutinesApi::class)
@RequiresApi(Build.VERSION_CODES.O)
class HomeVisitsViewModel(
    getVisitsByDateUseCase: GetVisitsByDateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeVisitsUiState())
    val uiState = _uiState.asStateFlow()

    // Este StateFlow reage a mudanças na data selecionada e busca as visitas correspondentes.
    val visitsForSelectedDay: StateFlow<List<Visit>> = _uiState
        .flatMapLatest { state ->
            getVisitsByDateUseCase(state.selectedDate)
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun onEvent(event: HomeVisitsEvent) {
        when (event) {
            is HomeVisitsEvent.OnDateSelected -> {
                _uiState.update { it.copy(selectedDate = event.date) }
            }
            HomeVisitsEvent.OnNextMonthClicked -> {
                _uiState.update { it.copy(currentMonth = it.currentMonth.plusMonths(1)) }
            }
            HomeVisitsEvent.OnPreviousMonthClicked -> {
                _uiState.update { it.copy(currentMonth = it.currentMonth.minusMonths(1)) }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
data class HomeVisitsUiState(
    val currentMonth: YearMonth = YearMonth.now(),
    val selectedDate: LocalDate = LocalDate.now()
)

sealed interface HomeVisitsEvent {
    data class OnDateSelected(val date: LocalDate) : HomeVisitsEvent
    object OnNextMonthClicked : HomeVisitsEvent
    object OnPreviousMonthClicked : HomeVisitsEvent
}

@RequiresApi(Build.VERSION_CODES.O)
class HomeVisitsViewModelFactory(
    private val getVisitsByDateUseCase: GetVisitsByDateUseCase
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeVisitsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeVisitsViewModel(getVisitsByDateUseCase) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}