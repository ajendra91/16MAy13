package hello;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Emp extends ResourceSupport {

    public String demo;
    public String name;
    public String age;
}
