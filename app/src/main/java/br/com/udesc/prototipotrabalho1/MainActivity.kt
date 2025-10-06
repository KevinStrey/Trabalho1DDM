package br.com.udesc.prototipotrabalho1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
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
import br.com.udesc.prototipotrabalho1.ui.screens.FamiliesScreen
import br.com.udesc.prototipotrabalho1.ui.screens.FamilyMembersScreen
import br.com.udesc.prototipotrabalho1.ui.screens.HomeScreen
import br.com.udesc.prototipotrabalho1.ui.screens.NewDormitoryScreen
import br.com.udesc.prototipotrabalho1.ui.screens.NewFamilyScreen
import br.com.udesc.prototipotrabalho1.ui.theme.PrototipoTrabalho1Theme

// Definindo as rotas e os itens da barra de navegação
// For all navigation routes
sealed class NavRoute(val route: String) {
    object Home : NavRoute("home")
    object Families : NavRoute("families")
    object Professionals : NavRoute("professionals")
    object Settings : NavRoute("settings")
    object NewFamily : NavRoute("new_family")
    object NewDormitory : NavRoute("new_dormitory")
    object FamilyMembers : NavRoute("family_members/{familyId}") {
        fun createRoute(familyId: Int) = "family_members/$familyId"
    }
}

// For bottom navigation items
data class BottomNavItem(val route: String, val label: String, val icon: ImageVector)

val bottomNavItems = listOf(
    BottomNavItem(NavRoute.Home.route, "Início", Icons.Default.Home),
    BottomNavItem(NavRoute.Families.route, "Famílias", Icons.Default.People),
    BottomNavItem(NavRoute.Professionals.route, "Profissionais", Icons.Default.MedicalServices),
    BottomNavItem(NavRoute.Settings.route, "Configurações", Icons.Default.Settings)
)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            PrototipoTrabalho1Theme {
                MainApp()
            }
        }
    }
}

@Composable
fun MainApp() {
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
            composable(NavRoute.Home.route) { HomeScreen(navController) }
            composable(NavRoute.Families.route) { FamiliesScreen(navController = navController, onAddFamilyClick = { /*TODO*/ }) }
            composable(
                route = NavRoute.FamilyMembers.route,
                arguments = listOf(navArgument("familyId") { type = NavType.IntType })
            ) { backStackEntry ->
                val familyId = backStackEntry.arguments?.getInt("familyId")
                if (familyId != null) {
                    FamilyMembersScreen(familyId, navController)
                } else {
                    Text("Erro: ID da família não encontrado.")
                }
            }
            composable(NavRoute.NewFamily.route) { NewFamilyScreen(navController) }
            composable(NavRoute.NewDormitory.route) { NewDormitoryScreen(navController) }
            composable(NavRoute.Professionals.route) { PlaceholderScreen("Profissionais") }
            composable(NavRoute.Settings.route) { PlaceholderScreen("Configurações") }
        }
    }
}

// Tela genérica para os itens que ainda não foram implementados
@Composable
fun PlaceholderScreen(screenTitle: String) {
    Surface(modifier = Modifier.padding(16.dp)) {
        Text(text = "Tela de $screenTitle", style = MaterialTheme.typography.headlineMedium)
    }
}