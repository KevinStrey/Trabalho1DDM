package br.com.udesc.prototipotrabalho1.domain.model

import androidx.annotation.DrawableRes

/**
 * Modelo de negócio puro, representando a entidade Família.
 *
 * O campo 'address' foi removido. A informação de endereço agora é gerenciada
 * pela entidade 'Dormitory', que se vincula a esta família através do 'familyId'.
 */
data class Family(
    val id: Int,
    val name: String,
    // O campo 'address: String' foi removido daqui.
    @DrawableRes val avatarResId: Int
)