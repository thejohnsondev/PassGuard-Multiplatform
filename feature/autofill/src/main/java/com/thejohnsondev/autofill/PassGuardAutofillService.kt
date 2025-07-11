package com.thejohnsondev.autofill

import android.content.Context
import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.thejohnsondev.common.utils.Logger
import org.koin.mp.KoinPlatform.getKoin

class PassGuardAutofillService: AutofillService() {

    private val context: Context = getKoin().get()

    override fun onConnected() {
        super.onConnected()
        Logger.i(TAG_AUTOFILL, "Autofill service connected")
    }

    override fun onDisconnected() {
        super.onDisconnected()
        Logger.i(TAG_AUTOFILL, "Autofill service disconnected")
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        AutoFillHandler.handleAutoFillRequest(
            context = context,
            request = request,
            callback = callback,
            cancellationSignal = cancellationSignal
        )
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        AutoSaveHandler.handleAutoSaveRequest(
            context = context,
            request = request,
            callback = callback
        )
    }

    companion object {
        const val TAG_AUTOFILL = "VaultAutofillService"
    }
}