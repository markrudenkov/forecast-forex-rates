package spaApp.utils.repository.model;

import org.springframework.data.domain.Persistable;

public class ModelDb implements Persistable<Long> {

    private Long id;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public boolean isNew() {
        return false;
    }

    public void setId(Long id) {
        this.id = id;
    }
}