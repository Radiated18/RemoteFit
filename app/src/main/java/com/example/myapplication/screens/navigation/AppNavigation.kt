package com.example.myapplication.screens.navigation

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.screens.SettingsMenu.SettingsMenuViewModel
import com.example.myapplication.screens.SettingsMenuScreen
import com.example.myapplication.screens.SplashScreen.SplashScreen
import com.example.myapplication.screens.activepauses.ActivePausesViewModel
import com.example.myapplication.screens.activepauses.ui.ActivePausesScreen
import com.example.myapplication.screens.badgeforuser.BadgeForUserViewModel
import com.example.myapplication.screens.badgeforuser.ui.BadgeForUserScreen
import com.example.myapplication.screens.compareresults.CompareResultsViewModel
import com.example.myapplication.screens.compareresults.ui.CompareResultsScreen
import com.example.myapplication.screens.daytrainingslist.DayTrainingListViewModel
import com.example.myapplication.screens.daytrainingslist.ui.DayTrainingListScreen
import com.example.myapplication.screens.editinformation.ui.EditInformationScreen
import com.example.myapplication.screens.finalobjetive.FinalObjetiveViewModel
import com.example.myapplication.screens.finalobjetive.ui.FinalObjetiveScreen
import com.example.myapplication.screens.foodDesriptionVisualizer.FoodDescriptionVisualizerScreen
import com.example.myapplication.screens.foodsjourneyvisualizer.FoodsJourneyVisualizerViewModel
import com.example.myapplication.screens.foodsjourneyvisualizer.ui.FoodsJourneyVisualizerScreen
import com.example.myapplication.screens.foodsvisualizerlist.FoodsVisualizerListViewModel
import com.example.myapplication.screens.foodsvisualizerlist.ui.FoodsVisualizerListScreen
import com.example.myapplication.screens.formpage.FormPageViewModel
import com.example.myapplication.screens.formpage.ui.FormPageScreen
import com.example.myapplication.screens.goalLogs.GoalLogsViewModel
import com.example.myapplication.screens.goalLogs.ui.GoalLogsScreen
import com.example.myapplication.screens.goalssettingsscreen.ui.GoalsSettingsScreen
import com.example.myapplication.screens.homescreen.HomeScreenViewModel
import com.example.myapplication.screens.homescreen.ui.HomeScreen
import com.example.myapplication.screens.levelandfinal.LevelAndFinalViewModel
import com.example.myapplication.screens.levelandfinal.ui.LevelAndFinalScreen
import com.example.myapplication.screens.login.LoginScreenViewModel
import com.example.myapplication.screens.login.ui.LoginScreen
import com.example.myapplication.screens.monitoringRegister.MonitoringRegisterViewModel
import com.example.myapplication.screens.monitoringRegister.ui.MonitoringRegisterScreen
import com.example.myapplication.screens.recoverpassword.RecoverPasswordViewModel
import com.example.myapplication.screens.recoverpassword.ui.RecoverPasswordScreen
import com.example.myapplication.screens.register.RegisterViewModel
import com.example.myapplication.screens.register.ui.RegisterScreen
import com.example.myapplication.screens.selectdaytraining.SelectDayTrainingScreen
import com.example.myapplication.screens.selectplacetraining.SelectPlaceTrainingScreen
import com.example.myapplication.screens.welcomeregister.WelcomeRegisterScreen

@Composable

fun AppNavigation(){
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = ProjectScreens.AppSplashScreen.name){
        composable(ProjectScreens.AppSplashScreen.name){
            SplashScreen(navController = navController)
        }
        composable(ProjectScreens.LoginScreen.name){
            LoginScreen(navController = navController, LoginScreenViewModel())
        }
        composable(ProjectScreens.HomeScreen.name){
            HomeScreen(HomeScreenViewModel(), navController)
        }
        composable(ProjectScreens.RegisterScreen.name){
            RegisterScreen(navController = navController, viewModel = RegisterViewModel())
        }
        composable(ProjectScreens.WelcomeRegisterScreen.name){
            WelcomeRegisterScreen(navController = navController)
        }
        composable(ProjectScreens.FormPageScreen.name){
            FormPageScreen(navController =navController, viewModel = FormPageViewModel())
        }
        composable(ProjectScreens.SettingsMenuScreen.name){
            SettingsMenuScreen(navController =navController, viewModel = SettingsMenuViewModel())
        }
        composable(ProjectScreens.EditInformationScreen.name){
            EditInformationScreen(navController =navController, viewModel = hiltViewModel())
        }
        composable(ProjectScreens.GoalsSettingsScreen.name){
            GoalsSettingsScreen(navController = navController)
        }
        composable(ProjectScreens.GoalLogsScreen.name){
            GoalLogsScreen(navController = navController, viewModel = GoalLogsViewModel())
        }
        composable(ProjectScreens.MonitoringRegisterScreen.name){
            MonitoringRegisterScreen(navController = navController, viewModel = MonitoringRegisterViewModel())
        }
        composable(ProjectScreens.FinalObjetiveScreen.name){
            FinalObjetiveScreen(navController = navController, viewModel = FinalObjetiveViewModel())
        }
        composable(ProjectScreens.SelectPlaceTrainingScreen.name){
            SelectPlaceTrainingScreen(navController = navController)
        }
        composable(ProjectScreens.SelectDayTrainingScreen.name){
            SelectDayTrainingScreen(navController = navController)
        }
        composable(ProjectScreens.DayTrainingListScreen.name){
            DayTrainingListScreen(navController = navController, viewModel = DayTrainingListViewModel())
        }
        composable(ProjectScreens.FoodsJourneyVisualizerScreen.name){
            FoodsJourneyVisualizerScreen(navController = navController, viewModel = FoodsJourneyVisualizerViewModel())
        }
        composable(ProjectScreens.FoodsVisualizerListScreen.name){
            FoodsVisualizerListScreen(navController = navController, viewModel = FoodsVisualizerListViewModel())
        }
        composable(ProjectScreens.LevelAndFinalScreen.name){
            LevelAndFinalScreen(navController = navController, viewModel = LevelAndFinalViewModel())
        }
        composable(ProjectScreens.FoodDescriptionVisualizerScreen.name){
            FoodDescriptionVisualizerScreen(navController = navController)
        }
        composable(ProjectScreens.BadgeForUserScreen.name){
            BadgeForUserScreen(navController = navController, viewModel = BadgeForUserViewModel())
        }
        composable(ProjectScreens.ActivePausesScreen.name){
            ActivePausesScreen(navController = navController, viewModel = ActivePausesViewModel())
        }
        composable(ProjectScreens.RecoverPasswordScreen.name){
            RecoverPasswordScreen(navController = navController, viewModel = RecoverPasswordViewModel())
        }
        composable(ProjectScreens.CompareResultsScreen.name){
            CompareResultsScreen(navController = navController, viewModel = CompareResultsViewModel())
        }
    }
}