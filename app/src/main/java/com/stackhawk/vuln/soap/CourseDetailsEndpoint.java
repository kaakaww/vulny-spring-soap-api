package com.stackhawk.vuln.soap;


import com.stackhawk.vuln.soap.bean.Course;
import com.stackhawk.vuln.soap.service.CourseDetailsService;
import com.stackhawk.vuln.soap.service.CourseDetailsService.Status;
import com.stackhawk.vulnsoap.CourseDetails;
import com.stackhawk.vulnsoap.DeleteCourseDetailsRequest;
import com.stackhawk.vulnsoap.DeleteCourseDetailsResponse;
import com.stackhawk.vulnsoap.GetAllCourseDetailsRequest;
import com.stackhawk.vulnsoap.GetAllCourseDetailsResponse;
import com.stackhawk.vulnsoap.GetCourseDetailsRequest;
import com.stackhawk.vulnsoap.GetCourseDetailsResponse;
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

	// http://www.stackhawk.com/vulnsoap
	// GetCourseDetailsRequest
	@PayloadRoot(namespace = "http://www.stackhawk.com/vulnsoap", localPart = "GetCourseDetailsRequest")
	@ResponsePayload
	public GetCourseDetailsResponse processCourseDetailsRequest(@RequestPayload GetCourseDetailsRequest request) {
		System.out.println("Getting course " + request.getName());
		List<Course> course = service.findByName(request.getName());
		return mapCourseDetails(course);
	}

	private GetCourseDetailsResponse mapCourseDetails(List<Course> courses) {
		GetCourseDetailsResponse response = new GetCourseDetailsResponse();
		for (Course course : courses) {
			CourseDetails mapCourse = mapCourse(course);
			response.getCourseDetails().add(mapCourse);
		}
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

	@PayloadRoot(namespace = "http://www.stackhawk.com/vulnsoap", localPart = "GetAllCourseDetailsRequest")
	@ResponsePayload
	public GetAllCourseDetailsResponse processAllCourseDetailsRequest(
			@RequestPayload GetAllCourseDetailsRequest request) {
		System.out.println("Getting all courses...");
		List<Course> courses = service.findAll();

		return mapAllCourseDetails(courses);
	}

	@PayloadRoot(namespace = "http://www.stackhawk.com/vulnsoap", localPart = "DeleteCourseDetailsRequest")
	@ResponsePayload
	public DeleteCourseDetailsResponse deleteCourseDetailsRequest(@RequestPayload DeleteCourseDetailsRequest request) {

		Status status = service.deleteById(request.getId());

		DeleteCourseDetailsResponse response = new DeleteCourseDetailsResponse();
		response.setStatus(mapStatus(status));

		return response;
	}

	private com.stackhawk.vulnsoap.Status mapStatus(Status status) {
		if (status == Status.FAILURE)
			return com.stackhawk.vulnsoap.Status.FAILURE;
		return com.stackhawk.vulnsoap.Status.SUCCESS;
	}
}
