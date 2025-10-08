package br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction

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
import androidx.navigation.NavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewInteractionScreen(
    navController: NavController,
    factory: NewInteractionViewModelFactory
) {
    var interactionType by remember { mutableStateOf("") }
    var interactionDate by remember { mutableStateOf("") }
    var interactionSummary by remember { mutableStateOf("") }
    var nextSteps by remember { mutableStateOf("") }

    val highlightColor = Color(0xFF26C4C6) // Cor do seu app

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Interação") },
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
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text("Detalhes da Interação", style = MaterialTheme.typography.titleMedium)

            // Campo Tipo de Interação (Dropdown)
            InteractionTypeDropdown(
                selectedType = interactionType,
                onTypeSelected = { interactionType = it }
            )

            // Campo Data
            OutlinedTextField(
                value = interactionDate,
                onValueChange = {
                    // Limita o input a 8 caracteres numéricos
                    if (it.length <= 8) {
                        interactionDate = it.filter { char -> char.isDigit() }
                    }
                },
                label = { Text("Data") },
                placeholder = { Text("DD/MM/AAAA") },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
            )

            // Campo Resumo da Interação
            OutlinedTextField(
                value = interactionSummary,
                onValueChange = { interactionSummary = it },
                label = { Text("Resumo da Interação") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
            )

            // Campo Próximos Passos
            OutlinedTextField(
                value = nextSteps,
                onValueChange = { nextSteps = it },
                label = { Text("Próximos Passos") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
            )

            // Espaçador para empurrar o botão para baixo
            Spacer(modifier = Modifier.weight(1f))

            // Botão Salvar
            Button(
                onClick = { /* TODO */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
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
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
            },
            colors = ExposedDropdownMenuDefaults.textFieldColors(),
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
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