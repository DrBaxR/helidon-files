package com.example.app;

import jakarta.ws.rs.core.Feature;
import jakarta.ws.rs.core.FeatureContext;
import jakarta.ws.rs.ext.Provider;
import org.glassfish.jersey.media.multipart.MultiPartFeature;

@Provider
public class MultiPartFeatureProvider implements Feature {

    @Override
    public boolean configure(FeatureContext featureContext) {
        return new MultiPartFeature().configure(featureContext);
    }
}
