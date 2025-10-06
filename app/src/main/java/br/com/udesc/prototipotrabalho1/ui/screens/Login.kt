package br.com.udesc.prototipotrabalho1.ui.screens // Adapte para o seu pacote

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen() {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFF0F8F7)
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val highlightColor = Color(0xFF26C4C6)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Título
            Text(
                text = "Entrar",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Campo de Nome de Usuário
            TextField(
                value = username,
                onValueChange = { username = it },
                placeholder = { Text("Nome do usuário", color = placeholderColor) },
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

            Spacer(modifier = Modifier.height(16.dp))

            // Campo de Senha
            TextField(
                value = password,
                onValueChange = { password = it },
                placeholder = { Text("Senha", color = placeholderColor) },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                singleLine = true,
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = textFieldBackgroundColor,
                    unfocusedContainerColor = textFieldBackgroundColor,
                    disabledContainerColor = textFieldBackgroundColor,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(fontSize = 16.sp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Link "Esqueceu a senha?"
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterStart
            ) {
                TextButton(onClick = { /* Lógica para esquecer a senha */ }) {
                    Text(
                        "Esqueceu a senha?",
                        color = highlightColor,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }


            Spacer(modifier = Modifier.height(16.dp))

            // Botão Entrar
            Button(
                onClick = { /* Lógica de login */ },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highlightColor,
                    contentColor = Color.White
                )
            ) {
                Text("Entrar", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Preview(showBackground = true, name = "Tela de Login")
@Composable
fun LoginScreenPreview() {
    LoginScreen()
}