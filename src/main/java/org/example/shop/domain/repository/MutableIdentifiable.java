package org.example.shop.domain.repository;

public interface MutableIdentifiable<ID> extends Identifiable<ID> {
    void setId(ID id);
}
