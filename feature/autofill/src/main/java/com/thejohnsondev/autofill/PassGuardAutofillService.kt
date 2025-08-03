package com.thejohnsondev.autofill

import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.thejohnsondev.common.utils.Logger

class PassGuardAutofillService : AutofillService() {

    override fun onConnected() {
        super.onConnected()
        Logger.i("Autofill service connected")
    }

    override fun onDisconnected() {
        super.onDisconnected()
        Logger.i("Autofill service disconnected")
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        AutoFillHandler.handleAutoFillRequest(
            context = this,
            request = request,
            callback = callback,
            cancellationSignal = cancellationSignal
        )
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        AutoSaveHandler.handleAutoSaveRequest(
            context = this,
            request = request,
            callback = callback
        )
    }

}