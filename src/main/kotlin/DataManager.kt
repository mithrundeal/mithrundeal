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
            val sJdbc = "jdbc:sqlite:"
            val dbPath = ""
            val dbName = "data.db"
            try {
                connection = DriverManager.getConnection(sJdbc + dbPath + dbName)
                connection!!.createStatement().executeUpdate("CREATE TABLE IF NOT EXISTS clients(ip_last_part INTEGER, name TEXT, public_key TEXT)")
                println("DB Connected.")
            } catch (ex: SQLException) {
                ex.printStackTrace()
            }
        }

        fun getClientByIP(IPLastPart: Int): Client {
            val sql = "SELECT * FROM clients WHERE ip_last_part = $IPLastPart"
            val rs = connection!!.createStatement().executeQuery(sql)
            return Client(
                IPLastPart = rs.getInt("ip_last_part"),
                name = rs.getString("name"),
                publicKey = rs.getString("public_key")
            )
        }

        fun getClientByPublicKey(publicKey: String): Client {
            val sql = "SELECT * FROM clients WHERE public_key = $publicKey"
            val rs = connection!!.createStatement().executeQuery(sql)
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
            return clients
        }

        fun saveClient(client: Client) {
            val sql = "INSERT INTO clients values(${client.IPLastPart}, '${client.name}', '${client.publicKey}')"
            connection!!.createStatement().executeUpdate(sql)
            connection!!.close()
        }

        fun deleteClientByIP(IPLastPart: Int) {
            val sql = "DELETE FROM clients WHERE ip_last_part = $IPLastPart"
            connection!!.createStatement().executeUpdate(sql)
            connection!!.close()
        }

        fun deleteClient(client: Client) {
            val sql = "DELETE FROM clients WHERE public_key = ${client.publicKey}"
            connection!!.createStatement().executeUpdate(sql)
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