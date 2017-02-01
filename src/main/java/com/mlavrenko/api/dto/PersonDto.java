package com.mlavrenko.api.dto;

import org.springframework.hateoas.ResourceSupport;

public class PersonDto extends ResourceSupport {
    private Long entityId;

    public Long getEntityId() {
        return entityId;
    }

    public void setEntityId(Long entityId) {
        this.entityId = entityId;
    }
}
