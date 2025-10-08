package br.com.udesc.prototipotrabalho1

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MedicalServices
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
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
import br.com.udesc.prototipotrabalho1.ui.theme.PrototipoTrabalho1Theme

// Imports das telas refatoradas
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


sealed class NavRoute(val route: String) {
    object Home : NavRoute("home")
    object Families : NavRoute("families")
    object Professionals : NavRoute("professionals")
    object Settings : NavRoute("settings")
    object NewFamily : NavRoute("new_family")
    object NewDormitory : NavRoute("new_dormitory")
    object NewMember : NavRoute("new_member") // ROTA ADICIONADA
    object FamilyMembers : NavRoute("family_members/{familyId}") {
        fun createRoute(familyId: Int) = "family_members/$familyId"
    }
}
data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)
val bottomNavItems = listOf(
    BottomNavItem(NavRoute.Home.route, "Início", Icons.Default.Home),
    BottomNavItem(NavRoute.Families.route, "Famílias", Icons.Default.People),
    BottomNavItem(NavRoute.Professionals.route, "Profissionais", Icons.Default.MedicalServices),
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
                    val factory = FamilyMembersViewModelFactory(
                        familyId = familyId,
                        getFamilyByIdUseCase = appContainer.getFamilyByIdUseCase
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

            // --- CONEXÃO DA NOVA TELA DE MEMBRO ---
            composable(NavRoute.NewMember.route) {
                val factory = NewMemberViewModelFactory()
                NewMemberScreen(
                    navController = navController,
                    factory = factory
                )
            }

            composable(NavRoute.Professionals.route) { PlaceholderScreen("Profissionais") }
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