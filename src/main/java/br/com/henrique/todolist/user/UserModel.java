package br.com.henrique.todolist.user;

import java.time.LocalDateTime;
import java.util.UUID;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
//import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
//import lombok.Getter;
//import lombok.Setter;

@Data
@Entity(name = "tb_users")
//@Getter
//@Setter
public class UserModel {
    //@Getter
    //@Setter
    @Id
    @GeneratedValue(generator = "UUID")
    private UUID id;

    //@Column(name = "user")
    @Column(unique = true)
    private String username;
    private String name;
    private String password;

   /*  public void setName(String name) {
        this.name = name;
    } */

    /* public String getName() {
        return name;
    } */

    @CreationTimestamp
    private LocalDateTime createdAt;
}
