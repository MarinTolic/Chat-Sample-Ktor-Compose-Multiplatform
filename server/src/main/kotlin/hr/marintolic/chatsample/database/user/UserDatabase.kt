package hr.marintolic.chatsample.database.user

import hr.marintolic.chatsample.EnvironmentVariables
import hr.marintolic.chatsample.database.user.model.User
import hr.marintolic.chatsample.database.user.model.Users
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Used to connect to the user database and perform transactions.
 */
object UserDatabase {

    /**
     * The default list of users that the app will add at launch if such do not already exist.
     */
    private val defaultChatUsers = listOf(
        User(
            username = "Stanley",
            password = "password"
        ),
        User(
            username = "Maurice",
            password = "password"
        ),
        User(
            username = "Laura",
            password = "password"
        ),
        User(
            username = "Susan",
            password = "password"
        ),
    )

    /**
     * The user database.
     */
    private val userDatabase: Database = createDatabase()

    /**
     * Initializes the database.
     */
    internal fun initialize() {
        createUserTable()
        addDefaultUsers()
    }

    /**
     * Adds the default users to the database if such do not already exist.
     */
    private fun addDefaultUsers() {
        defaultChatUsers.forEach {
            val doesUserExist = getUser(it.username) != null

            if (!doesUserExist) insertUser(it)
        }
    }

    /**
     * Initializes the database.
     *
     * Call this function only once as multiple calls to it will create memory leaks.
     *
     * @return The database containing users.
     */
    private fun createDatabase(): Database {
        // Map<K,V>.getValue() will cause a crash if no such value exists, which is preferable to a silent failure here
        val dbName = EnvironmentVariables.chatSampleUserDbName
        val dbUserName = EnvironmentVariables.chatSampleUserDbUserName
        val dbUserPassword = EnvironmentVariables.chatSampleUserDbUserPassword

        val jdbcUrl = createJdbcUrl(
            dbName = dbName
        )

        return Database.connect(
            url = jdbcUrl,
            driver = DRIVER_CLASS_NAME,
            user = dbUserName,
            password = dbUserPassword
        )
    }

    /**
     * Creates a Jdbc PostgreSQL URL from a templated string.
     *
     * @param dbName The name of the database.
     * @param host Where the database is hosted on. The default is [DEFAULT_HOST].
     * @param port The port used to connect to the database. The default is [DEFAULT_PORT].
     *
     * @return The resulting Jdbc URL.
     */
    private fun createJdbcUrl(
        dbName: String,
        host: String = DEFAULT_HOST,
        port: String = DEFAULT_PORT
    ) =
        "jdbc:postgresql://$host:$port/$dbName"

    /**
     * Creates a table of users if such doesn't exist.
     */
    private fun createUserTable() {
        transaction(userDatabase) {
            SchemaUtils.create(Users)
        }
    }

    /**
     * Inserts a new user into the database.
     *
     * @param user The user to be inserted into the database.
     */
    internal fun insertUser(user: User) {
        transaction {
            Users.insert {
                it[username] = user.username
                it[password] = user.password
            }
        }
    }

    /**
     * Updates an existing user's information in the database.
     *
     * @param user The user whose information will be updated.
     */
    internal fun updateUser(user: User) {
        transaction {
            Users.update {
                it[username] = user.username
                it[password] = user.password
            }
        }
    }

    /**
     * Fetches a user by username from the database if such exists.
     *
     * @param username The name of the user to be fetched.
     *
     * @return The user data if such exists, null otherwise.
     */
    internal fun getUser(username: String): User? {
        return transaction {
            Users.selectAll().where { Users.username eq username }
                .map {
                    User(
                        username = it[Users.username],
                        password = it[Users.password],
                    )
                }.firstOrNull()
        }
    }

    /**
     * The name of the database driver.
     */
    private const val DRIVER_CLASS_NAME = "org.postgresql.Driver"

    /**
     * The default host for the database.
     */
    private const val DEFAULT_HOST = "localhost"

    /**
     * The default port for the database.
     */
    private const val DEFAULT_PORT = "5432"
}

/**
 * Checks to see if the given user credentials are valid or not.
 *
 * @receiver The user whose credentials are to be validated.
 *
 * @return True if the credentials are valid, false otherwise.
 */
internal fun User.areCredentialsValid(): Boolean {
    val storedUser = UserDatabase.getUser(this.username) ?: return false

    return storedUser.password == this.password
}