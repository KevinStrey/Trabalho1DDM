package br.com.udesc.prototipotrabalho1.data

import br.com.udesc.prototipotrabalho1.data.repository.FakeFamilyRepositoryImpl
import br.com.udesc.prototipotrabalho1.domain.repository.FamilyRepository
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamiliesUseCase
import br.com.udesc.prototipotrabalho1.domain.usecase.GetFamilyByIdUseCase

/**
 * Interface para o contêiner de dependências.
 * Facilita a substituição em testes.
 */
interface AppContainer {
    val familyRepository: FamilyRepository
    val getFamiliesUseCase: GetFamiliesUseCase // Adicionado
    val getFamilyByIdUseCase: GetFamilyByIdUseCase // Adicionado
}

/**
 * Implementação do contêiner que cria e gerencia as instâncias das dependências
 * para a aplicação em produção.
 */
class DefaultAppContainer : AppContainer {

    // A propriedade `lazy` garante que o repositório será criado apenas uma vez,
    // na primeira vez que for acessado (funcionando como um singleton).
    override val familyRepository: FamilyRepository by lazy {
        FakeFamilyRepositoryImpl()
    }

    // O AppContainer agora também sabe como criar nossos UseCases.
    // Ele passa a instância do repositório para o construtor do UseCase.
    override val getFamiliesUseCase: GetFamiliesUseCase by lazy {
        GetFamiliesUseCase(familyRepository)
    }

    override val getFamilyByIdUseCase: GetFamilyByIdUseCase by lazy {
        GetFamilyByIdUseCase(familyRepository)
    }
}