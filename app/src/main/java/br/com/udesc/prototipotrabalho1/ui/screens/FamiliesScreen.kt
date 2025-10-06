package br.com.udesc.prototipotrabalho1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.NavRoute
import br.com.udesc.prototipotrabalho1.R
import br.com.udesc.prototipotrabalho1.data.Family

// Lista de dado hardcoded
val sampleFamilies = listOf(
    Family(1, "Família Silva", "Rua das Flores, 123", R.drawable.avatar_1),
    Family(2, "Família Oliveira", "Avenida Central, 456", R.drawable.avatar_2),
    Family(3, "Família Santos", "Travessa da Paz, 789", R.drawable.avatar_3),
    Family(4, "Família Pereira", "Rua do Sol, 101", R.drawable.avatar_4),
    Family(5, "Família Costa", "Avenida da Lua, 202", R.drawable.avatar_5)
)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FamiliesScreen(
    navController: NavController,
    onAddFamilyClick: () -> Unit
) {
    var searchQuery by remember { mutableStateOf("") }

    Column(modifier = Modifier.fillMaxSize()) {
        // TopAppBar específico da tela
        TopAppBar(
            title = { Text("Famílias") },
            actions = {
                IconButton(onClick = onAddFamilyClick) {
                    Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Família")
                }
            }
        )

        // Conteúdo da tela
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            // Barra de Pesquisa
            OutlinedTextField(
                value = searchQuery,
                onValueChange = { searchQuery = it },
                label = { Text("Pesquisar famílias") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(24.dp)
            )

            // Botões de Filtro
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                FilterChip(
                    selected = true,
                    onClick = { /* TODO */ },
                    label = { Text("Todas") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                )
                FilterChip(
                    selected = false,
                    onClick = { /* TODO */ },
                    label = { Text("Recentes") },
                    trailingIcon = { Icon(Icons.Default.ArrowDropDown, contentDescription = null) }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Famílias
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(sampleFamilies) { family ->
                    FamilyListItem(family = family){ selectedFamily ->
                        navController.navigate(NavRoute.FamilyMembers.createRoute(selectedFamily.id))
                    }
                }
            }
        }
    }
}

@Composable
fun FamilyListItem(
    family: Family,
    onClick: (Family) -> Unit // callback para navegação
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick(family) } // torna clicável
            .padding(8.dp), // melhora o espaçamento para o clique
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = family.avatarResId),
            contentDescription = "Avatar da ${family.name}",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape) // Deixa a imagem redonda
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = family.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = family.address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}



/*
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun FamiliesScreenPreview() {
    PrototipoTrabalho1Theme {
        // Para o preview funcionar, passamos uma função vazia
        FamiliesScreen(onAddFamilyClick = {})
    }
}*/
