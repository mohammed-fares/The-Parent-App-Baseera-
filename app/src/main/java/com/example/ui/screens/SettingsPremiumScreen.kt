package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.model.PaymentMethodType
import com.example.model.User
import com.example.ui.theme.*

@Composable
fun SettingsPremiumScreen(
  user: User,
  onOpenPaymentGateway: (PaymentMethodType) -> Unit,
  selectedPaymentMethod: PaymentMethodType?,
  onClosePaymentGateway: () -> Unit,
  onSwitchLanguage: (String) -> Unit,
  onOpenLogoutDialog: () -> Unit,
  showLogoutDialog: Boolean,
  onCloseLogoutDialog: () -> Unit,
  onConfirmLogout: (Boolean) -> Unit,
  onTogglePremium: (Boolean) -> Unit,
  modifier: Modifier = Modifier
) {
  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // 1. Golden VIP Subscription Bento Card (بطاقة الترقية الذهبية)
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BaseeraGoldContainer),
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraGold),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("golden_vip_card")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(Color(0xFF4A3403), Color(0xFF1E1402))
            )
          )
          .padding(18.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(BaseeraGold)
                .border(1.dp, BaseeraGoldLight, RoundedCornerShape(14.dp))
            ) {
              Text("👑", fontSize = 24.sp)
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "باقة الحماية الأسرية الشاملة (VIP)",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = BaseeraGoldLight,
                  fontSize = 15.sp
                )
              )
              Text(
                text = if (user.isPremium) "الاشتراك نشط • صالح حتى أغسطس 2027" else "النسخة المجانية الحالية",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary, fontSize = 11.sp)
              )
            }
          }

          Switch(
            checked = user.isPremium,
            onCheckedChange = { onTogglePremium(it) },
            colors = SwitchDefaults.colors(
              checkedThumbColor = BaseeraGoldLight,
              checkedTrackColor = BaseeraGoldDark
            ),
            modifier = Modifier.testTag("premium_status_toggle")
          )
        }

        Spacer(modifier = Modifier.height(16.dp))

        // VIP Highlights
        Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
          VipFeatureRow(icon = "📍", text = "فتح الخريطة التفاعلية الحية والتتبع الجغرافي اللحظي")
          VipFeatureRow(icon = "👁️", text = "معاينة اللقطات المشفرة بدون قيود وبجودة كاملة")
          VipFeatureRow(icon = "⚡", text = "سرعة معالجة فورية عبر محرك Gemini 2.5 Pro")
          VipFeatureRow(icon = "🛡️", text = "حماية غير محدودة لجميع أفراد الأسرة وأجهزتهم")
        }
      }
    }

    // 2. Integrated Payment Gateways Section (بوابة الدفع المدمجة) - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(18.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
              .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
          ) {
            Icon(Icons.Default.Payment, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "بوابات الدفع الإلكتروني المعتمدة",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 15.sp
              )
            )
            Text(
              text = "طرق سداد مرنة ومباشرة للسوق المصرية والعالمية",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        PaymentMethodType.values().forEach { method ->
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = BentoTileGlow,
            border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .clickable { onOpenPaymentGateway(method) }
              .testTag("payment_method_${method.name}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.padding(14.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text(text = method.icon, fontSize = 22.sp)
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                  Text(
                    text = method.titleAr,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = BaseeraTextPrimary
                    )
                  )
                  Text(
                    text = method.subtitleAr,
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 11.sp,
                      color = BaseeraTextSecondary
                    )
                  )
                }
              }

              Icon(
                imageVector = Icons.Default.ArrowForwardIos,
                contentDescription = null,
                tint = BaseeraCyanLight,
                modifier = Modifier.size(14.dp)
              )
            }
          }
        }
      }
    }

    // 3. Language Selector Section (محدد لغة التطبيق يدوياً) - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(18.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraCyan.copy(alpha = 0.2f))
              .border(1.dp, BaseeraCyanLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
          ) {
            Text("🌐", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "لغة التطبيق (Language Selection)",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary,
              fontSize = 15.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          val isAr = user.selectedLanguage == "العربية"
          Button(
            onClick = { onSwitchLanguage("العربية") },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (isAr) BaseeraEmerald else BentoTileGlow,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            border = if (!isAr) androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder) else null,
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("lang_arabic_btn")
          ) {
            Text("🇪🇬 العربية (افتراضي)", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }

          Button(
            onClick = { onSwitchLanguage("English") },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (!isAr) BaseeraEmerald else BentoTileGlow,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(14.dp),
            border = if (isAr) androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder) else null,
            modifier = Modifier
              .weight(1f)
              .height(44.dp)
              .testTag("lang_english_btn")
          ) {
            Text("🇺🇸 English", fontWeight = FontWeight.Bold, fontSize = 12.sp)
          }
        }
      }
    }

    // 4. Security & Account Actions - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(18.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(34.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
              .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
          ) {
            Icon(Icons.Default.Security, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "إعدادات الأمان والحساب",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary,
              fontSize = 15.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        Surface(
          shape = RoundedCornerShape(16.dp),
          color = BentoTileGlow,
          border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.padding(14.dp)
          ) {
            Column {
              Text("المصادقة الثنائية (2FA)", style = MaterialTheme.typography.bodyMedium.copy(color = BaseeraTextPrimary, fontWeight = FontWeight.SemiBold))
              Text("تأكيد الدخول عبر OTP دائماً", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp))
            }
            var twoFaEnabled by remember { mutableStateOf(true) }
            Switch(
              checked = twoFaEnabled,
              onCheckedChange = { twoFaEnabled = it },
              colors = SwitchDefaults.colors(
                checkedThumbColor = BaseeraEmeraldLight,
                checkedTrackColor = BaseeraEmeraldDark
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Logout Button
        Button(
          onClick = onOpenLogoutDialog,
          colors = ButtonDefaults.buttonColors(
            containerColor = BaseeraRedDark.copy(alpha = 0.3f),
            contentColor = BaseeraRed
          ),
          shape = RoundedCornerShape(16.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .border(1.dp, BaseeraRed.copy(alpha = 0.6f), RoundedCornerShape(16.dp))
            .testTag("open_logout_dialog_btn")
        ) {
          Icon(Icons.Default.ExitToApp, contentDescription = null)
          Spacer(modifier = Modifier.width(8.dp))
          Text("تسجيل الخروج وقطع الاتصال", fontWeight = FontWeight.Bold, fontSize = 13.sp)
        }
      }
    }
  }

  // Payment Method Dialog
  selectedPaymentMethod?.let { method ->
    PaymentDetailsDialog(
      method = method,
      onDismiss = onClosePaymentGateway
    )
  }

  // Logout Confirmation Dialog (مع خيار تذكرني على هذا الجهاز)
  if (showLogoutDialog) {
    LogoutConfirmDialog(
      onDismiss = onCloseLogoutDialog,
      onConfirm = onConfirmLogout
    )
  }
}

@Composable
fun VipFeatureRow(icon: String, text: String) {
  Row(verticalAlignment = Alignment.CenterVertically) {
    Text(text = icon, fontSize = 14.sp)
    Spacer(modifier = Modifier.width(8.dp))
    Text(
      text = text,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = FontWeight.Medium,
        color = BaseeraTextPrimary
      )
    )
  }
}

@Composable
fun PaymentDetailsDialog(
  method: PaymentMethodType,
  onDismiss: () -> Unit
) {
  var isReceiptAttached by remember { mutableStateOf(false) }
  var referenceNumber by remember { mutableStateOf("") }
  var cardNumber by remember { mutableStateOf("4532 8901 2345 6789") }
  var cardExpiry by remember { mutableStateOf("09/28") }
  var cardCvv by remember { mutableStateOf("842") }
  var isSubmitted by remember { mutableStateOf(false) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(22.dp),
      color = BaseeraNavySurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraGold),
      modifier = Modifier
        .fillMaxWidth()
        .padding(6.dp)
        .testTag("payment_details_dialog")
    ) {
      Column(
        modifier = Modifier
          .padding(20.dp)
          .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = method.icon, fontSize = 24.sp)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = method.titleAr,
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary
              )
            )
          }
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "إغلاق", tint = BaseeraTextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        if (isSubmitted) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(60.dp)
              .clip(CircleShape)
              .background(BaseeraEmeraldDark.copy(alpha = 0.2f))
              .border(1.dp, BaseeraEmerald, CircleShape)
          ) {
            Icon(Icons.Default.Check, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(32.dp))
          }
          Spacer(modifier = Modifier.height(12.dp))
          Text(
            text = "تم استلام طلب السداد بنجاح!",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraEmeraldLight
            )
          )
          Spacer(modifier = Modifier.height(6.dp))
          Text(
            text = "سيقوم فريق دعم بصيرة بمراجعة الإيصال وتفعيل باقة VIP لحسابك خلال دقائق معدودة.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BaseeraTextSecondary,
              textAlign = TextAlign.Center
            )
          )
          Spacer(modifier = Modifier.height(16.dp))
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = BaseeraEmerald),
            modifier = Modifier.fillMaxWidth()
          ) {
            Text("حسناً، عودة", fontWeight = FontWeight.Bold)
          }
        } else {
          when (method) {
            PaymentMethodType.VODAFONE_CASH -> {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BaseeraNavyDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraRed.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text("📱 رقم محفظة فودافون كاش الرسمية:", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary))
                  Spacer(modifier = Modifier.height(4.dp))
                  Text("010 9876 5432", style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold, color = BaseeraRed))
                  Spacer(modifier = Modifier.height(8.dp))
                  Text("خطوات التحويل:\n1. اطلب *9*7*الرقم*المبلغ#\n2. احتفظ بلقطة شاشة لرسالة التحويل.\n3. أرفق الإيصال بالأسفل لتأكيد التفعيل.", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary, lineHeight = 18.sp))
                }
              }
            }
            PaymentMethodType.INSTAPAY_WALLET -> {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BaseeraNavyDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraCyan.copy(alpha = 0.5f)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text("💳 عنوان إنستاباي الرسمي (InstaPay Alias):", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary))
                  Spacer(modifier = Modifier.height(4.dp))
                  Text("baseera.defense@instapay", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BaseeraCyanLight))
                  Spacer(modifier = Modifier.height(8.dp))
                  Text("التحويل الفوري من أي بنك مصري أو محفظة إلكترونية على مدار 24 ساعة.", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary))
                }
              }
            }
            PaymentMethodType.BANK_CARD -> {
              Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                  value = cardNumber,
                  onValueChange = { cardNumber = it },
                  label = { Text("رقم البطاقة (16 رقم)") },
                  leadingIcon = { Icon(Icons.Default.CreditCard, contentDescription = null, tint = BaseeraEmeraldLight) },
                  singleLine = true,
                  colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = BaseeraEmerald,
                    unfocusedBorderColor = BaseeraNavyBorder,
                    focusedContainerColor = BaseeraNavyCard,
                    unfocusedContainerColor = BaseeraNavyCard
                  ),
                  shape = RoundedCornerShape(12.dp),
                  modifier = Modifier.fillMaxWidth()
                )

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                  OutlinedTextField(
                    value = cardExpiry,
                    onValueChange = { cardExpiry = it },
                    label = { Text("تاريخ الانتهاء (MM/YY)") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = BaseeraEmerald,
                      unfocusedBorderColor = BaseeraNavyBorder,
                      focusedContainerColor = BaseeraNavyCard,
                      unfocusedContainerColor = BaseeraNavyCard
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                  )

                  OutlinedTextField(
                    value = cardCvv,
                    onValueChange = { cardCvv = it },
                    label = { Text("CVV") },
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                      focusedBorderColor = BaseeraEmerald,
                      unfocusedBorderColor = BaseeraNavyBorder,
                      focusedContainerColor = BaseeraNavyCard,
                      unfocusedContainerColor = BaseeraNavyCard
                    ),
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.weight(1f)
                  )
                }
              }
            }
            PaymentMethodType.INSTAGRAM_SUPPORT -> {
              Surface(
                shape = RoundedCornerShape(12.dp),
                color = BaseeraNavyDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraGold),
                modifier = Modifier.fillMaxWidth()
              ) {
                Column(modifier = Modifier.padding(14.dp)) {
                  Text("📸 حساب إنستجرام الرسمي:", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary))
                  Text("@BaseeraDefenseApp", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold, color = BaseeraGoldLight))
                  Spacer(modifier = Modifier.height(8.dp))
                  Text("يمكنك إرسال استفسارك أو طلب تفعيل الباقة السنوية مباشرة عبر رسائل الدايركت أو واتساب الدعم الفني.", style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary))
                }
              }
            }
          }

          Spacer(modifier = Modifier.height(14.dp))

          // Receipt Attachment & Reference Box
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = BaseeraNavyCard,
            border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraNavyBorder),
            modifier = Modifier
              .fillMaxWidth()
              .clickable { isReceiptAttached = !isReceiptAttached }
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(12.dp)
            ) {
              Icon(
                imageVector = if (isReceiptAttached) Icons.Default.CheckCircle else Icons.Default.AttachFile,
                contentDescription = null,
                tint = if (isReceiptAttached) BaseeraEmeraldLight else BaseeraCyanLight
              )
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = if (isReceiptAttached) "تم إرفاق إيصال الدفع بنجاح ✅" else "إرفاق إيصال أو صورة التحويل 📎",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = if (isReceiptAttached) BaseeraEmeraldLight else BaseeraTextPrimary
                  )
                )
                Text(
                  text = "انقر لتحديد صورة الإيصال لمراجعتها",
                  style = MaterialTheme.typography.bodySmall.copy(fontSize = 10.sp, color = BaseeraTextSecondary)
                )
              }
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          Button(
            onClick = { isSubmitted = true },
            colors = ButtonDefaults.buttonColors(containerColor = BaseeraGold),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier
              .fillMaxWidth()
              .height(48.dp)
              .testTag("submit_payment_btn")
          ) {
            Text("تأكيد السداد وتفعيل الباقة 👑", color = Color.Black, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}

@Composable
fun LogoutConfirmDialog(
  onDismiss: () -> Unit,
  onConfirm: (Boolean) -> Unit
) {
  var rememberDevice by remember { mutableStateOf(true) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = BaseeraNavySurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraRed),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("logout_dialog")
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Box(
          contentAlignment = Alignment.Center,
          modifier = Modifier
            .size(54.dp)
            .clip(CircleShape)
            .background(BaseeraRedContainer)
        ) {
          Icon(Icons.Default.Warning, contentDescription = null, tint = BaseeraRed, modifier = Modifier.size(30.dp))
        }

        Spacer(modifier = Modifier.height(14.dp))

        Text(
          text = "تأكيد تسجيل الخروج",
          style = MaterialTheme.typography.titleMedium.copy(
            fontWeight = FontWeight.Bold,
            color = BaseeraTextPrimary
          )
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
          text = "تنبيه: عند تسجيل الخروج، سيتم قطع الاتصال الفوري بين تطبيق الوالد وتطبيق الطفل حتى إعادة تسجيل الدخول وربط الحساب.",
          style = MaterialTheme.typography.bodySmall.copy(
            color = BaseeraTextSecondary,
            textAlign = TextAlign.Center,
            lineHeight = 18.sp
          )
        )

        Spacer(modifier = Modifier.height(14.dp))

        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.clickable { rememberDevice = !rememberDevice }
        ) {
          Checkbox(
            checked = rememberDevice,
            onCheckedChange = { rememberDevice = it },
            colors = CheckboxDefaults.colors(checkedColor = BaseeraEmerald)
          )
          Spacer(modifier = Modifier.width(6.dp))
          Text(
            text = "تذكرني على هذا الجهاز",
            style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextPrimary)
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          Button(
            onClick = onDismiss,
            colors = ButtonDefaults.buttonColors(containerColor = BaseeraNavyCard),
            modifier = Modifier.weight(1f)
          ) {
            Text("إلغاء", color = BaseeraTextPrimary)
          }

          Button(
            onClick = {
              onDismiss()
              onConfirm(rememberDevice)
            },
            colors = ButtonDefaults.buttonColors(containerColor = BaseeraRed),
            modifier = Modifier
              .weight(1.3f)
              .testTag("confirm_logout_btn")
          ) {
            Text("تأكيد الخروج", color = Color.White, fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
