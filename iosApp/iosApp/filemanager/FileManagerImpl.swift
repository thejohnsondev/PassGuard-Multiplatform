import Foundation
import ComposeApp
import UniformTypeIdentifiers
import SwiftUI

@objcMembers
class FileManagerImpl: NSObject, PlatformFileManager, UIDocumentPickerDelegate {
    
    weak var presentingViewController: UIViewController?
    private var pickerDelegateStrongRef: FileManagerImpl?
    private var temporaryFileURL: URL?
    
    func downloadCSVWithContent(content: String, fileName: String) -> ExportResult {
        guard let presenter = presentingViewController else {
            print("FileManagerImpl: Error: Presenting UIViewController is not set. Cannot present document picker.")
            return ExportResult(success: false, message: "Export failed: UI context not available. (UIViewController not set)")
        }
        
        let tempFileName = "temp_\(fileName)"
        let tempDir = FileManager.default.temporaryDirectory
        let fileURL = tempDir.appendingPathComponent(tempFileName)
        self.temporaryFileURL = fileURL
        
        do {
            try content.write(to: fileURL, atomically: true, encoding: .utf8)
            DispatchQueue.main.async {
                self.presentPicker(for: fileURL, fileName: fileName, presenter: presenter)
            }
            
            return ExportResult(success: true, message: "File export initiated. Please choose a location to save.")
        } catch {
            return ExportResult(success: false, message: "Export failed: Failed to create temporary file: \(error.localizedDescription)")
        }
    }
    
    func selectFile() -> String? {
        // TODO: Implement file selection using UIDocumentPickerViewController for importing files.
        print("FileManagerImpl: selectFile not yet implemented for iOS.")
        return nil
    }
    
    
    private func presentPicker(for fileURL: URL, fileName: String, presenter: UIViewController) {
        let documentPicker = UIDocumentPickerViewController(forExporting: [fileURL], asCopy: true)
        
        documentPicker.delegate = self
        self.pickerDelegateStrongRef = self
        
        documentPicker.shouldShowFileExtensions = true
        
        presenter.present(documentPicker, animated: true)
    }
    
    
    func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        if let savedURL = urls.first {
            print("FileManagerImpl: File successfully saved to: \(savedURL.lastPathComponent)")
        } else {
            print("FileManagerImpl: Document picker completed, but no URL was returned.")
        }
        cleanupExportProcess()
    }
    
    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        print("FileManagerImpl: Document picker was cancelled.")
        cleanupExportProcess()
    }
    
    
    private func cleanupExportProcess() {
        if let url = temporaryFileURL {
            do {
                try FileManager.default.removeItem(at: url)
                print("FileManagerImpl: Temporary file removed: \(url.lastPathComponent)")
            } catch {
                print("FileManagerImpl: Error removing temporary file: \(error.localizedDescription)")
            }
        }
        self.temporaryFileURL = nil
        self.pickerDelegateStrongRef = nil
    }
}
