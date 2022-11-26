import model.TransferData
import java.io.BufferedReader
import java.io.PrintWriter
import java.net.Socket

class DataTransferManager {

    companion object {
        var publicKeys: HashMap<Socket, String> = HashMap<Socket, String>()
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
                }
                200 -> {
                    //Encrypted data transfer
                }
                else -> println("Unknown Process Code!")
            }
        }

    }

}