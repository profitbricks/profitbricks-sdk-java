package com.ionosenterprise.sdk;

import com.ionosenterprise.rest.client.RestClientException;
import com.ionosenterprise.rest.domain.Label;
import com.ionosenterprise.rest.domain.Labels;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

public class AbstractLabelApi extends AbstractBaseApi {

    private static final String LABELS_PATH_SEGMENT = "/labels";

    public AbstractLabelApi(String pathFormat) {
        super(pathFormat);
    }

    /**
     * Returns the rest response containing all labels for single resource.
     *
     * @param resourceId the id of the resource for which to get the labels
     * @param pathParams the params form the path to the resource
     * @return the Labels object containing a list of Label objects.
     * @throws RestClientException
     * @throws IOException
     */
    public Labels getAllLabels(String resourceId, String... pathParams) throws RestClientException, IOException {
        return client.get(getResourcePathBuilder().withPathParams(pathParams).appendPathSegment(resourceId)
                .appendPathSegment(LABELS_PATH_SEGMENT).withDepth().build(), null, Labels.class);
    }

    /**
     * Returns the rest response containing a label fetching using resource id and label key.
     *
     * @param labelKey the key of the label to retrieve
     * @param resourceId the id of the resource for which to get the labels
     * @param pathParams the params form the path to the resource
     * @return the Labels object containing a list of Label objects.
     * @throws RestClientException
     * @throws IOException
     */
    public Label getLabel(String labelKey, String resourceId, String... pathParams)
            throws RestClientException, IOException {

        return client.get(getResourcePathBuilder().withPathParams(pathParams).appendPathSegment(resourceId)
                .appendPathSegment(LABELS_PATH_SEGMENT).appendPathSegment(labelKey).withDepth().build(),
                null, Label.class);
    }

    /**
     * Create label on a particular resource.
     *
     * @param label object to be created.
     * @param resourceId the id of the resource for which to get the labels
     * @param pathParams the params form the path to the resource
     * @return created label object.
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws RestClientException
     * @throws IOException
     */
    public Label createLabel(Label label, String resourceId, String... pathParams) throws InvocationTargetException,
            NoSuchMethodException, IllegalAccessException, RestClientException, IOException {

        return client.create(getResourcePathBuilder().withPathParams(pathParams).appendPathSegment(resourceId)
                .appendPathSegment(LABELS_PATH_SEGMENT).build(), label, Label.class, 201);
    }

    /**
     * Update the value of the label.
     *
     * @param labelKey the key of the label.
     * @param labelProperties the label Properties object that has the following properties:
     * <br>
     * value = The value of the label key.
     * @param resourceId the id of the resource for which to get the labels
     * @param pathParams the params form the path to the resource
     * @return updated label object.
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @throws IllegalAccessException
     * @throws RestClientException
     * @throws IOException
     */
    public Label updateLabel(String labelKey, Label.Properties labelProperties, String resourceId, String... pathParams)
            throws InvocationTargetException, NoSuchMethodException, IllegalAccessException,
            RestClientException, IOException {

        return client.put(getResourcePathBuilder().withPathParams(pathParams).appendPathSegment(resourceId)
                .appendPathSegment(LABELS_PATH_SEGMENT).appendPathSegment(labelKey).build(),
                labelProperties, Label.class, 200);
    }

    /**
     * Delete a label form a particular resource.
     *
     * @param labelKey the key of the label
     * @param resourceId the id of the resource for which to get the labels
     * @param pathParams the params form the path to the resource
     * @throws RestClientException
     * @throws IOException
     */
    public void deleteLabel(String labelKey, String resourceId, String... pathParams)
            throws RestClientException, IOException {

        client.delete(getResourcePathBuilder().withPathParams(pathParams).appendPathSegment(resourceId)
                .appendPathSegment(LABELS_PATH_SEGMENT).appendPathSegment(labelKey).build(), 200);
    }
}
