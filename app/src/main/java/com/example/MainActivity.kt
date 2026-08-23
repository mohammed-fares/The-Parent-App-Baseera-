package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.model.MainTab
import com.example.ui.BaseeraViewModel
import com.example.ui.components.BaseeraBottomBar
import com.example.ui.components.BaseeraTopBar
import com.example.ui.screens.*
import com.example.ui.theme.BaseeraNavyDark
import com.example.ui.theme.MyApplicationTheme

class MainActivity : ComponentActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContent {
      MyApplicationTheme(darkTheme = true) {
        val viewModel: BaseeraViewModel = viewModel()
        val repository = viewModel.repository

        val isAuthenticated by repository.isAuthenticated.collectAsStateWithLifecycle()
        val authScreen by repository.authScreen.collectAsStateWithLifecycle()
        val currentUser by repository.currentUser.collectAsStateWithLifecycle()
        val children by repository.children.collectAsStateWithLifecycle()
        val selectedChildId by repository.selectedChildId.collectAsStateWithLifecycle()
        val timelineLogs by repository.timelineLogs.collectAsStateWithLifecycle()
        val geofenceZones by repository.geofenceZones.collectAsStateWithLifecycle()
        val cloudConfig by repository.cloudConfig.collectAsStateWithLifecycle()
        val isParentLocationShared by repository.isParentLocationShared.collectAsStateWithLifecycle()
        val externalTrackingLink by repository.externalTrackingLink.collectAsStateWithLifecycle()
        val bannerMessage by repository.bannerMessage.collectAsStateWithLifecycle()

        val currentTab by viewModel.currentTab.collectAsStateWithLifecycle()
        val previewLog by viewModel.previewLog.collectAsStateWithLifecycle()
        val selectedPaymentMethod by viewModel.selectedPaymentMethod.collectAsStateWithLifecycle()
        val showAddChildDialog by viewModel.showAddChildDialog.collectAsStateWithLifecycle()
        val showLogoutDialog by viewModel.showLogoutDialog.collectAsStateWithLifecycle()
        val showUpgradeDialog by viewModel.showUpgradeDialog.collectAsStateWithLifecycle()
        val pairingModalChild by viewModel.showPairingCodeModal.collectAsStateWithLifecycle()
        val selectedLogFilter by viewModel.selectedLogFilter.collectAsStateWithLifecycle()

        val snackbarHostState = remember { SnackbarHostState() }

        LaunchedEffect(bannerMessage) {
          bannerMessage?.let { msg ->
            snackbarHostState.showSnackbar(
              message = msg,
              duration = SnackbarDuration.Short
            )
            repository.clearBannerMessage()
          }
        }

        val selectedChild = children.find { it.id == selectedChildId } ?: children.firstOrNull()

        if (!isAuthenticated) {
          // Authentication Flows
          AnimatedContent(
            targetState = authScreen,
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "auth_flow"
          ) { screen ->
            when (screen) {
              "WELCOME" -> WelcomeScreen(
                onNavigateLogin = { repository.setAuthScreen("LOGIN") },
                onNavigateSignUp = { repository.setAuthScreen("SIGNUP") },
                onSocialLogin = { provider ->
                  repository.login("social_parent@baseera.net", "123456")
                }
              )
              "SIGNUP" -> SignUpScreen(
                onSignUpSubmitted = { name, email, phone, pass ->
                  repository.signUp(name, email, phone, pass)
                },
                onNavigateBack = { repository.setAuthScreen("WELCOME") },
                onNavigateLogin = { repository.setAuthScreen("LOGIN") }
              )
              "LOGIN" -> LoginScreen(
                onLoginSubmitted = { email, pass ->
                  repository.login(email, pass)
                },
                onNavigateBack = { repository.setAuthScreen("WELCOME") },
                onNavigateSignUp = { repository.setAuthScreen("SIGNUP") },
                onNavigateForgotPassword = { repository.setAuthScreen("FORGOT") },
                onSocialLogin = { repository.login("social_parent@baseera.net", "123456") }
              )
              "OTP" -> OtpVerificationScreen(
                onOtpVerified = { code ->
                  repository.verifyOtp(code)
                },
                onNavigateBack = { repository.setAuthScreen("SIGNUP") }
              )
              "FORGOT" -> ForgotPasswordScreen(
                onResetRequested = {
                  repository.setAuthScreen("OTP")
                },
                onNavigateBack = { repository.setAuthScreen("LOGIN") }
              )
            }
          }
        } else {
          // Main Parent Dashboard with 6 Screens
          Scaffold(
            topBar = {
              BaseeraTopBar(
                user = currentUser,
                children = children,
                selectedChild = selectedChild,
                onSelectChild = { childId -> repository.selectChild(childId) },
                onOpenUpgrade = { viewModel.selectTab(MainTab.SETTINGS) }
              )
            },
            bottomBar = {
              BaseeraBottomBar(
                currentTab = currentTab,
                onTabSelected = { tab -> viewModel.selectTab(tab) }
              )
            },
            snackbarHost = {
              SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.padding(16.dp)
              )
            },
            modifier = Modifier
              .fillMaxSize()
              .background(BaseeraNavyDark)
              .testTag("baseera_parent_app_scaffold")
          ) { innerPadding ->
            Box(
              modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(BaseeraNavyDark)
            ) {
              when (currentTab) {
                // Page 1: سجل التنبيهات والأحداث
                MainTab.TIMELINE -> TimelineLogsScreen(
                  logs = timelineLogs,
                  selectedFilter = selectedLogFilter,
                  onFilterSelected = { filter -> viewModel.setLogFilter(filter) },
                  previewLog = previewLog,
                  onOpenPreview = { log -> viewModel.openLogPreview(log) },
                  onClosePreview = { viewModel.closeLogPreview() },
                  isPremium = currentUser.isPremium,
                  onOpenUpgrade = { viewModel.selectTab(MainTab.SETTINGS) }
                )

                // Page 2: الرصد المباشر والخريطة والأوامر الفورية
                MainTab.LIVE_HUB -> LiveHubScreen(
                  child = selectedChild,
                  geofenceZones = geofenceZones,
                  isPremium = currentUser.isPremium,
                  onToggleLock = { id -> repository.toggleLockChild(id) },
                  onTriggerAlarm = { id -> repository.triggerAlarm(id) },
                  onExtendTime = { id -> repository.extendTime(id, 30) },
                  onOpenUpgrade = { viewModel.selectTab(MainTab.SETTINGS) }
                )

                // Page 3: تحليلات ومواهب
                MainTab.AI_INSIGHTS -> AiInsightsScreen(
                  child = selectedChild
                )

                // Page 4: إدارة الأطفال وربط الأجهزة
                MainTab.CHILDREN -> ChildManagementScreen(
                  children = children,
                  selectedChildId = selectedChildId,
                  onSelectChild = { id -> repository.selectChild(id) },
                  onOpenAddChildDialog = { viewModel.setAddChildDialogVisible(true) },
                  showAddChildDialog = showAddChildDialog,
                  onCloseAddChildDialog = { viewModel.setAddChildDialogVisible(false) },
                  onAddNewChild = { name, age, pin, uninstallPass ->
                    repository.addNewChild(name, age, pin, uninstallPass)
                  },
                  pairingModalChild = pairingModalChild,
                  onOpenPairingModal = { child -> viewModel.showPairingModal(child) },
                  onClosePairingModal = { viewModel.closePairingModal() },
                  isParentLocationShared = isParentLocationShared,
                  onToggleParentLocationShare = { repository.toggleParentLocationSharing() },
                  externalTrackingLink = externalTrackingLink,
                  onGenerateNewTrackingLink = { repository.generateNewExternalTrackingLink() }
                )

                // Page 5: بوابة AI السحابية
                MainTab.CLOUD_BRIDGE -> CloudBridgeScreen(
                  config = cloudConfig,
                  onUpdateConfig = { cfg -> repository.updateCloudConfig(cfg) },
                  onTestLatency = { repository.testAiLatency() },
                  onToggleLocalProcessing = { repository.toggleLocalProcessing() }
                )

                // Page 6: الإعدادات والاشتراك
                MainTab.SETTINGS -> SettingsPremiumScreen(
                  user = currentUser,
                  onOpenPaymentGateway = { method -> viewModel.openPaymentGateway(method) },
                  selectedPaymentMethod = selectedPaymentMethod,
                  onClosePaymentGateway = { viewModel.closePaymentGateway() },
                  onSwitchLanguage = { lang -> repository.switchLanguage(lang) },
                  onOpenLogoutDialog = { viewModel.setLogoutDialogVisible(true) },
                  showLogoutDialog = showLogoutDialog,
                  onCloseLogoutDialog = { viewModel.setLogoutDialogVisible(false) },
                  onConfirmLogout = { rememberDevice -> repository.logout(rememberDevice) },
                  onTogglePremium = { isPrem -> repository.togglePremium(isPrem) }
                )
              }
            }
          }
        }
      }
    }
  }
}
