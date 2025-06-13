package ucd.fs.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import ucd.fs.model.User;

@FeignClient(name = "employee-profile", url = "${services.employee-profile-url}")
public interface EmployeeProfileClient {
    @GetMapping("/users/{id}")
    User getUserById(@PathVariable("id") Long id);
}
