package br.com.udesc.prototipotrabalho1

// Imports das telas refatoradas
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import br.com.udesc.prototipotrabalho1.data.AppContainer
import br.com.udesc.prototipotrabalho1.data.DefaultAppContainer
import br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory.NewDormitoryScreen
import br.com.udesc.prototipotrabalho1.ui.feature_dormitory.new_dormitory.NewDormitoryViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_families.family_list.FamiliesScreen
import br.com.udesc.prototipotrabalho1.ui.feature_families.family_list.FamiliesViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_families.family_members.FamilyMembersScreen
import br.com.udesc.prototipotrabalho1.ui.feature_families.family_members.FamilyMembersViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_families.new_family.NewFamilyScreen
import br.com.udesc.prototipotrabalho1.ui.feature_families.new_family.NewFamilyViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_families.new_member.NewMemberScreen
import br.com.udesc.prototipotrabalho1.ui.feature_families.new_member.NewMemberViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_home.HomeScreen
import br.com.udesc.prototipotrabalho1.ui.feature_home.HomeViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction.NewInteractionScreen
import br.com.udesc.prototipotrabalho1.ui.feature_visits.new_interaction.NewInteractionViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_list.HomeVisitsScreen
import br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_list.HomeVisitsViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_report.VisitReportScreen
import br.com.udesc.prototipotrabalho1.ui.feature_visits.visit_report.VisitReportViewModelFactory
import br.com.udesc.prototipotrabalho1.ui.theme.PrototipoTrabalho1Theme


sealed class NavRoute(val route: String) {
    object Home : NavRoute("home")
    object Families : NavRoute("families")
    object Settings : NavRoute("settings")
    object NewFamily : NavRoute("new_family")
    object NewDormitory : NavRoute("new_dormitory")


    object NewInteraction : NavRoute("new_interaction/{familyId}") {
        fun createRoute(familyId: Int) = "new_interaction/$familyId"
    }
    object Visit : NavRoute("visit") // <-- ADICIONE/MODIFIQUE ESTA LINHA
    object NewMember : NavRoute("new_member/{familyId}") {
        fun createRoute(familyId: Int) = "new_member/$familyId"
    }
    object FamilyMembers : NavRoute("family_members/{familyId}") {
        fun createRoute(familyId: Int) = "family_members/$familyId"
    }
    object VisitReport : NavRoute("visit_report/{visitId}") { // <-- ADICIONE ESTA ROTA
        fun createRoute(visitId: Int) = "visit_report/$visitId"
    }

}
data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
val bottomNavItems = listOf(
    BottomNavItem(NavRoute.Home.route, "Início", Icons.Default.Home),
    BottomNavItem(NavRoute.Families.route, "Famílias", Icons.Default.People),
    BottomNavItem(NavRoute.Visit.route, "Visitas", Icons.Default.Groups),
    BottomNavItem(NavRoute.Settings.route, "Configurações", Icons.Default.Settings)
)


class MainActivity : ComponentActivity() {

    private lateinit var appContainer: AppContainer

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        appContainer = DefaultAppContainer()

        enableEdgeToEdge()
        setContent {
            PrototipoTrabalho1Theme {
                MainApp(appContainer = appContainer)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MainApp(appContainer: AppContainer) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            NavigationBar {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.label) },
                        label = { Text(item.label) },
                        selected = currentDestination?.hierarchy?.any { it.route == item.route } == true,
                        onClick = {
                            navController.navigate(item.route) {
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = NavRoute.Home.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(NavRoute.Home.route) {
                val factory = HomeViewModelFactory()
                HomeScreen(navController, factory)
            }

            composable(NavRoute.Families.route) {
                val factory = FamiliesViewModelFactory(appContainer.getFamiliesUseCase)
                FamiliesScreen(
                    navController = navController,
                    factory = factory
                )
            }

            composable(
                route = NavRoute.FamilyMembers.route,
                arguments = listOf(navArgument("familyId") { type = NavType.IntType })
            ) { backStackEntry ->
                val familyId = backStackEntry.arguments?.getInt("familyId")

                if (familyId != null) {
                    // ATUALIZAÇÃO: Passe o novo UseCase para a Factory
                    val factory = FamilyMembersViewModelFactory(
                        familyId = familyId,
                        getFamilyByIdUseCase = appContainer.getFamilyByIdUseCase,
                        getMembersByFamilyIdUseCase = appContainer.getMembersByFamilyIdUseCase,
                        getInteractionsByFamilyIdUseCase = appContainer.getInteractionsByFamilyIdUseCase // <-- Adicionado
                    )
                    FamilyMembersScreen(
                        navController = navController,
                        factory = factory
                    )
                } else {
                    Text("Erro: ID da família não encontrado.")
                }
            }



            composable(NavRoute.NewFamily.route) {
                val factory = NewFamilyViewModelFactory()
                NewFamilyScreen(
                    navController = navController,
                    factory = factory
                )
            }

            composable(NavRoute.NewDormitory.route) {
                val factory = NewDormitoryViewModelFactory()
                NewDormitoryScreen(
                    navController = navController,
                    factory = factory
                )
            }

            // --- CONEXÃO DA NOVA TELA DE MEMBRO (CORRIGIDA) ---
            composable(
                route = NavRoute.NewMember.route,
                arguments = listOf(navArgument("familyId") { type = NavType.IntType })
            ) { backStackEntry ->
                // 1. Extraia o familyId que veio na rota
                val familyId = backStackEntry.arguments?.getInt("familyId") ?: -1

                // 2. Crie a factory passando as dependências necessárias
                val factory = NewMemberViewModelFactory(
                    familyId = familyId,
                    addMemberUseCase = appContainer.addMemberUseCase
                )

                // 3. Chame a tela com a factory
                NewMemberScreen(
                    navController = navController,
                    factory = factory
                )
            }

            composable(
                route = NavRoute.NewInteraction.route,
                arguments = listOf(navArgument("familyId") { type = NavType.IntType })
            ) { backStackEntry ->
                val familyId = backStackEntry.arguments?.getInt("familyId")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && familyId != null) {
                    val factory = NewInteractionViewModelFactory(
                        familyId = familyId,
                        addInteractionUseCase = appContainer.addInteractionUseCase
                    )
                    NewInteractionScreen(
                        navController = navController,
                        factory = factory
                    )
                } else {
                    Text("Erro: ID da família não encontrado ou versão do Android incompatível.")
                }
            }


            // --- CONEXÃO DA NOVA TELA DE VISITAS ---
            composable(NavRoute.Visit.route) {
                // A anotação @RequiresApi é necessária aqui porque a tela e o ViewModel usam APIs de data recentes
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val factory = HomeVisitsViewModelFactory(appContainer.getVisitsByDateUseCase)
                    HomeVisitsScreen(
                        navController = navController,
                        factory = factory
                    )
                } else {
                    // Fallback para versões do Android mais antigas que não suportam a tela
                    PlaceholderScreen("Funcionalidade indisponível nesta versão do Android")
                }
            }
            // --- CONEXÃO DA NOVA TELA DE RELATÓRIO DE VISITA ---
            composable(
                route = NavRoute.VisitReport.route,
                arguments = listOf(navArgument("visitId") { type = NavType.IntType })
            ) { backStackEntry ->
                val visitId = backStackEntry.arguments?.getInt("visitId")
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && visitId != null) {
                    val factory = VisitReportViewModelFactory(
                        visitId = visitId,
                        getVisitByIdUseCase = appContainer.getVisitByIdUseCase
                    )
                    VisitReportScreen(
                        navController = navController,
                        factory = factory
                    )
                } else {
                    Text("Erro: ID da visita não encontrado ou versão do Android incompatível.")
                }
            }
            composable(NavRoute.Settings.route) { PlaceholderScreen("Configurações") }
        }
    }
}

@Composable
fun PlaceholderScreen(screenTitle: String) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Text(text = "Tela de $screenTitle", style = MaterialTheme.typography.headlineMedium)
    }
}