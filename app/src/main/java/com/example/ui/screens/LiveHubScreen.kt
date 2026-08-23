package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Child
import com.example.model.GeofenceZone
import com.example.model.RiskLevel
import com.example.ui.components.LiveMapCanvas
import com.example.ui.components.ThreatLevelIndicator
import com.example.ui.theme.*

@Composable
fun LiveHubScreen(
  child: Child?,
  geofenceZones: List<GeofenceZone>,
  isPremium: Boolean,
  onToggleLock: (String) -> Unit,
  onTriggerAlarm: (String) -> Unit,
  onExtendTime: (String) -> Unit,
  onOpenUpgrade: () -> Unit,
  modifier: Modifier = Modifier
) {
  if (child == null) {
    Box(
      contentAlignment = Alignment.Center,
      modifier = modifier
        .fillMaxSize()
        .background(BaseeraNavyDark)
    ) {
      Text("يرجى اختيار طفل من القائمة بالأعلى", color = BaseeraTextSecondary)
    }
    return
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Bento Child Live Status Hero Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("child_live_status_hero")
    ) {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.linearGradient(
              colors = listOf(
                BentoTileElevated,
                BentoTileSurface
              )
            )
          )
          .padding(18.dp)
      ) {
        Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(50.dp)
                  .clip(RoundedCornerShape(16.dp))
                  .background(Color(child.avatarColorHex))
                  .border(2.dp, BaseeraEmeraldLight.copy(alpha = 0.4f), RoundedCornerShape(16.dp))
              ) {
                Text(
                  text = child.name.take(1),
                  style = MaterialTheme.typography.titleMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    fontSize = 20.sp
                  )
                )
              }

              Spacer(modifier = Modifier.width(12.dp))

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = "الرصد المباشر لـ ${child.name}",
                    style = MaterialTheme.typography.titleMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = BaseeraTextPrimary
                    )
                  )
                  Spacer(modifier = Modifier.width(6.dp))
                  if (child.isOnline) {
                    Box(
                      modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(BaseeraEmeraldDark.copy(alpha = 0.35f))
                        .border(1.dp, BaseeraEmerald.copy(alpha = 0.6f), RoundedCornerShape(8.dp))
                        .padding(horizontal = 7.dp, vertical = 2.5.dp)
                    ) {
                      Text(
                        text = "● متصل",
                        style = MaterialTheme.typography.bodySmall.copy(
                          fontSize = 10.sp,
                          color = BaseeraEmeraldLight,
                          fontWeight = FontWeight.Bold
                        )
                      )
                    }
                  }
                }

                Text(
                  text = "جهاز: ${child.deviceModel} • 🔋 ${child.batteryLevel}%",
                  style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary)
                )
              }
            }

            // Screen time pill
            Surface(
              shape = RoundedCornerShape(16.dp),
              color = BentoTileGlow,
              border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraCyan.copy(alpha = 0.5f))
            ) {
              Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
              ) {
                Text(
                  text = "${child.screenTimeMinutes}/${child.totalAllowedMinutes} د",
                  style = MaterialTheme.typography.labelLarge.copy(
                    fontWeight = FontWeight.Bold,
                    color = BaseeraCyanLight
                  )
                )
                Text(
                  text = "الوقت المتاح",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 9.sp,
                    color = BaseeraTextMuted
                  )
                )
              }
            }
          }

          // Screen Time Progress Bar
          val progress = (child.screenTimeMinutes.toFloat() / child.totalAllowedMinutes.coerceAtLeast(1).toFloat()).coerceIn(0f, 1f)
          Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
            Row(
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "استهلاك الشاشة اليومي",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 11.sp, color = BaseeraTextSecondary)
              )
              Text(
                text = "${(progress * 100).toInt()}% مستخدم",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (progress > 0.85f) BaseeraRed else BaseeraEmeraldLight
                )
              )
            }
            Box(
              modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .clip(RoundedCornerShape(4.dp))
                .background(BaseeraNavyDark)
            ) {
              Box(
                modifier = Modifier
                  .fillMaxHeight()
                  .fillMaxWidth(progress)
                  .clip(RoundedCornerShape(4.dp))
                  .background(
                    Brush.horizontalGradient(
                      colors = if (progress > 0.85f) {
                        listOf(BaseeraOrange, BaseeraRed)
                      } else {
                        listOf(BaseeraCyan, BaseeraEmeraldLight)
                      }
                    )
                  )
              )
            }
          }
        }
      }
    }

    // 2-Column Bento Grid Row: Active App Tile & Risk Indicator Tile
    Row(
      horizontalArrangement = Arrangement.spacedBy(12.dp),
      modifier = Modifier.fillMaxWidth()
    ) {
      // Bento Tile 1: Active App
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
        modifier = Modifier
          .weight(1f)
          .testTag("screen_monitor_card")
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(BentoTileElevated, BentoTileSurface)
              )
            )
            .padding(14.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BaseeraNavyCard)
                .border(1.dp, BaseeraCyan.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            ) {
              Icon(Icons.Default.PhoneAndroid, contentDescription = null, tint = BaseeraCyanLight, modifier = Modifier.size(18.dp))
            }
            Box(
              modifier = Modifier
                .clip(RoundedCornerShape(6.dp))
                .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                .padding(horizontal = 6.dp, vertical = 2.dp)
            ) {
              Text("نشط الآن", style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp, color = BaseeraEmeraldLight, fontWeight = FontWeight.Bold))
            }
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "التطبيق النشط",
            style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
          )
          Text(
            text = "▶️ ${child.currentApp}",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary,
              fontSize = 14.sp
            ),
            maxLines = 1
          )
        }
      }

      // Bento Tile 2: Battery & Connectivity status
      Card(
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
        border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
        modifier = Modifier.weight(1f)
      ) {
        Column(
          modifier = Modifier
            .fillMaxWidth()
            .background(
              Brush.verticalGradient(
                colors = listOf(BentoTileElevated, BentoTileSurface)
              )
            )
            .padding(14.dp)
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
          ) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BaseeraNavyCard)
                .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.4f), RoundedCornerShape(10.dp))
            ) {
              Icon(Icons.Default.BatteryChargingFull, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(18.dp))
            }
            Text(
              text = "${child.batteryLevel}%",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraEmeraldLight,
                fontSize = 14.sp
              )
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "مستوى البطارية",
            style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
          )
          Text(
            text = if (child.batteryLevel > 20) "شحن ممتاز ومستقر" else "شحن منخفض ⚠️",
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = if (child.batteryLevel > 20) BaseeraTextPrimary else BaseeraOrangeLight,
              fontSize = 12.sp
            ),
            maxLines = 1
          )
        }
      }
    }

    // Threat Level Indicator Bento Tile
    ThreatLevelIndicator(riskLevel = child.riskLevel)

    // Live Map Bento Card Section
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("bento_live_map_card")
    ) {
      Column(modifier = Modifier.padding(14.dp)) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 10.dp)
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(32.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                .border(1.dp, BaseeraEmerald.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            ) {
              Icon(Icons.Default.Map, contentDescription = null, tint = BaseeraEmeraldLight, modifier = Modifier.size(18.dp))
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "الخريطة وسياج الأمان (Geofencing)",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 14.sp
              )
            )
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(BaseeraNavyCard)
              .border(1.dp, BaseeraCyan.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 3.dp)
          ) {
            Text(
              text = "3 مناطق أمان نشطة",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraCyanLight, fontSize = 10.sp, fontWeight = FontWeight.Bold)
            )
          }
        }

        LiveMapCanvas(
          child = child,
          geofenceZones = geofenceZones,
          isPremium = isPremium,
          onUpgradeClick = onOpenUpgrade
        )
      }
    }

    // Emergency Bento Controls Section (لوحة التدخل الفوري)
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("bento_emergency_controls_card")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(16.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.padding(bottom = 12.dp)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(32.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraRedDark.copy(alpha = 0.3f))
              .border(1.dp, BaseeraRed.copy(alpha = 0.6f), RoundedCornerShape(10.dp))
          ) {
            Icon(Icons.Default.FlashOn, contentDescription = null, tint = BaseeraRed, modifier = Modifier.size(18.dp))
          }
          Spacer(modifier = Modifier.width(8.dp))
          Column {
            Text(
              text = "لوحة التدخل الفوري بلمسة واحدة",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 14.sp
              )
            )
            Text(
              text = "استجابة فورية لأي طارئ على جهاز الطفل",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 10.sp)
            )
          }
        }

        // 3 Bento Action Buttons Row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          // 1. Lock Device Button
          val lockBtnBg by animateColorAsState(
            targetValue = if (child.isLocked) BaseeraRedDark else BaseeraRed,
            label = "lock_btn"
          )
          Button(
            onClick = { onToggleLock(child.id) },
            colors = ButtonDefaults.buttonColors(
              containerColor = lockBtnBg,
              contentColor = Color.White
            ),
            shape = RoundedCornerShape(18.dp),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp),
            modifier = Modifier
              .weight(1f)
              .height(100.dp)
              .testTag("action_lock_device_btn")
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = if (child.isLocked) Icons.Default.LockOpen else Icons.Default.Block,
                contentDescription = "قفل الهاتف",
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (child.isLocked) "إلغاء القفل" else "🛑 قفل شامل",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 11.sp
              )
              Text(
                text = if (child.isLocked) "الهاتف مجمد" else "تجميد فوري",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp, color = Color.White.copy(alpha = 0.8f))
              )
            }
          }

          // 2. Forced Alarm Button
          Button(
            onClick = { onTriggerAlarm(child.id) },
            colors = ButtonDefaults.buttonColors(
              containerColor = if (child.isAlarmTriggered) BaseeraOrangeLight else BentoTileGlow,
              contentColor = if (child.isAlarmTriggered) Color.Black else BaseeraCyanLight
            ),
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(
              1.2.dp,
              if (child.isAlarmTriggered) BaseeraOrange else BaseeraCyan.copy(alpha = 0.6f)
            ),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp),
            modifier = Modifier
              .weight(1f)
              .height(100.dp)
              .testTag("action_sound_alarm_btn")
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.NotificationsActive,
                contentDescription = "رنين قسري",
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = if (child.isAlarmTriggered) "إيقاف الرنين" else "🔊 رنين قسري",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 11.sp
              )
              Text(
                text = "حتى بالصامت",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp)
              )
            }
          }

          // 3. Extend Time Button
          Button(
            onClick = { onExtendTime(child.id) },
            colors = ButtonDefaults.buttonColors(
              containerColor = BentoTileGlow,
              contentColor = BaseeraEmeraldLight
            ),
            shape = RoundedCornerShape(18.dp),
            border = androidx.compose.foundation.BorderStroke(1.2.dp, BaseeraEmerald.copy(alpha = 0.7f)),
            contentPadding = PaddingValues(vertical = 12.dp, horizontal = 6.dp),
            modifier = Modifier
              .weight(1f)
              .height(100.dp)
              .testTag("action_extend_time_btn")
          ) {
            Column(
              horizontalAlignment = Alignment.CenterHorizontally,
              verticalArrangement = Arrangement.Center
            ) {
              Icon(
                imageVector = Icons.Default.MoreTime,
                contentDescription = "تمديد الوقت",
                modifier = Modifier.size(24.dp)
              )
              Spacer(modifier = Modifier.height(4.dp))
              Text(
                text = "⏳ +30 دقيقة",
                style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Bold),
                fontSize = 11.sp
              )
              Text(
                text = "تمديد مرن",
                style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp, color = BaseeraTextSecondary)
              )
            }
          }
        }
      }
    }
  }
}
