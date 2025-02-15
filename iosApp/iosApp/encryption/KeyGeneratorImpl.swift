import Foundation
import ComposeApp

class KeyGeneratorImpl: NSObject, KeyGenerator {
    
    func generateSecretKey() -> KotlinByteArray {
        return KotlinByteArray(size: 2) { index in
            return index == 0 ? 0x00 : 0x01 // TODO for testing, replace with implementation
        }
    }
    
    func generateKeyWithPBKDF(password: String) -> KotlinByteArray {
        return KotlinByteArray(size: 2) { index in
            return index == 0 ? 0x00 : 0x01 // TODO for testing, replace with implementation
        }
    }
    
}
