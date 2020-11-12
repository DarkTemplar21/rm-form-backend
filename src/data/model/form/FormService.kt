package com.richmeat.data.model.form

import com.richmeat.data.model.form.Forms.anden_1y2_in_range
import com.richmeat.data.model.form.Forms.anden_1y2_on
import com.richmeat.data.model.form.Forms.anden_1y2_reviewed
import com.richmeat.data.model.form.Forms.anden_3y4_in_range
import com.richmeat.data.model.form.Forms.anden_3y4_on
import com.richmeat.data.model.form.Forms.anden_3y4_reviewed
import com.richmeat.data.model.form.Forms.atemperado_mp_in_range
import com.richmeat.data.model.form.Forms.atemperado_mp_on
import com.richmeat.data.model.form.Forms.atemperado_mp_reviewed
import com.richmeat.data.model.form.Forms.conservacion_mp_in_range
import com.richmeat.data.model.form.Forms.conservacion_mp_on
import com.richmeat.data.model.form.Forms.conservacion_mp_reviewed
import com.richmeat.data.model.form.Forms.conservacion_pt_in_range
import com.richmeat.data.model.form.Forms.conservacion_pt_on
import com.richmeat.data.model.form.Forms.conservacion_pt_reviewed
import com.richmeat.data.model.form.Forms.created_by
import com.richmeat.data.model.form.Forms.created_date
import com.richmeat.data.model.form.Forms.empaque_in_range
import com.richmeat.data.model.form.Forms.empaque_on
import com.richmeat.data.model.form.Forms.empaque_reviewed
import com.richmeat.data.model.form.Forms.pasillo_in_range
import com.richmeat.data.model.form.Forms.pasillo_on
import com.richmeat.data.model.form.Forms.pasillo_reviewed
import com.richmeat.data.model.form.Forms.preenfriamiento_pt_in_range
import com.richmeat.data.model.form.Forms.preenfriamiento_pt_on
import com.richmeat.data.model.form.Forms.preenfriamiento_pt_reviewed
import com.richmeat.data.model.form.Forms.proceso_in_range
import com.richmeat.data.model.form.Forms.proceso_on
import com.richmeat.data.model.form.Forms.proceso_reviewed
import com.richmeat.data.model.form.Forms.reviewed_by
import com.richmeat.data.model.form.Forms.reviewed_date
import com.richmeat.data.model.form.Forms.status
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction


class FormService {
    private suspend fun <T> dbQuery(block: () -> T): T =
        withContext(Dispatchers.IO) {
            transaction { block() }
        }

    private fun toForm(row: ResultRow) =
        Form(
            created_date = row[created_date],
            reviewed_date = row[reviewed_date],
            status = row[status],
            created_by = row[created_by],
            reviewed_by = row[reviewed_by],
            anden_1y2_on = row[anden_1y2_on],
            anden_1y2_in_range = row[anden_1y2_in_range],
            anden_1y2_reviewed = row[anden_1y2_reviewed],
            conservacion_mp_on = row[conservacion_mp_on],
            conservacion_mp_in_range = row[conservacion_mp_in_range],
            conservacion_mp_reviewed = row[conservacion_mp_reviewed],
            conservacion_pt_on = row[conservacion_pt_on],
            conservacion_pt_in_range = row[conservacion_pt_in_range],
            conservacion_pt_reviewed = row[conservacion_pt_reviewed],
            anden_3y4_on = row[anden_3y4_on],
            anden_3y4_reviewed = row[anden_3y4_reviewed],
            anden_3y4_in_range = row[anden_3y4_in_range],
            pasillo_on = row[pasillo_on],
            pasillo_in_range = row[pasillo_in_range],
            pasillo_reviewed = row[pasillo_reviewed],
            empaque_on = row[empaque_on],
            empaque_reviewed = row[empaque_reviewed],
            empaque_in_range = row[empaque_in_range],
            preenfriamiento_pt_on = row[preenfriamiento_pt_on],
            preenfriamiento_pt_in_range = row[preenfriamiento_pt_in_range],
            preenfriamiento_pt_reviewed = row[preenfriamiento_pt_reviewed],
            proceso_on = row[proceso_on],
            proceso_in_range = row[proceso_in_range],
            proceso_reviewed = row[proceso_reviewed],
            atemperado_mp_on = row[atemperado_mp_on],
            atemperado_mp_in_range = row[atemperado_mp_in_range],
            atemperado_mp_reviewed = row[atemperado_mp_reviewed]
        )

    suspend fun getAllForms(): List<Form> = dbQuery {
        return@dbQuery Forms.selectAll().map {
            toForm(it)
        }
    }

    suspend fun insertForm(form: Form) {
        transaction {
            Forms.insert {
                it[created_date] = form.created_date
                it[reviewed_date] = form.reviewed_date
                it[status] = form.status
                it[created_by] = form.created_by
                it[reviewed_by] = form.reviewed_by
                it[anden_1y2_on] = form.anden_1y2_on
                it[anden_1y2_in_range] = form.anden_1y2_in_range
                it[anden_1y2_reviewed] = form.anden_1y2_reviewed
                it[conservacion_mp_on] = form.conservacion_mp_on
                it[conservacion_mp_in_range] = form.conservacion_mp_in_range
                it[conservacion_mp_reviewed] = form.conservacion_mp_reviewed
                it[conservacion_pt_on] = form.conservacion_pt_on
                it[conservacion_pt_in_range] = form.conservacion_pt_in_range
                it[conservacion_pt_reviewed] = form.conservacion_pt_reviewed
                it[anden_3y4_on] = form.anden_3y4_on
                it[anden_3y4_reviewed] = form.anden_3y4_reviewed
                it[anden_3y4_in_range] = form.anden_3y4_in_range
                it[pasillo_on] = form.pasillo_on
                it[pasillo_in_range] = form.pasillo_in_range
                it[pasillo_reviewed] = form.pasillo_reviewed
                it[empaque_on] = form.empaque_on
                it[empaque_reviewed] = form.empaque_reviewed
                it[empaque_in_range] = form.empaque_in_range
                it[preenfriamiento_pt_on] = form.preenfriamiento_pt_on
                it[preenfriamiento_pt_in_range] = form.preenfriamiento_pt_in_range
                it[preenfriamiento_pt_reviewed] = form.preenfriamiento_pt_reviewed
                it[proceso_on] = form.proceso_on
                it[proceso_in_range] = form.proceso_in_range
                it[proceso_reviewed] = form.proceso_reviewed
                it[atemperado_mp_on] = form.atemperado_mp_on
                it[atemperado_mp_in_range] = form.atemperado_mp_in_range
                it[atemperado_mp_reviewed] = form.atemperado_mp_reviewed
            }
        }
    }
}