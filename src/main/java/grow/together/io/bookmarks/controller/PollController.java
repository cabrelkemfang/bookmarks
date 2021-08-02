package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.domain.Poll;
import grow.together.io.bookmarks.service.PollService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/polls")
@RequiredArgsConstructor
public class PollController {
    private final PollService pollService;

    @GetMapping()
    public ResponseEntity<List<Poll>> getAllPolls() {
        List<Poll> pollList = this.pollService.getAllPoll();
        return new ResponseEntity<>(pollList, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
        this.pollService.createPoll(poll);
        return new ResponseEntity<>(null, HttpStatus.CREATED);
    }
}
