package grow.together.io.bookmarks.mapper;

import grow.together.io.bookmarks.domain.MetaData;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;
import org.springframework.stereotype.Service;

@Service
public class MetaDataMapper {

    public MetaData map(MetaDataDto metaDataDto) {
        MetaData metaData = new MetaData();

        metaData.setTitle(metaDataDto.getTitle());
        metaData.setDescription(metaDataDto.getDescription());
        metaData.setUrl(metaDataDto.getUrl());
        metaData.setSiteName(metaDataDto.getSiteName());
        metaData.setImageLink(metaDataDto.getImageLink());
        return metaData;
    }
}
