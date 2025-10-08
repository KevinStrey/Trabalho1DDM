package br.com.udesc.prototipotrabalho1.common.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.ArrowDropUp
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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
                indication = null,
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