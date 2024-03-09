# 👓 Overview

This is a Kotlin Multiplatform project demonstrating a small, rudimentary chat application created for the sake of a public lecture on Ktor. 
As such, this application does not follow certain best practices such as password encryption, so please do not use it as a foundation for another application.

# 🔧 Setting up the Application

## PostgreSQL

This application uses PostgreSQL, so before you can run the app, you need to set PostreSQL.

To do so, do the following steps

1) Install PostgreSQL on your system. This is platform specific so this README won't go into further detail.
2) Clone the project and into your local Gradle properties (local.properties) file add the following values:
   - user_db_name=\<The name of the PostgreSQL database\>
   - user_db_user_name=\<The name of the PostgreSQL user given the privilege of accessing the database\>
   - user_db_user_password=\<The password of the PostgreSQL user given the privilege of accessing the database\>
3) Make sure that the PostgreSQL server is up and running. (platform dependant, `systemctl status postgresql` on Linux)


-----

This is a Kotlin Multiplatform project targeting Android, iOS, Web, Desktop, Server.

* `/composeApp` is for code that will be shared across your Compose Multiplatform applications.
  It contains several subfolders:
  - `commonMain` is for code that’s common for all targets.
  - Other folders are for Kotlin code that will be compiled for only the platform indicated in the folder name.
    For example, if you want to use Apple’s CoreCrypto for the iOS part of your Kotlin app,
    `iosMain` would be the right folder for such calls.

* `/iosApp` contains iOS applications. Even if you’re sharing your UI with Compose Multiplatform, 
  you need this entry point for your iOS app. This is also where you should add SwiftUI code for your project.

* `/server` is for the Ktor server application.

* `/shared` is for the code that will be shared between all targets in the project.
  The most important subfolder is `commonMain`. If preferred, you can add code to the platform-specific folders here too.


Learn more about [Kotlin Multiplatform](https://www.jetbrains.com/help/kotlin-multiplatform-dev/get-started.html),
[Compose Multiplatform](https://github.com/JetBrains/compose-multiplatform/#compose-multiplatform),
[Kotlin/Wasm](https://kotl.in/wasm/)…

**Note:** Compose/Web is Experimental and may be changed at any time. Use it only for evaluation purposes.
We would appreciate your feedback on Compose/Web and Kotlin/Wasm in the public Slack channel [#compose-web](https://slack-chats.kotlinlang.org/c/compose-web).
If you face any issues, please report them on [GitHub](https://github.com/JetBrains/compose-multiplatform/issues).

You can open the web application by running the `:composeApp:wasmJsBrowserDevelopmentRun` Gradle task.