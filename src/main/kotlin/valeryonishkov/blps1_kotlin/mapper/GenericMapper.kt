package valeryonishkov.blps1_kotlin.mapper

import com.mapk.kmapper.KMapper
import java.util.concurrent.ConcurrentHashMap
import kotlin.reflect.KClass
import kotlin.reflect.full.primaryConstructor

class GenericMapper {

    private val mapperCache = ConcurrentHashMap<KClass<*>, KMapper<Any>>()

    @Suppress("UNCHECKED_CAST")
    fun <T: Any> map(src: Any, targetClass: KClass<T>): T {
        val mapper = mapperCache.computeIfAbsent(targetClass) { cls ->
            val ctor = cls.primaryConstructor ?: throw IllegalArgumentException("У класса $cls нет primaryConstructor")
            KMapper(ctor) as KMapper<Any>
        }
        return mapper.map(src) as T
    }

    inline infix fun <reified T : Any> Any.mapTo(src: Any) = map(src, T::class)
}