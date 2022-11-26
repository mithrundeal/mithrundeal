
fun main(args: Array<String>) {

    var mithrundealNetwork: Mithrundeal = Mithrundeal("my-network-secret-separator")

    Thread {
        mithrundealNetwork.start()
    }.start()

    while (true) {
        val data = readln()
        for (client in mithrundealNetwork.peerList()) {
            mithrundealNetwork.sendData(client, data)
        }
    }




}

