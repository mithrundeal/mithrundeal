import com.google.gson.Gson
import model.Client
import java.sql.Connection
import java.sql.DatabaseMetaData
import java.sql.DriverManager
import java.sql.SQLException

class DataManager {
    companion object {
        private var clients: List<Client> = listOf<Client>()
        private var connection: Connection? = null

        init {
            try {
                connection = DriverManager.getConnection("jdbc:sqlite::memory:")
                connection!!.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS clients(ip_last_part INTEGER, name TEXT, public_key TEXT)")
                println("DB Connected.")
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
        }

        fun getClientByIP(IPLastPart: Int): Client {
            val sql = "SELECT * FROM clients WHERE ip_last_part = $IPLastPart"
            val rs = connection!!.createStatement().executeQuery(sql)
            connection!!.close()
            return Client(
                IPLastPart = rs.getInt("ip_last_part"),
                name = rs.getString("name"),
                publicKey = rs.getString("public_key")
            )
        }

        fun getClientByPublicKey(publicKey: String): Client {
            val sql = "SELECT * FROM clients WHERE public_key = ?"
            val preparedStatement = connection!!.prepareStatement(sql)
            preparedStatement.setString(1, publicKey)
            val rs = preparedStatement.executeQuery()
            connection!!.close()
            return Client(
                IPLastPart = rs.getInt("ip_last_part"),
                name = rs.getString("name"),
                publicKey = rs.getString("public_key")
            )
        }

        fun getClientList(): List<Client> {
            val sql = "SELECT * FROM clients"
            val rs = connection!!.createStatement().executeQuery(sql)
            while (rs.next()) {
                val client = Client(
                    IPLastPart = rs.getInt("ip_last_part"),
                    name = rs.getString("name"),
                    publicKey = rs.getString("public_key")
                )
                clients += client
            }
            connection!!.close()
            return clients
        }

        fun getClientsListToJSON(): String? {
            return Gson().toJson(getClientList())
        }

        fun getClientsJSONToList(clientsJSON: String): List<Client>{
            return Gson().fromJson(clientsJSON, Array<Client>::class.java).asList()
        }

        fun getClientToJSON(client: Client): String? {
            return Gson().toJson(client)
        }

        fun getJSONToClient(clientJSON: String): Client{
            return Gson().fromJson(clientJSON, Client::class.java)
        }

        fun saveClient(client: Client) {
            val sql = "INSERT INTO clients values(${client.IPLastPart}, ?, ?)"
            val preparedStatement = connection!!.prepareStatement(sql)
            preparedStatement.setString(1, client.name)
            preparedStatement.setString(2, client.publicKey)
            preparedStatement.executeUpdate()
            connection!!.close()
        }

        fun deleteClientByIP(IPLastPart: Int) {
            val sql = "DELETE FROM clients WHERE ip_last_part = $IPLastPart"
            connection!!.createStatement().executeUpdate(sql)
            connection!!.close()
        }

        fun deleteClient(client: Client) {
            val sql = "DELETE FROM clients WHERE public_key = ?"
            val preparedStatement = connection!!.prepareStatement(sql)
            preparedStatement.setString(1, client.publicKey)
            preparedStatement.executeUpdate()
            connection!!.close()
        }

        fun deleteAllClients() {
            val sql = "TRUNCATE TABLE clients"
            connection!!.createStatement().executeUpdate(sql)
            connection!!.close()
        }

        fun getDatabaseInfo() {
            println("Connected Database Info")
            val dm = connection!!.metaData as DatabaseMetaData
            println("Driver name: " + dm.driverName)
            println("Driver version: " + dm.driverVersion)
            println("Product name: " + dm.databaseProductName)
            println("Product version: " + dm.databaseProductVersion)
            connection!!.close()
        }
    }
}