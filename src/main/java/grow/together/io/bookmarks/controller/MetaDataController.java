package grow.together.io.bookmarks.controller;

import grow.together.io.bookmarks.dtomodel.DataResponse;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;
import grow.together.io.bookmarks.service.MetaDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api/bookmarks/v1/metadata")
public class MetaDataController {

    private final MetaDataService metaDataService;

    @Autowired
    public MetaDataController(MetaDataService metaDataService) {
        this.metaDataService = metaDataService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public DataResponse<MetaDataDto> getMetaData(@RequestParam String url) throws IOException {
        return this.metaDataService.getMetData(url);
    }
}
