package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository

/**
 * Caso de Uso para obter a lista de todas as famílias.
 *
 * Esta classe tem uma única responsabilidade: buscar a lista de famílias.
 * Ela depende da abstração FamilyRepository, não da implementação concreta.
 * Sua instância será criada manualmente pelo nosso AppContainer.
 */
class GetFamiliesUseCase(
    private val familyRepository: FamilyRepository
) {
    /**
     * O operador 'invoke' permite chamar a classe como se fosse uma função.
     * Ex: getFamiliesUseCase()
     */
    operator fun invoke() = familyRepository.getFamilies()
}