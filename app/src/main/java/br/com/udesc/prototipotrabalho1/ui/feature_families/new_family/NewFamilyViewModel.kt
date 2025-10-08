package br.com.udesc.prototipotrabalho1.ui.feature_families.new_family

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamiliesUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

/**
 * ViewModel para a NewFamilyScreen.
 *
 * Responsável por gerenciar o estado do formulário de criação de uma nova família.
 */
class NewFamilyViewModel(
    // No futuro, poderíamos injetar um 'SaveFamilyUseCase' aqui
) : ViewModel() {

    private val _uiState = MutableStateFlow(NewFamilyUiState())
    val uiState = _uiState.asStateFlow()

    fun onEvent(event: NewFamilyEvent) {
        when (event) {
            is NewFamilyEvent.FamilyNameChanged -> {
                _uiState.update { it.copy(familyName = event.name) }
            }

            is NewFamilyEvent.AddressChanged -> {
                _uiState.update { it.copy(address = event.address) }
            }

            is NewFamilyEvent.MemberCountChanged -> {
                _uiState.update { it.copy(memberCount = event.count) }
            }

            is NewFamilyEvent.ContactChanged -> {
                _uiState.update { it.copy(contact = event.contact) }
            }
        }
    }
}

/**
 * Representa o estado dos campos do formulário na NewFamilyScreen.
 */
data class NewFamilyUiState(
    val familyName: String = "",
    val address: String = "",
    val memberCount: String = "",
    val contact: String = ""
)

/**
 * Representa as ações que podem ser originadas da UI.
 */
sealed interface NewFamilyEvent {
    data class FamilyNameChanged(val name: String) : NewFamilyEvent
    data class AddressChanged(val address: String) : NewFamilyEvent
    data class MemberCountChanged(val count: String) : NewFamilyEvent
    data class ContactChanged(val contact: String) : NewFamilyEvent
}

/**
 * Fábrica para criar o NewFamilyViewModel.
 * Como este ViewModel não tem dependências por enquanto, a fábrica é bem simples.
 */
class NewFamilyViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewFamilyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return NewFamilyViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}