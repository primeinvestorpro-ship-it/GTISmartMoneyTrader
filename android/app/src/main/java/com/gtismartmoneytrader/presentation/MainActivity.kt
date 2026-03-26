package com.gtismartmoneytrader.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.gtismartmoneytrader.domain.model.UserSettings
import com.gtismartmoneytrader.presentation.ui.backtest.BacktestScreen
import com.gtismartmoneytrader.presentation.ui.dashboard.DashboardScreen
import com.gtismartmoneytrader.presentation.ui.home.HomeScreen
import com.gtismartmoneytrader.presentation.ui.settings.SettingsScreen
import com.gtismartmoneytrader.presentation.ui.straddle.StraddleScreen
import com.gtismartmoneytrader.presentation.ui.theme.GTITheme
import com.gtismartmoneytrader.presentation.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GTITheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    GTIQuantProApp()
                }
            }
        }
    }
}

// ──────────────────────────────────────────────────────────────────────────────
// Navigation routes
// ──────────────────────────────────────────────────────────────────────────────
sealed class Screen(val route: String) {
    // Bottom-nav destinations
    object Dashboard : Screen("dashboard")
    object GTIEngine : Screen("gti_engine")
    object Straddle : Screen("straddle")
    object Backtest : Screen("backtest")
    // Settings (top-bar icon)
    object Settings : Screen("settings")
}

data class BottomNavItem(
    val screen: Screen,
    val label: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(Screen.Dashboard, "Dashboard", Icons.Default.Dashboard),
    BottomNavItem(Screen.GTIEngine, "GTI", Icons.Default.ShowChart),
    BottomNavItem(Screen.Straddle, "Straddle", Icons.Default.BarChart),
    BottomNavItem(Screen.Backtest, "Backtest", Icons.Default.Science)
)

// ──────────────────────────────────────────────────────────────────────────────
// Root composable
// ──────────────────────────────────────────────────────────────────────────────
@Composable
fun GTIQuantProApp() {
    val navController = rememberNavController()
    val homeViewModel: HomeViewModel = hiltViewModel()
    var currentSettings by remember { mutableStateOf(UserSettings()) }

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    // Hide bottom nav on Settings screen
    val showBottomBar = bottomNavItems.any { it.screen.route == currentDestination?.route }

    Scaffold(
        bottomBar = {
            if (showBottomBar) {
                NavigationBar {
                    bottomNavItems.forEach { item ->
                        NavigationBarItem(
                            icon = { Icon(item.icon, contentDescription = item.label) },
                            label = { Text(item.label) },
                            selected = currentDestination?.hierarchy?.any { it.route == item.screen.route } == true,
                            onClick = {
                                navController.navigate(item.screen.route) {
                                    popUpTo(navController.graph.findStartDestination().id) { saveState = true }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Dashboard.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(Screen.Dashboard.route) {
                DashboardScreen(viewModel = homeViewModel)
            }
            composable(Screen.GTIEngine.route) {
                HomeScreen(
                    viewModel = homeViewModel,
                    onNavigateToSettings = { navController.navigate(Screen.Settings.route) }
                )
            }
            composable(Screen.Straddle.route) {
                StraddleScreen(viewModel = homeViewModel)
            }
            composable(Screen.Backtest.route) {
                BacktestScreen()
            }
            composable(Screen.Settings.route) {
                SettingsScreen(
                    currentSettings = currentSettings,
                    onSettingsChanged = { newSettings ->
                        currentSettings = newSettings
                        homeViewModel.updateSettings(newSettings)
                    },
                    onNavigateBack = { navController.popBackStack() }
                )
            }
        }
    }
}
