package com.example.librarybookapp.view

import android.app.DatePickerDialog
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.time.LocalDate
import java.util.Calendar

@Composable
fun ShowDatePicker(onDateSelected: (LocalDate) -> Unit, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val calender = Calendar.getInstance()

    val datePickerDialog = DatePickerDialog(
        context,
        { _, year, month, dayOfMonth ->
            onDateSelected(LocalDate.of(year, month + 1, dayOfMonth))
        },
        calender.get(Calendar.YEAR),
        calender.get(Calendar.MONTH),
        calender.get(Calendar.DAY_OF_MONTH)
    )
    datePickerDialog.setOnDismissListener { onDismiss() }
    datePickerDialog.show()
}