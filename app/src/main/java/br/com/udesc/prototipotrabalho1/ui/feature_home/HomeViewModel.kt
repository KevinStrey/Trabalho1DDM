package br.com.udesc.prototipotrabalho1.ui.feature_home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel para a HomeScreen.
 *
 * Mesmo que a tela inicial seja simples, usar um ViewModel mantém a consistência
 * da arquitetura e prepara o terreno para futuras funcionalidades (ex: carregar um
 * resumo de dados, notícias, etc.).
 */
class HomeViewModel(
    // No futuro, poderíamos injetar UseCases aqui
) : ViewModel() {

    // Por enquanto, não há estado dinâmico ou eventos, mas a estrutura está pronta.
}

/**
 * Fábrica para criar o HomeViewModel.
 */
class HomeViewModelFactory : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}