package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.common.VariableName;
import grow.together.io.bookmarks.domain.SubscriberStatus;
import grow.together.io.bookmarks.domain.Subscriber;
import grow.together.io.bookmarks.dtomodel.*;
import grow.together.io.bookmarks.errorhandler.BadRequestException;
import grow.together.io.bookmarks.errorhandler.ResourceNotFoundException;
import grow.together.io.bookmarks.eventlistener.event.SubscriptionEvent;
import grow.together.io.bookmarks.repository.SubscribersRepository;
import grow.together.io.bookmarks.service.SubscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class SubscribersServiceImpl implements SubscriberService {

    private final SubscribersRepository subscribersRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public SubscribersServiceImpl(SubscribersRepository subscribersRepository, ApplicationEventPublisher eventPublisher) {
        this.subscribersRepository = subscribersRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public DataResponse<Void> subscribe(SubcriberDtoIn subcriberDtoIn) {
        Subscriber subscriber = new Subscriber();
        subscriber.setEmail(subcriberDtoIn.getEmail());
        subscriber.setName(subcriberDtoIn.getName());
        subscriber.setStatus(SubscriberStatus.SUBSCRIBE);
        this.subscribersRepository.save(subscriber);

        eventPublisher.publishEvent(new SubscriptionEvent(this, subscriber));
        return new DataResponse<>("Successfully Subscribe", HttpStatus.CREATED.value());
    }

    @Override
    public PageableResult<SubscriberDtoOut> getAllSubscribers(int page, int size) {

        if (page < 0) {
            throw new BadRequestException(VariableName.PAGE_LESS_THAN_ZERO);
        }

        Page<Subscriber> subscribers = this.subscribersRepository.findAll(PageRequest.of(page - 1, size));

        return new PageableResult<>(page,
                size,
                subscribers.getTotalElements(),
                subscribers.getTotalPages(),
                subscribers.getContent().stream().map(SubscriberDtoOut::new).collect(Collectors.toList()));
    }

    @Override
    public DataResponse<Void> unsubscribe(UnsubscribeDtoIn unsubscribeDtoIn) {

        //check if the user have already subscribe
        Subscriber subscriber = this.subscribersRepository.findByEmail(unsubscribeDtoIn.getEmail())
                .orElseThrow(() -> new ResourceNotFoundException("Not Subscription Found For This Email"));

        subscriber.setStatus(SubscriberStatus.UNSUBSCRIBE);
        subscriber.setReason(subscriber.getReason());
        this.subscribersRepository.save(subscriber);

        return new DataResponse<>("Successfully Unsubscribe", HttpStatus.ACCEPTED.value());
    }

}
