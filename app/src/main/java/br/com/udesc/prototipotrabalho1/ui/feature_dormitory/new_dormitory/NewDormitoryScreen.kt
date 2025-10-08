package br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.*
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

@Composable
fun NewDormitoryScreen(
    navController: NavController,
    factory: NewDormitoryViewModelFactory
) {
    val viewModel: NewDormitoryViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when(event) {
                UiEvent.SaveSuccess -> navController.popBackStack()
                is UiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    NewDormitoryContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateBack = { navController.popBackStack() }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewDormitoryContent(
    uiState: NewDormitoryUiState,
    onEvent: (NewDormitoryEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit
) {
    val backgroundColor = Color(0xFFF0F8F7)
    val highlightColor = Color(0xFF26C4C6)

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
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

            // Dropdown de Famílias (continua igual)
            ExposedDropdownMenuBox(
                expanded = uiState.isFamilyDropdownExpanded,
                onExpandedChange = { onEvent(if (it) NewDormitoryEvent.OnFamilyDropdownClicked else NewDormitoryEvent.OnFamilyDropdownDismiss) }
            ) {
                InfoTextField(
                    value = uiState.selectedFamily?.name ?: "",
                    onValueChange = {},
                    readOnly = true,
                    placeholder = "Selecione a Família",
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isFamilyDropdownExpanded) },
                    modifier = Modifier.menuAnchor()
                )

                ExposedDropdownMenu(
                    expanded = uiState.isFamilyDropdownExpanded,
                    onDismissRequest = { onEvent(NewDormitoryEvent.OnFamilyDropdownDismiss) }
                ) {
                    uiState.allFamilies.forEach { family ->
                        DropdownMenuItem(
                            text = { Text(family.name) },
                            onClick = { onEvent(NewDormitoryEvent.OnFamilySelected(family)) }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Endereço (continua igual)
            InfoTextField(
                value = uiState.address,
                onValueChange = { onEvent(NewDormitoryEvent.OnAddressChanged(it)) },
                placeholder = "Endereço Completo (Rua, Nº, Bairro...)"
            )
            Spacer(modifier = Modifier.height(24.dp))

            // --- CÓDIGO DO MAPA REINSERIDO AQUI ---
            Text(
                text = "Geolocalização",
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
                    .clickable { /* TODO: Lógica para abrir o mapa */ },
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.dp, Color.LightGray),
                colors = CardDefaults.cardColors(containerColor = Color.White)
            ) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Ícone de Localização",
                            tint = Color.Gray,
                            modifier = Modifier.size(40.dp)
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text("Toque para definir no mapa", color = Color.Gray)
                    }
                }
            }
            // --- FIM DO CÓDIGO DO MAPA ---

            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { onEvent(NewDormitoryEvent.OnSaveClicked) },
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