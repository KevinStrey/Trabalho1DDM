package br.com.udesc.prototipotrabalho1.domain.usecase

import br.com.udesc.prototipotrabalho1.domain.model.Member
import br.com.udesc.prototipotrabalho1.domain.repository.MemberRepository

/**
 * Caso de Uso para adicionar um novo membro.
 *
 * Contém a lógica de negócio específica para esta ação.
 */
class AddMemberUseCase(
    private val memberRepository: MemberRepository
) {
    /**
     * Executa o caso de uso.
     * @param member O novo membro a ser adicionado.
     * @throws IllegalArgumentException se os dados do membro forem inválidos.
     */
    suspend operator fun invoke(member: Member) {
        // Exemplo de regra de negócio: não permitir nome em branco.
        if (member.name.isBlank()) {
            throw IllegalArgumentException("O nome do membro não pode estar em branco.")
        }

        // Se a validação passar, chama o repositório para salvar os dados.
        memberRepository.addMember(member)
    }
}