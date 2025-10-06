package br.com.udesc.prototipotrabalho1.ui.screens // Adapte para o seu pacote

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
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

@Composable
fun NewDormitoryScreen(
    navController: NavController
) {
    var selectedFamily by remember { mutableStateOf("") }
    var housingType by remember { mutableStateOf("") }
    var sanitationConditions by remember { mutableStateOf("") }
    var peoplePerRoom by remember { mutableStateOf("") }

    val backgroundColor = Color(0xFFF0F8F7)
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val highlightColor = Color(0xFF26C4C6)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        // Cabeçalho
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
                text = "Novo Domicílio",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(32.dp))

        // Campo "Selecione a Família" - Simula um dropdown
        SelectableTextField(
            value = selectedFamily,
            placeholder = "Selecione a Família",
            onClick = { /* Lógica para abrir seleção de família */ }
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Outros campos de texto
        InfoTextField(
            value = housingType,
            onValueChange = { housingType = it },
            placeholder = "Tipo de Moradia"
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoTextField(
            value = sanitationConditions,
            onValueChange = { sanitationConditions = it },
            placeholder = "Condições de Saneamento"
        )

        Spacer(modifier = Modifier.height(16.dp))

        InfoTextField(
            value = peoplePerRoom,
            onValueChange = { peoplePerRoom = it },
            placeholder = "Número de Pessoas por Cômodo"
        )


        // Botão Salvar
        Spacer(modifier = Modifier.weight(1f))
        Button(
            onClick = { /* Lógica para salvar o domicílio */ },
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

@Composable
fun SelectableTextField(
    value: String,
    placeholder: String,
    onClick: () -> Unit
) {
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .background(textFieldBackgroundColor, RoundedCornerShape(12.dp))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null, // Sem efeito visual de clique
                onClick = onClick
            )
            .padding(horizontal = 16.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = if (value.isEmpty()) placeholder else value,
                color = if (value.isEmpty()) placeholderColor else Color.Black,
                fontSize = 16.sp
            )
            Column {
                Icon(Icons.Default.ArrowDropUp, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
                Icon(Icons.Default.ArrowDropDown, contentDescription = null, tint = Color.Gray, modifier = Modifier.size(20.dp))
            }
        }
    }
}

@Composable
fun InfoTextField(
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String
) {
    val textFieldBackgroundColor = Color(0xFFE0F2F1)
    val placeholderColor = Color.Gray.copy(alpha = 0.7f)

    TextField(
        value = value,
        onValueChange = onValueChange,
        placeholder = { Text(placeholder, color = placeholderColor) },
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


@Preview(showBackground = true, name = "Tela Novo Domicílio")
@Composable
fun NewDormitoryScreenPreview() {
    NewDormitoryScreen(navController = rememberNavController())
}