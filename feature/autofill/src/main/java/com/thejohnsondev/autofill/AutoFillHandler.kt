package com.thejohnsondev.autofill

import android.content.Context
import android.os.CancellationSignal
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest

object AutoFillHandler {

    fun handleAutoFillRequest(
        context: Context,
        request: FillRequest,
        callback: FillCallback,
        cancellationSignal: CancellationSignal,
    ) {
        // TODO implement autofill logic
    }

}