import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.PrintWriter
import java.net.ConnectException
import java.net.NetworkInterface
import java.net.ServerSocket
import java.net.Socket

class Mithrundeal(val networkPassKey: String, val port: Int = 57611) {

    private val dataManager: DataManager = DataManager()
    private var cryptoManager: CryptoManager? = null

    private val localAddress : String = detectLocalAddress()
    private val localAddressParts = localAddress.split(".")
    private val localAddressLastPart : Int = Integer.parseInt(localAddressParts[localAddressParts.lastIndex].toString())

    private val localAddressPrefix : String = "${localAddressParts[0]}.${localAddressParts[1]}.${localAddressParts[2]}"

    init {
        //Server & Client Setup
    }

    public fun start() {
        cryptoManager = CryptoManager()
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
        while(true) {
            val processCode = reader.readLine()
            println("Code -> $processCode")
            when(processCode) {
                "100" -> {
                    val publicKey = reader.readLine()
                    println("100 Connection Handshake")
                    println("public key -> $publicKey")
                }
                else -> println("Unknown Process Code!")
            }


        }

    }

    var serverSocket: Socket? = null
    fun connectToServer(IPLastPart: Int) {
        try {
            serverSocket = Socket(localAddressPrefix+"."+IPLastPart,  port)
            println(localAddressPrefix+"."+IPLastPart+":"+port)
            val output = PrintWriter(serverSocket!!.getOutputStream(), true)
            output.println(100)
            output.println()
        }catch (e: ConnectException){
            println(e.message + " -> "  + IPLastPart)
        }

    }

    private var connectionThreads : MutableList<Thread> = mutableListOf()
    private fun scanLocalNetwork() {

        while (serverSocket != null)

        for (x in 2..254) {

            /*if(x == localAddressLastPart)
                    continue*/


            Thread{
                connectToServer(x)
            }.start()

            //connectionThreads.last().start()
        }
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