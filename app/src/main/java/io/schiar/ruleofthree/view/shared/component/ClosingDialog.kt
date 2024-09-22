package io.schiar.ruleofthree.view.shared.component

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material.dialog.Dialog
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import io.schiar.ruleofthree.R
import io.schiar.ruleofthree.view.shared.util.fillMaxRectangle

@Composable
fun ClosingDialog(
    showDialog: Boolean = false,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    Dialog(showDialog, onDismissRequest = onDismissRequest) {
        Box {
            content()
            Box(modifier = Modifier.fillMaxRectangle()) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .size(20.dp),
                    onClick = onDismissRequest
                ) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_close_clear_cancel),
                        contentDescription = "close",
                        tint = colorResource(R.color.hashColor)
                    )
                }
            }
        }
    }
}