package lib.domainsupport.valueobject

/**
 * 値オブジェクトの基底クラス
 */
open class ValueObject<T>(
    val value: T
) {
    
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ValueObject<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }

    override fun toString(): String {
        return value.toString()
    }
}