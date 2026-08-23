package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Dangerous
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.RiskLevel
import com.example.model.TalentCategory
import com.example.ui.theme.*

@Composable
fun TalentDonutChart(
  categories: List<TalentCategory>,
  modifier: Modifier = Modifier
) {
  var animationPlayed by remember { mutableStateOf(false) }
  val curProgress by animateFloatAsState(
    targetValue = if (animationPlayed) 1f else 0f,
    animationSpec = tween(durationMillis = 1000),
    label = "donut_progress"
  )

  LaunchedEffect(key1 = true) {
    animationPlayed = true
  }

  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier.size(170.dp)
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val strokeWidth = 32f
      val diameter = size.minDimension - strokeWidth
      val topLeft = Offset(strokeWidth / 2f, strokeWidth / 2f)
      val arcSize = Size(diameter, diameter)

      var startAngle = -90f
      categories.forEach { cat ->
        val sweepAngle = (cat.percentage / 100f) * 360f * curProgress
        drawArc(
          color = Color(cat.colorHex),
          startAngle = startAngle,
          sweepAngle = sweepAngle - 3f, // small gap
          useCenter = false,
          topLeft = topLeft,
          size = arcSize,
          style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
        )
        startAngle += sweepAngle
      }
    }

    Column(
      horizontalAlignment = Alignment.CenterHorizontally
    ) {
      Text(
        text = "40%",
        style = MaterialTheme.typography.titleLarge.copy(
          fontWeight = FontWeight.Bold,
          color = BaseeraEmeraldLight
        )
      )
      Text(
        text = "شغف علمي",
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 11.sp,
          color = BaseeraTextSecondary
        )
      )
    }
  }
}

@Composable
fun ThreatLevelIndicator(
  riskLevel: RiskLevel,
  modifier: Modifier = Modifier
) {
  val (label, color, icon, bgGradient) = when (riskLevel) {
    RiskLevel.SAFE -> Quad(
      "نطاق آمن تماماً",
      BaseeraEmeraldLight,
      Icons.Default.CheckCircle,
      listOf(BaseeraEmeraldDark.copy(alpha = 0.2f), BaseeraNavyCard)
    )
    RiskLevel.WARNING -> Quad(
      "تنبيه: محتوى غير معتاد",
      BaseeraOrangeLight,
      Icons.Default.Warning,
      listOf(BaseeraOrange.copy(alpha = 0.2f), BaseeraNavyCard)
    )
    RiskLevel.DANGER -> Quad(
      "مخالفة شديدة / خطر!",
      BaseeraRed,
      Icons.Default.Dangerous,
      listOf(BaseeraRedDark.copy(alpha = 0.3f), BaseeraNavyCard)
    )
  }

  Box(
    modifier = modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(14.dp))
      .background(Brush.horizontalGradient(bgGradient))
      .border(1.dp, color.copy(alpha = 0.4f), RoundedCornerShape(14.dp))
      .padding(horizontal = 14.dp, vertical = 10.dp)
  ) {
    Row(
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.SpaceBetween,
      modifier = Modifier.fillMaxWidth()
    ) {
      Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          tint = color,
          modifier = Modifier.size(22.dp)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Column {
          Text(
            text = "مؤشر الخطورة اللحظي",
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              color = BaseeraTextSecondary
            )
          )
          Text(
            text = label,
            style = MaterialTheme.typography.titleSmall.copy(
              fontWeight = FontWeight.Bold,
              color = color
            )
          )
        }
      }

      // Smooth Level Gauge Pills
      Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
        Box(
          modifier = Modifier
            .size(width = 18.dp, height = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(BaseeraEmeraldLight)
        )
        Box(
          modifier = Modifier
            .size(width = 18.dp, height = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
              if (riskLevel == RiskLevel.WARNING || riskLevel == RiskLevel.DANGER) BaseeraOrangeLight else BaseeraNavyBorder
            )
        )
        Box(
          modifier = Modifier
            .size(width = 18.dp, height = 8.dp)
            .clip(RoundedCornerShape(4.dp))
            .background(
              if (riskLevel == RiskLevel.DANGER) BaseeraRed else BaseeraNavyBorder
            )
        )
      }
    }
  }
}

private data class Quad<A, B, C, D>(val first: A, val second: B, val third: C, val fourth: D)
