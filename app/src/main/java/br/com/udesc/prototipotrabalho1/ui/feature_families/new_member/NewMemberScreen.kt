package br.com.udesc.prototipotrabalho1.ui.feature_families.new_member

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.common.composables.InfoTextField
import br.com.udesc.prototipotrabalho1.common.composables.SelectableTextField

@Composable
fun NewMemberScreen(
    navController: NavController,
    factory: NewMemberViewModelFactory
) {
    val viewModel: NewMemberViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewMemberContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = { navController.popBackStack() },
        onAddMember = {
            // TODO: Chamar o ViewModel para salvar o membro
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewMemberContent(
    uiState: NewMemberUiState,
    onEvent: (NewMemberEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onAddMember: () -> Unit
) {
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
                    IconButton(onClick = onNavigateBack) {
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
                value = uiState.fullName,
                onValueChange = { onEvent(NewMemberEvent.OnFullNameChanged(it)) },
                placeholder = "Nome Completo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = uiState.birthDate,
                onValueChange = { onEvent(NewMemberEvent.OnBirthDateChanged(it)) },
                placeholder = "Data de Nascimento"
            )
            Spacer(modifier = Modifier.height(16.dp))
            SelectableTextField(
                value = uiState.kinship,
                placeholder = "Parentesco com o Chefe",
                onClick = { /* Lógica para abrir seleção */ }
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = uiState.healthInfo,
                onValueChange = { onEvent(NewMemberEvent.OnHealthInfoChanged(it)) },
                placeholder = "Informações de Saúde"
            )

            Spacer(modifier = Modifier.weight(1f)) // Empurra o botão para baixo

            // Botão Adicionar Membro
            Button(
                onClick = onAddMember,
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