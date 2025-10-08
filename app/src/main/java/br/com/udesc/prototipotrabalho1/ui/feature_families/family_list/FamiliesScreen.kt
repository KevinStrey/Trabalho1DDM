package br.com.udesc.prototipotrabalho1.ui.feature_families.family_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.NavRoute
import br.com.udesc.prototipotrabalho1.domain.model.Family

/**
 * Composable "inteligente" (stateful).
 *
 * Sua responsabilidade é se conectar ao ViewModel para obter o estado e
 * passar os dados para os Composables de apresentação ("burros").
 */
@Composable
fun FamiliesScreen(
    navController: NavController,
    factory: FamiliesViewModelFactory
) {
    val viewModel: FamiliesViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    FamiliesContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        onFamilyClick = { familyId ->
            navController.navigate(NavRoute.FamilyMembers.createRoute(familyId))
        },
        onAddFamilyClick = {
            navController.navigate(NavRoute.NewFamily.route)
        }
    )
}

/**
 * Composable de apresentação "burro" (stateless).
 *
 * Não possui lógica, apenas recebe o estado e emite eventos.
 * É reutilizável e fácil de prever e testar em isolation.
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FamiliesContent(
    uiState: FamiliesUiState,
    onEvent: (FamiliesEvent) -> Unit,
    onFamilyClick: (Int) -> Unit,
    onAddFamilyClick: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Famílias") },
                actions = {
                    IconButton(onClick = onAddFamilyClick) {
                        Icon(imageVector = Icons.Default.Add, contentDescription = "Adicionar Família")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(horizontal = 16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.searchQuery,
                onValueChange = { query -> onEvent(FamiliesEvent.SearchQueryChanged(query)) },
                label = { Text("Pesquisar famílias") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                shape = RoundedCornerShape(24.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (uiState.isLoading) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            } else {
                LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(uiState.families) { family ->
                        FamilyListItem(
                            family = family,
                            onClick = { onFamilyClick(family.id) }
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun FamilyListItem(
    family: Family,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = family.avatarResId),
            contentDescription = "Avatar da ${family.name}",
            modifier = Modifier
                .size(56.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column {
            Text(
                text = family.name,
                style = MaterialTheme.typography.bodyLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = family.address,
                style = MaterialTheme.typography.bodyMedium,
                color = Color.Gray
            )
        }
    }
}