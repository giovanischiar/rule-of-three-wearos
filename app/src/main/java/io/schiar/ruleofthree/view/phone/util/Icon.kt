package io.schiar.ruleofthree.view.shared.util

import androidx.compose.ui.graphics.vector.ImageVector

data class Icon(
    val selected: ImageVector,
    val unselected: ImageVector,
    val contentDescriptionStringID: Int
) {
    fun chooseWhether(isSelected: Boolean): ImageVector {
        return if (isSelected) selected else unselected
    }
}