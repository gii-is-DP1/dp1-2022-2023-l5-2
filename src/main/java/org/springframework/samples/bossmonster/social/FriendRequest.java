package org.springframework.samples.bossmonster.social;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "friend_requests")
public class FriendRequest extends BaseEntity{
    
    @Column(columnDefinition = "boolean default false")
    private Boolean accepted=false;

    @OneToOne
    @NotNull(message = "A request must have a requester")
    @JoinColumn(name = "requester")
    private User requester;

    @OneToOne
    @NotNull(message = "A request must have a receiver")
    @JoinColumn(name = "receiver")
    private User receiver;

}
