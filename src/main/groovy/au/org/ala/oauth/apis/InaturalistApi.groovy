package au.org.ala.oauth.apis

import com.github.scribejava.core.builder.api.DefaultApi20
import grails.util.Holders

class InaturalistApi extends DefaultApi20 {

    private static final String DEFAULT_BASE_URL = 'https://www.inaturalist.org/'

    private static class InstanceHolder {
        private static final InaturalistApi INSTANCE = new InaturalistApi()
    }

    static InaturalistApi instance() {
        return InstanceHolder.INSTANCE
    }

    static String getBaseUrl() {
        Holders.config.getProperty("inaturalist.baseUrl", String, DEFAULT_BASE_URL)
    }

    @Override
    String getAccessTokenEndpoint() {
        return "${baseUrl}oauth/token"
    }

    @Override
    protected String getAuthorizationBaseUrl() {
        return "${baseUrl}oauth/authorize"
    }
}
