package com.richmeat.data.form


import com.richmeat.data.coldRoom.ColdRoom
import com.richmeat.data.coldRoom.ColdRooms
import com.richmeat.data.coldRoom.ColdRooms.in_range
import com.richmeat.data.coldRoom.ColdRooms.is_on
import com.richmeat.data.coldRoom.ColdRooms.is_reviewed
import com.richmeat.data.coldRoom.ColdRooms.name
import com.richmeat.data.coldRoom.ColdRooms.temperature_form_id
import com.richmeat.data.coldRoom.ColdRooms.temperature_range
import com.richmeat.data.form.TemperatureForms.created_by
import com.richmeat.data.form.TemperatureForms.created_date

import com.richmeat.data.form.TemperatureForms.id

import com.richmeat.data.form.TemperatureForms.reviewed_by
import com.richmeat.data.form.TemperatureForms.reviewed_date
import com.richmeat.data.form.TemperatureForms.status
import com.richmeat.data.util.DateHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.joda.time.DateTime
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter


class FormService {
    private suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    private fun toForm(row: ResultRow) =
        FormDTO(
            id = row[id],
            createdDate = row[created_date].toString("dd-MM-yyyy HH:mm:ss"),
            reviewedDate = row[reviewed_date].toString("dd-MM-yyyy HH:mm:ss"),
            status = row[status],
            createdBy = row[created_by],
            reviewedBy = row[reviewed_by],
            coldRooms = mutableListOf()
        )

    private fun toColdRoom(row: ResultRow) =
        ColdRoom(
            temperatureFormId = row[temperature_form_id],
            name = row[name],
            temperatureRange = row[temperature_range],
            isOn = row[is_on],
            inRange = row[in_range],
            isReviewed = row[is_reviewed]
        )

    suspend fun getUserFormById(userName: String,formId: Int): FormDTO = dbQuery {
        return@dbQuery transaction {
            val temperatureForm = TemperatureForms.select(created_by eq userName and (id eq formId)).map {
                toForm(it)
            }.first()
            temperatureForm.coldRooms = ColdRooms.select { temperature_form_id eq temperatureForm.id }.map {
                toColdRoom(it)
            }
            return@transaction temperatureForm
        }
    }

    suspend fun getAllUserForms(userName: String): List<FormDTO> = dbQuery {
        return@dbQuery transaction {
            val temperatureForm = TemperatureForms.select { created_by eq userName }
                .map {
                    toForm(it)
                }
            temperatureForm.forEach { mTemperatureForm ->
                mTemperatureForm.coldRooms = ColdRooms.select { temperature_form_id eq mTemperatureForm.id }.map {
                    toColdRoom(it)
                }
            }
            return@transaction temperatureForm

        }
    }

    fun insertForm(form: FormDTO, userName: String) {
        transaction {
            val formatter: DateTimeFormatter = DateTimeFormat.forPattern(DateHelper.DATE_FORMAT)
            val createdDate: DateTime = DateTime.now()
            transaction {
                val insertedTemperatureFormId = TemperatureForms.insert {
                    it[created_date] = createdDate
                    it[reviewed_date] = createdDate
                    it[status] = form.status
                    it[created_by] = userName
                    it[reviewed_by] = form.reviewedBy
                }[id]
                form.coldRooms.forEach { coldRoom ->
                    ColdRooms.insert {
                        it[temperature_form_id] = insertedTemperatureFormId
                        it[name] = coldRoom.name
                        it[is_on] = coldRoom.isOn
                        it[in_range] = coldRoom.inRange
                        it[is_reviewed] = coldRoom.isReviewed
                        it[temperature_range] = coldRoom.temperatureRange
                    }
                }

            }
        }
    }
}