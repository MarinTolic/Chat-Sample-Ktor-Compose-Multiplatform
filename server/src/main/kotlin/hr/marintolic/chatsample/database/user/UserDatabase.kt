package hr.marintolic.chatsample.database.user

import hr.marintolic.chatsample.database.user.model.User
import hr.marintolic.chatsample.database.user.model.Users
import hr.marintolic.chatsample.utils.getLocalProperties
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * Used to connect to the user database and perform transactions.
 */
object UserDatabase {
    /**
     * The user database.
     */
    private val userDatabase: Database = createDatabase()

    /**
     * Initializer block.
     */
    init {
        createUserTable()
    }

    /**
     * Initializes the database.
     *
     * Call this function only once as multiple calls to it will create memory leaks.
     *
     * @return The database containing users.
     */
    private fun createDatabase(): Database {
        val databaseProperties: Map<String, String> = getLocalProperties()

        // Map<K,V>.getValue() will cause a crash if no such value exists, which is preferable to a silent failure here
        val dbName = databaseProperties.getValue(DB_NAME)
        val dbPassword = databaseProperties.getValue(DB_PASSWORD)
        val dbUserName = databaseProperties.getValue(DB_USER_NAME)
        val dbUserPassword = databaseProperties.getValue(DB_USER_PASSWORD)

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
     * The name of the PostgreSQL database.
     */
    private const val DB_NAME = "user_db_name"

    /**
     * The password to the PostgreSQL database.
     */
    private const val DB_PASSWORD = "user_db_password"

    /**
     * The name of the PostgreSQL user given the privilege of accessing the database.
     */
    private const val DB_USER_NAME = "user_db_user_name"

    /**
     * The password of the PostgreSQL user given the privilege of accessing the database.
     */
    private const val DB_USER_PASSWORD = "user_db_user_password"

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