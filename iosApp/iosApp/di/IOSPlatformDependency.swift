import Foundation
import UIKit
import ComposeApp

class IOSPlatformDependency: NSObject, PlatformDependency {
    
    private let fileManagerInstance = FileManagerImpl()
    func setPresentingViewController(viewController: UIViewController) {
        self.fileManagerInstance.presentingViewController = viewController
    }
    
    func getKeyGenerator() -> KeyGenerator {
        return KeyGeneratorImpl()
    }
    
    func getEncryptionUtils() -> EncryptionUtils {
        return EncryptionUtilsImpl()
    }
    
    func getSecureStorage() -> SecureStorage {
        return SecureStorageImpl()
    }
    
    func getClipboardUtils() -> ClipboardUtils {
        return ClipboardUtilsImpl()
    }
    
    func getFileManager() -> PlatformFileManager {
        return fileManagerInstance
    }
    
}
