package br.com.udesc.prototipotrabalho1.domain.model

import androidx.annotation.DrawableRes

/**
 * Modelo de negócio puro, representando a entidade Família.
 * Esta classe não deve ter dependências com camadas externas (como a camada de dados ou UI).
 * É a "fonte da verdade" para a estrutura de dados da família.
 */
data class Family(
    val id: Int,
    val name: String,
    val address: String,
    @DrawableRes val avatarResId: Int
)