package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;

import java.io.IOException;
import java.net.UnknownHostException;

public interface MetaDataService {
    public DataResponse<MetaDataDto> getMetData(String url) throws IOException;

    @Retryable(maxAttempts = 3, value = {UnknownHostException.class}, backoff = @Backoff(delay = 2000, multiplier = 2))
    public MetaDataDto getMetaData(String url) throws IOException;

    @Recover
    public String getResponseFallback(UnknownHostException e, String url);
}
