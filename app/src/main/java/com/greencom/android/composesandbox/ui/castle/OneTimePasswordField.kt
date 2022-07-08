package com.greencom.android.composesandbox.ui.castle

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.LocalTextStyle
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

private const val TAG = "OneTimePasswordField"

private const val CellCountMin = 4
private const val CellCountMax = 8

private const val SymbolEmpty = ' '

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OneTimePasswordField(
    value: String,
    onValueChanged: (String) -> Unit,
    cellCount: Int,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(4.dp),
    backgroundColor: Color = MaterialTheme.colors.background,
    border: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colors.primary),
    spacedBy: Dp = 8.dp,
    textStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
) {
    check(cellCount in CellCountMin..CellCountMax) {
        "Cell count should be in $CellCountMin to $CellCountMax, actual $cellCount"
    }

    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    BasicTextField(
        modifier = Modifier
            .focusRequester(focusRequester)
            .size(0.dp)
            .alpha(0f),
        value = value,
        onValueChange = {
            val new = if (it.length <= cellCount) it else it.substring(0 until cellCount)
            onValueChanged(new)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
    )

    Row(
        modifier = modifier.clickable(
            indication = null,
            interactionSource = remember { MutableInteractionSource() },
        ) {
            focusRequester.requestFocus()
            keyboardController?.show()
        },
        horizontalArrangement = Arrangement.spacedBy(spacedBy),
    ) {
        for (i in 0 until cellCount) {
            OneTimePasswordCell(
                symbol = if (value.length > i) value[i] else SymbolEmpty,
                shape = shape,
                backgroundColor = backgroundColor,
                border = border,
                textStyle = textStyle,
            )
        }
    }
}

@Composable
private fun OneTimePasswordCell(
    symbol: Char,
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    modifier: Modifier = Modifier,
    textStyle: TextStyle = LocalTextStyle.current,
) {
    Surface(
        modifier = modifier.size(48.dp),
        shape = shape,
        color = backgroundColor,
        border = border,
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier.align(Alignment.Center),
                text = symbol.toString(),
                style = textStyle,
            )
        }
    }
}
