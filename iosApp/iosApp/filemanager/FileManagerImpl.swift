import Foundation
import ComposeApp
import UniformTypeIdentifiers
import SwiftUI

@objcMembers
class FileManagerImpl: NSObject, PlatformFileManager {

    func downloadCSVWithContent(content: String, fileName: String) -> ExportResult {
        // TODO IMPLEMENT actual file saving
        return ExportResult(success: true, message: "File export initiated. Please choose a location to save.")
    }

}
