import crypto.AESHelper
import crypto.CryptoUtility.Companion.decodeHex
import crypto.CryptoUtility.Companion.toHexString
import crypto.RSAHelper
import model.AESCryptedData
import model.Client
import model.TransferData
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class DataTransferManager {


    var activeSockets : MutableList<Client> = mutableListOf()
    var publicKeys: HashMap<Socket, String> = HashMap<Socket, String>()
    var AESKeys: HashMap<Socket, String> = HashMap<Socket, String>()

    fun doFinal(jsonData : String, reader: BufferedReader, writer: PrintWriter, socket: Socket, selfCryptoManager: CryptoManager) {
        val transferData: TransferData = TransferData.fromJSON(jsonData)
        when(transferData.processCode) {
            100 -> {
                //Client to server handshake request.s

                if(transferData.data == null)
                    return

                publicKeys.put(socket, transferData.data!!)

                println("100 Connection Handshake")
                println("public key -> ${transferData.data}")

                val response = TransferData(101, selfCryptoManager.getPublicKey())
                writer.println(response.toJSON())
            }
            101 -> {
                //Handshake response.
                if(transferData.data == null)
                    return

                publicKeys.put(socket, transferData.data!!)

                println("101 Handshake Response")
                println("public key -> ${transferData.data}")

                val aesKey = AESHelper.getRandomAESKey()

                val clientRSAPublicKey = RSAHelper.generatePublicKey(publicKeys.get(socket)!!.decodeHex())
                val aesKeyCrypted = RSAHelper.rsaEncrypt(clientRSAPublicKey, aesKey.encoded)
                val aesKeyCryptedHex = aesKeyCrypted.toHexString()

                activeSockets.add(Client(socket = socket, AESKey = aesKey.encoded.toHexString(), writer = writer))

                AESKeys.put(socket, aesKeyCryptedHex)
                println("SEND AES -> ${aesKey.encoded.toHexString()}")
                writer.println(TransferData(102, aesKeyCryptedHex).toJSON())
            }
            102 -> {
                //AES Key

                if(transferData.data == null)
                    return

                val clientAESKey = transferData.data!!


                println("AES KEY -> $clientAESKey")

                val hexAESKey = RSAHelper.rsaDecrypt(selfCryptoManager.getPrivateKey(), clientAESKey.decodeHex())
                println("AES KEY -> ${hexAESKey.toHexString()}")
                AESKeys.put(socket, hexAESKey.toString())
                activeSockets.add(Client(socket = socket, AESKey = hexAESKey.toString(), writer = writer))
            }
            200 -> {
                //Encrypted data transfer

                if(transferData.data == null)
                    return

                val aesCryptedData = AESCryptedData.fromJSON(transferData.data!!)

                println("Data Transfer AES Key")
                println(AESKeys.get(socket)!!)
                println(aesCryptedData.iv)
                println(aesCryptedData.encryptedData.decodeHex())

                val hexAES = AESHelper.constructKey(AESKeys.get(socket)!!.decodeHex())

                val aesDecryption = AESHelper.ctrDecrypt(
                    hexAES,
                    aesCryptedData.iv,
                    aesCryptedData.encryptedData.decodeHex()
                )

                println(aesDecryption.toString())
            }
            else -> println("Unknown Process Code!")
        }
    }
}