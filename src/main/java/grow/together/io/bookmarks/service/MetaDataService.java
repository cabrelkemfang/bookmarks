package grow.together.io.bookmarks.service;

import grow.together.io.bookmarks.dtomodel.DataResponse;

public interface MetaDataService {
    public DataResponse<Void> getMetData(String url);
}
