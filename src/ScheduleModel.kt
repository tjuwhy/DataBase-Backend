package com.example

import com.google.gson.Gson
import java.sql.DriverManager
import java.sql.SQLException

object ScheduleModel {
    const val JDBC_DRIVER = "com.mysql.cj.jdbc.Driver"
    const val DB_URL = "jdbc:mysql://localhost:3306/mydb?serverTimezone=UTC&characterEncoding=UTF-8"
    const val DB_USER = "root"
    const val DB_PASSWORD = ""

    fun init() {
        Class.forName(JDBC_DRIVER);
    }

    fun getStudent():String {
        val res :CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM student s ")
        val rs = stmt.executeQuery()
        var result= mutableListOf<StudentBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    result.add(StudentBean(getString(1), getString(2), getString(3), getInt(4), getInt(5), getInt(6)))
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "failed", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getStudentBySid(sid: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM student s WHERE s.sid=?")
        stmt.setString(1, sid)
        val rs = stmt.executeQuery()
        var result: StudentBean
        res = if (rs.first()) {
            do {
                rs.apply {
                    result = StudentBean(getString(1), getString(2), getString(3), getInt(4), getInt(5), getInt(6))
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "没有找到学号为$sid 的同学", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getStudentBySname(sname: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM student s WHERE s.sname=?")
        stmt.setString(1, sname)
        val rs = stmt.executeQuery()
        var result= mutableListOf<StudentBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    result .add( StudentBean(getString(1), getString(2), getString(3), getInt(4), getInt(5), getInt(6)))
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "没有找到姓名为${sname}的同学", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getRecordBySid(sid: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM record r WHERE r.sid=?")
        stmt.setString(1, sid)
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<RecordBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(RecordBean(getString(1), getInt(2), getInt(4)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "没有找到学号为$sid 的同学", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getRecordBySname(sname: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT r.* FROM record r, student s WHERE s.sname=? ANd r.sid=s.sid")
        stmt.setString(1, sname)
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<RecordBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(RecordBean(getString(1), getInt(2), getInt(4)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "没有找到姓名为$sname 的同学", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getRecordByCid(cid: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM record r WHERE r.cid=?")
        stmt.setString(1, cid)
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<RecordBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(RecordBean(getString(1), getInt(2), getInt(4)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "没有找到课程编号为$cid 的课程", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getRecordByCname(cname: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT r.* FROM record r, course c WHERE c.cname=? ANd r.cid=c.cid")
        stmt.setString(1, cname)
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<RecordBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(RecordBean(getString(1), getInt(2), getInt(4)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "没有找到名字为$cname 的课程", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getScore(sid: String, cid: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT r.grade FROM record r WHERE r.sid=? ANd r.cid=?")
        stmt.setString(1, sid)
        stmt.setString(2, cid)
        var result: String
        val rs = stmt.executeQuery()
        res = if (rs.first()) {
            do {
                rs.apply {
                    result = getString(1)
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "在选课列表中没有找到学号为$sid,课程编号为${cid}的记录", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getCourse():String{
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM course c")
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<CourseBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(CourseBean(getInt(1), getString(2), getString(3), getInt(4), getInt(5)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "failed", null)
        }
        return Gson().toJson(res)
    }

    fun getCourseByCid(cid: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM course c WHERE c.cid=?")
        stmt.setString(1, cid)
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<CourseBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(CourseBean(getInt(1), getString(2), getString(3), getInt(4), getInt(5)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "没有找到课程号为${cid}的课程", null)
        }
        return Gson().toJson(res)
    }

    fun getCourseByCname(cname: String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT * FROM course c WHERE c.cname=?")
        stmt.setString(1, cname)
        val rs = stmt.executeQuery()
        var result: CourseBean
        res = if (rs.first()) {
            do {
                rs.apply {
                    result = CourseBean(getInt(1), getString(2), getString(3), getInt(4), getInt(5))
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "没有找到课程名为${cname}的课程", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getStudentAverageScore(sid:String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT AVG(r.grade) FROM Record r WHERE r.sid =? ")
        stmt.setString(1,sid)
        val rs = stmt.executeQuery()
        var result = ""
        res = if (rs.first()) {
            do {
                rs.apply {
                    result= getFloat(1).toString()
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getClassAverageScore(): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt =
            conn.prepareStatement("SELECT s.class,AVG(r.grade) FROM Record r,Student s WHERE r.sid = s.sid GROUP BY s.class")
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<ClassAverageBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(ClassAverageBean(getInt(1), getFloat(2)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getCourseAverageScore(): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement(
            "SELECT c.cname, r.cid,AVG(r.grade) \n" +
                    "FROM Record r,Course c\n" +
                    "WHERE r.cid = c.cid\n" +
                    "GROUP BY r.cid,c.cname"
        )
        val rs = stmt.executeQuery()
        val resultList = mutableListOf<CourseAverageBean>()
        res = if (rs.first()) {
            do {
                rs.apply {
                    resultList.add(CourseAverageBean(getString(1), getInt(2), getFloat(3)))
                }
            } while (rs.next())
            CommonBody("0", "ok", resultList.toTypedArray())
        } else {
            CommonBody("-1", "", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun getDistribution(cid :String): String {
        val res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement(
            "SELECT c.cname, r.cid, SUM(case when r.grade <60 then 1 else 0 end),SUM(case when r.grade >=60 and r.grade < 70 then 1 else 0 end),SUM(case when r.grade >=70 and r.grade < 80 then 1 else 0 end),SUM(case when r.grade >=80 and r.grade < 90 then 1 else 0 end),SUM(case when r.grade >=90 and r.grade <= 100 then 1 else 0 end)\n" +
                    "FROM Record r,Course c\n" +
                    "WHERE r.cid = c.cid AND c.cid=?\n" +
                    "GRoup BY r.cid"
        )
        stmt.setString(1,cid)
        val rs = stmt.executeQuery()
        var result:DistributionBean
        res = if (rs.first()) {
            do {
                rs.apply {
                    result =
                        DistributionBean(
                            getString(1),
                            getInt(2),
                            getInt(3),
                            getInt(4),
                            getInt(5),
                            getInt(6),
                            getInt(7)
                        )
                }
            } while (rs.next())
            CommonBody("0", "ok", result)
        } else {
            CommonBody("-1", "", null)
        }
        rs.close()
        stmt.close()
        conn.close()
        return Gson().toJson(res)
    }

    fun updateStudentName(sid: String, sname: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE student s SET s.sname=? WHERE s.sid=?")
        stmt.setString(1, sname)
        stmt.setString(2, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateStudentGender(sid: String, gender: String): String {
        var res: CommonBody
        if (!gender.equals("男") && !gender.equals("女")) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE student s SET s.gender=? WHERE s.sid=?")
        stmt.setString(1, gender)
        stmt.setString(2, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateStudentAdmissionAge(sid: String, add_age: Int): String {
        var res: CommonBody
        if (add_age < 10 || add_age > 50) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE student s SET s.addmission_age=? WHERE s.sid=?")
        stmt.setInt(1, add_age)
        stmt.setString(2, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateStudentAdmissionYear(sid: String, add_year: Int): String {
        var res: CommonBody
        if (add_year < 2000 || add_year > 2020) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE student s SET s.addmission_year=? WHERE s.sid=?")
        stmt.setInt(1, add_year)
        stmt.setString(2, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateStudentClass(sid: String, class_num: Int): String {
        var res: CommonBody
        if (class_num < 0 || class_num > 5) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE student s SET s.class=? WHERE s.sid=?")
        stmt.setInt(1, class_num)
        stmt.setString(2, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateRecordSelectYear(sid: String, cid: String, sel_year: Int): String {
        var res: CommonBody
        if (sel_year < 2000 || sel_year > 2020) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE record c SET c.select_year=?  WHERE c.sid=? AND c.cid=?")
        stmt.setInt(1, sel_year)
        stmt.setString(2, sid)
        stmt.setString(3, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateRecordGrade(cid: String, sid: String, grade: Float): String {
        var res: CommonBody
        if (grade < 0 || grade > 100) {
            return Gson().toJson(CommonBody("-1", "参数错误", null))
        }
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE record r SET r.grade=? WHERE r.sid=? AND r.cid=?")
        stmt.setFloat(1, grade)
        stmt.setString(2, sid)
        stmt.setString(3, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateCourseName(cid: String, name: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE course c SET c.cname=? WHERE c.cid=?")
        stmt.setString(1, name)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateCourseTeacherName(cid: String, tname: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE course c SET c.teacher_name=? WHERE c.cid=?")
        stmt.setString(1, tname)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateCourseCredit(cid: String, credit: Int): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE course c SET c.credit=? WHERE c.cid=?")
        stmt.setInt(1, credit)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateCourseSuitableGrade(cid: String, grade: Int): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE course c SET c.suitable_grade=? WHERE c.cid=?")
        stmt.setInt(1, grade)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun updateCourseCanceledYear(cid: String, year: Int): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("UPDATE course c SET c.canceled_year=? WHERE c.cid=?")
        stmt.setInt(1, year)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun addStudent(sid: String, sname: String, gender: String, add_age: Int, add_year: Int, class_num: Int): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("INSERT INTO student values (?,?,?,?,?,?)")
        stmt.setString(1, sid)
        stmt.setString(2, sname)
        stmt.setString(3, gender)
        stmt.setInt(4, add_age)
        stmt.setInt(5, add_year)
        stmt.setInt(6, class_num)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun addCourse(
        cid: String,
        cname: String,
        teacher_name: String,
        credit: Int,
        suitable_grade: Int,
        canceled_year: Int
    ): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("INSERT INTO course values (?,?,?,?,?,?)")
        stmt.setString(1, cid)
        stmt.setString(2, cname)
        stmt.setString(3, teacher_name)
        stmt.setInt(4, credit)
        stmt.setInt(5, suitable_grade)
        stmt.setInt(6, canceled_year)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun addRecord(sid: String, cid: String, select_year: Int, grade: Int): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("INSERT INTO record values (?,?,?,?)")
        stmt.setString(1, sid)
        stmt.setString(2, cid)
        stmt.setInt(3, select_year)
        stmt.setInt(4, grade)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }


    fun deleteCourse(cid: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("DELETE FROM course where cid=?")
        stmt.setString(1, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun deleteRecord(sid: String, cid: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("DELETE FROM record where sid=? AND cid=?")
        stmt.setString(1, sid)
        stmt.setString(2, cid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun deleteStudent(sid: String): String {
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("DELETE FROM student WHERE sid=?")
        stmt.setString(1, sid)
        try {
            stmt.execute()
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        res = CommonBody("0", "修改成功", null)
        return Gson().toJson(res)
    }

    fun login(username: String, pass: String) :String{
        var res: CommonBody
        val conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)
        val stmt = conn.prepareStatement("SELECT COUNT(*) FROM login WHERE username=? AND pass=?")
        stmt.setString(1,username)
        stmt.setString(2,pass)
        var count = 0;
        try {
            val rs = stmt.executeQuery()
            rs.first()
            count = rs.getInt(1)
        } catch (e: SQLException) {
            res = CommonBody("-1", e.toString(), null)
            stmt.close()
            conn.close()
            return Gson().toJson(res)
        } finally {
            stmt.close()
            conn.close()
        }
        if (count==0){
            res = CommonBody("0", "Failed", null)
        } else {
            res = CommonBody("0","Success",null)
        }
        return Gson().toJson(res)
    }
}