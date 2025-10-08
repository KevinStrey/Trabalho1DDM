package br.com.udesc.prototipotrabalho1.domain.model

/**
 * Modelo de negócio puro, representando a entidade Membro.
 *
 * @param id Identificador único do membro.
 * @param familyId Identificador da família à qual o membro pertence.
 * @param name Nome completo do membro.
 * @param birthDate Data de nascimento (armazenada como String para simplicidade no formulário).
 * @param kinship Relação de parentesco com o chefe da família.
 * @param healthInfo Informações de saúde relevantes.
 * @param photoUri O caminho (URI) para a foto do membro, armazenado como String.
 * Pode ser nulo se nenhuma foto for adicionada.
 */
data class Member(
    val id: Int,
    val familyId: Int,
    val name: String,
    val birthDate: String,
    val kinship: String,
    val healthInfo: String,
    val photoUri: String? = null
)