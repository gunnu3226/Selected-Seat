package io.nbc.selectedseat.db.core.domain.common;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@MappedSuperclass
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {
    @Column
    @CreatedDate
    private LocalDateTime createdAt;
    @LastModifiedDate
    @Column
    private LocalDateTime modifiedAt;

    @Column
    private LocalDateTime deletedAt;
}
