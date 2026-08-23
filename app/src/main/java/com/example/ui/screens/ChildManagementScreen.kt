package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.model.Child
import com.example.ui.components.QrCodeGeneratorCanvas
import com.example.ui.theme.*

@Composable
fun ChildManagementScreen(
  children: List<Child>,
  selectedChildId: String,
  onSelectChild: (String) -> Unit,
  onOpenAddChildDialog: () -> Unit,
  showAddChildDialog: Boolean,
  onCloseAddChildDialog: () -> Unit,
  onAddNewChild: (String, Int, String, String) -> Unit,
  pairingModalChild: Child?,
  onOpenPairingModal: (Child) -> Unit,
  onClosePairingModal: () -> Unit,
  isParentLocationShared: Boolean,
  onToggleParentLocationShare: () -> Unit,
  externalTrackingLink: String,
  onGenerateNewTrackingLink: () -> Unit,
  modifier: Modifier = Modifier
) {
  LazyColumn(
    contentPadding = PaddingValues(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
  ) {
    // Header & Add Child CTA Bento Card
    item {
      Card(
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
        modifier = Modifier.fillMaxWidth()
      ) {
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(BentoTileElevated, BentoTileSurface)
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
                  .size(48.dp)
                  .clip(RoundedCornerShape(14.dp))
                  .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                  .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
              ) {
                Text("👶", fontSize = 24.sp)
              }
              Spacer(modifier = Modifier.width(12.dp))
              Column {
                Text(
                  text = "إدارة الأطفال والأجهزة المرتبطة",
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BaseeraTextPrimary,
                    fontSize = 15.sp
                  )
                )
                Text(
                  text = "${children.size} أجهزة مسجلة في شبكة العائلة",
                  style = MaterialTheme.typography.bodySmall.copy(color = BaseeraCyanLight, fontSize = 11.sp)
                )
              }
            }

            Button(
              onClick = onOpenAddChildDialog,
              colors = ButtonDefaults.buttonColors(
                containerColor = BaseeraEmerald,
                contentColor = Color.White
              ),
              shape = RoundedCornerShape(14.dp),
              contentPadding = PaddingValues(horizontal = 14.dp, vertical = 8.dp),
              modifier = Modifier.testTag("add_new_child_btn")
            ) {
              Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(16.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("إضافة طفل", fontWeight = FontWeight.Bold, fontSize = 12.sp)
            }
          }
        }
      }
    }

    // Children Cards List
    items(children) { child ->
      ChildCardItem(
        child = child,
        isSelected = child.id == selectedChildId,
        onSelect = { onSelectChild(child.id) },
        onOpenPairing = { onOpenPairingModal(child) }
      )
    }

    // Location Sharing & Tracking Section (مشاركة الموقع والتتبع) - Bento Card
    item {
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
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(36.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            ) {
              Icon(Icons.Default.ShareLocation, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(20.dp))
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "مشاركة الموقع والتتبع العائلي",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraTextPrimary,
                  fontSize = 15.sp
                )
              )
              Text(
                text = "إعدادات الأمان المتبادل ومشاركة الموقع مع أفراد العائلة",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(16.dp))

          // Toggle: Share Parent Live Location
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
              Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                Text("📍", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "مشاركة موقع الوالد المباشر (Live Location)",
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = BaseeraTextPrimary
                    )
                  )
                  Text(
                    text = "يتيح للطفل رؤية موقعك للأمان المتبادل والاطمئنان",
                    style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
                  )
                }
              }

              Switch(
                checked = isParentLocationShared,
                onCheckedChange = { onToggleParentLocationShare() },
                colors = SwitchDefaults.colors(
                  checkedThumbColor = BaseeraEmeraldLight,
                  checkedTrackColor = BaseeraEmeraldDark
                ),
                modifier = Modifier.testTag("parent_location_share_switch")
              )
            }
          }

          Spacer(modifier = Modifier.height(12.dp))

          // External Family Tracking Link
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = BentoTileGlow,
            border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
            modifier = Modifier.fillMaxWidth()
          ) {
            Column(modifier = Modifier.padding(14.dp)) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                Text("🔗", fontSize = 20.sp)
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                  Text(
                    text = "إنشاء رابط تتبع خارجي مشفر",
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = BaseeraTextPrimary
                    )
                  )
                  Text(
                    text = "رابط فريد يمكن مشاركته مع أفراد العائلة الموثوقين لتتبع الطفل عبر المتصفح",
                    style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
                  )
                }
              }

              Spacer(modifier = Modifier.height(10.dp))

              Surface(
                shape = RoundedCornerShape(10.dp),
                color = BaseeraNavyDark,
                border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraCyan.copy(alpha = 0.4f)),
                modifier = Modifier.fillMaxWidth()
              ) {
                Row(
                  verticalAlignment = Alignment.CenterVertically,
                  horizontalArrangement = Arrangement.SpaceBetween,
                  modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                ) {
                  Text(
                    text = externalTrackingLink,
                    style = MaterialTheme.typography.bodySmall.copy(
                      color = BaseeraCyanLight,
                      fontWeight = FontWeight.SemiBold
                    ),
                    modifier = Modifier.weight(1f)
                  )

                  IconButton(
                    onClick = onGenerateNewTrackingLink,
                    modifier = Modifier.size(28.dp)
                  ) {
                    Icon(
                      imageVector = Icons.Default.Refresh,
                      contentDescription = "تجديد الرابط",
                      tint = BaseeraEmeraldLight,
                      modifier = Modifier.size(18.dp)
                    )
                  }
                }
              }
            }
          }
        }
      }
    }
  }

  // Add Child Dialog Modal
  if (showAddChildDialog) {
    AddChildDialog(
      onDismiss = onCloseAddChildDialog,
      onConfirm = onAddNewChild
    )
  }

  // Pairing Modal with QR Code & Numeric Code
  pairingModalChild?.let { child ->
    PairingCodeModal(
      child = child,
      onDismiss = onClosePairingModal
    )
  }
}

@Composable
fun ChildCardItem(
  child: Child,
  isSelected: Boolean,
  onSelect: () -> Unit,
  onOpenPairing: () -> Unit
) {
  Card(
    shape = RoundedCornerShape(22.dp),
    colors = CardDefaults.cardColors(
      containerColor = if (isSelected) BentoTileElevated else BentoTileSurface
    ),
    border = androidx.compose.foundation.BorderStroke(
      width = if (isSelected) 1.5.dp else 1.dp,
      color = if (isSelected) BaseeraEmeraldLight else BentoTileBorder
    ),
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onSelect() }
      .testTag("child_card_${child.id}")
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .background(
          Brush.verticalGradient(
            colors = if (isSelected) {
              listOf(BentoTileElevated, BentoTileSurface)
            } else {
              listOf(BentoTileSurface, BaseeraNavySurface)
            }
          )
        )
        .padding(16.dp)
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
              .size(48.dp)
              .clip(CircleShape)
              .background(Color(child.avatarColorHex))
          ) {
            Text(
              text = child.name.take(1),
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = Color.White
              )
            )
          }

          Spacer(modifier = Modifier.width(12.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "${child.name} (${child.age} سنوات)",
                style = MaterialTheme.typography.titleSmall.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraTextPrimary,
                  fontSize = 14.sp
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              if (isSelected) {
                Box(
                  modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(BaseeraEmeraldDark)
                    .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(6.dp))
                    .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                  Text("الطفل المحدد", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
                }
              }
            }

            Text(
              text = "جهاز: ${child.deviceModel} • PIN: ${child.appPin}",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
            )
          }
        }

        // Pairing Button
        Button(
          onClick = onOpenPairing,
          colors = ButtonDefaults.buttonColors(
            containerColor = BentoTileGlow,
            contentColor = BaseeraCyanLight
          ),
          shape = RoundedCornerShape(12.dp),
          border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraCyan.copy(alpha = 0.5f)),
          contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
          modifier = Modifier.height(36.dp)
        ) {
          Icon(Icons.Default.QrCode, contentDescription = null, modifier = Modifier.size(16.dp))
          Spacer(modifier = Modifier.width(4.dp))
          Text("رمز الربط", fontSize = 11.sp, fontWeight = FontWeight.Bold)
        }
      }

      Spacer(modifier = Modifier.height(14.dp))

      // Protection passwords overview Bento row
      Row(
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.fillMaxWidth()
      ) {
        Surface(
          shape = RoundedCornerShape(10.dp),
          color = BentoTileGlow,
          border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
          modifier = Modifier.weight(1f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
          ) {
            Icon(Icons.Default.Pin, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "رمز التطبيق: ${child.appPin}",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = BaseeraTextPrimary)
            )
          }
        }

        Surface(
          shape = RoundedCornerShape(10.dp),
          color = BentoTileGlow,
          border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
          modifier = Modifier.weight(1.3f)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 7.dp)
          ) {
            Icon(Icons.Default.Shield, contentDescription = null, tint = BaseeraRed, modifier = Modifier.size(15.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "حماية الحذف: نشطة 🔒",
              style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = BaseeraTextPrimary)
            )
          }
        }
      }
    }
  }
}

@Composable
fun AddChildDialog(
  onDismiss: () -> Unit,
  onConfirm: (String, Int, String, String) -> Unit
) {
  var name by remember { mutableStateOf("") }
  var ageText by remember { mutableStateOf("10") }
  var appPin by remember { mutableStateOf("2026") }
  var uninstallPassword by remember { mutableStateOf("GUARD_SEC_99") }
  var errorMessage by remember { mutableStateOf<String?>(null) }

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = BaseeraNavySurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraEmerald),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("add_child_dialog")
    ) {
      Column(
        modifier = Modifier.padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "إضافة طفل جديد للمنظومة",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary
            )
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "إغلاق", tint = BaseeraTextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        OutlinedTextField(
          value = name,
          onValueChange = { name = it },
          label = { Text("اسم الطفل") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BaseeraEmerald,
            unfocusedBorderColor = BaseeraNavyBorder,
            focusedContainerColor = BaseeraNavyCard,
            unfocusedContainerColor = BaseeraNavyCard
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("new_child_name_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = ageText,
          onValueChange = { ageText = it },
          label = { Text("العمر (بالسنوات)") },
          singleLine = true,
          colors = OutlinedTextFieldDefaults.colors(
            focusedBorderColor = BaseeraEmerald,
            unfocusedBorderColor = BaseeraNavyBorder,
            focusedContainerColor = BaseeraNavyCard,
            unfocusedContainerColor = BaseeraNavyCard
          ),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .testTag("new_child_age_input")
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = appPin,
          onValueChange = { appPin = it },
          label = { Text("رمز PIN لتطبيق الطفل (4 أرقام)") },
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

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
          value = uninstallPassword,
          onValueChange = { uninstallPassword = it },
          label = { Text("كلمة سر الحماية لمنع حذف التطبيق") },
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

        errorMessage?.let {
          Spacer(modifier = Modifier.height(6.dp))
          Text(text = it, color = BaseeraRed, fontSize = 11.sp)
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = {
            if (name.isBlank()) {
              errorMessage = "يرجى كتابة اسم الطفل."
            } else {
              val age = ageText.toIntOrNull() ?: 10
              onConfirm(name, age, appPin, uninstallPassword)
              onDismiss()
            }
          },
          colors = ButtonDefaults.buttonColors(containerColor = BaseeraEmerald),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .height(48.dp)
            .testTag("confirm_add_child_button")
        ) {
          Text("تأكيد وإنشاء كود الربط", fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}

@Composable
fun PairingCodeModal(
  child: Child,
  onDismiss: () -> Unit
) {
  var selectedTab by remember { mutableStateOf(0) } // 0 = QR, 1 = Numeric

  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(22.dp),
      color = BaseeraNavySurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraEmeraldLight),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("pairing_code_modal")
    ) {
      Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(20.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Text(
            text = "ربط هاتف ${child.name}",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary
            )
          )
          IconButton(onClick = onDismiss) {
            Icon(Icons.Default.Close, contentDescription = "إغلاق", tint = BaseeraTextSecondary)
          }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Tab Selector (QR Code vs Numeric PIN)
        TabRow(
          selectedTabIndex = selectedTab,
          containerColor = BaseeraNavyCard,
          contentColor = BaseeraEmeraldLight,
          indicator = {},
          divider = {},
          modifier = Modifier.clip(RoundedCornerShape(12.dp))
        ) {
          Tab(
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
            text = { Text("📷 مسح QR Code", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
          )
          Tab(
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
            text = { Text("🔢 كود رقمي يدوي", fontWeight = FontWeight.Bold, fontSize = 12.sp) }
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        if (selectedTab == 0) {
          // QR Code Canvas
          QrCodeGeneratorCanvas(token = child.qrToken)

          Spacer(modifier = Modifier.height(14.dp))

          Text(
            text = "افتح تطبيق بصيرة على هاتف الطفل واختر 'مسح كود QR'",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BaseeraTextSecondary,
              textAlign = TextAlign.Center
            )
          )
        } else {
          // Numeric Code Box
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = BaseeraNavyDark,
            border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraCyanLight),
            modifier = Modifier.padding(12.dp)
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              modifier = Modifier.padding(horizontal = 28.dp, vertical = 20.dp)
            ) {
              Text(
                text = child.pairingCode,
                style = MaterialTheme.typography.displayMedium.copy(
                  fontWeight = FontWeight.ExtraBold,
                  color = BaseeraEmeraldLight,
                  letterSpacing = 4.sp
                )
              )
              Spacer(modifier = Modifier.height(6.dp))
              Text(
                text = "صالح لمدة 15 دقيقة",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextMuted, fontSize = 10.sp)
              )
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "أدخل هذا الكود في تطبيق الطفل لربط الحسابين في ثوانٍ معدودة.",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BaseeraTextSecondary,
              textAlign = TextAlign.Center
            )
          )
        }

        Spacer(modifier = Modifier.height(18.dp))

        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = BaseeraNavyCard),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BaseeraNavyBorder, RoundedCornerShape(12.dp))
        ) {
          Text("تم الربط بنجاح", color = BaseeraEmeraldLight, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
