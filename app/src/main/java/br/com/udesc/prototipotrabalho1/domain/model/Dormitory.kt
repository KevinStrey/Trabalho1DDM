package br.com.udesc.prototipotrabalho1.domain.model

/**
 * Modelo de negócio puro, representando a entidade Domicílio (Dormitory).
 *
 * @param id Identificador único do domicílio.
 * @param familyId Identificador da família à qual o domicílio pertence.
 * @param address Endereço completo em formato de texto.
 * @param geolocation Objeto contendo as coordenadas geográficas do domicílio.
 */
data class Dormitory(
    val id: Int,
    val familyId: Int,
    val address: String,
    val geolocation: Geolocation? = null // Pode ser nulo inicialmente
)