package hello;

import lombok.*;
import org.springframework.hateoas.ResourceSupport;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Greeting extends ResourceSupport {

    public String content;

    /*@JsonCreator
    public Greeting(@JsonProperty("content") String content) {
        this.content = content;
    }*/

}
