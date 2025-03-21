package com.wafflestudio.shared.db

import java.sql.Connection
import java.sql.DriverManager

// TODO: make db connection info configurable by environment variables
fun mustGetDBConnection(): Connection {
    try {
        return DriverManager.getConnection(
            "jdbc:mysql://localhost:3306/csereal?useUnicode=true&characterEncoding=utf8&serverTimezone=Asia/Seoul",
            "root",
            "password"
        )
    } catch (e: Exception) {
        e.printStackTrace()
        throw RuntimeException("Failed to connect to database")
    }
}