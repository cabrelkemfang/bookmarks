package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.domain.MetaData;
import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;
import grow.together.io.bookmarks.errorhandler.RetryException;
import grow.together.io.bookmarks.service.MetaDataService;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

@Service
@Slf4j
public class MetaDataServiceImpl implements MetaDataService {

    @Override
    public DataResponse<MetaDataDto> getMetData(String url) throws IOException {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url).get();
            log.info(getAttribute(document, "meta[name=description]"));
            log.info(document.toString());
            MetaDataDto metaDataDto = new MetaDataDto();


            if (getAttribute(document, "meta[name=title]").isEmpty() && getAttribute(document, "meta[property=og:title]").isEmpty()) {
                metaDataDto.setTitle(getAttribute(document, "meta[name=description]"));
            } else if (getAttribute(document, "meta[property=og:title]").isEmpty()) {
                metaDataDto.setTitle(getAttribute(document, "meta[name=title]"));
            } else {
                metaDataDto.setTitle(getAttribute(document, "meta[property=og:title]"));
            }
            metaDataDto.setUrl(url);

//            if (getAttribute(document, "meta[property=og:url]").isEmpty()) {
//                metaDataDto.setUrl(url);
//            } else {
//                metaDataDto.setUrl(getAttribute(document, "meta[property=og:url]"));
//            }
            if (getAttribute(document, "meta[property=og:image]").isEmpty()) {
                metaDataDto.setImageLink(getAttribute(document, "meta[name=og:image]"));
            } else {
                metaDataDto.setImageLink(getAttribute(document, "meta[property=og:image]"));
            }


            metaDataDto.setIconLink(getAttribute(document, "link[rel=icon]"));

            return new DataResponse<MetaDataDto>("Meta Data Load Successfully", HttpStatus.OK.value(), metaDataDto);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public MetaDataDto getMetaData(String url) throws IOException {
        log.info("Billing Service Failed");
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url).get();

            MetaDataDto metaDataDto = new MetaDataDto();

            if (getAttribute(document, "meta[name=title]").isEmpty() && getAttribute(document, "meta[property=og:title]").isEmpty()) {
                metaDataDto.setTitle(getAttribute(document, "meta[name=description]"));
            } else if (getAttribute(document, "meta[property=og:title]").isEmpty()) {
                metaDataDto.setTitle(getAttribute(document, "meta[name=title]"));
            } else {
                metaDataDto.setTitle(getAttribute(document, "meta[property=og:title]"));
            }
            metaDataDto.setUrl(url);

            if (getAttribute(document, "meta[property=og:image]").isEmpty()) {
                metaDataDto.setImageLink(getAttribute(document, "meta[name=og:image]"));
            } else {
                metaDataDto.setImageLink(getAttribute(document, "meta[property=og:image]"));
            }

            return metaDataDto;
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String getResponseFallback(UnknownHostException e, String url) {
        throw new RetryException("Meta Data Unavailable please Retry Again");
    }

    private String getAttribute(Document document, String value) {
        return document.select(value)
                .attr("content");
    }
}
