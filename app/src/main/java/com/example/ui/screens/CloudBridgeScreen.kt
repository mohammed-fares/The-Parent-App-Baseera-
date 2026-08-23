package com.example.ui.screens

import androidx.compose.animation.AnimatedVisibility
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.AiProvider
import com.example.model.CloudServerConfig
import com.example.ui.theme.*

@Composable
fun CloudBridgeScreen(
  config: CloudServerConfig,
  onUpdateConfig: (CloudServerConfig) -> Unit,
  onTestLatency: () -> Unit,
  onToggleLocalProcessing: () -> Unit,
  modifier: Modifier = Modifier
) {
  var selectedProvider by remember(config.provider) { mutableStateOf(config.provider) }
  var isDefaultServer by remember(config.isDefaultServer) { mutableStateOf(config.isDefaultServer) }
  var serverUrl by remember(config.customServerUrl) { mutableStateOf(config.customServerUrl) }
  var apiKey by remember(config.customApiKey) { mutableStateOf(config.customApiKey) }
  var showProviderDropdown by remember { mutableStateOf(false) }

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Top Bridge Status Bento Card
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
          horizontalArrangement = Arrangement.SpaceBetween,
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
              contentAlignment = Alignment.Center,
              modifier = Modifier
                .size(46.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(BaseeraCyan.copy(alpha = 0.2f))
                .border(1.dp, BaseeraCyanLight.copy(alpha = 0.6f), RoundedCornerShape(14.dp))
            ) {
              Icon(
                imageVector = Icons.Default.CloudQueue,
                contentDescription = null,
                tint = BaseeraCyanLight,
                modifier = Modifier.size(24.dp)
              )
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column {
              Text(
                text = "بوابة الذكاء الاصطناعي السحابية",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraTextPrimary,
                  fontSize = 15.sp
                )
              )
              Text(
                text = "إدارة نماذج AI ومسارات الفحص الفوري للقطات",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
              )
            }
          }

          // Live status badge
          Surface(
            shape = RoundedCornerShape(20.dp),
            color = if (config.isConnected) BaseeraEmeraldDark.copy(alpha = 0.3f) else BaseeraRedContainer,
            border = androidx.compose.foundation.BorderStroke(
              1.dp,
              if (config.isConnected) BaseeraEmeraldLight else BaseeraRed
            )
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
              Box(
                modifier = Modifier
                  .size(8.dp)
                  .clip(CircleShape)
                  .background(if (config.isConnected) BaseeraEmeraldLight else BaseeraRed)
              )
              Spacer(modifier = Modifier.width(6.dp))
              Text(
                text = if (config.isConnected) "متصل 🟢" else "منقطع 🔴",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 10.sp,
                  fontWeight = FontWeight.Bold,
                  color = if (config.isConnected) BaseeraEmeraldLight else BaseeraRed
                )
              )
            }
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Latency Optimizer Bar Bento Sub-Card
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
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text("⚡", fontSize = 18.sp)
              Spacer(modifier = Modifier.width(8.dp))
              Column {
                Text(
                  text = "مؤشر سرعة الاستجابة (Latency):",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 11.sp,
                    color = BaseeraTextMuted
                  )
                )
                Text(
                  text = "${config.latencyMs} مللي ثانية - ممتاز جداً 🚀",
                  style = MaterialTheme.typography.bodyMedium.copy(
                    fontWeight = FontWeight.Bold,
                    color = BaseeraEmeraldLight,
                    fontSize = 13.sp
                  )
                )
              }
            }

            Button(
              onClick = onTestLatency,
              colors = ButtonDefaults.buttonColors(
                containerColor = BentoTileSurface,
                contentColor = BaseeraCyanLight
              ),
              shape = RoundedCornerShape(10.dp),
              border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraCyan.copy(alpha = 0.5f)),
              contentPadding = PaddingValues(horizontal = 12.dp, vertical = 6.dp),
              modifier = Modifier
                .height(34.dp)
                .testTag("test_latency_btn")
            ) {
              Icon(Icons.Default.Speed, contentDescription = null, modifier = Modifier.size(14.dp))
              Spacer(modifier = Modifier.width(4.dp))
              Text("فحص السرعة", fontSize = 11.sp, fontWeight = FontWeight.Bold)
            }
          }
        }
      }
    }

    // 1. AI Provider Selection Section (اختيار مزود AI) - Bento Card
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
            Text("🤖", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "اختيار محرك ونموذج الذكاء الاصطناعي",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 15.sp
              )
            )
            Text(
              text = "حدد المحرك المسؤول عن التدقيق اللحظي وكشف المحتوى",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
            )
          }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Providers Radio List
        AiProvider.values().forEach { provider ->
          val isSelected = provider == selectedProvider
          Surface(
            shape = RoundedCornerShape(16.dp),
            color = if (isSelected) BentoTileGlow else BentoTileSurface,
            border = androidx.compose.foundation.BorderStroke(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = if (isSelected) BaseeraEmeraldLight else BentoTileBorder
            ),
            modifier = Modifier
              .fillMaxWidth()
              .padding(vertical = 4.dp)
              .clickable {
                selectedProvider = provider
                onUpdateConfig(config.copy(provider = provider))
              }
              .testTag("ai_provider_${provider.name}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.SpaceBetween,
              modifier = Modifier.padding(14.dp)
            ) {
              Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                  selected = isSelected,
                  onClick = {
                    selectedProvider = provider
                    onUpdateConfig(config.copy(provider = provider))
                  },
                  colors = RadioButtonDefaults.colors(selectedColor = BaseeraEmeraldLight)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                  Text(
                    text = provider.title,
                    style = MaterialTheme.typography.bodyMedium.copy(
                      fontWeight = FontWeight.Bold,
                      color = if (isSelected) Color.White else BaseeraTextPrimary
                    )
                  )
                  Text(
                    text = "النموذج: ${provider.modelName}",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 11.sp,
                      color = BaseeraTextSecondary
                    )
                  )
                }
              }

              Text(
                text = "~${provider.defaultLatencyMs}ms",
                style = MaterialTheme.typography.bodySmall.copy(
                  fontSize = 11.sp,
                  color = BaseeraCyanLight,
                  fontWeight = FontWeight.SemiBold
                )
              )
            }
          }
        }
      }
    }

    // 2. Server Settings Section (إعدادات السيرفر) - Bento Card
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
            Text("🖥️", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Text(
            text = "إعدادات الخادم والاتصال",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraTextPrimary,
              fontSize = 15.sp
            )
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Switch: Default Baseera Cloud vs Custom Server
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
            Column(modifier = Modifier.weight(1f)) {
              Text(
                text = if (isDefaultServer) "استخدام سحابة بصيرة الرسمية (الموصى به)" else "استخدام سيرفر خاص مخصص",
                style = MaterialTheme.typography.bodyMedium.copy(
                  fontWeight = FontWeight.SemiBold,
                  color = BaseeraTextPrimary
                )
              )
              Text(
                text = "اتصال سحابي مشفر وفائق السرعة ومحمي بموجب اتفاقية الخصوصية",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
              )
            }

            Switch(
              checked = !isDefaultServer,
              onCheckedChange = {
                isDefaultServer = !it
                onUpdateConfig(config.copy(isDefaultServer = isDefaultServer))
              },
              colors = SwitchDefaults.colors(
                checkedThumbColor = BaseeraCyanLight,
                checkedTrackColor = BaseeraNavyBorder
              ),
              modifier = Modifier.testTag("custom_server_switch")
            )
          }
        }

        // Custom Server fields if toggled
        if (!isDefaultServer) {
          Spacer(modifier = Modifier.height(14.dp))

          OutlinedTextField(
            value = serverUrl,
            onValueChange = {
              serverUrl = it
              onUpdateConfig(config.copy(customServerUrl = it))
            },
            label = { Text("عنوان السيرفر الخاص (Server URL)") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = BaseeraCyan,
              unfocusedBorderColor = BaseeraNavyBorder,
              focusedContainerColor = BaseeraNavyCard,
              unfocusedContainerColor = BaseeraNavyCard
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
          )

          Spacer(modifier = Modifier.height(10.dp))

          OutlinedTextField(
            value = apiKey,
            onValueChange = {
              apiKey = it
              onUpdateConfig(config.copy(customApiKey = it))
            },
            label = { Text("مفتاح API الخاص بالسيرفر") },
            singleLine = true,
            colors = OutlinedTextFieldDefaults.colors(
              focusedBorderColor = BaseeraCyan,
              unfocusedBorderColor = BaseeraNavyBorder,
              focusedContainerColor = BaseeraNavyCard,
              unfocusedContainerColor = BaseeraNavyCard
            ),
            shape = RoundedCornerShape(14.dp),
            modifier = Modifier.fillMaxWidth()
          )
        }
      }
    }

    // 3. Local Smart Processing Toggle (المعالجة المحلية الذكية) - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
          .fillMaxWidth()
          .background(
            Brush.verticalGradient(
              colors = listOf(BentoTileElevated, BentoTileSurface)
            )
          )
          .padding(18.dp)
      ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
              .border(1.dp, BaseeraEmeraldLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
          ) {
            Text("📶", fontSize = 18.sp)
          }
          Spacer(modifier = Modifier.width(12.dp))
          Column {
            Text(
              text = "المعالجة المحلية الذكية (توفير الباقة)",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 14.sp
              )
            )
            Text(
              text = "تفعيل المعالجة على الهاتف مباشرة لتوفير باقة الإنترنت للطفل عند اتصاله بالـ Wi-Fi",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
            )
          }
        }

        Switch(
          checked = config.isLocalProcessingEnabled,
          onCheckedChange = { onToggleLocalProcessing() },
          colors = SwitchDefaults.colors(
            checkedThumbColor = BaseeraEmeraldLight,
            checkedTrackColor = BaseeraEmeraldDark
          ),
          modifier = Modifier.testTag("local_processing_switch")
        )
      }
    }
  }
}
