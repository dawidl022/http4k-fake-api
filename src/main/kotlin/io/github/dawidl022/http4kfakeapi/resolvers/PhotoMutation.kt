package io.github.dawidl022.http4kfakeapi.resolvers

import io.github.dawidl022.http4kfakeapi.models.Photo
import io.github.dawidl022.http4kfakeapi.models.Photos

class PhotoMutation {
    fun createPhoto(photo: Photo) =
        Photos.create(photo)

    fun updatePhoto(id: Int, photo: Photo) =
        Photos.put(id, photo)

    fun deletePhoto(id: Int) =
        Photos.delete(id)
}
