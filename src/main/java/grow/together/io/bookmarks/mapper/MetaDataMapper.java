package grow.together.io.bookmarks.mapper;

import grow.together.io.bookmarks.domain.MetaData;
import grow.together.io.bookmarks.dtomodel.MetaDataDto;
import org.springframework.stereotype.Service;

@Service
public final class MetaDataMapper {


    public static MetaData map(final MetaDataDto metaDataDto) {
        MetaData metaData = new MetaData();
        metaData.setTitle(metaDataDto.getTitle());
        metaData.setUrl(metaDataDto.getUrl());
        metaData.setImageLink(metaDataDto.getImageLink());
        return metaData;
    }
}
