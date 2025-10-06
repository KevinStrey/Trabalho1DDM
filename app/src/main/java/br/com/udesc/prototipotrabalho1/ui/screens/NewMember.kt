package br.com.udesc.prototipotrabalho1.ui.screens // Adapte para o seu pacote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Group
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewMemberScreen(
    navController: NavController
) {
    var fullName by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var kinship by remember { mutableStateOf("") }
    var healthInfo by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFF0F8F7)
    val highlightColor = Color(0xFF26C4C6)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        "Adicionar Membro",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = backgroundColor
                )
            )
        },
        bottomBar = {
            BottomNavigationBar()
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            // Campos de Texto
            InfoTextField(
                value = fullName,
                onValueChange = { fullName = it },
                placeholder = "Nome Completo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = birthDate,
                onValueChange = { birthDate = it },
                placeholder = "Data de Nascimento"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectableTextField(
                value = kinship,
                placeholder = "Parentesco com o Chefe",
                onClick = { /* Lógica para abrir seleção */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = healthInfo,
                onValueChange = { healthInfo = it },
                placeholder = "Informações de Saúde"
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Botão Adicionar Membro
            Button(
                onClick = { /* Lógica para adicionar membro */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highlightColor,
                    contentColor = Color.White
                )
            ) {
                Text("Adicionar Membro", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun BottomNavigationBar() {
    val selectedIndex = remember { mutableStateOf(1) } // "Famílias" está selecionado
    val highlightColor = Color(0xFF26C4C6)

    BottomAppBar(
        containerColor = Color.White,
        contentColor = Color.Gray,
        tonalElevation = 8.dp
    ) {
        NavigationBarItem(
            selected = selectedIndex.value == 0,
            onClick = { selectedIndex.value = 0 },
            icon = { Icon(Icons.Default.Home, contentDescription = "Início") },
            label = { Text("Início") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = highlightColor,
                selectedTextColor = highlightColor,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedIndex.value == 1,
            onClick = { selectedIndex.value = 1 },
            icon = { Icon(Icons.Default.Group, contentDescription = "Famílias") },
            label = { Text("Famílias") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = highlightColor,
                selectedTextColor = highlightColor,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedIndex.value == 2,
            onClick = { selectedIndex.value = 2 },
            icon = { Icon(Icons.Default.Person, contentDescription = "Profissionais") }, // Use um ícone adequado
            label = { Text("Profissionais") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = highlightColor,
                selectedTextColor = highlightColor,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
        NavigationBarItem(
            selected = selectedIndex.value == 3,
            onClick = { selectedIndex.value = 3 },
            icon = { Icon(Icons.Default.Build, contentDescription = "Configurações") }, // Use um ícone adequado
            label = { Text("Configurações") },
            colors = NavigationBarItemDefaults.colors(
                selectedIconColor = highlightColor,
                selectedTextColor = highlightColor,
                unselectedIconColor = Color.Gray,
                unselectedTextColor = Color.Gray,
                indicatorColor = Color.Transparent
            )
        )
    }
}


@Preview(showBackground = true, name = "Tela Adicionar Membro")
@Composable
fun NewMemberScreenPreview() {
    NewMemberScreen(navController = rememberNavController())
}