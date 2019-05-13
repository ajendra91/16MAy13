package hello;


import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(url="http://localhost:9090/",name="USER-CLIENT")
public interface UserClient {

	@RequestMapping(value = "/users")
	public String getUsers();

	@RequestMapping(value = "/user/{name}")
	String getId(@PathVariable String name);
}