package br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NewInteractionScreen(
    navController: NavController,
    factory: NewInteractionViewModelFactory
) {
    val viewModel: NewInteractionViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                UiEvent.SaveSuccess -> navController.popBackStack()
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    NewInteractionContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewInteractionContent(
    uiState: NewInteractionUiState,
    onEvent: (NewInteractionEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit
) {
    val highlightColor = Color(0xFF26C4C6)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text("Nova Interação") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Detalhes da Interação", style = MaterialTheme.typography.titleMedium)

            InteractionTypeDropdown(
                selectedType = uiState.interactionType,
                onTypeSelected = { onEvent(NewInteractionEvent.OnInteractionTypeChanged(it)) }
            )

            OutlinedTextField(
                value = uiState.interactionDate,
                onValueChange = { onEvent(NewInteractionEvent.OnDateChanged(it)) },
                label = { Text("Data") },
                placeholder = { Text("DDMMAAAA") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            OutlinedTextField(
                value = uiState.interactionSummary,
                onValueChange = { onEvent(NewInteractionEvent.OnSummaryChanged(it)) },
                label = { Text("Resumo da Interação") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
            )

            OutlinedTextField(
                value = uiState.nextSteps,
                onValueChange = { onEvent(NewInteractionEvent.OnNextStepsChanged(it)) },
                label = { Text("Próximos Passos") },
                modifier = Modifier.fillMaxWidth().height(120.dp),
            )

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { onEvent(NewInteractionEvent.OnSaveClicked) },
                modifier = Modifier.fillMaxWidth().height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = highlightColor)
            ) {
                Text("Salvar Interação", color = Color.White)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractionTypeDropdown(
    selectedType: String,
    onTypeSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val interactionTypes = listOf("Visita domiciliar", "Contato telefônico", "Atendimento na unidade", "Outro")

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded }
    ) {
        OutlinedTextField(
            value = selectedType,
            onValueChange = {},
            readOnly = true,
            label = { Text("Tipo de Interação") },
            placeholder = { Text("Selecione") },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier.menuAnchor().fillMaxWidth()
        )
        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            interactionTypes.forEach { type ->
                DropdownMenuItem(
                    text = { Text(type) },
                    onClick = {
                        onTypeSelected(type)
                        expanded = false
                    }
                )
            }
        }
    }
}