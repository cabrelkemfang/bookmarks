package grow.together.io.bookmarks.serviceimpl;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.service.MetaDataService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MetaDataServiceImpl implements MetaDataService {


    @Override
    public DataResponse<Void> getMetData(String url) {
        Document document;
        try {
            //Get Document object after parsing the html from given url.
            document = Jsoup.connect(url)
                    .get();

            //Get description from document object.
            String description =
                    document.select("meta[name=description]").get(0)
                            .attr("content");
            //Print description.
            System.out.println("Meta Description: " + description);

            //Get keywords from document object.
            String keywords =
                    document.select("meta[name=keywords]").first()
                            .attr("content");
            //Print keywords.
            System.out.println("Meta Keyword : " + keywords);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return new DataResponse<>("Meta Data Load Successfully", HttpStatus.OK.value());
    }
}
