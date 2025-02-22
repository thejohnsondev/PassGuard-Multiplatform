import Foundation
import ComposeApp
import Security
import CommonCrypto

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
        let keyLength = 32 // Same as Android
        let iterations = 1000
        let salt = password.data(using: .utf8)! // Using password as salt (like Android)

        if let derivedKey = pbkdf2(
            algorithm: CCPBKDFAlgorithm(kCCPBKDF2),
            password: password,
            salt: salt,
            iterations: iterations,
            keyLength: keyLength
        ) {
            return KotlinByteArray(size: Int32(keyLength)) { index in
                KotlinByte(value: Int8(truncating: derivedKey[Int(truncating: index)] as NSNumber))
            }
        } else {
            fatalError("PBKDF2 key derivation failed")
        }
    }
    
    func pbkdf2(
        algorithm: CCPBKDFAlgorithm,
        password: String,
        salt: Data,
        iterations: Int,
        keyLength: Int
    ) -> Data? {
        let passwordData = password.data(using: .utf8)!
        var derivedKey = Data(count: keyLength)

        let status = derivedKey.withUnsafeMutableBytes { derivedKeyBytes in
            salt.withUnsafeBytes { saltBytes in
                CCKeyDerivationPBKDF(
                    algorithm,
                    password, passwordData.count,
                    saltBytes.baseAddress?.assumingMemoryBound(to: UInt8.self), salt.count,
                    CCPseudoRandomAlgorithm(kCCPRFHmacAlgSHA256),
                    UInt32(iterations),
                    derivedKeyBytes.baseAddress?.assumingMemoryBound(to: UInt8.self), keyLength
                )
            }
        }

        return status == kCCSuccess ? derivedKey : nil
    }
    
}
