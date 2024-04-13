package com.example.myapplication.models

data class FinalObjetiveForUser(val id_user: String,
                                val date_register: String,
                                val weigh_register: Int,
                                val per_adb: Int,
                                val per_hip: Int,
                                val per_neck: Int,
                                val index_imc: Double,
                                val index_imm: Double,
                                val index_ica: Double,
                                val index_perc_bfat: Double){

    fun toMap(): MutableMap<String, Any>{
        return mutableMapOf(
            "id_user" to this.id_user,
            "date_register" to this.date_register,
            "weigh_register" to this.weigh_register,
            "per_adb" to this.per_adb,
            "per_hip" to this.per_hip,
            "per_neck" to this.per_neck,
            "index_imc" to this.index_imc,
            "index_imm" to this.index_imm,
            "index_ica" to this.index_ica,
            "index_perc_bfat" to this.index_perc_bfat
        )
    }
}
