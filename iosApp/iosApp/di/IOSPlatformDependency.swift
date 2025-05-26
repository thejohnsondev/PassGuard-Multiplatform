import Foundation
import ComposeApp

class IOSPlatformDependency: NSObject, PlatformDependency {
    
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
        return FileManagerImpl()
    }
    
}
