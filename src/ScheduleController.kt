package com.example

import io.ktor.application.call
import io.ktor.http.ContentType
import io.ktor.response.respondText
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty

fun main() {

    val server = embeddedServer(Netty,8080){
        ScheduleModel.init()
        routing {
            get("/"){
                call.respondText("Hello world!",ContentType.Text.Plain)
            }
            get("/student"){
                call.respondText(ScheduleModel.getStudent(),ContentType.Text.Plain)
            }
            get("/studentbysid"){
                call.respondText(ScheduleModel.getStudentBySid(call.parameters["sid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/studentbysname"){
                call.respondText(ScheduleModel.getStudentBySname(call.parameters["sname"].orEmpty()),ContentType.Text.Plain)
            }
            get("/recordbysname") {
                call.respondText(ScheduleModel.getRecordBySname(call.parameters["sname"].orEmpty()),ContentType.Text.Plain)
            }
            get("/recordbysid") {
                println("rbs")
                println(call.parameters["sid"].orEmpty())
                call.respondText(ScheduleModel.getRecordBySid(call.parameters["sid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/recordbycname") {
                call.respondText(ScheduleModel.getRecordByCname(call.parameters["cname"].orEmpty()),ContentType.Text.Plain)
            }
            get("/recordbycid") {
                call.respondText(ScheduleModel.getRecordByCid(call.parameters["cid"].orEmpty()),ContentType.Text.Plain)
                println(ScheduleModel.getRecordByCid(call.parameters["cid"].orEmpty()))
            }
            get("/score"){
                call.respondText(ScheduleModel.getScore(call.parameters["sid"].orEmpty(),call.parameters["cid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/course"){
                call.respondText(ScheduleModel.getCourse(),ContentType.Text.Plain)
            }
            get("/coursebycid"){
                call.respondText(ScheduleModel.getCourseByCid(call.parameters["cid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/coursebycname"){
                call.respondText(ScheduleModel.getCourseByCname(call.parameters["cname"].orEmpty()),ContentType.Text.Plain)
            }
            get("/average/student"){
                call.respondText(ScheduleModel.getStudentAverageScore(call.parameters["sid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/average/class"){
                call.respondText(ScheduleModel.getClassAverageScore(),ContentType.Text.Plain)
            }
            get("/average/course"){
                call.respondText(ScheduleModel.getCourseAverageScore(),ContentType.Text.Plain)
            }
            get("/distribution"){
                call.respondText(ScheduleModel.getDistribution(call.parameters["cid"].orEmpty()),ContentType.Text.Plain)
            }
            get("/update/student/sname"){
                call.respondText(ScheduleModel.updateStudentName(call.parameters["sid"].orEmpty(), call.parameters["sname"].orEmpty()),ContentType.Text.Plain)
            }
            get("/update/student/gender"){
                call.respondText(ScheduleModel.updateStudentGender(call.parameters["sid"].orEmpty(), call.parameters["gender"].orEmpty()),ContentType.Text.Plain)
            }
            get("/update/student/add_year"){
                call.respondText(ScheduleModel.updateStudentAdmissionYear(call.parameters["sid"].orEmpty(), Integer.valueOf(call.parameters["add_year"])),ContentType.Text.Plain)
            }
            get("/update/student/add_age"){
                call.respondText(ScheduleModel.updateStudentAdmissionAge(call.parameters["sid"].orEmpty(), Integer.valueOf(call.parameters["add_age"])),ContentType.Text.Plain)
            }
            get("/update/student/class"){
                call.respondText(ScheduleModel.updateStudentClass(call.parameters["sid"].orEmpty(), Integer.valueOf(call.parameters["class"])),ContentType.Text.Plain)
            }
            get("/update/record/select_year"){
                call.respondText(ScheduleModel.updateRecordSelectYear(call.parameters["sid"].orEmpty(),call.parameters["cid"].orEmpty(), Integer.valueOf(call.parameters["sel_year"])),ContentType.Text.Plain)
            }
            get("/update/record/grade"){
                call.respondText(ScheduleModel.updateRecordSelectYear(call.parameters["sid"].orEmpty(),call.parameters["cid"].orEmpty(), Integer.valueOf(call.parameters["grade"])),ContentType.Text.Plain)
            }
            get("/update/course/name"){
                call.respondText(ScheduleModel.updateCourseName(call.parameters["cid"].orEmpty(),call.parameters["name"].orEmpty()),ContentType.Text.Plain)
            }
            get("/update/course/teacher_name"){
                var cid = ""
                var name = ""
                name = call.parameters["teacher_name"].orEmpty()
                cid = call.parameters["cid"].orEmpty()
                call.respondText(ScheduleModel.updateCourseTeacherName(cid,name),ContentType.Text.Plain)
            }
            get("/update/course/credit"){
                call.respondText(ScheduleModel.updateCourseCredit(call.parameters["cid"].orEmpty(),Integer.valueOf(call.parameters["credit"])),ContentType.Text.Plain)
            }
            get("/update/course/suitable_grade"){
                call.respondText(ScheduleModel.updateCourseSuitableGrade(call.parameters["cid"].orEmpty(),Integer.valueOf(call.parameters["suitable_grade"].orEmpty())),ContentType.Text.Plain)
            }
            get("/update/course/canceled_year"){
                call.respondText(ScheduleModel.updateCourseCanceledYear(call.parameters["cid"].orEmpty(),Integer.valueOf(call.parameters["canceled_year"].orEmpty())),ContentType.Text.Plain)
            }
            get("/add/student"){
                call.parameters.apply {
                    call.respondText(ScheduleModel.addStudent(get("sid").orEmpty(),get("sname").orEmpty(),get("gender").orEmpty(),Integer.valueOf(get("add_age")),Integer.valueOf(get("add_year")),Integer.valueOf(get("class_num"))),ContentType.Text.Plain)
                }
            }
            get("/add/course"){
                call.parameters.apply {
                    call.respondText(ScheduleModel.addCourse(get("cid").orEmpty(),get("cname").orEmpty(),get("teacher_name").orEmpty(),Integer.valueOf(get("credit")),Integer.valueOf(get("suitable_grade")),Integer.valueOf(get("canceled_year"))),ContentType.Text.Plain)
                }
            }
            get("/add/record"){
                call.parameters.apply {
                    call.respondText(ScheduleModel. addRecord(get("sid").orEmpty(),get("cid").orEmpty(),Integer.valueOf(get("select_year")),Integer.valueOf(get("grade"))),ContentType.Text.Plain)
                }
            }
            get("/delete/student"){
                call.parameters.apply {
                    call.respondText(ScheduleModel.deleteStudent(get("sid").orEmpty()),ContentType.Text.Plain)
                }
            }

            get("/delete/course"){
                call.parameters.apply {
                    call.respondText(ScheduleModel.deleteCourse(get("cid").orEmpty()),ContentType.Text.Plain)
                }
            }
            get("/delete/record"){
                call.parameters.apply {
                    call.respondText(ScheduleModel.deleteRecord(get("sid").orEmpty(),get("cid").orEmpty()),ContentType.Text.Plain)
                }
            }
            get("/login"){
                println("login")
                call.parameters.apply {
                    call.respondText(ScheduleModel.login(get("username").orEmpty(),get("pass").orEmpty()),ContentType.Text.Plain)
                }
            }
        }
    }
    server.start(wait=true)
}