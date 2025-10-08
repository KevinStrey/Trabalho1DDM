package br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.common.composables.InfoTextField
import br.com.udesc.prototipotrabalho1.common.composables.SelectableTextField

@Composable
fun NewDormitoryScreen(
    navController: NavController,
    factory: NewDormitoryViewModelFactory
) {
    val viewModel: NewDormitoryViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewDormitoryContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = { navController.popBackStack() },
        onSave = {
            // TODO: Chamar ViewModel para salvar
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewDormitoryContent(
    uiState: NewDormitoryUiState,
    onEvent: (NewDormitoryEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {
    val backgroundColor = Color(0xFFF0F8F7)
    val highlightColor = Color(0xFF26C4C6)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Novo Domicílio", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
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
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(16.dp))

            SelectableTextField(
                value = uiState.selectedFamily,
                placeholder = "Selecione a Família",
                onClick = { /* TODO: Lógica para abrir seleção */ }
            )
            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                value = uiState.housingType,
                onValueChange = { onEvent(NewDormitoryEvent.OnHousingTypeChanged(it)) },
                placeholder = "Tipo de Moradia"
            )
            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                value = uiState.sanitationConditions,
                onValueChange = { onEvent(NewDormitoryEvent.OnSanitationChanged(it)) },
                placeholder = "Condições de Saneamento"
            )
            Spacer(modifier = Modifier.height(16.dp))

            InfoTextField(
                value = uiState.peoplePerRoom,
                onValueChange = { onEvent(NewDormitoryEvent.OnPeoplePerRoomChanged(it)) },
                placeholder = "Número de Pessoas por Cômodo"
            )

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = onSave,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highlightColor,
                    contentColor = Color.White
                )
            ) {
                Text("Salvar Domicílio", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}