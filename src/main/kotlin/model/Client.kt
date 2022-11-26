package model

import java.net.Socket

data class Client(
    var name: String = "Unknown Client",
    var AESKey: String,
    var socket: Socket,
) {

}