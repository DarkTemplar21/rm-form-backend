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
}