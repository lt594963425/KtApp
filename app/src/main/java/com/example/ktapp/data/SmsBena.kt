package com.example.ktapp.data

import java.io.Serializable

/**
 *
 * @ProjectName: KtApp
 * @Package: com.example.ktapp
 * @ClassName: SmsBena
 * @Description:
 * @Author: LiuTao
 * @CreateDate: 2020/4/27 16:27
 * @UpdateUser: LiuTao
 */

data class UserBean(
    val code: Int,
    val `data`: Data,
    val msg_customer: String,
    val msg_programmer: String
) : Serializable{
    data class Data(
        val adder: String,
        val area: String,
        val bind_num: Int,
        val comment: String,
        val company_name: String,
        val construction_costs: String,
        val contractor: String,
        val data_alter_time: String,
        val data_create_time: String,
        val is_bind: Int,
        val location: String,
        val location_type: String,
        val loge_image_path: String,
        val number: Int,
        val number_buildings: String,
        val pm_administrator: String,
        val pm_id: Int,
        val pm_name: String,
        val region: String,
        val start_time: String,
        val state: Int,
        val use_capacity: Int,
        val used_capacity: Int
    ) : Serializable

}

