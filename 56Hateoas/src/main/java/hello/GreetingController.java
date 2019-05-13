package hello;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.mvc.ControllerLinkBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@RestController
public class GreetingController {

    private static ArrayList<User> lst = new ArrayList<User>(10);

    static {
        User user1 = new User(1, "Mohit", "10");
        User user2 = new User(2, "Rohit", "20");
        User user3 = new User(3, "Test", "30");
        lst.add(user1);
        lst.add(user2);
        lst.add(user3);

    }
    @GetMapping("/user")
    public ArrayList<User> getAllUserData() {
        return lst;
    }

    @GetMapping("/myuser/{id}")
    public User findById(@PathVariable int id) {
        return lst.get(id);
    }

    @GetMapping(path = "/user/{id}")
    public Resource<User> getSpecUser(@PathVariable int id) {

        User user = lst.get(id);
        System.out.println(user.name);
        Resource<User> resource = new Resource<User>(user);
        ControllerLinkBuilder linkTo = linkTo(methodOn(this.getClass()).getAllUserData());
        resource.add(linkTo.withRel("display-user"));

        //controller link 2
        // containing link to access itself
        linkTo=ControllerLinkBuilder.linkTo(ControllerLinkBuilder.methodOn(this.getClass()).findById(id));

        // adding to resource
        resource.add(linkTo.withSelfRel().withTitle("sample-title").withType("String"));


        return resource;
    }

    @GetMapping(path = "/userwohat/{id}")
    public Resource<User> getSpecUserWithoutHat(@PathVariable int id) {

        User user = lst.get(id);
        System.out.println(user.name);
        Resource<User> resource = new Resource<User>(user);
        //linkTo(methodOn(this.getClass()).getAllUserData()).withRel("display-all-user");
        resource.add(linkTo(methodOn(this.getClass()).getAllUserData()).withRel("display-all-user"));
        resource.add(linkTo(methodOn(this.getClass()).getSpecUser(id)).withSelfRel());
        return resource;
    }

    @GetMapping("/all")
    public List<Emp> getAll() {
        Emp users1 = getUser();
        Emp users2 = new Emp("1","Sam", "10");
        return Arrays.asList(users1, users2);
    }

    private Emp getUser() {
        Emp emp = new Emp("2","Peter", "20");
        Link link = ControllerLinkBuilder.linkTo(GreetingController.class)
                .slash(emp.name)
                .withSelfRel();
        emp.add(link);
        return emp;
    }

    @GetMapping(value = "/hateoas/all", produces = MediaTypes.HAL_JSON_VALUE)
    public List<Emp> getHateOASAll() {
        Emp users1 = new Emp("1","Peter", "hg");
        Link link = ControllerLinkBuilder.linkTo(GreetingController.class)
                .slash(users1.age).withSelfRel();
        Link link2 = ControllerLinkBuilder.linkTo(GreetingController.class)
                .slash(users1.age).withRel("salary");
        users1.add(link, link2);
        Emp users2 = new Emp("77","Sam", "77");
        users2.add(ControllerLinkBuilder.linkTo(GreetingController.class)
                .slash(users2.age).withSelfRel());
        return Arrays.asList(users1, users2);
    }

    @RequestMapping("/greeting/{name}")
    public HttpEntity<Greeting> greeting(@PathVariable String name) {
        Greeting greeting = new Greeting(name);
        greeting.add(linkTo(methodOn(GreetingController.class).greeting(name)).withSelfRel());
        return new ResponseEntity<>(greeting, HttpStatus.OK);
    }
}
