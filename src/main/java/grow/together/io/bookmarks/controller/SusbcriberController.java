package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/bookmarksv1/")
public class SusbcriberController {

    private final SubscriberService subscriberService;

    @Autowired
    public SusbcriberController(SubscriberService subscriberService) {
        this.subscriberService = subscriberService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE, path = "/admin/subscribe")
    @PreAuthorize("hasAuthority('VIEW_SUBSCRIBER')")
    public PageableResult<SubscriberDtoOut> getAllSubscriber(@RequestParam(required = false, defaultValue = "1") int page,
                                                             @RequestParam(required = false, defaultValue = "10") int size) {
        return this.subscriberService.getAllSubscribers(page, size);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/subscribe")
    public DataResponse<Void> subscribe(@Valid @RequestBody SubcriberDtoIn subcriberDtoIn) {
        return this.subscriberService.subscribe(subcriberDtoIn);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, path = "/unsubscribe")
    public DataResponse<Void> unsubscribe(@Valid @RequestBody UnsubscribeDtoIn unsubscribeDtoIn) {
        return this.subscriberService.unsubscribe(unsubscribeDtoIn);
    }


}
