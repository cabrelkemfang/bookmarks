package grow.together.io.bookmarks.service;


import grow.together.io.bookmarks.dtomodel.*;

public interface SubscriberService {
    DataResponse<Void> subscribe(SubcriberDtoIn subcriberDtoIn);

    PageableResult<SubscriberDtoOut> getAllSubscribers(int page, int size);

    DataResponse<Void> unsubscribe(UnsubscribeDtoIn unsubscribeDtoIn);

}
