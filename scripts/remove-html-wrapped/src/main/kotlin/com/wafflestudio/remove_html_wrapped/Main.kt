package com.wafflestudio.remove_html_wrapped

import com.wafflestudio.generated.jooq.tables.references.NEWS
import com.wafflestudio.generated.jooq.tables.references.NOTICE
import com.wafflestudio.generated.jooq.tables.references.SEMINAR
import com.wafflestudio.shared.db.mustGetDBConnection
import io.github.oshai.kotlinlogging.KotlinLogging
import org.jooq.*
import org.jooq.exception.DataAccessException
import org.jooq.impl.DSL
import org.jooq.impl.TableImpl
import org.jsoup.Jsoup
import org.jsoup.nodes.Element

private val logger = KotlinLogging.logger {}

fun main() {
    val dbConn = mustGetDBConnection()

    logger.info { "Start removing HTML tags..."}
    dbConn.use {
        val dslContext = DSL.using(it, SQLDialect.MYSQL)

        // News
        logger.info { "news" }
        extractBodyQueryExecution(
            dslContext,
            NEWS,
            NEWS.ID,
            NEWS.DESCRIPTION,
        )

        // Seminar - description
        logger.info { "seminar - description" }
        extractBodyQueryExecution(
            dslContext,
            SEMINAR,
            SEMINAR.ID,
            SEMINAR.DESCRIPTION,
        )

        // Seminar - introduction
        logger.info { "seminar - introduction" }
        extractBodyQueryExecution(
            dslContext,
            SEMINAR,
            SEMINAR.ID,
            SEMINAR.INTRODUCTION,
        )

        // Seminar - additional note
        logger.info { "seminar - additional note" }
        extractBodyQueryExecution(
            dslContext,
            SEMINAR,
            SEMINAR.ID,
            SEMINAR.ADDITIONAL_NOTE,
        )

        // Notice
        logger.info { "notice" }
        extractBodyQueryExecution(
            dslContext,
            NOTICE,
            NOTICE.ID,
            NOTICE.DESCRIPTION,
        )
    }
}

fun <R: Record, T: TableImpl<R>> extractBodyQueryExecution(
    dslContext: DSLContext,
    table: T,
    idField: TableField<R, Long?>,
    descriptionField: TableField<R, String?>,
) {
    val cursor = dslContext.selectFrom(table)
        .whereHtmlClause(descriptionField)
        .orderBy(idField.desc())
        .fetchLazy()

    cursor.forEach { col ->
        val description = col.get(descriptionField.name) as String?
        val id = col.get(idField.name) as Long?

        try {
            description?.let {
                dslContext.update(table)
                    .set(descriptionField, extractOnlyBody(it))
                    .where(idField.eq(id))
                    .execute()
            }
            logger.debug { "Updated notice id: ${id}" }
        } catch (e: DataAccessException) {
            logger.error { "Failed to update notice id: ${id}, error: ${e.message}" }
        }
    }
}

fun <R: Record> SelectWhereStep<R>.whereHtmlClause(descriptionField: TableField<R, String?>): SelectConditionStep<R> {
    return this.where(descriptionField.like("""<html>%"""))
        .and(descriptionField.like("""%</html>"""))
}

fun extractOnlyBody(original: String): String {
    val document = Jsoup.parse(original)
    val bodies = document.select("body")

    return when (bodies.size) {
        0 -> document.html()
        1 -> bodies.first()!!.html()
        else -> {
            val div = Element("div")
            bodies.flatMap { it.children() }
                .let { div.appendChildren(it) }
                .outerHtml()
        }
    }
}
