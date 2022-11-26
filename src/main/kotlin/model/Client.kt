package model

import java.net.Socket

data class Client(
    var IPLastPart: Int = 0,
    var publicKey: String = "test",
    var name: String = "Unknown Client",
    var AESKey: String,
    var socket: Socket,
) {

}