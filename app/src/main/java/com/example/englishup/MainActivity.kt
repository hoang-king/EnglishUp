package com.example.englishup

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.englishup.frameworks.ui.*
import com.example.englishup.ui.theme.*
import dagger.hilt.android.AndroidEntryPoint



sealed class Screen(val route: String, val title: String) {
    object Home : Screen("home", "Trang chủ")
    object Vocabulary : Screen("vocabulary", "Flashcard")
    object Grammar : Screen("grammar", "Ngữ pháp")
    object Quiz : Screen("quiz", "Quiz")
    object Listening : Screen("listening", "Nghe")
    object Reading : Screen("reading", "Đọc")
    object Progress : Screen("progress", "Tiến độ")
    object Profile : Screen("profile", "Hồ sơ")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            EnglishUPTheme {
                val navController = rememberNavController()
                Scaffold(
                    bottomBar = { BottomNavigationBar(navController) }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = Screen.Home.route,
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable(Screen.Home.route) {
                            HomeScreen(
                                onNavigateToVocab = { 
                                    navController.navigate(Screen.Vocabulary.route) {
                                        popUpTo(navController.graph.findStartDestination().id) {
                                            saveState = true
                                        }
                                        launchSingleTop = true
                                        restoreState = true
                                    }
                                },
                                onNavigateToGrammar = { navController.navigate(Screen.Grammar.route) },
                                onNavigateToReading = { navController.navigate(Screen.Reading.route) },
                                onNavigateToListening = { navController.navigate(Screen.Listening.route) },
                            )
                        }
                        composable(Screen.Vocabulary.route) {
                            VocabularyScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Grammar.route) {
                            GrammarScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Quiz.route) {
                            QuizScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Listening.route) {
                            ListeningScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Reading.route) {
                            ReadingScreen(onBack = { navController.popBackStack() })
                        }
                        composable(Screen.Progress.route) {
                            ProgressScreen()
                        }
                        composable(Screen.Profile.route) {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) { 
                                Text("Màn hình Hồ sơ") 
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    data class NavItem(val route: String, val title: String, val icon: ImageVector)
    val items = listOf(
        NavItem(Screen.Home.route, "Trang chủ", Icons.Filled.Home),
        NavItem(Screen.Vocabulary.route, "Flashcard", Icons.Filled.Star),
        NavItem(Screen.Quiz.route, "Quiz", Icons.Filled.List),
        NavItem(Screen.Progress.route, "Tiến độ", Icons.Filled.DateRange),
        NavItem(Screen.Profile.route, "Hồ sơ", Icons.Filled.Person),
    )

    NavigationBar(
        containerColor = Bg2,
        contentColor = TextTertiary,
    ) {
        items.forEach { item ->
            val selected = currentRoute == item.route
            NavigationBarItem(
                icon = { Icon(imageVector = item.icon, contentDescription = item.title) },
                label = { Text(item.title, style = MaterialTheme.typography.labelSmall) },
                selected = selected,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Green,
                    selectedTextColor = Green,
                    unselectedIconColor = TextTertiary,
                    unselectedTextColor = TextTertiary,
                    indicatorColor = GreenDim,
                ),
                onClick = {
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}
