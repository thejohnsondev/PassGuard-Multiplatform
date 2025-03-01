import UIKit
import ComposeApp

class ClipboardUtilsImpl: ClipboardUtils {
    func doCopyToClipboard(text: String) {
        UIPasteboard.general.string = text
    }
    
    func doCopyToClipboardSensitive(text: String) {
        doCopyToClipboard(text: text)
    }
}
