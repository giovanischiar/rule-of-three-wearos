package io.schiar.ruleofthree.view.shared.component

import android.content.pm.PackageManager
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.wear.compose.material3.Icon
import androidx.wear.compose.material3.IconButton
import io.schiar.ruleofthree.R
import androidx.compose.material3.BasicAlertDialog as PhoneDialog
import androidx.wear.compose.material.dialog.Dialog as WatchDialog

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClosingDialog(
    showDialog: Boolean = false,
    onDismissRequest: () -> Unit = {},
    content: @Composable () -> Unit
) {
    val isWatch = LocalContext.current.packageManager.hasSystemFeature(PackageManager.FEATURE_WATCH)
    val configuration = LocalConfiguration.current
    val DialogContent = @Composable {
        Box(modifier = Modifier.background(color = colorResource(R.color.backgroundColor))) {
            Box(Modifier.padding(if (isWatch) 0.dp else 30.dp)) { content() }
            Box(Modifier.padding(if (isWatch) 0.dp else 15.dp)) {
                IconButton(
                    modifier = Modifier
                        .align(alignment = Alignment.TopStart)
                        .size(if (isWatch) 20.dp else 25.dp),
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

    if (isWatch) {
        WatchDialog(showDialog, onDismissRequest = onDismissRequest) {
            DialogContent()
        }
    } else {
        if (showDialog) {
            PhoneDialog(onDismissRequest = onDismissRequest) {
                Card(
                    modifier = Modifier
                        .height(configuration.screenWidthDp.dp)
                        .shadow(5.dp)
                ) {
                    DialogContent()
                }
            }
        }
    }
}