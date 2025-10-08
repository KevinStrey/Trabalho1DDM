package br.com.udesc.prototipotrabalho1.ui.feature_families.new_member

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AddAPhoto
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import br.com.udesc.prototipotrabalho1.common.composables.InfoTextField
import coil.compose.AsyncImage
import coil.request.ImageRequest

@Composable
fun NewMemberScreen(
    navController: NavController,
    factory: NewMemberViewModelFactory
) {
    val viewModel: NewMemberViewModel = viewModel(factory = factory)
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Efeito para escutar eventos únicos do ViewModel (como sucesso ou erro ao salvar)
    LaunchedEffect(Unit) {
        viewModel.uiEvent.collect { event ->
            when (event) {
                is UiEvent.ShowSnackbar -> {
                    snackbarHostState.showSnackbar(
                        message = event.message,
                        duration = SnackbarDuration.Short
                    )
                }
                UiEvent.SaveSuccess -> {
                    // Se salvou com sucesso, volta para a tela anterior
                    navController.popBackStack()
                }
            }
        }
    }

    NewMemberContent(
        uiState = uiState,
        onEvent = viewModel::onEvent,
        snackbarHostState = snackbarHostState,
        onNavigateBack = { navController.popBackStack() }
    )
}

// ui/feature_families/new_member/NewMemberScreen.kt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewMemberContent(
    uiState: NewMemberUiState,
    onEvent: (NewMemberEvent) -> Unit,
    snackbarHostState: SnackbarHostState,
    onNavigateBack: () -> Unit
) {
    val backgroundColor = Color(0xFFEBFEFE)
    val highlightColor = Color(0xFF26C4C6)
    val kinshipOptions = listOf("Chefe da Família", "Cônjuge", "Filho(a)", "Outro Parente")
    val textFieldBackgroundColor = Color(0xFFE0F2F1) // Cor para o TextField
    val placeholderColor = Color.Gray.copy(alpha = 0.7f) // Cor para o placeholder

    val imagePickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let {
            onEvent(NewMemberEvent.OnPhotoSelected(it.toString()))
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Adicionar Membro", fontSize = 20.sp, fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Voltar")
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(containerColor = backgroundColor)
            )
        },
        containerColor = backgroundColor
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Seção da Foto (permanece igual)
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color.LightGray)
                    .border(2.dp, highlightColor, CircleShape)
                    .clickable { imagePickerLauncher.launch("image/*") },
                contentAlignment = Alignment.Center
            ) {
                if (uiState.photoUri != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(uiState.photoUri)
                            .crossfade(true)
                            .build(),
                        contentDescription = "Foto do Membro",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier.fillMaxSize()
                    )
                } else {
                    Icon(
                        imageVector = Icons.Default.AddAPhoto,
                        contentDescription = "Adicionar Foto",
                        tint = Color.Gray,
                        modifier = Modifier.size(48.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Campos de Texto
            InfoTextField(
                value = uiState.fullName,
                onValueChange = { onEvent(NewMemberEvent.OnFullNameChanged(it)) },
                placeholder = "Nome Completo"
            )
            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = uiState.birthDate,
                onValueChange = { onEvent(NewMemberEvent.OnBirthDateChanged(it)) },
                placeholder = "Data de Nascimento"
            )
            Spacer(modifier = Modifier.height(16.dp))

            // --- CORREÇÃO DO DROPDOWN ---
            ExposedDropdownMenuBox(
                expanded = uiState.isKinshipDropdownExpanded,
                onExpandedChange = { onEvent(if (it) NewMemberEvent.OnKinshipDropdownClicked else NewMemberEvent.OnKinshipDropdownDismiss) }
            ) {
                // Usando o TextField padrão do Material 3 aqui, pois ele aceita trailingIcon
                TextField(
                    value = uiState.kinship,
                    onValueChange = {},
                    readOnly = true,
                    placeholder = { Text("Parentesco com o Chefe", color = placeholderColor) },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = uiState.isKinshipDropdownExpanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = textFieldBackgroundColor,
                        unfocusedContainerColor = textFieldBackgroundColor,
                        disabledContainerColor = textFieldBackgroundColor,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    textStyle = TextStyle(fontSize = 16.sp)
                )

                ExposedDropdownMenu(
                    expanded = uiState.isKinshipDropdownExpanded,
                    onDismissRequest = { onEvent(NewMemberEvent.OnKinshipDropdownDismiss) }
                ) {
                    kinshipOptions.forEach { option ->
                        DropdownMenuItem(
                            text = { Text(option) },
                            onClick = { onEvent(NewMemberEvent.OnKinshipChanged(option)) }
                        )
                    }
                }
            }
            // --- FIM DA CORREÇÃO ---

            Spacer(modifier = Modifier.height(16.dp))
            InfoTextField(
                value = uiState.healthInfo,
                onValueChange = { onEvent(NewMemberEvent.OnHealthInfoChanged(it)) },
                placeholder = "Informações de Saúde"
            )

            Spacer(modifier = Modifier.weight(1f))

            // Botão Adicionar Membro (permanece igual)
            Button(
                onClick = { onEvent(NewMemberEvent.OnAddMemberClicked) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = highlightColor,
                    contentColor = Color.White
                )
            ) {
                Text("Adicionar Membro", fontSize = 18.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}