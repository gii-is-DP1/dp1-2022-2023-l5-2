/*
package org.springframework.samples.bossmonster.util;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.envers.AuditReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.bossmonster.user.User;
import org.springframework.stereotype.Service;

@Service
public class UserRevisionService {

    private AuditReader auditReader;

    public UserRevisionService(AuditReader auditReader){
        this.auditReader = auditReader;
    }

    public List<AuditDTO> getRevisionHistory(String username) {
        List<AuditDTO> revisionHistory = new ArrayList<>();
        List<Number> revisionNumbers = auditReader.getRevisions(User.class, username);
        for (Number rev : revisionNumbers) {
            User user = auditReader.find(User.class, username, rev);
            UserRevEntity revision = (UserRevEntity) auditReader.createQuery()
                    .forEntitiesAtRevision(UserRevEntity.class, rev)
                    .getSingleResult();
            String modifiedBy = revision.getUsername();
            revisionHistory.add(new AuditDTO(rev.intValue(), user, modifiedBy));
        }
        return revisionHistory;
    }
    
    
}
*/
