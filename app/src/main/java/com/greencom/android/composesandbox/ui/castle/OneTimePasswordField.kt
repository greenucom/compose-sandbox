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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp

private const val TAG = "OneTimePasswordField"

private const val SymbolEmpty = ' '

private val CellSizeDefault = 48.dp

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OneTimePasswordField(
    value: String,
    onValueChanged: (String) -> Unit,
    cellCount: Int,
    modifier: Modifier = Modifier,
    cellSize: DpSize = DpSize(CellSizeDefault, CellSizeDefault),
    cellShape: Shape = RoundedCornerShape(4.dp),
    cellBackgroundColor: Color = MaterialTheme.colors.background,
    cellBorder: BorderStroke? = BorderStroke(1.dp, MaterialTheme.colors.primary),
    cellTextStyle: TextStyle = LocalTextStyle.current,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    spacedBy: Dp = 8.dp,
) {
    require(cellCount > 0) { "cellCount should be positive" }

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
                size = cellSize,
                shape = cellShape,
                backgroundColor = cellBackgroundColor,
                border = cellBorder,
                textStyle = cellTextStyle,
            )
        }
    }
}

@Composable
private fun OneTimePasswordCell(
    symbol: Char,
    size: DpSize,
    shape: Shape,
    backgroundColor: Color,
    border: BorderStroke?,
    textStyle: TextStyle,
    modifier: Modifier = Modifier,
) {
    Surface(
        modifier = modifier.size(size),
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
