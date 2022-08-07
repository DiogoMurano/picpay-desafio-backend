package br.com.diogomurano.picpay.common.adapter.repository.configuration

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.type.EnumType
import java.sql.PreparedStatement
import java.sql.Types

class PostgreSQLEnumType : EnumType<Enum<*>>() {

    override fun nullSafeSet(
        st: PreparedStatement,
        value: Any?,
        index: Int,
        session: SharedSessionContractImplementor
    ) {
        val nameValue = if (value != null) {
            (value as Enum<*>).name
        } else {
            null
        }

        st.setObject(index, nameValue, Types.OTHER)
    }

}
