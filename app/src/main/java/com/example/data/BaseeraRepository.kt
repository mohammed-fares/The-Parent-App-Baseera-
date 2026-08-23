package com.example.data

import com.example.model.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class BaseeraRepository {

  private val _currentUser = MutableStateFlow(
    User(
      id = "parent_01",
      fullName = "د. محمد فاروق",
      email = "mf.z.hussein.pal@gmail.com",
      phone = "+20 109 876 5432",
      isPremium = true,
      selectedLanguage = "العربية"
    )
  )
  val currentUser: StateFlow<User> = _currentUser.asStateFlow()

  private val _isAuthenticated = MutableStateFlow(true)
  val isAuthenticated: StateFlow<Boolean> = _isAuthenticated.asStateFlow()

  private val _authScreen = MutableStateFlow("WELCOME") // WELCOME, LOGIN, SIGNUP, OTP, FORGOT
  val authScreen: StateFlow<String> = _authScreen.asStateFlow()

  private val _children = MutableStateFlow<List<Child>>(
    listOf(
      Child(
        id = "child_1",
        name = "أحمد",
        age = 11,
        avatarColorHex = 0xFF10B981,
        batteryLevel = 85,
        isOnline = true,
        currentApp = "YouTube",
        currentAppPackage = "com.google.android.youtube",
        riskLevel = RiskLevel.SAFE,
        locationLat = 30.0444,
        locationLng = 31.2357,
        locationName = "مدرسة النور الخاصة - القاهرة",
        appPin = "2026",
        uninstallPassword = "SECURE_GUARD_PARENT_99",
        screenTimeMinutes = 115,
        totalAllowedMinutes = 180,
        isLocked = false,
        deviceModel = "Samsung Galaxy A54 5G",
        pairingCode = "739-142",
        qrToken = "BASEERA_CHILD_AHMED_PAIR_99182"
      ),
      Child(
        id = "child_2",
        name = "سارة",
        age = 8,
        avatarColorHex = 0xFF06B6D4,
        batteryLevel = 92,
        isOnline = true,
        currentApp = "Roblox",
        currentAppPackage = "com.roblox.client",
        riskLevel = RiskLevel.WARNING,
        locationLat = 30.0520,
        locationLng = 31.2400,
        locationName = "المنزل - التجمع الخامس",
        appPin = "1234",
        uninstallPassword = "SECURE_GUARD_PARENT_99",
        screenTimeMinutes = 80,
        totalAllowedMinutes = 120,
        isLocked = false,
        deviceModel = "iPad Air 5th Gen",
        pairingCode = "481-903",
        qrToken = "BASEERA_CHILD_SARA_PAIR_88219"
      ),
      Child(
        id = "child_3",
        name = "عمر",
        age = 14,
        avatarColorHex = 0xFFF59E0B,
        batteryLevel = 42,
        isOnline = false,
        currentApp = "Snapchat",
        currentAppPackage = "com.snapchat.android",
        riskLevel = RiskLevel.DANGER,
        locationLat = 30.0610,
        locationLng = 31.2290,
        locationName = "نادي الجزيرة الرياضي",
        appPin = "7788",
        uninstallPassword = "SECURE_GUARD_PARENT_99",
        screenTimeMinutes = 195,
        totalAllowedMinutes = 150,
        isLocked = true,
        deviceModel = "Xiaomi Redmi Note 12",
        pairingCode = "619-332",
        qrToken = "BASEERA_CHILD_OMAR_PAIR_33190"
      )
    )
  )
  val children: StateFlow<List<Child>> = _children.asStateFlow()

  private val _selectedChildId = MutableStateFlow("child_1")
  val selectedChildId: StateFlow<String> = _selectedChildId.asStateFlow()

  private val _timelineLogs = MutableStateFlow<List<TimelineLog>>(
    listOf(
      TimelineLog(
        id = "log_1",
        childId = "child_1",
        childName = "أحمد",
        appName = "Snapchat",
        timestampStr = "منذ 10 دقائق",
        exactTime = "14:42",
        message = "تم حظر محادثة خاصة تحتوي على كلمات تنمر وإساءة لفظية موجهة للطفل.",
        type = LogType.INAPPROPRIATE_CONTENT,
        previewCaption = "لقطة فورية مشفرة للمحادثة مع تظليل العبارات المسيئة تلقائياً.",
        previewSnippet = "رسالة محظورة: '...أنت لا تستطيع اللعب معنا اذهب بعيداً...'",
        riskScorePercent = 88,
        isEncrypted = true,
        isFreePreview = true
      ),
      TimelineLog(
        id = "log_2",
        childId = "child_1",
        childName = "أحمد",
        appName = "خرائط وتتبع",
        timestampStr = "منذ 35 دقيقة",
        exactTime = "14:15",
        message = "غادر أحمد نطاق الأمان المحدد (مدرسة النور الخاصة) في غير الموعد المعتاد.",
        type = LogType.GEOFENCE_BREACH,
        previewCaption = "مسار الحركة اللحظي مع إحداثيات الخروج من البوابة الشرقية.",
        previewSnippet = "إحداثيات: 30.0451° N, 31.2388° E - السرعة: 4 كم/ساعة (مشياً).",
        riskScorePercent = 94,
        isEncrypted = false,
        isFreePreview = true
      ),
      TimelineLog(
        id = "log_3",
        childId = "child_2",
        childName = "سارة",
        appName = "Google Chrome",
        timestampStr = "منذ ساعة ونصف",
        exactTime = "13:20",
        message = "تم اعتراض محاولة فتح موقع إلكتروني مصنف كغير لائق أخلاقياً للأطفال.",
        type = LogType.INAPPROPRIATE_CONTENT,
        previewCaption = "صفحة الويب المحجوبة عبر جدار الحماية الفوري لبصيرة.",
        previewSnippet = "تم استبدال المحتوى بصفحة الأمان الأسرية لبصيرة.",
        riskScorePercent = 99,
        isEncrypted = true,
        isFreePreview = true
      ),
      TimelineLog(
        id = "log_4",
        childId = "child_1",
        childName = "أحمد",
        appName = "YouTube Kids",
        timestampStr = "منذ 3 ساعات",
        exactTime = "11:50",
        message = "رصد مقطع تعليمي عن فيزياء الفضاء والكون، تم تصنيف الاهتمام كشغف علمي إيجابي.",
        type = LogType.SCREEN_TIME_WARNING,
        previewCaption = "تحليل المحتوى الإيجابي: وثائقي المجموعة الشمسية والجاذبية.",
        previewSnippet = "توصية بصيرة: شجع أحمد بزيارة القبة السماوية أو إهدائه تلسكوباً بسيطاً.",
        riskScorePercent = 10,
        isEncrypted = false,
        isFreePreview = true
      ),
      TimelineLog(
        id = "log_5",
        childId = "child_3",
        childName = "عمر",
        appName = "نظام الأمان الصوتي",
        timestampStr = "أمس الساعة 18:30",
        exactTime = "18:30",
        message = "تنبيه طارئ: تم رصد أصوات صراخ واضطراب صوتي محيط بالهاتف لمدة دقيقتين متواصلتين.",
        type = LogType.SOS_EMERGENCY,
        previewCaption = "تسجيل صوتي مشفر لمحيط الهاتف للتحقق من السلامة الجسدية للطفل.",
        previewSnippet = "مستوى الديسيبل الصوتي: 85dB - تم تحديد الموقع بدقة 5 أمتار.",
        riskScorePercent = 96,
        isEncrypted = true,
        isFreePreview = false
      )
    )
  )
  val timelineLogs: StateFlow<List<TimelineLog>> = _timelineLogs.asStateFlow()

  private val _geofenceZones = MutableStateFlow<List<GeofenceZone>>(
    listOf(
      GeofenceZone("zone_1", "مدرسة النور الخاصة", 350, 0.45f, 0.40f, 0xFF10B981, isBreached = false),
      GeofenceZone("zone_2", "المنزل العائلي", 200, 0.50f, 0.65f, 0xFF06B6D4, isBreached = false),
      GeofenceZone("zone_3", "ملعب ونادي الحي", 400, 0.70f, 0.30f, 0xFFF59E0B, isBreached = true)
    )
  )
  val geofenceZones: StateFlow<List<GeofenceZone>> = _geofenceZones.asStateFlow()

  private val _cloudConfig = MutableStateFlow(
    CloudServerConfig(
      provider = AiProvider.GEMINI,
      isDefaultServer = true,
      customServerUrl = "https://ai-bridge.baseera-defense.net/v1",
      customApiKey = "sk_live_baseera_ai_sec_9942",
      isConnected = true,
      latencyMs = 180,
      isLocalProcessingEnabled = true
    )
  )
  val cloudConfig: StateFlow<CloudServerConfig> = _cloudConfig.asStateFlow()

  private val _isParentLocationShared = MutableStateFlow(true)
  val isParentLocationShared: StateFlow<Boolean> = _isParentLocationShared.asStateFlow()

  private val _externalTrackingLink = MutableStateFlow("https://baseera.app/live-track/ahmed-7739x")
  val externalTrackingLink: StateFlow<String> = _externalTrackingLink.asStateFlow()

  private val _bannerMessage = MutableStateFlow<String?>(null)
  val bannerMessage: StateFlow<String?> = _bannerMessage.asStateFlow()

  // Actions
  fun setAuthScreen(screen: String) {
    _authScreen.value = screen
  }

  fun login(emailOrPhone: String, pass: String): Boolean {
    if (emailOrPhone.isNotBlank() && pass.length >= 4) {
      _isAuthenticated.value = true
      _authScreen.value = "WELCOME"
      _bannerMessage.value = "مرحباً بك مجدداً في بصيرة! تم التحقق بنجاح."
      return true
    }
    return false
  }

  fun signUp(name: String, email: String, phone: String, pass: String): Boolean {
    if (name.isNotBlank() && email.isNotBlank()) {
      _currentUser.value = _currentUser.value.copy(fullName = name, email = email, phone = phone)
      _authScreen.value = "OTP"
      return true
    }
    return false
  }

  fun verifyOtp(code: String): Boolean {
    if (code.length == 4 || code.length == 6) {
      _isAuthenticated.value = true
      _authScreen.value = "WELCOME"
      _bannerMessage.value = "تم تفعيل حساب الوالد بنجاح وربط المنظومة!"
      return true
    }
    return false
  }

  fun logout(rememberMe: Boolean = false) {
    _isAuthenticated.value = false
    _authScreen.value = "WELCOME"
    _bannerMessage.value = "تم تسجيل الخروج وفصل الاتصال اللحظي بجهاز الطفل."
  }

  fun selectChild(childId: String) {
    _selectedChildId.value = childId
  }

  fun getSelectedChild(): Child? {
    return _children.value.find { it.id == _selectedChildId.value } ?: _children.value.firstOrNull()
  }

  fun toggleLockChild(childId: String) {
    _children.value = _children.value.map { child ->
      if (child.id == childId) {
        val newLocked = !child.isLocked
        _bannerMessage.value = if (newLocked) "🛑 تم قفل وتجميد هاتف ${child.name} فوراً!" else "🔓 تم إلغاء القفل وعودة الهاتف للعمل."
        child.copy(isLocked = newLocked)
      } else child
    }
  }

  fun triggerAlarm(childId: String) {
    _children.value = _children.value.map { child ->
      if (child.id == childId) {
        val newAlarm = !child.isAlarmTriggered
        _bannerMessage.value = if (newAlarm) "🔊 تم إطلاق صفارة الرنين القسري بأقصى صوت على هاتف ${child.name}!" else "🔕 تم إيقاف الرنين."
        child.copy(isAlarmTriggered = newAlarm)
      } else child
    }
  }

  fun extendTime(childId: String, additionalMinutes: Int = 30) {
    _children.value = _children.value.map { child ->
      if (child.id == childId) {
        _bannerMessage.value = "⏳ تم تمديد وقت الشاشة لـ ${child.name} بـ +$additionalMinutes دقيقة إضافية بنجاح!"
        child.copy(totalAllowedMinutes = child.totalAllowedMinutes + additionalMinutes)
      } else child
    }
  }

  fun addNewChild(name: String, age: Int, pin: String, uninstallPass: String): Child {
    val newId = "child_${UUID.randomUUID().toString().take(6)}"
    val newPairCode = "${(100..999).random()}-${(100..999).random()}"
    val newChild = Child(
      id = newId,
      name = name,
      age = age,
      avatarColorHex = listOf(0xFF10B981, 0xFF06B6D4, 0xFFF59E0B, 0xFF8B5CF6, 0xFFEC4899).random(),
      batteryLevel = (75..100).random(),
      isOnline = true,
      currentApp = "الشاشة الرئيسية",
      currentAppPackage = "com.android.launcher",
      riskLevel = RiskLevel.SAFE,
      locationLat = 30.0444,
      locationLng = 31.2357,
      locationName = "المنزل - قيد التحديث",
      appPin = pin.ifBlank { "1234" },
      uninstallPassword = uninstallPass.ifBlank { "BASEERA_SEC_2026" },
      screenTimeMinutes = 0,
      totalAllowedMinutes = 120,
      pairingCode = newPairCode,
      qrToken = "BASEERA_CHILD_${name.uppercase()}_TOKEN_${(10000..99999).random()}"
    )
    _children.value = _children.value + newChild
    _selectedChildId.value = newId
    _bannerMessage.value = "🎉 تم إنشاء ملف الطفل ${name} وجاهز للربط عبر QR Code أو كود الربط: $newPairCode"
    return newChild
  }

  fun toggleParentLocationSharing() {
    val next = !_isParentLocationShared.value
    _isParentLocationShared.value = next
    _bannerMessage.value = if (next) "📍 تم تفعيل مشاركة موقعك اللحظي مع أطفالك." else "🔒 تم إيقاف مشاركة موقعك."
  }

  fun generateNewExternalTrackingLink(): String {
    val code = (1000..9999).random()
    val child = getSelectedChild()
    val link = "https://baseera.app/live-track/${child?.name?.lowercase() ?: "child"}-$code"
    _externalTrackingLink.value = link
    _bannerMessage.value = "🔗 تم إنشاء رابط تتبع عائلي مباشر جديد وتحديث الرابط المشفر."
    return link
  }

  fun updateCloudConfig(config: CloudServerConfig) {
    _cloudConfig.value = config
    _bannerMessage.value = "⚡ تم حفظ إعدادات خادم ومزود الذكاء الاصطناعي (${config.provider.title})."
  }

  fun testAiLatency() {
    val currentProvider = _cloudConfig.value.provider
    val jitter = (-20..25).random()
    val newLatency = (currentProvider.defaultLatencyMs + jitter).coerceAtLeast(40)
    _cloudConfig.value = _cloudConfig.value.copy(latencyMs = newLatency, isConnected = true)
    _bannerMessage.value = "🚀 تم فحص سرعة الاستجابة بنجاح: ${newLatency} مللي ثانية - ممتاز جداً!"
  }

  fun toggleLocalProcessing() {
    val current = _cloudConfig.value.isLocalProcessingEnabled
    _cloudConfig.value = _cloudConfig.value.copy(isLocalProcessingEnabled = !current)
    _bannerMessage.value = if (!current) "📶 تم تفعيل المعالجة المحلية لتوفير باقة الهاتف." else "☁️ تم تحويل المعالجة للسحابة بالكامل."
  }

  fun togglePremium(isPremium: Boolean) {
    _currentUser.value = _currentUser.value.copy(isPremium = isPremium)
    _bannerMessage.value = if (isPremium) "👑 تم ترقية الحساب إلى VIP الذهبي! تم فتح الخرائط الفورية وسجل اللقطات." else "تم ضبط الخطة للمجانية."
  }

  fun switchLanguage(lang: String) {
    _currentUser.value = _currentUser.value.copy(selectedLanguage = lang)
    _bannerMessage.value = "🌐 تم تغيير لغة التطبيق إلى: $lang"
  }

  fun clearBannerMessage() {
    _bannerMessage.value = null
  }
}
