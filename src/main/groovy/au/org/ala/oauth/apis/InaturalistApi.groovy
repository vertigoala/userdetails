package au.org.ala.oauth.apis

import com.github.scribejava.core.builder.api.DefaultApi20

class InaturalistApi extends DefaultApi20 {

    static final String BASE_URL = 'https://www.inaturalist.org/'

    private static class InstanceHolder {
        private static final InaturalistApi INSTANCE = new InaturalistApi()
    }

    static InaturalistApi instance() {
        return InstanceHolder.INSTANCE
    }

    @Override
    String getAccessTokenEndpoint() {
        return "${BASE_URL}oauth/token"
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "${BASE_URL}oauth/authorize"
    }
}
