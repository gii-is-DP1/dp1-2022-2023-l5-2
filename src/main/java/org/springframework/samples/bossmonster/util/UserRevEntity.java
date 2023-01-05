package org.springframework.samples.bossmonster.util;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.hibernate.envers.DefaultRevisionEntity;
import org.hibernate.envers.RevisionEntity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@RevisionEntity(UserRevisionListener.class)
public class UserRevEntity extends DefaultRevisionEntity {
        @Column(name = "user")
        private String username;
}



