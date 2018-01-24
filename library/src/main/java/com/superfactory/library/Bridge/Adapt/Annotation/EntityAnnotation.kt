package com.superfactory.library.Bridge.Adapt.Annotation

/**
 * Created by vicky on 2018/1/24.
 */
import kotlin.reflect.KClass

@Target(AnnotationTarget.CLASS, AnnotationTarget.FILE)
@Retention(AnnotationRetention.RUNTIME)
annotation class EntityAnnotation(val entityClass: KClass<*>)