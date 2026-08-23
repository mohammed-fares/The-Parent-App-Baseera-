package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
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
import com.example.model.LogType
import com.example.model.TimelineLog
import com.example.ui.theme.*

@Composable
fun TimelineLogsScreen(
  logs: List<TimelineLog>,
  selectedFilter: LogType?,
  onFilterSelected: (LogType?) -> Unit,
  previewLog: TimelineLog?,
  onOpenPreview: (TimelineLog) -> Unit,
  onClosePreview: () -> Unit,
  isPremium: Boolean,
  onOpenUpgrade: () -> Unit,
  modifier: Modifier = Modifier
) {
  val filteredLogs = remember(logs, selectedFilter) {
    if (selectedFilter == null) logs else logs.filter { it.type == selectedFilter }
  }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
  ) {
    // Top Narrative Bento Banner
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
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
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(38.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.6f), RoundedCornerShape(12.dp))
            ) {
              Icon(
                imageVector = Icons.Default.History,
                contentDescription = null,
                tint = BaseeraEmeraldLight,
                modifier = Modifier.size(20.dp)
              )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "سجل التنبيهات والأحداث اللحظي",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraTextPrimary,
                  fontSize = 15.sp
                )
              )
              Text(
                text = "جدول زمني وسرد قصصي عائلي مريح من الأحدث للأقدم",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
              )
            }
          }

          // Live Pulse Dot
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(BaseeraNavyCard)
              .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.3f), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(BaseeraEmeraldLight)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = "مباشر",
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                color = BaseeraEmeraldLight
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Filter Chips Row
        LazyRow(
          horizontalArrangement = Arrangement.spacedBy(8.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          item {
            FilterPill(
              title = "جميع التنبيهات (${logs.size})",
              isSelected = selectedFilter == null,
              onClick = { onFilterSelected(null) },
              testTag = "filter_all"
            )
          }
          item {
            FilterPill(
              title = "🚫 محتوى محظور",
              isSelected = selectedFilter == LogType.INAPPROPRIATE_CONTENT,
              onClick = { onFilterSelected(LogType.INAPPROPRIATE_CONTENT) },
              testTag = "filter_inappropriate"
            )
          }
          item {
            FilterPill(
              title = "📍 تنبيهات جغرافية",
              isSelected = selectedFilter == LogType.GEOFENCE_BREACH,
              onClick = { onFilterSelected(LogType.GEOFENCE_BREACH) },
              testTag = "filter_geofence"
            )
          }
          item {
            FilterPill(
              title = "🚨 طوارئ واستغاثة",
              isSelected = selectedFilter == LogType.SOS_EMERGENCY,
              onClick = { onFilterSelected(LogType.SOS_EMERGENCY) },
              testTag = "filter_sos"
            )
          }
        }
      }
    }

    // Vertical Timeline List
    LazyColumn(
      contentPadding = PaddingValues(16.dp),
      verticalArrangement = Arrangement.spacedBy(16.dp),
      modifier = Modifier.fillMaxSize()
    ) {
      items(filteredLogs) { log ->
        TimelineLogCard(
          log = log,
          onPreviewClick = { onOpenPreview(log) },
          isPremium = isPremium
        )
      }
    }
  }

  // Scene Preview Dialog Modal (معاينة المشهد)
  previewLog?.let { log ->
    ScenePreviewDialog(
      log = log,
      isPremium = isPremium,
      onDismiss = onClosePreview,
      onUpgrade = onOpenUpgrade
    )
  }
}

@Composable
fun FilterPill(
  title: String,
  isSelected: Boolean,
  onClick: () -> Unit,
  testTag: String
) {
  Surface(
    shape = RoundedCornerShape(12.dp),
    color = if (isSelected) BaseeraEmeraldDark else BaseeraNavyCard,
    border = androidx.compose.foundation.BorderStroke(
      width = 1.dp,
      color = if (isSelected) BaseeraEmeraldLight else BaseeraNavyBorder
    ),
    modifier = Modifier
      .clickable { onClick() }
      .testTag(testTag)
  ) {
    Text(
      text = title,
      style = MaterialTheme.typography.bodySmall.copy(
        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
        color = if (isSelected) Color.White else BaseeraTextSecondary
      ),
      modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
    )
  }
}

@Composable
fun TimelineLogCard(
  log: TimelineLog,
  onPreviewClick: () -> Unit,
  isPremium: Boolean
) {
  val (cardBorderColor, iconTint, badgeBg) = when (log.type) {
    LogType.INAPPROPRIATE_CONTENT -> Triple(BaseeraRed.copy(alpha = 0.5f), BaseeraRed, BaseeraRedContainer)
    LogType.GEOFENCE_BREACH -> Triple(BaseeraOrange.copy(alpha = 0.5f), BaseeraOrangeLight, Color(0xFF3B2404))
    LogType.SOS_EMERGENCY -> Triple(BaseeraRed, BaseeraRed, BaseeraRedContainer)
    LogType.SCREEN_TIME_WARNING -> Triple(BaseeraCyan.copy(alpha = 0.5f), BaseeraCyanLight, Color(0xFF072738))
  }

  Row(modifier = Modifier.fillMaxWidth()) {
    // Left Dotted Vertical Timeline Column
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.width(36.dp)
    ) {
      // Timeline Dot with Icon
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .size(32.dp)
          .clip(CircleShape)
          .background(badgeBg)
          .border(1.5.dp, iconTint, CircleShape)
      ) {
        Text(text = log.type.icon, fontSize = 14.sp)
      }

      // Vertical connecting line
      Box(
        modifier = Modifier
          .width(2.dp)
          .height(90.dp)
          .background(
            Brush.verticalGradient(
              colors = listOf(iconTint.copy(alpha = 0.7f), BaseeraNavyBorder.copy(alpha = 0.2f))
            )
          )
      )
    }

    Spacer(modifier = Modifier.width(8.dp))

    // Bento Card Content
    Card(
      shape = RoundedCornerShape(22.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, cardBorderColor),
      modifier = Modifier
        .weight(1f)
        .testTag("log_card_${log.id}")
    ) {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(15.dp)
      ) {
        // Card Header: App Name & Exact Time
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(
              shape = RoundedCornerShape(8.dp),
              color = BentoTileGlow,
              border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder)
            ) {
              Text(
                text = "📱 ${log.appName}",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.sp,
                  fontWeight = FontWeight.Bold,
                  color = BaseeraCyanLight
                ),
                modifier = Modifier.padding(horizontal = 7.dp, vertical = 3.dp)
              )
            }
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "• ${log.childName}",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.SemiBold,
                color = BaseeraTextPrimary
              )
            )
          }

          Text(
            text = "⏱️ ${log.timestampStr}",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = BaseeraTextMuted
            )
          )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Narrative Friendly Text
        Text(
          text = log.message,
          style = MaterialTheme.typography.bodyMedium.copy(
            fontWeight = FontWeight.Medium,
            color = BaseeraTextPrimary,
            lineHeight = 20.sp
          )
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Bottom Action: Scene Preview Button
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          // Threat score badge
          Text(
            text = "درجة الخطورة: ${log.riskScorePercent}%",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 10.sp,
              color = if (log.riskScorePercent > 80) BaseeraRed else BaseeraOrangeLight,
              fontWeight = FontWeight.Bold
            )
          )

          // "👁️ معاينة المشهد" Button
          Button(
            onClick = onPreviewClick,
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
              containerColor = BentoTileGlow,
              contentColor = BaseeraEmeraldLight
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
            modifier = Modifier
              .height(36.dp)
              .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
              .testTag("preview_scene_btn_${log.id}")
          ) {
            Text(
              text = "👁️ معاينة المشهد",
              style = MaterialTheme.typography.bodySmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = 11.sp
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun ScenePreviewDialog(
  log: TimelineLog,
  isPremium: Boolean,
  onDismiss: () -> Unit,
  onUpgrade: () -> Unit
) {
  Dialog(onDismissRequest = onDismiss) {
    Surface(
      shape = RoundedCornerShape(20.dp),
      color = BaseeraNavySurface,
      border = androidx.compose.foundation.BorderStroke(1.5.dp, BaseeraEmerald),
      modifier = Modifier
        .fillMaxWidth()
        .padding(8.dp)
        .testTag("scene_preview_dialog")
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
          Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(Icons.Default.Security, contentDescription = null, tint = BaseeraEmeraldLight)
            Spacer(modifier = Modifier.width(8.dp))
            Text(
              text = "معاينة اللقطة المشفرة",
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

        Spacer(modifier = Modifier.height(12.dp))

        // Encrypted Preview Container
        Box(
          modifier = Modifier
            .fillMaxWidth()
            .height(180.dp)
            .clip(RoundedCornerShape(14.dp))
            .background(BaseeraNavyDark)
            .border(1.dp, BaseeraNavyBorder, RoundedCornerShape(14.dp))
            .padding(14.dp)
        ) {
          Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxSize()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.fillMaxWidth()
            ) {
              Text(
                text = "🔒 مشفر للوالد فقط (E2EE)",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 10.sp,
                  color = BaseeraEmeraldLight,
                  fontWeight = FontWeight.Bold
                )
              )
              Text(
                text = log.exactTime,
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 10.sp,
                  color = BaseeraTextMuted
                )
              )
            }

            // Simulated snapshot box
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(vertical = 8.dp)
                .clip(RoundedCornerShape(8.dp))
                .background(BaseeraNavyCard)
                .padding(10.dp)
            ) {
              Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Icon(
                  imageVector = Icons.Default.Visibility,
                  contentDescription = null,
                  tint = BaseeraCyanLight,
                  modifier = Modifier.size(24.dp)
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                  text = log.previewSnippet,
                  style = MaterialTheme.typography.bodySmall.copy(
                    color = BaseeraTextPrimary,
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Center
                  )
                )
              }
            }

            Text(
              text = log.previewCaption,
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 11.sp,
                color = BaseeraTextSecondary
              )
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (!isPremium) {
          // Free tier incentive banner
          Surface(
            shape = RoundedCornerShape(12.dp),
            color = BaseeraGoldContainer,
            border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraGold),
            modifier = Modifier.fillMaxWidth()
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(10.dp)
            ) {
              Text(text = "👑", fontSize = 20.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column(modifier = Modifier.weight(1f)) {
                Text(
                  text = "احصل على المعاينة الكاملة والتحكم المطلق",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontWeight = FontWeight.Bold,
                    color = BaseeraGoldLight
                  )
                )
                Text(
                  text = "في النسخة المدفوعة، يمكنك تنزيل لقطة الشاشة عالية الدقة مباشرة.",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = BaseeraTextSecondary
                  )
                )
              }
              Spacer(modifier = Modifier.width(6.dp))
              Button(
                onClick = onUpgrade,
                colors = ButtonDefaults.buttonColors(containerColor = BaseeraGold),
                shape = RoundedCornerShape(8.dp),
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp),
                modifier = Modifier.height(30.dp)
              ) {
                Text("ترقية", color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 11.sp)
              }
            }
          }
          Spacer(modifier = Modifier.height(12.dp))
        }

        Button(
          onClick = onDismiss,
          colors = ButtonDefaults.buttonColors(containerColor = BaseeraNavyCard),
          shape = RoundedCornerShape(12.dp),
          modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, BaseeraNavyBorder, RoundedCornerShape(12.dp))
        ) {
          Text("تم التحقق من المشهد", color = BaseeraTextPrimary, fontWeight = FontWeight.Bold)
        }
      }
    }
  }
}
