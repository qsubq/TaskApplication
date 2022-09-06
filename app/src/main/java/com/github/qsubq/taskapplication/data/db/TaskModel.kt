package com.github.qsubq.taskapplication.data.db

import io.realm.RealmObject
import io.realm.annotations.RealmClass
import io.realm.annotations.Required
import io.realm.kotlin.types.annotations.PrimaryKey

@RealmClass
open class TaskModel : RealmObject() {

    @PrimaryKey
    var id: String? = null

    @Required
    var name: String? = null

    var color: String? = null
    var timeStart: Int = 0
    var timeFinish: Int = 0
    var date: String? = null
    var description: String? = null
}