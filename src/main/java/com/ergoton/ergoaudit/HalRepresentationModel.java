package com.ergoton.ergoaudit;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.hateoas.RepresentationModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public abstract class HalRepresentationModel<T extends HalRepresentationModel<? extends T>> extends RepresentationModel<T> {

    private final Map<String, ArrayList<RepresentationModel>> embedded = new HashMap<String, ArrayList<RepresentationModel>>();

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    @JsonProperty("_embedded")
    public Map<String, ArrayList<RepresentationModel>> getEmbeddedResources() {
        return embedded;
    }

    public void embed(String relationship, RepresentationModel resource) {

        if (embedded.containsKey(relationship)) {

            ArrayList resourceList = embedded.get(relationship);
            resourceList.add(resource);

        } else {
            ArrayList<RepresentationModel> resourceList = new ArrayList<RepresentationModel>();
            resourceList.add(resource);
            embedded.put(relationship, resourceList);
        }

    }

}