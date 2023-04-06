package com.example.usermanagementapi.general.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.sql.Timestamp;
import java.util.Objects;

@MappedSuperclass
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BaseTableImpl implements BaseTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @UpdateTimestamp
    private Timestamp dateUpdated;
    @CreationTimestamp
    private Timestamp dateCreation;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        BaseTableImpl baseTableImpl = (BaseTableImpl) o;
        return id != null && Objects.equals(id, baseTableImpl.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
