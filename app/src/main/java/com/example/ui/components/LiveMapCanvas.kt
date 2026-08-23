package com.example.ui.components

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.Child
import com.example.model.GeofenceZone
import com.example.ui.theme.*

@Composable
fun LiveMapCanvas(
  child: Child?,
  geofenceZones: List<GeofenceZone>,
  isPremium: Boolean,
  onUpgradeClick: () -> Unit,
  modifier: Modifier = Modifier
) {
  // Infinite pulsing animation for child GPS marker
  val infiniteTransition = rememberInfiniteTransition(label = "pulse")
  val pulseRadius by infiniteTransition.animateFloat(
    initialValue = 16f,
    targetValue = 44f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "radius"
  )
  val pulseAlpha by infiniteTransition.animateFloat(
    initialValue = 0.7f,
    targetValue = 0.0f,
    animationSpec = infiniteRepeatable(
      animation = tween(1400, easing = FastOutSlowInEasing),
      repeatMode = RepeatMode.Restart
    ),
    label = "alpha"
  )

  Box(
    modifier = modifier
      .fillMaxWidth()
      .height(260.dp)
      .clip(RoundedCornerShape(20.dp))
      .border(1.dp, BaseeraNavyBorder, RoundedCornerShape(20.dp))
      .background(BaseeraNavyDark)
      .testTag("live_map_container")
  ) {
    // Map Canvas
    Canvas(
      modifier = Modifier
        .fillMaxSize()
        .then(if (!isPremium) Modifier.blur(14.dp) else Modifier)
    ) {
      val w = size.width
      val h = size.height

      // Background subtle map tiles
      drawRect(
        brush = Brush.radialGradient(
          colors = listOf(Color(0xFF13233C), Color(0xFF091220)),
          center = Offset(w * 0.5f, h * 0.5f),
          radius = w * 0.8f
        )
      )

      // Simulated City Road Grid (Lines & Curves)
      val roadColor = Color(0xFF1E385B)
      val roadAccent = Color(0xFF2E5384)

      // Main Avenues
      drawLine(
        color = roadAccent,
        start = Offset(0f, h * 0.35f),
        end = Offset(w, h * 0.35f),
        strokeWidth = 14f
      )
      drawLine(
        color = Color(0xFF3B679B),
        start = Offset(0f, h * 0.35f),
        end = Offset(w, h * 0.35f),
        strokeWidth = 4f
      )

      drawLine(
        color = roadAccent,
        start = Offset(w * 0.55f, 0f),
        end = Offset(w * 0.55f, h),
        strokeWidth = 16f
      )

      // Secondary Streets
      val gridLinesY = listOf(0.15f, 0.6f, 0.85f)
      gridLinesY.forEach { yRatio ->
        drawLine(
          color = roadColor,
          start = Offset(0f, h * yRatio),
          end = Offset(w, h * yRatio),
          strokeWidth = 6f
        )
      }

      val gridLinesX = listOf(0.2f, 0.8f)
      gridLinesX.forEach { xRatio ->
        drawLine(
          color = roadColor,
          start = Offset(w * xRatio, 0f),
          end = Offset(w * xRatio, h),
          strokeWidth = 6f
        )
      }

      // Diagonal highway
      drawLine(
        color = Color(0xFF254B78),
        start = Offset(0f, h * 0.8f),
        end = Offset(w * 0.9f, 0f),
        strokeWidth = 8f
      )

      // Park / River area
      val parkPath = Path().apply {
        moveTo(w * 0.05f, h * 0.05f)
        lineTo(w * 0.35f, h * 0.05f)
        lineTo(w * 0.30f, h * 0.28f)
        lineTo(w * 0.05f, h * 0.28f)
        close()
      }
      drawPath(
        path = parkPath,
        color = Color(0xFF0F3B36).copy(alpha = 0.6f)
      )

      // Draw Geofencing Zones
      geofenceZones.forEach { zone ->
        val centerX = w * zone.latOffsetPercentX
        val centerY = h * zone.latOffsetPercentY
        val radius = zone.radiusMeters * 0.18f

        // Translucent fill
        drawCircle(
          color = Color(zone.colorHex).copy(alpha = if (zone.isBreached) 0.35f else 0.18f),
          radius = radius,
          center = Offset(centerX, centerY)
        )

        // Dotted / dashed border
        drawCircle(
          color = Color(zone.colorHex).copy(alpha = 0.85f),
          radius = radius,
          center = Offset(centerX, centerY),
          style = Stroke(width = 3f)
        )
      }

      // Draw Active Child Marker
      child?.let { c ->
        val childX = w * 0.48f
        val childY = h * 0.42f

        // Radar wave pulse
        drawCircle(
          color = BaseeraEmerald.copy(alpha = pulseAlpha),
          radius = pulseRadius * 1.6f,
          center = Offset(childX, childY)
        )

        // Inner glowing halo
        drawCircle(
          color = BaseeraEmeraldLight,
          radius = 18f,
          center = Offset(childX, childY)
        )

        // Center solid dot
        drawCircle(
          color = Color.White,
          radius = 8f,
          center = Offset(childX, childY)
        )
      }
    }

    // Top overlay labels: Child location chip & accuracy
    Row(
      modifier = Modifier
        .align(Alignment.TopStart)
        .padding(12.dp)
        .clip(RoundedCornerShape(12.dp))
        .background(BaseeraNavyDark.copy(alpha = 0.85f))
        .border(1.dp, BaseeraNavyBorder, RoundedCornerShape(12.dp))
        .padding(horizontal = 10.dp, vertical = 6.dp),
      verticalAlignment = Alignment.CenterVertically
    ) {
      Icon(
        imageVector = Icons.Default.GpsFixed,
        contentDescription = "GPS",
        tint = BaseeraEmeraldLight,
        modifier = Modifier.size(14.dp)
      )
      Spacer(modifier = Modifier.width(6.dp))
      Text(
        text = child?.locationName ?: "جاري تحديد الموقع بدقة...",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 11.sp,
          fontWeight = FontWeight.Medium,
          color = BaseeraTextPrimary
        )
      )
    }

    // Bottom map badges: Geofence legends
    Row(
      modifier = Modifier
        .align(Alignment.BottomStart)
        .padding(12.dp),
      horizontalArrangement = Arrangement.spacedBy(6.dp)
    ) {
      geofenceZones.forEach { zone ->
        Surface(
          shape = RoundedCornerShape(8.dp),
          color = BaseeraNavyDark.copy(alpha = 0.9f),
          border = androidx.compose.foundation.BorderStroke(1.dp, Color(zone.colorHex).copy(alpha = 0.6f))
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 6.dp, vertical = 4.dp)
          ) {
            Box(
              modifier = Modifier
                .size(8.dp)
                .clip(CircleShape)
                .background(Color(zone.colorHex))
            )
            Spacer(modifier = Modifier.width(4.dp))
            Text(
              text = zone.name,
              style = MaterialTheme.typography.bodySmall.copy(
                fontSize = 9.sp,
                color = BaseeraTextPrimary
              )
            )
          }
        }
      }
    }

    // Free Version Blur / Lock Overlay
    if (!isPremium) {
      Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
          .fillMaxSize()
          .background(BaseeraNavyDark.copy(alpha = 0.65f))
          .padding(16.dp)
      ) {
        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(54.dp)
              .clip(CircleShape)
              .background(BaseeraGoldContainer)
              .border(1.dp, BaseeraGold, CircleShape)
          ) {
            Icon(
              imageVector = Icons.Default.Lock,
              contentDescription = "قفل الخريطة",
              tint = BaseeraGoldLight,
              modifier = Modifier.size(28.dp)
            )
          }

          Spacer(modifier = Modifier.height(10.dp))

          Text(
            text = "الخريطة الذكية والتتبع اللحظي (ميزة VIP)",
            style = MaterialTheme.typography.titleMedium.copy(
              fontWeight = FontWeight.Bold,
              color = BaseeraGoldLight
            )
          )

          Spacer(modifier = Modifier.height(4.dp))

          Text(
            text = "قم بالترقية لمشاهدة تحركات طفلك الحية وسياج الأمان الجغرافي",
            style = MaterialTheme.typography.bodySmall.copy(
              color = BaseeraTextSecondary,
              textAlign = androidx.compose.ui.text.style.TextAlign.Center
            )
          )

          Spacer(modifier = Modifier.height(12.dp))

          Button(
            onClick = onUpgradeClick,
            colors = ButtonDefaults.buttonColors(
              containerColor = BaseeraGold,
              contentColor = Color.Black
            ),
            shape = RoundedCornerShape(12.dp),
            modifier = Modifier.testTag("map_upgrade_button")
          ) {
            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text("فتح الخريطة الآن", fontWeight = FontWeight.Bold)
          }
        }
      }
    }
  }
}
