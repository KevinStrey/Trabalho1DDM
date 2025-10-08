package br.com.udesc.prototipotrabalho1.ui.feature_families.new_family

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController

@Composable
fun NewFamilyScreen(
    navController: NavController,
    factory: NewFamilyViewModelFactory
) {
    val viewModel: NewFamilyViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    NewFamilyContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onNavigateBack = { navController.popBackStack() },
        onSave = {
            // TODO: Chamar o ViewModel para salvar os dados
            navController.popBackStack()
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewFamilyContent(
    uiState: NewFamilyUiState,
    onEvent: (NewFamilyEvent) -> Unit,
    onNavigateBack: () -> Unit,
    onSave: () -> Unit
) {
    val backgroundColor = Color(0xFFF0F8F7)
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val highlightColor = Color(0xFF26C4C6)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)
    val cancelButtonColor = Color(0xFFEFEFEF)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Família", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
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
            // Formulário
            FormSection(label = "Nome da Família") {
                TextField(
                    value = uiState.familyName,
                    onValueChange = { onEvent(NewFamilyEvent.FamilyNameChanged(it)) },
                    placeholder = { Text("Sobrenome ou nome da família", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = textFieldColors(textFieldBackgroundColor),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(Modifier.height(24.dp))
            FormSection(label = "Endereço") {
                TextField(
                    value = uiState.address,
                    onValueChange = { onEvent(NewFamilyEvent.AddressChanged(it)) },
                    placeholder = { Text("Rua, número, bairro", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = textFieldColors(textFieldBackgroundColor),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(Modifier.height(24.dp))
            FormSection(label = "Número de Membros") {
                TextField(
                    value = uiState.memberCount,
                    onValueChange = { onEvent(NewFamilyEvent.MemberCountChanged(it)) },
                    placeholder = { Text("Quantidade de pessoas na família", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = textFieldColors(textFieldBackgroundColor),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(Modifier.height(24.dp))
            FormSection(label = "Contato") {
                TextField(
                    value = uiState.contact,
                    onValueChange = { onEvent(NewFamilyEvent.ContactChanged(it)) },
                    placeholder = { Text("Telefone ou e-mail", color = placeholderColor) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    singleLine = true,
                    colors = textFieldColors(textFieldBackgroundColor),
                    textStyle = TextStyle(fontSize = 16.sp)
                )
            }
            Spacer(Modifier.height(24.dp))
            Button(
                onClick = { /* TODO: Navegar para Adicionar Membro */ },
                modifier = Modifier.fillMaxWidth().height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = textFieldBackgroundColor,
                    contentColor = Color.DarkGray
                )
            ) {
                Text("Adicionar Membro", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            }
            Spacer(Modifier.weight(1f))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onNavigateBack,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = cancelButtonColor,
                        contentColor = Color.DarkGray
                    )
                ) {
                    Text("Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
                Spacer(Modifier.width(16.dp))
                Button(
                    onClick = onSave,
                    modifier = Modifier.weight(1f).height(56.dp),
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = highlightColor,
                        contentColor = Color.White
                    )
                ) {
                    Text("Salvar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
                }
            }
        }
    }
}

@Composable
private fun FormSection(label: String, content: @Composable () -> Unit) {
    Column {
        Text(
            text = label,
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold,
            color = Color.Black,
            modifier = Modifier.padding(bottom = 8.dp)
        )
        content()
    }
}

@Composable
private fun textFieldColors(backgroundColor: Color): TextFieldColors {
    return TextFieldDefaults.colors(
        focusedContainerColor = backgroundColor,
        unfocusedContainerColor = backgroundColor,
        disabledContainerColor = backgroundColor,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent
    )
}