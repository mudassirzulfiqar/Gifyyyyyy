package com.moodi.data.mapper

/**
 * This interface is used to map the data from one layer to another
 */
interface DomainMapper<Entity, Domain> {
    fun asAppModel(domain: Entity): Domain
}
