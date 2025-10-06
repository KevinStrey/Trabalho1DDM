package br.com.udesc.prototipotrabalho1.data

import androidx.annotation.DrawableRes
import br.com.udesc.prototipotrabalho1.R

// Modelo de dados para Família
data class Family(
    val id: Int,
    val name: String,
    val address: String,
    @DrawableRes val avatarResId: Int
)

// Lista de dados hardcoded que pode ser acessada de qualquer tela
val sampleFamilies = listOf(
    Family(1, "Família Silva", "Rua das Flores, 123", R.drawable.avatar_1),
    Family(2, "Família Oliveira", "Avenida Central, 456", R.drawable.avatar_2),
    Family(3, "Família Santos", "Travessa da Paz, 789", R.drawable.avatar_3),
    Family(4, "Família Pereira", "Rua do Sol, 101", R.drawable.avatar_4),
    Family(5, "Família Costa", "Avenida da Lua, 202", R.drawable.avatar_5)
)