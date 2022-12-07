package com.nanangmaxfi.jetmoviesubs

import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.nanangmaxfi.jetmoviesubs.ui.navigation.NavigationItem
import com.nanangmaxfi.jetmoviesubs.ui.navigation.Screen
import com.nanangmaxfi.jetmoviesubs.ui.screen.detail.DetailMovieScreen
import com.nanangmaxfi.jetmoviesubs.ui.screen.favorite.FavoriteScreen
import com.nanangmaxfi.jetmoviesubs.ui.screen.home.HomeScreen
import com.nanangmaxfi.jetmoviesubs.ui.screen.profile.ProfileScreen
import com.nanangmaxfi.jetmoviesubs.ui.theme.JetMovieSubsTheme

@Composable
fun JetMovieApp(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            if (currentRoute != Screen.DetailMovie.route) {
                BottomBar(navController)
            }
        },
        modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Home.route){
                HomeScreen(
                    navigateToDetail = { movieId ->
                        navController.navigate(Screen.DetailMovie.createRoute(movieId))
                    }
                )
            }
            composable(Screen.Favorite.route){
                FavoriteScreen(navigateToDetail = {movieId->
                    navController.navigate(Screen.DetailMovie.createRoute(movieId))
                })
            }
            composable(Screen.Profile.route) {
                ProfileScreen()
            }
            composable(
                route = Screen.DetailMovie.route,
                arguments = listOf(navArgument("movieId") { type = NavType.LongType })
            ){
                val id = it.arguments?.getLong("movieId") ?: -1L
                DetailMovieScreen(
                    movieId = id,
                    navigateBack = {
                        navController.navigateUp()
                    }
                )
            }
        }
    }
}

@Composable
fun BottomBar(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    BottomNavigation(
        modifier = modifier
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        val navigationItems = listOf(
            NavigationItem(
                title = stringResource(R.string.menu_home),
                icon = Icons.Default.Home,
                screen = Screen.Home
            ),
            NavigationItem(
                title = stringResource(R.string.menu_favorite),
                icon = Icons.Filled.Favorite,
                screen = Screen.Favorite
            ),
            NavigationItem(
                title = stringResource(R.string.menu_profile),
                icon = Icons.Default.AccountCircle,
                screen = Screen.Profile
            )
        )
        BottomNavigation {
            navigationItems.map { item ->
                BottomNavigationItem(
                    icon = {
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.title
                        )
                    },
                    label = { Text(item.title) },
                    selected = currentRoute == item.screen.route,
                    onClick = {
                        navController.navigate(item.screen.route) {
                            popUpTo(navController.graph.findStartDestination().id) {
                                saveState = true
                            }
                            restoreState = true
                            launchSingleTop = true
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun JetMovieAppPreview() {
    JetMovieSubsTheme {
        JetMovieApp()
    }
}