package io.schiar.ruleofthree.view.shared.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.vectorResource
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton

@Composable
fun TopAppBarActionButton(
    iconResId: Int,
    description: String,
    enabled: Boolean = true,
    onPressed: () -> Unit,
) {
    IconButton(onClick = onPressed, enabled = enabled) {
        val imageVector = ImageVector.vectorResource(id = iconResId)

        Icon(
            imageVector = imageVector,
            contentDescription = description,
            tint = colorResource(id = android.R.color.white)
        )
    }
}