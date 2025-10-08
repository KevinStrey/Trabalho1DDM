package br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_report

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun VisitReportScreen(
    navController: NavController,
    factory: VisitReportViewModelFactory
) {
    val viewModel: VisitReportViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Relatório de Visita") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        }
    ) { innerPadding ->
        if (uiState.isLoading) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.visit != null) {
            VisitReportContent(
                modifier = Modifier.padding(innerPadding),
                uiState = uiState,
                onEvent = viewModel::onEvent,
                onSave = {
                    // TODO: Chamar o ViewModel para salvar
                    navController.popBackStack()
                }
            )
        } else {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Não foi possível carregar os dados da visita.")
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
private fun VisitReportContent(
    modifier: Modifier = Modifier,
    uiState: VisitReportUiState,
    onEvent: (VisitReportEvent) -> Unit,
    onSave: () -> Unit
) {
    val visit = uiState.visit!! // Sabemos que não é nulo aqui
    val highlightColor = Color(0xFF26C4C6)
    val dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Spacer(modifier = Modifier.height(16.dp))

        SectionTitle("Dados da Visita")
        InfoTextField(label = "Data da Visita", value = visit.date.format(dateFormatter))
        Spacer(modifier = Modifier.height(8.dp))
        InfoTextField(label = "Família", value = visit.familyName)
        Spacer(modifier = Modifier.height(8.dp))
        InfoTextField(label = "Endereço", value = visit.address)
        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Observações")
        OutlinedTextField(
            value = uiState.observations,
            onValueChange = { onEvent(VisitReportEvent.OnObservationsChanged(it)) },
            placeholder = { Text("Observações") },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Ações Realizadas")
        CheckboxRow("Aconselhamento sobre saúde", uiState.healthAdviceChecked) {
            onEvent(VisitReportEvent.OnHealthAdviceToggled(it))
        }
        CheckboxRow("Verificação de vacinação", uiState.vaccinationChecked) {
            onEvent(VisitReportEvent.OnVaccinationToggled(it))
        }
        CheckboxRow("Distribuição de medicamentos", uiState.medicineDistributionChecked) {
            onEvent(VisitReportEvent.OnMedicineDistributionToggled(it))
        }
        Spacer(modifier = Modifier.height(24.dp))

        SectionTitle("Acompanhamento")
        OutlinedTextField(
            value = uiState.followUp,
            onValueChange = { onEvent(VisitReportEvent.OnFollowUpChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
        )
        Spacer(modifier = Modifier.height(32.dp))

        Button(
            onClick = onSave,
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = highlightColor)
        ) {
            Text("Salvar Relatório", color = Color.White)
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@Composable
private fun SectionTitle(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(bottom = 8.dp)
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun InfoTextField(label: String, value: String) {
    TextField(
        value = value,
        onValueChange = {},
        label = { Text(label) },
        readOnly = true,
        modifier = Modifier.fillMaxWidth(),
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            disabledContainerColor = MaterialTheme.colorScheme.surfaceVariant,
            unfocusedIndicatorColor = Color.Transparent,
            focusedIndicatorColor = Color.Transparent
        )
    )
}

@Composable
private fun CheckboxRow(
    label: String,
    isChecked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckedChange)
        Text(text = label, modifier = Modifier.padding(start = 8.dp))
    }
}