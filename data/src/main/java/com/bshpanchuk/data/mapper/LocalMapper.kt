package com.bshpanchuk.data.mapper

import com.bshpanchuk.data.local.db.table.PlaceRoom
import com.bshpanchuk.domain.model.Place

fun Place.toDb(): PlaceRoom {
    return PlaceRoom(
        id = id,
        lat = lat,
        lon = lon,
        label = label
    )
}

fun PlaceRoom.toDomain(): Place {
    return Place(
        id = id,
        lat = lat,
        lon = lon,
        label = label
    )
}