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
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Service
@Slf4j
public class MetaDataServiceImpl implements MetaDataService {

    @Override
    public DataResponse<MetaDataDto> getMetData(String url) throws IOException {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url).get();
//            log.info(document.toString());
            MetaDataDto metaDataDto = new MetaDataDto();

            metaDataDto.setDescription(getAttribute(document, "meta[property=og:description]"));
            metaDataDto.setImageLink(getAttribute(document, "meta[property=og:image]"));
            metaDataDto.setTitle(getAttribute(document, "meta[property=og:title]"));
            metaDataDto.setSiteName(getAttribute(document, "meta[property=og:site_name]"));
            metaDataDto.setUrl(getAttribute(document, "meta[property=og:url]"));
            metaDataDto.setIconLink(getAttribute(document, "link[rel=icon]"));

            return new DataResponse<MetaDataDto>("Meta Data Load Successfully", HttpStatus.OK.value(), metaDataDto);
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public MetaDataDto getMetaData(String url) throws IOException {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url).get();

            MetaDataDto metaDataDto = new MetaDataDto();

            metaDataDto.setDescription(getAttribute(document, "meta[property=og:description]"));
            metaDataDto.setImageLink(getAttribute(document, "meta[property=og:image]"));
            metaDataDto.setTitle(getAttribute(document, "meta[property=og:title]"));
            metaDataDto.setSiteName(getAttribute(document, "meta[property=og:site_name]"));
            metaDataDto.setUrl(getAttribute(document, "meta[property=og:url]"));

            return metaDataDto;
        } catch (IOException e) {
            throw new IOException(e);
        }
    }

    @Override
    public String getResponseFallback(RuntimeException e) {
        throw new RetryException("Meta Data Unavailable please Retry Again");
    }

    private String getAttribute(Document document, String value) {
        return document.select(value)
                .attr("content");
    }
}
