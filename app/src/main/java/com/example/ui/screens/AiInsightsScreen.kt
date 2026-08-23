package com.example.ui.screens

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
import androidx.compose.runtime.Composable
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
import com.example.model.TalentCategory
import com.example.ui.components.TalentDonutChart
import com.example.ui.theme.*

@Composable
fun AiInsightsScreen(
  child: Child?,
  modifier: Modifier = Modifier
) {
  val talents = listOf(
    TalentCategory("العلوم والفيزياء والفضاء", 40, 0xFF10B981, 14.5f, "تعليمي"),
    TalentCategory("الرسم والتصميم الرقمي", 25, 0xFF06B6D4, 9.0f, "إبداعي"),
    TalentCategory("البرمجة وألعاب التفكير", 20, 0xFFF59E0B, 7.2f, "مهاري"),
    TalentCategory("اللغات والقصص الأدبية", 15, 0xFF8B5CF6, 5.5f, "ثقافي")
  )

  Column(
    modifier = modifier
      .fillMaxSize()
      .background(BaseeraNavyDark)
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
  ) {
    // Advisor Title Header Banner Bento Card
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
            Brush.linearGradient(
              colors = listOf(
                Color(0xFF1E1B4B).copy(alpha = 0.8f),
                BentoTileSurface
              )
            )
          )
          .padding(18.dp)
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(52.dp)
              .clip(RoundedCornerShape(16.dp))
              .background(
                Brush.linearGradient(
                  colors = listOf(Color(0xFF6366F1), Color(0xFF312E81))
                )
              )
              .border(1.dp, Color(0xFF818CF8).copy(alpha = 0.5f), RoundedCornerShape(16.dp))
          ) {
            Text("🧠", fontSize = 26.sp)
          }

          Spacer(modifier = Modifier.width(14.dp))

          Column {
            Text(
              text = "المستشار التربوي والنفسي الرقمي",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraTextPrimary,
                fontSize = 16.sp
              )
            )
            Text(
              text = "تحليلات ذكية لاهتمامات ${child?.name ?: "الطفل"} السلوكية وتنمية مهاراته",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraCyanLight, fontSize = 11.sp)
            )
          }
        }
      }
    }

    // 1. Talent Detection Section (مؤشر الشغف والمواهب) - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("talent_detection_card")
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
                .size(34.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(BaseeraEmeraldDark.copy(alpha = 0.3f))
                .border(1.dp, BaseeraEmerald.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
            ) {
              Text("⭐", fontSize = 16.sp)
            }
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "مؤشر الشغف والمواهب المكتشفة",
                style = MaterialTheme.typography.titleMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraEmeraldLight,
                  fontSize = 15.sp
                )
              )
              Text(
                text = "توزيع المحتوى المفيد بناءً على سلوك المشاهدة",
                style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
              )
            }
          }

          Box(
            modifier = Modifier
              .clip(RoundedCornerShape(8.dp))
              .background(BaseeraNavyCard)
              .border(1.dp, BaseeraEmerald.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
              .padding(horizontal = 8.dp, vertical = 4.dp)
          ) {
            Text("ذكاء اصطناعي", style = MaterialTheme.typography.bodySmall.copy(fontSize = 9.sp, color = BaseeraEmeraldLight, fontWeight = FontWeight.Bold))
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Donut Chart & Legend Row
        Row(
          verticalAlignment = Alignment.CenterVertically,
          horizontalArrangement = Arrangement.SpaceAround,
          modifier = Modifier.fillMaxWidth()
        ) {
          TalentDonutChart(categories = talents)

          Column(
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.padding(start = 12.dp)
          ) {
            talents.forEach { cat ->
              Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                  modifier = Modifier
                    .size(10.dp)
                    .clip(CircleShape)
                    .background(Color(cat.colorHex))
                )
                Spacer(modifier = Modifier.width(6.dp))
                Column {
                  Text(
                    text = "${cat.name} (${cat.percentage}%)",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontWeight = FontWeight.SemiBold,
                      color = BaseeraTextPrimary,
                      fontSize = 11.sp
                    )
                  )
                  Text(
                    text = "${cat.hoursSpent} ساعة هذا الأسبوع",
                    style = MaterialTheme.typography.bodySmall.copy(
                      fontSize = 9.sp,
                      color = BaseeraTextMuted
                    )
                  )
                }
              }
            }
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Pedagogical Recommendation Bento Sub-Card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = BentoTileGlow,
          border = androidx.compose.foundation.BorderStroke(1.dp, BaseeraGold.copy(alpha = 0.4f)),
          modifier = Modifier.fillMaxWidth()
        ) {
          Row(
            verticalAlignment = Alignment.Top,
            modifier = Modifier.padding(14.dp)
          ) {
            Icon(
              imageVector = Icons.Default.Lightbulb,
              contentDescription = null,
              tint = BaseeraGold,
              modifier = Modifier.size(20.dp)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
              Text(
                text = "توصية بصيرة التربوية:",
                style = MaterialTheme.typography.labelMedium.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraGoldLight,
                  fontSize = 12.sp
                )
              )
              Spacer(modifier = Modifier.height(3.dp))
              Text(
                text = "يُظهر ${child?.name ?: "أحمد"} اهتماماً كبيراً بنسبة 40% في مقاطع الفيديو التعليمية الخاصة بالعلوم والفلك والرسم، ونوصي بدعمه بإلحاقه بأنشطة الروبوتيكس أو تزويده بكتب علمية تفاعلية.",
                style = MaterialTheme.typography.bodySmall.copy(
                  color = BaseeraTextPrimary,
                  lineHeight = 18.sp,
                  fontSize = 12.sp
                )
              )
            }
          }
        }
      }
    }

    // 2. Emotional & Well-being Analysis (مؤشر الحالة المزاجية) - Bento Card
    Card(
      shape = RoundedCornerShape(24.dp),
      colors = CardDefaults.cardColors(containerColor = BentoTileSurface),
      border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
      modifier = Modifier
        .fillMaxWidth()
        .testTag("mood_analysis_card")
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
              .size(34.dp)
              .clip(RoundedCornerShape(10.dp))
              .background(BaseeraCyan.copy(alpha = 0.2f))
              .border(1.dp, BaseeraCyanLight.copy(alpha = 0.5f), RoundedCornerShape(10.dp))
          ) {
            Text("📈", fontSize = 16.sp)
          }
          Spacer(modifier = Modifier.width(10.dp))
          Column {
            Text(
              text = "مؤشر الحالة المزاجية والاستقرار النفسي",
              style = MaterialTheme.typography.titleMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraCyanLight,
                fontSize = 15.sp
              )
            )
            Text(
              text = "تحليل نفسي وسلوكي للكلمات وطبيعة المحادثات",
              style = MaterialTheme.typography.bodySmall.copy(color = BaseeraTextSecondary, fontSize = 11.sp)
            )
          }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Mood metrics Bento 3-column row
        Row(
          horizontalArrangement = Arrangement.spacedBy(10.dp),
          modifier = Modifier.fillMaxWidth()
        ) {
          MetricBox(
            title = "الاستقرار النفسي",
            value = "88/100",
            status = "ممتاز ومريح 😊",
            color = BaseeraEmeraldLight,
            modifier = Modifier.weight(1f)
          )

          MetricBox(
            title = "مستوى التوتر",
            value = "منخفض 12%",
            status = "طبيعي جداً ✅",
            color = BaseeraCyanLight,
            modifier = Modifier.weight(1f)
          )

          MetricBox(
            title = "مخاطر التنمر",
            value = "0 حالة",
            status = "محمي بالكامل 🛡️",
            color = BaseeraEmeraldLight,
            modifier = Modifier.weight(1f)
          )
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Behavioral summary Bento Sub-Card
        Surface(
          shape = RoundedCornerShape(16.dp),
          color = BentoTileGlow,
          border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
          modifier = Modifier.fillMaxWidth()
        ) {
          Column(modifier = Modifier.padding(14.dp)) {
            Text(
              text = "💡 ملخص الملاحظة السلوكية:",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = BaseeraCyanLight,
                fontSize = 12.sp
              )
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
              text = "المحادثات والتفاعلات اليومية تدل على تفاعل اجتماعي سليم وصداقات إيجابية، مع انعدام مؤشرات العزلة أو الضغط العصبي خلال الأيام السبعة الماضية.",
              style = MaterialTheme.typography.bodySmall.copy(
                color = BaseeraTextPrimary,
                lineHeight = 18.sp,
                fontSize = 12.sp
              )
            )
          }
        }
      }
    }
  }
}

@Composable
fun MetricBox(
  title: String,
  value: String,
  status: String,
  color: Color,
  modifier: Modifier = Modifier
) {
  Surface(
    shape = RoundedCornerShape(16.dp),
    color = BentoTileGlow,
    border = androidx.compose.foundation.BorderStroke(1.dp, BentoTileBorder),
    modifier = modifier
  ) {
    Column(
      horizontalAlignment = Alignment.CenterHorizontally,
      modifier = Modifier.padding(10.dp)
    ) {
      Text(
        text = title,
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 10.sp,
          color = BaseeraTextMuted
        )
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = value,
        style = MaterialTheme.typography.titleMedium.copy(
          fontWeight = FontWeight.Bold,
          color = color,
          fontSize = 13.sp
        )
      )
      Spacer(modifier = Modifier.height(2.dp))
      Text(
        text = status,
        style = MaterialTheme.typography.bodySmall.copy(
          fontSize = 9.sp,
          color = BaseeraTextSecondary
        )
      )
    }
  }
}
