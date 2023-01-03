package org.springframework.samples.bossmonster.gameResult;

import java.time.LocalDate;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.samples.bossmonster.model.BaseEntity;
import org.springframework.samples.bossmonster.user.User;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "game_result")
public class GameResult extends BaseEntity{

   @NotEmpty
   private Double duration;
   @NotEmpty
   @DateTimeFormat(pattern = "yyyy/MM/dd")
   private LocalDate date;

   private String souls;
   private String healths;

   @ManyToOne( optional = true)
   @JoinColumn(name = "winner", referencedColumnName = "username")
   private User winner;
      
   @ManyToMany
   @JoinTable(
      name = "results_users",
      joinColumns = @JoinColumn(name="game_result_id"),
      inverseJoinColumns = @JoinColumn(name= "user_id"))
   private Set<User> participants;
}
