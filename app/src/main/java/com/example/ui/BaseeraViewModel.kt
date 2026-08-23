package com.example.ui

import androidx.lifecycle.ViewModel
import com.example.data.BaseeraRepository
import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class BaseeraViewModel(
  val repository: BaseeraRepository = BaseeraRepository()
) : ViewModel() {

  private val _currentTab = MutableStateFlow(MainTab.TIMELINE)
  val currentTab: StateFlow<MainTab> = _currentTab.asStateFlow()

  // Selected Log for Scene Preview Modal
  private val _previewLog = MutableStateFlow<TimelineLog?>(null)
  val previewLog: StateFlow<TimelineLog?> = _previewLog.asStateFlow()

  // Selected Payment Method for Payment Gateway UI
  private val _selectedPaymentMethod = MutableStateFlow<PaymentMethodType?>(null)
  val selectedPaymentMethod: StateFlow<PaymentMethodType?> = _selectedPaymentMethod.asStateFlow()

  // Dialog states
  private val _showAddChildDialog = MutableStateFlow(false)
  val showAddChildDialog: StateFlow<Boolean> = _showAddChildDialog.asStateFlow()

  private val _showLogoutDialog = MutableStateFlow(false)
  val showLogoutDialog: StateFlow<Boolean> = _showLogoutDialog.asStateFlow()

  private val _showUpgradeDialog = MutableStateFlow(false)
  val showUpgradeDialog: StateFlow<Boolean> = _showUpgradeDialog.asStateFlow()

  private val _showPairingCodeModal = MutableStateFlow<Child?>(null)
  val showPairingCodeModal: StateFlow<Child?> = _showPairingCodeModal.asStateFlow()

  // Filter for timeline logs
  private val _selectedLogFilter = MutableStateFlow<LogType?>(null)
  val selectedLogFilter: StateFlow<LogType?> = _selectedLogFilter.asStateFlow()

  fun selectTab(tab: MainTab) {
    _currentTab.value = tab
  }

  fun openLogPreview(log: TimelineLog) {
    _previewLog.value = log
  }

  fun closeLogPreview() {
    _previewLog.value = null
  }

  fun openPaymentGateway(method: PaymentMethodType) {
    _selectedPaymentMethod.value = method
  }

  fun closePaymentGateway() {
    _selectedPaymentMethod.value = null
  }

  fun setAddChildDialogVisible(visible: Boolean) {
    _showAddChildDialog.value = visible
  }

  fun setLogoutDialogVisible(visible: Boolean) {
    _showLogoutDialog.value = visible
  }

  fun setUpgradeDialogVisible(visible: Boolean) {
    _showUpgradeDialog.value = visible
  }

  fun showPairingModal(child: Child) {
    _showPairingCodeModal.value = child
  }

  fun closePairingModal() {
    _showPairingCodeModal.value = null
  }

  fun setLogFilter(type: LogType?) {
    _selectedLogFilter.value = type
  }
}
