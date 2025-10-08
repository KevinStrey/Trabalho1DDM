package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.repository.MemberRepository

/**
 * Caso de Uso para obter a lista de membros de uma família específica.
 */
class GetMembersByFamilyIdUseCase(
    private val memberRepository: MemberRepository
) {
    /**
     * Executa o caso de uso.
     * @param familyId O ID da família da qual se deseja obter os membros.
     * @return um Flow contendo a lista de membros.
     */
    operator fun invoke(familyId: Int) = memberRepository.getMembersByFamilyId(familyId)
}