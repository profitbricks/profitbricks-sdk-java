package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Labels;

import java.io.IOException;

public class Label extends AbstractBaseApi {

    public Label() {
        super("labels");
    }

    /**
     * Retrieve all labels you have on your account resources.
     *
     * @return Labels object with properties and metadata.
     */
    public Labels getAllLabels() throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withDepth().build(),null, Labels.class);
    }

    /**
     * Retrieve a label bu its URN.
     *
     * @param  urn The unique URN of the label.
     * @return Label object with properties and metadata.
     */
    public com.ionosenterprise.rest.domain.Label getLabel(String urn) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().appendPathSegment(urn).withDepth().build(),
                null, com.ionosenterprise.rest.domain.Label.class);
    }
}
