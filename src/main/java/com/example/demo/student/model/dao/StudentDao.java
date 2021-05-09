package com.example.demo.student.model.dao;

import java.time.LocalDate;
import java.time.Month;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.demo.student.model.vo.Student;

@Repository
public interface StudentDao extends JpaRepository<Student, Long> {

	//select * from student where email = ?
	//Student는 테이블 명이 아니라 클래스명
	@Query("select s from Student s where s.email = ?1")
	Optional<Student> findStudentByEmail(String email);
}
