package com.example.model

enum class RiskLevel(val labelAr: String, val labelEn: String) {
  SAFE("آمن", "Safe"),
  WARNING("تنبيه", "Warning"),
  DANGER("مخالفة / خطر", "Danger")
}

enum class LogType(val labelAr: String, val icon: String) {
  INAPPROPRIATE_CONTENT("محتوى محظور أخلاقياً", "🚫"),
  GEOFENCE_BREACH("تنبيه جغرافي", "📍"),
  SOS_EMERGENCY("تنبيه طارئ", "🚨"),
  SCREEN_TIME_WARNING("تجاوز الوقت", "⏱️")
}

enum class AiProvider(val title: String, val modelName: String, val defaultLatencyMs: Int) {
  GEMINI("Google Gemini (افتراضي)", "Gemini 2.5 Flash / Pro", 140),
  OPENAI("OpenAI GPT", "GPT-4o Mini", 210),
  CLAUDE("Anthropic Claude", "Claude 3.7 Sonnet", 195),
  LOCAL_CUSTOM("سيرفر محلي مخصص (On-Prem)", "Custom Llama 3 / Ollama", 85)
}

enum class PaymentMethodType(val titleAr: String, val subtitleAr: String, val icon: String) {
  VODAFONE_CASH("فودافون كاش (Vodafone Cash)", "تحويل فوري لرقم الحساب الرسمي", "📱"),
  INSTAPAY_WALLET("المحفظة الإلكترونية / إنستاباي", "تحويل عبر InstaPay أو QR Code", "💳"),
  BANK_CARD("بطاقة البنك (Visa / MasterCard)", "دفع مشفر وآمن عبر البوابة البنكية", "🔒"),
  INSTAGRAM_SUPPORT("الدعم عبر إنستجرام / تواصل مباشر", "متابعة وإتمام الاشتراك مع فريق الدعم", "💬")
}

data class User(
  val id: String = "parent_01",
  val fullName: String = "د. محمد فاروق",
  val email: String = "mf.z.hussein.pal@gmail.com",
  val phone: String = "+20 109 876 5432",
  val isPremium: Boolean = true,
  val selectedLanguage: String = "العربية"
)

data class Child(
  val id: String,
  val name: String,
  val age: Int,
  val avatarColorHex: Long,
  val batteryLevel: Int,
  val isOnline: Boolean,
  val currentApp: String,
  val currentAppPackage: String,
  val riskLevel: RiskLevel,
  val locationLat: Double,
  val locationLng: Double,
  val locationName: String,
  val appPin: String,
  val uninstallPassword: String,
  val screenTimeMinutes: Int,
  val totalAllowedMinutes: Int,
  val isLocked: Boolean = false,
  val isAlarmTriggered: Boolean = false,
  val deviceModel: String = "Samsung Galaxy A54",
  val pairingCode: String = "849-215",
  val qrToken: String = "BASEERA_CHILD_AUTH_token_ahmed_99128"
)

data class TimelineLog(
  val id: String,
  val childId: String,
  val childName: String,
  val appName: String,
  val timestampStr: String,
  val exactTime: String,
  val message: String,
  val type: LogType,
  val previewCaption: String,
  val previewSnippet: String,
  val riskScorePercent: Int,
  val isEncrypted: Boolean = true,
  val isFreePreview: Boolean = true
)

data class GeofenceZone(
  val id: String,
  val name: String,
  val radiusMeters: Int,
  val latOffsetPercentX: Float,
  val latOffsetPercentY: Float,
  val colorHex: Long,
  val isBreached: Boolean = false
)

data class TalentCategory(
  val name: String,
  val percentage: Int,
  val colorHex: Long,
  val hoursSpent: Float,
  val categoryType: String
)

data class MoodAnalysis(
  val overallMood: String,
  val scoreOutOf100: Int,
  val stressLevelText: String,
  val socialStateText: String,
  val talentRecommendation: String,
  val psychologicalAdvice: String
)

data class CloudServerConfig(
  val provider: AiProvider = AiProvider.GEMINI,
  val isDefaultServer: Boolean = true,
  val customServerUrl: String = "https://ai-bridge.baseera-defense.net/v1",
  val customApiKey: String = "sk_live_baseera_ai_sec_9942",
  val isConnected: Boolean = true,
  val latencyMs: Int = 180,
  val isLocalProcessingEnabled: Boolean = true
)

enum class MainTab(val titleAr: String, val titleEn: String) {
  TIMELINE("السجل", "Timeline"),
  LIVE_HUB("الرصد المباشر", "Live Hub"),
  AI_INSIGHTS("التحليلات", "AI Insights"),
  CHILDREN("الأطفال", "Children"),
  CLOUD_BRIDGE("السحابة", "Cloud AI"),
  SETTINGS("الإعدادات", "Settings")
}
