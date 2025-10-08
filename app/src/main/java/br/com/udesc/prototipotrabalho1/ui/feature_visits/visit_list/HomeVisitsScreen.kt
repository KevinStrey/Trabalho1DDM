package br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_list

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.NavRoute
import br.com.udesc.prototipotrabalho1.domain.model.Visit
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeVisitsScreen(
    navController: NavController,
    factory: HomeVisitsViewModelFactory
) {
    val viewModel: HomeVisitsViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val visitsForDay by viewModel.visitsForSelectedDay.collectAsStateWithLifecycle()



    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Visitas Domiciliares") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) { // Modificado para popBackStack
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            ExtendedFloatingActionButton(
                onClick = { /* TODO: navController.navigate(...) */ },
                containerColor = Color(0xFF26C4C6),
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
                Spacer(modifier = Modifier.width(8.dp))
                Text("Nova Visita")
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            CalendarView(
                uiState = uiState,
                onEvent = viewModel::onEvent
            )

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                "Visitas Agendadas para ${uiState.selectedDate.format(DateTimeFormatter.ofPattern("dd/MM"))}",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.height(16.dp))

            if (visitsForDay.isEmpty()) {
                Text("Nenhuma visita para esta data.")
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(visitsForDay) { visit ->
                        VisitItem(
                            visit = visit,
                            onClick = {
                                navController.navigate(NavRoute.VisitReport.createRoute(visit.id))
                            }                        )
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun CalendarView(
    uiState: HomeVisitsUiState,
    onEvent: (HomeVisitsEvent) -> Unit
) {
    val daysInMonth = uiState.currentMonth.lengthOfMonth()
    val firstDayOfMonth = uiState.currentMonth.atDay(1).dayOfWeek.value % 7
    val days = (1..daysInMonth).toList()

    val monthFormatter = DateTimeFormatter.ofPattern("MMMM yyyy", Locale("pt", "BR"))

    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            IconButton(onClick = { onEvent(HomeVisitsEvent.OnPreviousMonthClicked) }) {
                Icon(imageVector = Icons.Default.ChevronLeft, contentDescription = "Mês Anterior")
            }
            Text(
                text = uiState.currentMonth.format(monthFormatter).replaceFirstChar { it.uppercase() },
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold
            )
            IconButton(onClick = { onEvent(HomeVisitsEvent.OnNextMonthClicked) }) {
                Icon(imageVector = Icons.Default.ChevronRight, contentDescription = "Próximo Mês")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            val daysOfWeek = listOf("D", "S", "T", "Q", "Q", "S", "S")
            daysOfWeek.forEach { day ->
                Text(
                    text = day,
                    modifier = Modifier.weight(1f),
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodySmall,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Spacer(modifier = Modifier.height(8.dp))

        LazyVerticalGrid(
            columns = GridCells.Fixed(7),
            content = {
                for (i in 0 until firstDayOfMonth) {
                    item { Box(modifier = Modifier.size(40.dp)) }
                }
                items(days) { day ->
                    val date = uiState.currentMonth.atDay(day)
                    DayCell(
                        date = date,
                        isSelected = date == uiState.selectedDate,
                        onClick = { onEvent(HomeVisitsEvent.OnDateSelected(it)) }
                    )
                }
            }
        )
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DayCell(date: LocalDate, isSelected: Boolean, onClick: (LocalDate) -> Unit) {
    val backgroundColor = if (isSelected) Color(0xFF26C4C6) else Color.Transparent
    val contentColor = if (isSelected) Color.White else Color.Black

    Box(
        modifier = Modifier
            .size(40.dp)
            .clip(CircleShape)
            .background(backgroundColor)
            .clickable { onClick(date) },
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = date.dayOfMonth.toString(),
            color = contentColor,
            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
        )
    }
}

@Composable
fun VisitItem(visit: Visit, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(MaterialTheme.colorScheme.surfaceVariant),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(text = visit.familyName, fontWeight = FontWeight.SemiBold, fontSize = 16.sp)
            Text(text = visit.address, color = Color.Gray, fontSize = 14.sp)
        }
    }
}