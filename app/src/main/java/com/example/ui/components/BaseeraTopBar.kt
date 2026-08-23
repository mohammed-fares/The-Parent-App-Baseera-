package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
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
import com.example.model.User
import com.example.ui.theme.*

@Composable
fun BaseeraTopBar(
  user: User,
  children: List<Child>,
  selectedChild: Child?,
  onSelectChild: (String) -> Unit,
  onOpenUpgrade: () -> Unit,
  modifier: Modifier = Modifier
) {
  Surface(
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 6.dp,
    shadowElevation = 4.dp,
    modifier = modifier.fillMaxWidth()
  ) {
    Column(
      modifier = Modifier
        .statusBarsPadding()
        .padding(horizontal = 16.dp, vertical = 10.dp)
    ) {
      // Upper row: Logo & App Title + VIP Badge
      Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier.fillMaxWidth()
      ) {
        Row(
          verticalAlignment = Alignment.CenterVertically,
          modifier = Modifier.testTag("app_brand_header")
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(38.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(
                Brush.linearGradient(
                  colors = listOf(BaseeraEmeraldDark, BaseeraNavyCard)
                )
              )
              .border(1.dp, BaseeraEmerald.copy(alpha = 0.5f), RoundedCornerShape(12.dp))
          ) {
            Icon(
              imageVector = Icons.Default.Shield,
              contentDescription = "شعار بصيرة",
              tint = BaseeraEmeraldLight,
              modifier = Modifier.size(22.dp)
            )
          }

          Spacer(modifier = Modifier.width(10.dp))

          Column {
            Row(verticalAlignment = Alignment.CenterVertically) {
              Text(
                text = "بصيرة",
                style = MaterialTheme.typography.titleLarge.copy(
                  fontWeight = FontWeight.Bold,
                  color = BaseeraEmeraldLight,
                  letterSpacing = 0.5.sp
                )
              )
              Spacer(modifier = Modifier.width(6.dp))
              Box(
                modifier = Modifier
                  .clip(RoundedCornerShape(6.dp))
                  .background(BaseeraNavyBorder)
                  .padding(horizontal = 6.dp, vertical = 2.dp)
              ) {
                Text(
                  text = "تطبيق الوالد",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = BaseeraCyanLight
                  )
                )
              }
            }
            Text(
              text = user.fullName,
              style = MaterialTheme.typography.bodySmall.copy(
                color = MaterialTheme.colorScheme.onSurfaceVariant
              )
            )
          }
        }

        // VIP / Plan Badge
        Surface(
          shape = RoundedCornerShape(20.dp),
          color = if (user.isPremium) BaseeraGoldContainer else BaseeraNavyCard,
          border = androidx.compose.foundation.BorderStroke(
            1.dp,
            if (user.isPremium) BaseeraGold else BaseeraNavyBorder
          ),
          modifier = Modifier
            .clickable { onOpenUpgrade() }
            .testTag("vip_badge_button")
        ) {
          Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
          ) {
            Text(
              text = if (user.isPremium) "👑 VIP ذهبي" else "ترقية ⭐",
              style = MaterialTheme.typography.labelMedium.copy(
                fontWeight = FontWeight.Bold,
                color = if (user.isPremium) BaseeraGoldLight else BaseeraCyanLight
              )
            )
          }
        }
      }

      Spacer(modifier = Modifier.height(10.dp))

      // Child Switcher Horizontal Scroll Row
      Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
          .fillMaxWidth()
          .horizontalScroll(rememberScrollState())
      ) {
        children.forEach { child ->
          val isSelected = child.id == selectedChild?.id
          val chipBg = if (isSelected) BaseeraNavyCard else Color.Transparent
          val chipBorder = if (isSelected) BaseeraEmerald else BaseeraNavyBorder.copy(alpha = 0.5f)

          Surface(
            shape = RoundedCornerShape(14.dp),
            color = chipBg,
            border = androidx.compose.foundation.BorderStroke(
              width = if (isSelected) 1.5.dp else 1.dp,
              color = chipBorder
            ),
            modifier = Modifier
              .padding(end = 8.dp)
              .clickable { onSelectChild(child.id) }
              .testTag("child_chip_${child.id}")
          ) {
            Row(
              verticalAlignment = Alignment.CenterVertically,
              modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
            ) {
              // Child Avatar circle
              Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                  .size(24.dp)
                  .clip(CircleShape)
                  .background(Color(child.avatarColorHex))
              ) {
                Text(
                  text = child.name.take(1),
                  style = MaterialTheme.typography.labelSmall.copy(
                    color = Color.White,
                    fontWeight = FontWeight.Bold
                  )
                )
              }

              Spacer(modifier = Modifier.width(8.dp))

              Column {
                Row(verticalAlignment = Alignment.CenterVertically) {
                  Text(
                    text = child.name,
                    style = MaterialTheme.typography.titleSmall.copy(
                      fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                      color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                  )

                  if (child.isOnline) {
                    Spacer(modifier = Modifier.width(4.dp))
                    Box(
                      modifier = Modifier
                        .size(7.dp)
                        .clip(CircleShape)
                        .background(BaseeraEmeraldLight)
                    )
                  }
                }

                Text(
                  text = "🔋 ${child.batteryLevel}% • ${if (child.isOnline) "نشط الآن" else "غير متصل"}",
                  style = MaterialTheme.typography.bodySmall.copy(
                    fontSize = 10.sp,
                    color = if (child.isOnline) BaseeraEmeraldLight else BaseeraTextMuted
                  )
                )
              }
            }
          }
        }
      }
    }
  }
}
