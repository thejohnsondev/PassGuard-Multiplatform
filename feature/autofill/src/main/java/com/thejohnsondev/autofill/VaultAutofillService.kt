package com.thejohnsondev.autofill

import android.os.CancellationSignal
import android.service.autofill.AutofillService
import android.service.autofill.FillCallback
import android.service.autofill.FillRequest
import android.service.autofill.SaveCallback
import android.service.autofill.SaveRequest
import com.thejohnsondev.common.utils.Logger

class VaultAutofillService: AutofillService() {

    override fun onConnected() {
        super.onConnected()
        Logger.i(TAG, "Autofill service connected")
    }

    override fun onDisconnected() {
        super.onDisconnected()
        Logger.i(TAG, "Autofill service disconnected")
    }

    override fun onFillRequest(
        request: FillRequest,
        cancellationSignal: CancellationSignal,
        callback: FillCallback
    ) {
        // TODO implement
    }

    override fun onSaveRequest(request: SaveRequest, callback: SaveCallback) {
        // TODO implement
    }

    companion object {
        private const val TAG = "VaultAutofillService"
    }
}