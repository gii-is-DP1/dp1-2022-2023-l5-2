package org.springframework.samples.bossmonster.social;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "friend_requests")
public class FriendRequest extends BaseEntity{
    
    private Boolean accepted;

    @OneToOne
    @JoinColumn(name = "requester")
    private User requester;

    @OneToOne
    @JoinColumn(name = "receiver")
    private User receiver;

}
