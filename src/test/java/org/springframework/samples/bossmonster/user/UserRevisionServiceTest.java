/*
package org.springframework.samples.bossmonster.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.List;

import org.hibernate.envers.AuditReader;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.samples.bossmonster.game.GameService;
import org.springframework.samples.bossmonster.invitations.InvitationService;
import org.springframework.samples.bossmonster.util.AuditDTO;
import org.springframework.samples.bossmonster.util.UserRevEntity;
import org.springframework.samples.bossmonster.util.UserRevisionService;
import org.springframework.stereotype.Service;


@ExtendWith(MockitoExtension.class)

public class UserRevisionServiceTest {

    @Autowired
    private UserRevisionService userRevisionService;

    @Test
    public void testGetRevisionHistory() {
        User user = new User();
        user.setUsername("username");
        user.setPassword("password");
        user.setEnabled(true);
        UserRevEntity revision = new UserRevEntity();
        revision.setUsername("testUser");
        List<Number> revisionNumbers = List.of(1, 2);

        when(auditReader.getRevisions(User.class, "username")).thenReturn(revisionNumbers);
        when(auditReader.find(User.class, "username", 1)).thenReturn(user);
        when(auditReader.find(User.class, "username", 2)).thenReturn(user);
        when(auditReader.createQuery().forEntitiesAtRevision(UserRevEntity.class, 1).getSingleResult()).thenReturn(revision);
        when(auditReader.createQuery().forEntitiesAtRevision(UserRevEntity.class, 2).getSingleResult()).thenReturn(revision);

        List<AuditDTO> result = userRevisionService.getRevisionHistory("username");
        assertEquals(2, result.size());
        assertEquals("username", result.get(0).getUser().getUsername());
        assertEquals("testUser", result.get(0).getModifiedBy());
    }

}

*/
