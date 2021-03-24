package grow.together.io.bookmarks.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;

@RestController
public class HomeController {

    @GetMapping(path = "/")
    public String welcome() {
        return Instant.now().toString();
    }
}
