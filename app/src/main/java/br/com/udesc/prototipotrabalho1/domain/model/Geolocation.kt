package br.com.udesc.prototipotrabalho1.domain.model

/**
 * Modelo de dados simples para armazenar coordenadas geográficas.
 *
 * @param latitude A latitude da localização.
 * @param longitude A longitude da localização.
 */
data class Geolocation(
    val latitude: Double?,
    val longitude: Double?
)