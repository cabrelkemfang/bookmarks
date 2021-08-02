package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.domain.Poll;
import grow.together.io.bookmarks.repository.PollRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class PollService {
    private final PollRepository pollRepository;

    public List<Poll> getAllPoll() {
        List<Poll> polls = this.pollRepository.findAll();
        return polls;
    }

    public void createPoll(Poll poll) {
        this.pollRepository.save(poll);
    }


}
