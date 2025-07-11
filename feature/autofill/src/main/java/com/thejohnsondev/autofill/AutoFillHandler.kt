package com.thejohnsondev.autofill

import android.content.Context
import android.os.CancellationSignal
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import com.thejohnsondev.autofill.PassGuardAutofillService.Companion.TAG_AUTOFILL
import com.thejohnsondev.common.utils.Logger

object AutoFillHandler {

    fun handleAutoFillRequest(
        context: Context,
        request: FillRequest,
        callback: FillCallback,
        cancellationSignal: CancellationSignal,
    ) {
        Logger.i(TAG_AUTOFILL, "Handling autofill request")
        val windowNode = AutofillUtils.getWindowNodes(request.fillContexts).lastOrNull()
        if (windowNode?.rootViewNode == null) {
            Logger.i(TAG_AUTOFILL, "No window node found")
            callback.onSuccess(null)
            return
        }
    }

}