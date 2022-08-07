package br.com.diogomurano.picpay.common.adapter.repository

import org.hibernate.annotations.CreationTimestamp
import org.hibernate.annotations.UpdateTimestamp
import org.springframework.data.util.ProxyUtils
import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

/**
 * See the reference in https://kotlinexpertise.com/hibernate-with-kotlin-spring-boot/
 */
@MappedSuperclass
abstract class AbstractJpaPersistable<T : Serializable> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: T? = null

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    var createdAt: LocalDateTime = LocalDateTime.now()

    @Column(name = "updated_at", nullable = false)
    @UpdateTimestamp
    var updatedAt: LocalDateTime = LocalDateTime.now()

    override fun equals(other: Any?): Boolean {
        other ?: return false

        if (this === other) {
            return true
        }

        if (javaClass != ProxyUtils.getUserClass(other)) {
            return false
        }

        other as AbstractJpaPersistable<*>

        return if (null == this.id) {
            false
        } else {
            this.id == other.id
        }
    }

    override fun hashCode(): Int {
        return 31
    }

    override fun toString() = "Entity of type ${this.javaClass.name} with id: $id"

    companion object {
        private const val serialVersionUID = -5554308939380869754L
    }

}
