package org.springframework.samples.bossmonster.invitations;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class InvitationService {

    InvitationRepository repo;

    @Autowired
    public InvitationService(InvitationRepository repo){
        this.repo=repo;
    }
    public List<Invitation> getInvitations(String username){
        return repo.findAllInvitationsUser(username);
    }
    @Transactional
    public void sendInvitation(Invitation i){
        repo.save(i);
    }

    
}
