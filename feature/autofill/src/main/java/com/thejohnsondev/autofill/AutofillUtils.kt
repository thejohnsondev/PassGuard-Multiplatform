
package com.thejohnsondev.autofill

import android.app.assist.AssistStructure
import android.service.autofill.FillContext

object AutofillUtils {

    fun getAppPackageName(windowNode: AssistStructure.WindowNode): String {
        val wholePackageName = windowNode.title
        val packageComponents = wholePackageName.split("/")
        return packageComponents.first()
    }

    fun getWindowNodes(fillContexts: List<FillContext>): List<AssistStructure.WindowNode> {
        val fillContext = fillContexts
            .lastOrNull { !it.structure.activityComponent.className.contains("PopupWindow") }
            ?: return emptyList()
        val structure: AssistStructure = fillContext.structure
        return if (structure.windowNodeCount > 0)
            (0 until structure.windowNodeCount).map { structure.getWindowNodeAt(it) } else
            emptyList()
    }

}
