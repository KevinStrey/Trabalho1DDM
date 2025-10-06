package br.com.udesc.prototipotrabalho1.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CalendarToday
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.R // Importe sua classe R

@Composable
fun FamilyMembersScreen(
    familyId : Int, // Descomente se estiver usando dados dinâmicos
    navController: NavController
) {
    // Linhas de exemplo para dados dinâmicos
    val family = sampleFamilies.find { it.id == familyId }
    val familyName = family?.name ?: "Família Desconhecida"

    val highlightColor = Color(0xFF26C4C6)
    val backgroundColor = Color(0xFFF0F8F7)
    val lightGrayColor = Color(0xFFF0F0F0)


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
                text = familyName,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Seção: Membros da Família
        Text(
            text = "Membros da Família",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        // IMPORTANTE: Adicione as imagens 'maria.png', 'jose.png' e 'ana.png'
        // à sua pasta res/drawable
        FamilyMemberItem(
            image = painterResource(id = R.drawable.maria), // Imagem da pessoa
            name = "Maria Silva",
            role = "Parente",
            roleColor = highlightColor
        )
        FamilyMemberItem(
            image = painterResource(id = R.drawable.jose), // Imagem da pessoa
            name = "José Silva",
            role = "Parente",
            roleColor = highlightColor
        )
        FamilyMemberItem(
            image = painterResource(id = R.drawable.ana), // Imagem da pessoa
            name = "Ana Silva",
            role = "Parente",
            roleColor = highlightColor
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Seção: Domicílio
        Text(
            text = "Domicílio",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoItem(
            icon = Icons.Default.Home,
            iconBackgroundColor = lightGrayColor,
            title = "Endereço",
            detail = "Rua das Flores, 123"
        )

        Spacer(modifier = Modifier.height(24.dp))

        // Seção: Interações
        Text(
            text = "Interações",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        Spacer(modifier = Modifier.height(16.dp))
        InfoItem(
            icon = Icons.Default.CalendarToday,
            iconBackgroundColor = lightGrayColor,
            title = "Visita domiciliar",
            detail = "20/07/2024"
        )
        Spacer(modifier = Modifier.height(12.dp))
        InfoItem(
            icon = Icons.Default.Phone,
            iconBackgroundColor = lightGrayColor,
            title = "Contato telefônico",
            detail = "15/07/2024"
        )

        // Botão de Nova Interação
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            Button(
                onClick = { /* Lógica para nova interação */ },
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = highlightColor),
                modifier = Modifier.height(56.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color.White
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    "Nova Interação",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}

@Composable
fun FamilyMemberItem(image: Painter, name: String, role: String, roleColor: Color) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Image(
            painter = image,
            contentDescription = "Foto de $name",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(50.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = name, fontSize = 16.sp, fontWeight = FontWeight.SemiBold)
            Text(text = role, fontSize = 14.sp, color = roleColor)
        }
    }
}

@Composable
fun InfoItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    iconBackgroundColor: Color,
    title: String,
    detail: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(Color.White)
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(40.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(iconBackgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                tint = Color.Gray
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = title, fontSize = 16.sp, fontWeight = FontWeight.Normal, color = Color.Black)
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = detail, fontSize = 14.sp, color = Color.Gray)
        }
    }
}