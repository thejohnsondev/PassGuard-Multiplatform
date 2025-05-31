import Foundation
import ComposeApp
import UniformTypeIdentifiers
import UIKit

@objcMembers
class FileManagerImpl: NSObject, PlatformFileManager, UIDocumentPickerDelegate {

    weak var presentingViewController: UIViewController?
    private var pickerDelegateStrongRef: FileManagerImpl?
    private var temporaryFileURL: URL?
    private var kotlinCompletionCallback: ((ExportResult) -> Void)?

    func downloadCSVWithContent(content: String, fileName: String, onCompletion: @escaping (ExportResult) -> Void) {
        self.kotlinCompletionCallback = onCompletion

        guard let presenter = presentingViewController else {
            let errorResult = ExportResult(status: .failure, message: "Export failed: UI context not available (UIViewController not set).")
            onCompletion(errorResult)
            return
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
        } catch {
            let errorResult = ExportResult(status: .failure, message: "Failed to create temporary file: \(error.localizedDescription)")
            onCompletion(errorResult)
            cleanup()
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
            let result = ExportResult(status: .success, message: "File successfully saved to: \(savedURL.lastPathComponent)")
            kotlinCompletionCallback?(result)
        } else {
            let result = ExportResult(status: .failure, message: "File picker completed, but no URL returned.")
            kotlinCompletionCallback?(result)
        }
        cleanup()
    }

    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        let result = ExportResult(status: .canceled, message: "File export canceled by user.")
        kotlinCompletionCallback?(result)
        cleanup()
    }

    private func cleanup() {
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
        self.kotlinCompletionCallback = nil
    }
}
