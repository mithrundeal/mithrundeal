import model.Client

class DataManager {
    val clients: List<Client> = listOf<Client>()

    init {
        // Setup Database System
    }

    public fun getClientByIP(IPLastPart:Int) : Client {
        //For Init, Delete it
        return Client(IPLastPart=0, publicKey = "")
    }
    public fun getClientByPublicKey(publicKey:String) : Client {
        //For Init, Delete it
        return Client(IPLastPart=0, publicKey = "")
    }

    public fun getClientList() : List<Client>{
        //For Init, Delete it
        return listOf()
    }

    public fun saveClient(client: Client){}

    public fun deleteClientByIP(IPLastPart:Int){}
    public fun deleteClient(client: Client){}

    public fun deleteAllClients(){}
}