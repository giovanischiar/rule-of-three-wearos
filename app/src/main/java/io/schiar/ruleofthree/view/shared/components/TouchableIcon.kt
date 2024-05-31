package io.schiar.ruleofthree.view.shared.components

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon

@Composable
fun TouchableIcon(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    iconDrawableID: Int,
    contentDescription : String,
    colorID: Int,
    visible: Boolean = true
) {
    TouchableArea(modifier = modifier, onClick = { onClick() }, enabled = visible) {
        if (visible) {
            Icon(
                modifier = Modifier.padding(all = 5.dp),
                painter = painterResource(id = iconDrawableID),
                contentDescription = contentDescription,
                tint = colorResource(colorID)
            )
        }
    }
}