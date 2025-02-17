import Foundation
import ComposeApp
import Security

class KeyGeneratorImpl: NSObject, KeyGenerator {
    
    func generateSecretKey() -> KotlinByteArray {
        let keySize: Int32 = 32 // 256-bit AES key
        var keyData = [UInt8](repeating: 0, count: Int(keySize))
        
        let result = SecRandomCopyBytes(kSecRandomDefault, Int(keySize), &keyData)
        
        if result == errSecSuccess {
            return KotlinByteArray(size: keySize) { index in
                return KotlinByte(value: Int8(bitPattern: keyData[Int(truncating: index)]))
            }
        } else {
            fatalError("Failed to generate secure random key")
        }
    }
    
    func generateKeyWithPBKDF(password: String) -> KotlinByteArray {
        return KotlinByteArray(size: 2) { index in
            return index == 0 ? 0x00 : 0x01 // TODO for testing, replace with implementation
        }
    }
    
}
