package com.example.demo.student.model.service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import javax.persistence.Transient;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.student.model.dao.StudentDao;
import com.example.demo.student.model.vo.Student;

@Service
public class StudentService {

	private final StudentDao studentDao;

	@Autowired
	public StudentService(StudentDao studentDao) {
		this.studentDao = studentDao;
	}

	public List<Student> getStudents() {

		return studentDao.findAll();
	}

	public void addNewStudent(Student student) {
		Optional<Student> studentOptional = studentDao.findStudentByEmail(student.getEmail());
		if (studentOptional.isPresent()) {
			throw new IllegalStateException("email taken");
		}
		studentDao.save(student);
		System.out.println(student);
	}

	public void deleteStudent(Long studentId) {

		boolean exists = studentDao.existsById(studentId);
		if (!exists) {
			throw new IllegalStateException("student with id " + studentId + " dose not exists");
		}
		studentDao.deleteById(studentId);
	}

	@Transactional
	public void updateStudent(Long studentId, String name, String email) {
		Student student = studentDao.findById(studentId)
				.orElseThrow(() -> new IllegalStateException("student with id " + studentId + " dose not exists"));

		if (name != null && name.length() > 0 && !Objects.equals(student.getName(), name)) {
			student.setName(name);
		}
		if (email != null && email.length() > 0 && !Objects.equals(student.getEmail(), email)) {
			Optional<Student> studentOptional = studentDao.findStudentByEmail(email);

			if (studentOptional.isPresent()) {
				throw new IllegalStateException("email taken");
			}
			student.setEmail(email);
		}

	}

}
