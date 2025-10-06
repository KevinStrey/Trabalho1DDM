package br.com.udesc.prototipotrabalho1.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.outlined.People
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.NavRoute
import br.com.udesc.prototipotrabalho1.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navController: NavController
) {
    // Scaffold é o layout principal que nos dá a estrutura com TopBar, BottomBar, etc.
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Início") },
                actions = {
                    IconButton(onClick = { /* TODO: Ação de notificação */ }) {
                        Icon(
                            imageVector = Icons.Default.Notifications,
                            contentDescription = "Notificações"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface
                )
            )
        },
        bottomBar = {
            HomeBottomNavigation()
        }
    ) { innerPadding ->
        // O conteúdo principal da tela fica aqui
        // Usamos uma Column para empilhar os itens verticalmente
        Column(
            modifier = Modifier
                .padding(innerPadding) // Padding essencial para não ficar atrás das barras
                .fillMaxSize()
                .padding(16.dp), // Padding adicional para as bordas do conteúdo
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Imagem do médico
            Image(
                painterResource(id = R.drawable.doctor_illustration),
                "Ilustração de um médico",
                Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(12.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Título da seção "Ações"
            Text(
                text = "Ações",
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.align(Alignment.Start) // Alinha o texto à esquerda
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Linha com os botões de ação
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround // Espaça os botões igualmente
            ) {
                ActionButton(
                    text = "Cadastrar\nFamília",
                    icon = Icons.Default.People,
                    contentDescription = "Cadastrar Família",
                    onClick = {navController.navigate(NavRoute.NewFamily.route)}
                )
                ActionButton(
                    text = "Cadastrar\nDomicílio",
                    icon = Icons.Default.Home,
                    contentDescription = "Cadastrar Domicílio",
                    onClick = {navController.navigate(NavRoute.NewDormitory.route)}
                )
            }
        }
    }
}

@Composable
fun ActionButton(
    text: String,
    icon: ImageVector,
    contentDescription: String,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier.size(width = 150.dp, height = 100.dp),
        onClick = onClick,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        border = BorderStroke(1.dp, Color.LightGray)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(imageVector = icon, contentDescription = contentDescription)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = text, textAlign = TextAlign.Center)
        }
    }
}

@Composable
fun HomeBottomNavigation() {
    // Estado para controlar qual item está selecionado. Começa com "Início".
    var selectedItem by remember { mutableStateOf("Início") }
    val items = listOf("Início", "Famílias", "Profissionais", "Configurações")
    val icons = listOf(
        Icons.Filled.Home,
        Icons.Outlined.People,
        Icons.Default.MedicalServices,
        Icons.Outlined.Settings
    )
}