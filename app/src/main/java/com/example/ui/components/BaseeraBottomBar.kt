package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.MainTab
import com.example.ui.theme.*

data class NavigationItem(
  val tab: MainTab,
  val titleAr: String,
  val selectedIcon: ImageVector,
  val unselectedIcon: ImageVector,
  val testTag: String
)

@Composable
fun BaseeraBottomBar(
  currentTab: MainTab,
  onTabSelected: (MainTab) -> Unit,
  modifier: Modifier = Modifier
) {
  val items = listOf(
    NavigationItem(
      tab = MainTab.TIMELINE,
      titleAr = "السجل",
      selectedIcon = Icons.Filled.ListAlt,
      unselectedIcon = Icons.Outlined.ListAlt,
      testTag = "nav_tab_timeline"
    ),
    NavigationItem(
      tab = MainTab.LIVE_HUB,
      titleAr = "الرصد",
      selectedIcon = Icons.Filled.LocationOn,
      unselectedIcon = Icons.Outlined.LocationOn,
      testTag = "nav_tab_live_hub"
    ),
    NavigationItem(
      tab = MainTab.AI_INSIGHTS,
      titleAr = "التحليلات",
      selectedIcon = Icons.Filled.Psychology,
      unselectedIcon = Icons.Outlined.Psychology,
      testTag = "nav_tab_insights"
    ),
    NavigationItem(
      tab = MainTab.CHILDREN,
      titleAr = "الأطفال",
      selectedIcon = Icons.Filled.FamilyRestroom,
      unselectedIcon = Icons.Outlined.FamilyRestroom,
      testTag = "nav_tab_children"
    ),
    NavigationItem(
      tab = MainTab.CLOUD_BRIDGE,
      titleAr = "السحابة",
      selectedIcon = Icons.Filled.CloudSync,
      unselectedIcon = Icons.Outlined.CloudSync,
      testTag = "nav_tab_cloud"
    ),
    NavigationItem(
      tab = MainTab.SETTINGS,
      titleAr = "الإعدادات",
      selectedIcon = Icons.Filled.Settings,
      unselectedIcon = Icons.Outlined.Settings,
      testTag = "nav_tab_settings"
    )
  )

  Surface(
    color = MaterialTheme.colorScheme.surface,
    tonalElevation = 8.dp,
    shadowElevation = 12.dp,
    modifier = modifier
      .fillMaxWidth()
      .windowInsetsPadding(WindowInsets.navigationBars)
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 4.dp, vertical = 6.dp),
      horizontalArrangement = Arrangement.SpaceAround,
      verticalAlignment = Alignment.CenterVertically
    ) {
      items.forEach { item ->
        val isSelected = item.tab == currentTab
        val interactionSource = remember { MutableInteractionSource() }

        Column(
          horizontalAlignment = Alignment.CenterHorizontally,
          verticalArrangement = Arrangement.Center,
          modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(12.dp))
            .clickable(
              interactionSource = interactionSource,
              indication = ripple()
            ) {
              onTabSelected(item.tab)
            }
            .padding(vertical = 4.dp)
            .testTag(item.testTag)
        ) {
          Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
              .size(36.dp)
              .clip(RoundedCornerShape(12.dp))
              .background(
                if (isSelected) BaseeraEmerald.copy(alpha = 0.2f) else Color.Transparent
              )
              .border(
                width = if (isSelected) 1.dp else 0.dp,
                color = if (isSelected) BaseeraEmeraldLight.copy(alpha = 0.5f) else Color.Transparent,
                shape = RoundedCornerShape(12.dp)
              )
          ) {
            Icon(
              imageVector = if (isSelected) item.selectedIcon else item.unselectedIcon,
              contentDescription = item.titleAr,
              tint = if (isSelected) BaseeraEmeraldLight else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.7f),
              modifier = Modifier.size(20.dp)
            )
          }

          Spacer(modifier = Modifier.height(2.dp))

          Text(
            text = item.titleAr,
            style = MaterialTheme.typography.bodySmall.copy(
              fontSize = 11.sp,
              fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
              color = if (isSelected) BaseeraEmeraldLight else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.8f)
            )
          )
        }
      }
    }
  }
}
