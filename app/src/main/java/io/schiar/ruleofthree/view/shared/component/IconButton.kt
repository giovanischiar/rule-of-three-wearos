package io.schiar.ruleofthree.view.shared.component

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton as AndroidIconButton

@Composable
fun IconButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit, iconDrawableID: Int, contentDescription: String, tintID: Int,
    visible: Boolean = true
) {
    if (visible) {
        AndroidIconButton(modifier = modifier, onClick = onClick) {
            Icon(
                modifier = Modifier.padding(all = 5.dp),
                painter = painterResource(id = iconDrawableID),
                contentDescription = contentDescription,
                tint = colorResource(tintID)
            )
        }
    }
}