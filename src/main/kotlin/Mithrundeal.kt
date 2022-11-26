import crypto.AESHelper
import crypto.CryptoUtility.Companion.decodeHex
import model.AESCryptedData
import model.Client
import model.TransferData
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket

class Mithrundeal(val networkPassKey: String, val port: Int = 57611) {

    private val dataManager: DataManager = DataManager()
    private val dataTransferManager: DataTransferManager = DataTransferManager()
    private val cryptoManager: CryptoManager = CryptoManager()
    private val localAddress : String = detectLocalAddress()
    private val localAddressParts = localAddress.split(".")
    private val localAddressLastPart : Int = Integer.parseInt(localAddressParts[localAddressParts.lastIndex].toString())

    private val localAddressPrefix : String = "${localAddressParts[0]}.${localAddressParts[1]}.${localAddressParts[2]}"

    init {
        //Server & Client Setup
    }

    public fun start() {
        Thread { startServer() }.start()
        scanLocalNetwork()
    }
    public fun close(){}

    fun startServer() {
        println("Server Started!")
        val server = ServerSocket(port)
        while (true) {
            val socket = server.accept()            // accept a connection

            if(socket == null)
                continue

            Thread{ handleClient(socket) }.start()  // create a thread to deal with the client
        }
    }

    private fun handleClient(socket: Socket?) {

        if(socket == null)
            return

        println((socket.localAddress?.hostAddress ?: "Unknown IP") + " - Accepted for handshake.")
        val reader = BufferedReader(InputStreamReader(socket.inputStream))
        val writer = PrintWriter(socket.getOutputStream(), true)
        while(true) {
            val jsonData = reader.readLine()
            dataTransferManager.doFinal(jsonData, reader, writer, socket, cryptoManager)
        }
    }

    var serverSocket: Socket? = null
    fun connectToServer(IPLastPart: Int) {

        try {
            println(localAddressPrefix+"."+IPLastPart+":"+port)
            serverSocket = Socket(localAddressPrefix+"."+IPLastPart,  port)

            val writer = PrintWriter(serverSocket!!.getOutputStream(), true)
            writer.println(TransferData(100, cryptoManager.getPublicKey()).toJSON())

            val reader = BufferedReader(InputStreamReader(serverSocket!!.inputStream))
            while(true) {
                val jsonData = reader.readLine()
                dataTransferManager.doFinal(jsonData, reader, writer, serverSocket!!, cryptoManager)
            }
        }catch (e: ConnectException) {
            println(e.message + " -> "  + IPLastPart)
        }

    }

    private fun scanLocalNetwork() {

        for (x in 2..254) {

            if(x == localAddressLastPart)
                    continue

            Thread{
                connectToServer(x)
            }.start()

            //connectionThreads.last().start()
        }
    }


    fun peerList() : List<Client> {
        return dataTransferManager.activeSockets.toList()
    }

    fun sendData(client: Client , data: String) {
        val aesKey = AESHelper.constructKey(client.AESKey.decodeHex())
        val encrptedData =  AESHelper.ctrEncrypt(aesKey, data.toByteArray())
        val aesCryptedData = AESCryptedData(iv = encrptedData[0], encryptedData = encrptedData[1].toString())
        client.writer.println(TransferData(200, aesCryptedData.toJSON()));
    }

    private fun detectLocalAddress():String{
        for (networkInterface in NetworkInterface.getNetworkInterfaces()) {
            if(networkInterface.interfaceAddresses.size > 0){
                for (interfaceAddress in networkInterface.interfaceAddresses) {
                    if(interfaceAddress.address.hostAddress.contains("172.16"))
                    {
                        return interfaceAddress.address.hostAddress
                        break;
                    }
                }
            }
        }
        return String()
    }
}