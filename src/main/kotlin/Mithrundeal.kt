import io.ktor.network.selector.*
import io.ktor.network.sockets.*
import io.ktor.utils.io.*
import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.NetworkInterface
import kotlin.concurrent.fixedRateTimer

class Mithrundeal(val networkPassKey: String, val port: Int = 57611) {

    private val dataManager: DataManager = DataManager()
    private val cryptoManager: CryptoManager

    private val selectorManager = SelectorManager(Dispatchers.IO)
    private val serverSocket = aSocket(selectorManager).tcp().bind("0.0.0.0", port)

    private val localAddress: String = detectLocalAddress()
    private val localAddressParts = localAddress.split(".")
    private val localAddressLastPart:Int = Integer.parseInt(localAddressParts[localAddressParts.lastIndex].toString())

    private val localAddressPrefix: String = "${localAddressParts[0]}.${localAddressParts[1]}.${localAddressParts[2]}"

    init {
        //Server & Client Setup
        cryptoManager = CryptoManager()
    }

    public fun start() {
        runBlocking {
            async { startServer() }
            async { scanLocalNetwork() }
        }
    }
    public fun close(){}

    fun startServer() = runBlocking {
        println("Server Started!")
        async {
            while (true) {
                try {
                    val socket = serverSocket.accept()
                    println("Accepted ${socket.localAddress}")
                    launch {
                        val receiveChannel = socket.openReadChannel()
                        val sendChannel = socket.openWriteChannel(autoFlush = true)
                        try {
                            while (true) {
                                val name = receiveChannel.readUTF8Line()
                                println(name)
                            }

                        } catch (e: Throwable) {
                            socket.close()
                        }
                    }
                }catch (e:Exception)
                {
                    println(e.message)
                }
            }
        }
    }

    suspend fun connectToClient(IPLastPart: Int): Socket? {
        val remoteClientIP = localAddressPrefix+"."+IPLastPart
        //println("Trying connect $remoteClientIP")
        try {
            val socket = aSocket(selectorManager).tcp().connect(remoteClientIP, port)
            println("Trying connect /$remoteClientIP:$port")

            val sendChannel = socket.openWriteChannel(autoFlush = true)
            //sendChannel.writeFully((cryptoManager.getPublicKey()+"\n\r").toByteArray())

            return socket
        }catch (e: ConnectException) {
            println(e.message)
        }

        return null
    }

    private val connections: MutableList<Deferred<Socket?>> = mutableListOf()
    private fun scanLocalNetwork() = runBlocking{

        for (x in 1..254) {

            /*if(x == localAddressLastPart)
                    continue*/

            val newSocket: Deferred<Socket?> = async<Socket?> {
                val socket:Socket? = connectToClient(x)
                return@async socket
            }
            newSocket.invokeOnCompletion {
                connections.remove(newSocket)
            }
            connections.add(newSocket)
        }

    }

    private fun detectLocalAddress():String{
        for (networkInterface in NetworkInterface.getNetworkInterfaces()) {
            if(networkInterface.interfaceAddresses.size > 0){
                for (interfaceAddress in networkInterface.interfaceAddresses) {
                    if(interfaceAddress.address.hostAddress.contains("192.168"))
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