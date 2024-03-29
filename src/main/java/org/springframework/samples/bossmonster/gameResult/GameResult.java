package org.springframework.samples.bossmonster.gameResult;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

     @NotNull
     private Double minutes;
     @NotNull
     @DateTimeFormat(pattern = "yyyy/MM/dd")
     private LocalDate date;
     @NotNull
     private Integer rounds;

     private String souls;
     private String healths;

   @OneToOne( optional = true)
   @JoinColumn(name = "winner", referencedColumnName = "username")
   private User winner;

   @ManyToMany
   @JoinTable(
      name = "results_users",
      joinColumns = @JoinColumn(name="game_result_id"),
      inverseJoinColumns = @JoinColumn(name= "user_id"))
   private List<User> participants;
}
