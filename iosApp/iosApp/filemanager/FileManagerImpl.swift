import Foundation
import UIKit
import UniformTypeIdentifiers
import ComposeApp

@objcMembers
class FileManagerImpl: NSObject, PlatformFileManager, UIDocumentPickerDelegate {
    
    weak var presentingViewController: UIViewController?
    private var pickerDelegateStrongRef: FileManagerImpl?
    private var temporaryFileURL: URL?
    
    private enum CompletionHandler {
        case export((ExportResult) -> Void)
        case importFile((ImportResult) -> Void)
    }
    private var currentCompletionHandler: CompletionHandler?
    
    func downloadCSVWithContent(content: String, fileName: String, onCompletion: @escaping (ExportResult) -> Void) {
        self.currentCompletionHandler = .export(onCompletion)
        
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
                self.presentPicker(for: [fileURL], fileName: fileName, presenter: presenter, mode: .exportFile)
            }
        } catch {
            let errorResult = ExportResult(status: .failure, message: "Failed to create temporary file: \(error.localizedDescription)")
            onCompletion(errorResult)
            cleanup()
        }
    }
    
    func importCSV(onCompletion: @escaping (ImportResult) -> Void) {
        self.currentCompletionHandler = .importFile(onCompletion)
        
        guard let presenter = presentingViewController else {
            let errorResult = ImportResult(status: .failure, message: "Import failed: UI context not available (UIViewController not set).", fileContent: nil)
            onCompletion(errorResult)
            return
        }
        
        DispatchQueue.main.async {
            self.presentPicker(for: [], fileName: nil, presenter: presenter, mode: .importFile)
        }
    }
    
    private enum PickerMode {
        case exportFile
        case importFile
    }
    
    private func presentPicker(for urls: [URL], fileName: String?, presenter: UIViewController, mode: PickerMode) {
        let documentPicker: UIDocumentPickerViewController
        let contentTypes: [UTType] = [.data, .plainText, .commaSeparatedText, .text]
        
        switch mode {
        case .exportFile:
            documentPicker = UIDocumentPickerViewController(forExporting: urls, asCopy: true)
        case .importFile:
            documentPicker = UIDocumentPickerViewController(forOpeningContentTypes: contentTypes, asCopy: true)
            documentPicker.allowsMultipleSelection = false
        }
        
        documentPicker.delegate = self
        self.pickerDelegateStrongRef = self
        documentPicker.shouldShowFileExtensions = true
        
        presenter.present(documentPicker, animated: true)
    }
    
    
    func documentPicker(_ controller: UIDocumentPickerViewController, didPickDocumentsAt urls: [URL]) {
        guard let pickedURL = urls.first else {
            switch self.currentCompletionHandler {
            case .export(let completion):
                completion(ExportResult(status: .failure, message: "File save destination chosen, but no URL returned."))
            case .importFile(let completion):
                completion(ImportResult(status: .failure, message: "File selection completed, but no URL returned.", fileContent: nil))
            case .none:
                break
            }
            cleanup()
            return
        }
        
        if temporaryFileURL != nil {
            let result = ExportResult(status: .success, message: "File successfully saved to: \(pickedURL.lastPathComponent)")
            if case .export(let completion) = self.currentCompletionHandler {
                completion(result)
            }
        } else {
            var fileContent: String?
            var message: String
            var status: FileActionStatus = .failure
            
            let didStartAccessing = pickedURL.startAccessingSecurityScopedResource()
            
            if didStartAccessing {
                do {
                    fileContent = try String(contentsOf: pickedURL, encoding: .utf8)
                    status = .success
                    message = "File selected: \(pickedURL.lastPathComponent)"
                } catch {
                    message = "Failed to read file content: \(error.localizedDescription)"
                }
                pickedURL.stopAccessingSecurityScopedResource()
            } else {
                message = "Failed to gain security-scoped access to the file."
            }
            
            let result = ImportResult(status: status, message: message, fileContent: fileContent)
            if case .importFile(let completion) = self.currentCompletionHandler {
                completion(result)
            }
        }
        cleanup()
    }
    
    func documentPickerWasCancelled(_ controller: UIDocumentPickerViewController) {
        switch self.currentCompletionHandler {
        case .export(let completion):
            let result = ExportResult(status: .canceled, message: "File export canceled by user.")
            completion(result)
        case .importFile(let completion):
            let result = ImportResult(status: .canceled, message: "File selection canceled by user.", fileContent: nil)
            completion(result)
        case .none:
            break
        }
        cleanup()
    }
    
    private func cleanup() {
        if let url = temporaryFileURL {
            do {
                try FileManager.default.removeItem(at: url)
            } catch {
            }
        }
        self.temporaryFileURL = nil
        self.pickerDelegateStrongRef = nil
        self.currentCompletionHandler = nil
    }
}
