package com.stackhawk.vuln.soap.example;

import com.stackhawk.courses.CourseDetails;
import com.stackhawk.courses.DeleteCourseDetailsRequest;
import com.stackhawk.courses.DeleteCourseDetailsResponse;
import com.stackhawk.courses.GetAllCourseDetailsRequest;
import com.stackhawk.courses.GetAllCourseDetailsResponse;
import com.stackhawk.courses.GetCourseDetailsRequest;
import com.stackhawk.courses.GetCourseDetailsResponse;
import com.stackhawk.vuln.soap.example.bean.Course;
import com.stackhawk.vuln.soap.example.exception.CourseNotFoundException;
import com.stackhawk.vuln.soap.example.service.CourseDetailsService;
import com.stackhawk.vuln.soap.example.service.CourseDetailsService.Status;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class CourseDetailsEndpoint {

	@Autowired
	CourseDetailsService service;

	// method
	// input - GetCourseDetailsRequest
	// output - GetCourseDetailsResponse

	// http://stackhawk.com/courses
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://stackhawk.com/courses", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {

		Course course = service.findById(request.getId());

		if (course == null)
			throw new CourseNotFoundException("Invalid Course Id " + request.getId());

		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(Course course) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		response.setCourseDetails(mapCourse(course));
		return response;
	}

	private GetAllCourseDetailsResponse mapAllCourseDetails(List<Course> courses) {
		GetAllCourseDetailsResponse response = new GetAllCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
		return response;
	}

	private CourseDetails mapCourse(Course course) {
		CourseDetails courseDetails = new CourseDetails();

		courseDetails.setId(course.getId());

		courseDetails.setName(course.getName());

		courseDetails.setDescription(course.getDescription());
		return courseDetails;
	}

	@PayloadRoot(namespace = "http://stackhawk.com/courses", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {

		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://stackhawk.com/courses", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));

		return response;
	}

	private com.stackhawk.courses.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.stackhawk.courses.Status.FAILURE;
		return com.stackhawk.courses.Status.SUCCESS;
	}
}
