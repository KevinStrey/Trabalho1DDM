package br.com.udesc.prototipotrabalho1.ui.screens

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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewFamilyScreen(
    navController: NavController
) {
    var familyName by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var memberCount by remember { mutableStateOf("") }
    var contact by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFF0F8F7)
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val highlightColor = Color(0xFF26C4C6)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)
    val cancelButtonColor = Color(0xFFEFEFEF)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Cabeçalho: Botão de Voltar e Título
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            IconButton(
                onClick = { navController.popBackStack() },
                modifier = Modifier.align(Alignment.CenterStart)
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Voltar",
                    tint = Color.Black
                )
            }
            Text(
                text = "Nova Família",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Formulário
        FormSection(label = "Nome da Família") {
            TextField(
                value = familyName,
                onValueChange = { familyName = it },
                placeholder = { Text("Sobrenome ou nome da família", color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    disabledContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FormSection(label = "Endereço") {
            TextField(
                value = address,
                onValueChange = { address = it },
                placeholder = { Text("Rua, número, bairro", color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    disabledContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FormSection(label = "Número de Membros") {
            TextField(
                value = memberCount,
                onValueChange = { memberCount = it },
                placeholder = { Text("Quantidade de pessoas na família", color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    disabledContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        FormSection(label = "Contato") {
            TextField(
                value = contact,
                onValueChange = { contact = it },
                placeholder = { Text("Telefone ou e-mail", color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    disabledContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp)
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Botão Adicionar Membro
        Button(
            onClick = { /* Lógica para adicionar membro */ },
            modifier = Modifier.fillMaxWidth().height(56.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = textFieldBackgroundColor,
                contentColor = Color.DarkGray
            )
        ) {
            Text("Adicionar Membro", fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
        }

        // Botões de Ação na Base
        Spacer(modifier = Modifier.weight(1f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                onClick = { navController.popBackStack() },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = cancelButtonColor,
                    contentColor = Color.DarkGray
                )
            ) {
                Text("Cancelar", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = { /* Lógica para salvar a família */ },
                modifier = Modifier
                    .weight(1f)
                    .height(56.dp),
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

@Composable
fun FormSection(label: String, content: @Composable () -> Unit) {
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

@Preview(showBackground = true, name = "Tela Nova Família")
@Composable
fun NewFamilyScreenPreview() {
    // O rememberNavController() cria um controlador de navegação falso
    // que é necessário para a tela funcionar no modo de preview.
    NewFamilyScreen(navController = rememberNavController())
}
