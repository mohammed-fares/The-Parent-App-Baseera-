package com.example.ui.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import kotlin.math.abs

@Composable
fun QrCodeGeneratorCanvas(
  token: String,
  modifier: Modifier = Modifier
) {
  Box(
    contentAlignment = Alignment.Center,
    modifier = modifier
      .size(190.dp)
      .clip(RoundedCornerShape(16.dp))
      .background(Color.White)
      .border(2.dp, BaseeraEmeraldLight, RoundedCornerShape(16.dp))
      .padding(12.dp)
  ) {
    Canvas(modifier = Modifier.fillMaxSize()) {
      val gridSize = 21 // standard QR 21x21 matrix
      val cellSize = size.width / gridSize
      val hash = token.hashCode()

      for (row in 0 until gridSize) {
        for (col in 0 until gridSize) {
          val isCornerFinder =
            (row < 7 && col < 7) || (row < 7 && col >= gridSize - 7) || (row >= gridSize - 7 && col < 7)

          val isFinderBorder =
            (row in 0..6 && (col == 0 || col == 6)) ||
            (col in 0..6 && (row == 0 || row == 6)) ||
            (row in 0..6 && (col == gridSize - 7 || col == gridSize - 1)) ||
            (col in (gridSize - 7)..(gridSize - 1) && (row == 0 || row == 6)) ||
            (row in (gridSize - 7)..(gridSize - 1) && (col == 0 || col == 6)) ||
            (col in 0..6 && (row == gridSize - 7 || row == gridSize - 1))

          val isFinderCenter =
            (row in 2..4 && col in 2..4) ||
            (row in 2..4 && col in (gridSize - 5)..(gridSize - 3)) ||
            (row in (gridSize - 5)..(gridSize - 3) && col in 2..4)

          val isDataBlock = !isCornerFinder && (abs(row * 31 + col * 17 + hash) % 3 == 0)

          val isCenterLogoZone = (row in 9..11 && col in 9..11)

          if (!isCenterLogoZone) {
            if (isFinderBorder || isFinderCenter || isDataBlock) {
              drawRect(
                color = Color(0xFF0F172A),
                topLeft = Offset(col * cellSize, row * cellSize),
                size = Size(cellSize, cellSize)
              )
            }
          }
        }
      }
    }

    // Center Logo Shield in QR
    Box(
      contentAlignment = Alignment.Center,
      modifier = Modifier
        .size(34.dp)
        .clip(RoundedCornerShape(8.dp))
        .background(BaseeraNavyDark)
        .border(1.dp, BaseeraEmeraldLight, RoundedCornerShape(8.dp))
    ) {
      Icon(
        imageVector = Icons.Default.Shield,
        contentDescription = "شعار بصيرة",
        tint = BaseeraEmeraldLight,
        modifier = Modifier.size(20.dp)
      )
    }
  }
}
