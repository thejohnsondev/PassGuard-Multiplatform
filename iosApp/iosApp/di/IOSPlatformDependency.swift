import Foundation
import ComposeApp

class IOSPlatformDependency: NSObject, PlatformDependency {
    func getKeyGenerator() -> KeyGenerator {
        return KeyGeneratorImpl()
    }
}
