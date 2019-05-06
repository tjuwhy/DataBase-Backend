package com.example

data class CommonBody(val err_code :String,val message:String,val data:Any?)
data class RecordBean(val sid:String ,val cid:Int,val grade:Int)
data class CourseBean(val cid:Int,val cname:String,val tname:String,val credit:Int,val suitable_grade:Int)
data class StudentBean(val sid: String,val sname:String,val gender:String,val addmission_age:Int,val addmission_year:Int,val class_num :Int)
data class StudentAverageBean(val sname: String,val sid: Int,val grade: Float)
data class ClassAverageBean(val class_num: Int,val grade: Float)
data class CourseAverageBean(val cname: String,val cid: Int,val grade: Float)
data class DistributionBean(val cname: String,val cid: Int,val rate_u60:Int,val rate_u70:Int,val rate_u80:Int,val rate_u90: Int,val rate_u100:Int)
