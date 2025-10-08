package br.com.udesc.prototipotrabalho1.ui.feature_families.family_members

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.R

@Composable
fun FamilyMembersScreen(
    navController: NavController,
    factory: FamilyMembersViewModelFactory
) {
    val viewModel: FamilyMembersViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FamilyMembersContent(
        uiState = uiState,
        onNavigateBack = { navController.popBackStack() },
        onNewInteraction = { /* TODO: Navegar para nova interação */ }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FamilyMembersContent(
    uiState: FamilyMembersUiState,
    onNavigateBack: () -> Unit,
    onNewInteraction: () -> Unit
) {
    val family = uiState.family
    val highlightColor = Color(0xFF26C4C6)
    val backgroundColor = Color(0xFFF0F8F7)
    val lightGrayColor = Color(0xFFF0F0F0)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        family?.name ?: "Carregando...",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else if (family != null) {
                // Conteúdo da tela (Membros, Domicílio, Interações)
                // (O código visual daqui para baixo é o mesmo que o original)
                Text("Membros da Família", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                FamilyMemberItem(painterResource(id = R.drawable.maria), "Maria Silva", "Parente", highlightColor)
                FamilyMemberItem(painterResource(id = R.drawable.jose), "José Silva", "Parente", highlightColor)
                FamilyMemberItem(painterResource(id = R.drawable.ana), "Ana Silva", "Parente", highlightColor)
                Spacer(modifier = Modifier.height(24.dp))

                Text("Domicílio", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(Icons.Default.Home, lightGrayColor, "Endereço", family.address)
                Spacer(modifier = Modifier.height(24.dp))

                Text("Interações", fontSize = 18.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(16.dp))
                InfoItem(Icons.Default.CalendarToday, lightGrayColor, "Visita domiciliar", "20/07/2024")
                Spacer(modifier = Modifier.height(12.dp))
                InfoItem(Icons.Default.Phone, lightGrayColor, "Contato telefônico", "15/07/2024")
                Spacer(modifier = Modifier.height(16.dp))

                Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                    Button(
                        onClick = onNewInteraction,
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(containerColor = highlightColor),
                        modifier = Modifier.height(56.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, tint = Color.White)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Nova Interação", color = Color.White, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
                    }
                }
            } else {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Família não encontrada.")
                }
            }
        }
    }
}

// As funções FamilyMemberItem e InfoItem continuam as mesmas do arquivo original
@Composable
fun FamilyMemberItem(image: Painter, name: String, role: String, roleColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "Foto de $name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = role, fontSize = 14.sp, color = roleColor)
        }
    }
}

@Composable
fun InfoItem(icon: ImageVector, iconBackgroundColor: Color, title: String, detail: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(imageVector = icon, contentDescription = null, modifier = Modifier.size(24.dp), tint = Color.Gray)
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = detail, fontSize = 14.sp, color = Color.Gray)
        }
    }
}