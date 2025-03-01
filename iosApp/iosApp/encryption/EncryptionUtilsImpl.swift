import Foundation
import ComposeApp
import CommonCrypto


class EncryptionUtilsImpl: NSObject, EncryptionUtils {
    
    func encrypt(input: String, key: KotlinByteArray, iv: KotlinByteArray?) -> String {
        let keyData = Data((0..<key.size).map { UInt8(truncatingIfNeeded: key.get(index: $0)) })
        
        // Use the provided IV or derive it from the key's first 16 bytes
        let ivData = iv != nil
            ? Data((0..<iv!.size).map { UInt8(truncatingIfNeeded: iv!.get(index: $0)) })
            : keyData.prefix(kCCBlockSizeAES128) // Use the first 16 bytes of the key if IV is nil
        
        guard let inputData = input.data(using: .utf8) else { return "" }

        // Create a separate buffer for the encrypted data
        var encryptedBytes = [UInt8](repeating: 0, count: inputData.count + kCCBlockSizeAES128)
        var numBytesEncrypted: size_t = 0

        // Perform the encryption
        let status = keyData.withUnsafeBytes { keyBytes in
            ivData.withUnsafeBytes { ivBytes in
                inputData.withUnsafeBytes { inputBytes in
                    CCCrypt(
                        CCOperation(kCCEncrypt),
                        CCAlgorithm(kCCAlgorithmAES),
                        CCOptions(kCCOptionPKCS7Padding),
                        keyBytes.baseAddress, kCCKeySizeAES256,
                        ivBytes.baseAddress,
                        inputBytes.baseAddress, inputData.count,
                        &encryptedBytes, encryptedBytes.count,
                        &numBytesEncrypted
                    )
                }
            }
        }

        // If encryption was successful, return the base64-encoded string
        if status == kCCSuccess {
            let encryptedData = Data(encryptedBytes.prefix(numBytesEncrypted)) // Trim excess bytes
            return encryptedData.base64EncodedString()
        } else {
            return "" // Encryption failed
        }
    }

    func decrypt(input: String, key: KotlinByteArray, iv: KotlinByteArray?) -> String {
        let keyData = Data((0..<key.size).map { UInt8(truncatingIfNeeded: key.get(index: $0)) })

        // Use provided IV or derive it from the key's first 16 bytes
        let ivData = iv != nil
            ? Data((0..<iv!.size).map { UInt8(truncatingIfNeeded: iv!.get(index: $0)) })
            : keyData.prefix(kCCBlockSizeAES128)

        // Decode Base64 input
        guard let encryptedData = Data(base64Encoded: input) else { return "" }

        // Create a buffer for the decrypted data
        var decryptedBytes = [UInt8](repeating: 0, count: encryptedData.count + kCCBlockSizeAES128)
        var numBytesDecrypted: size_t = 0

        // Perform decryption
        let status = keyData.withUnsafeBytes { keyBytes in
            ivData.withUnsafeBytes { ivBytes in
                encryptedData.withUnsafeBytes { encryptedBytes in
                    CCCrypt(
                        CCOperation(kCCDecrypt),
                        CCAlgorithm(kCCAlgorithmAES),
                        CCOptions(kCCOptionPKCS7Padding),
                        keyBytes.baseAddress, kCCKeySizeAES256,
                        ivBytes.baseAddress,
                        encryptedBytes.baseAddress, encryptedData.count,
                        &decryptedBytes, decryptedBytes.count,
                        &numBytesDecrypted
                    )
                }
            }
        }

        // If decryption was successful, convert the result to a string
        if status == kCCSuccess {
            let decryptedData = Data(decryptedBytes.prefix(numBytesDecrypted)) // Trim excess bytes
            return String(data: decryptedData, encoding: .utf8) ?? "" // Convert to string
        } else {
            return "" // Decryption failed
        }
    }

}
