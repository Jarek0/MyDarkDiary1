
package myDarkDiary.service.model;


import java.util.Iterator;
import java.util.Objects;
import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "user")
public class User{
    
   private Long id;
    private String username;
    private String password;
    private String email;
    private boolean enabled;
    private boolean online;
    private boolean banned;
    private String passwordConfirm;
    private VerificationToken token;
    private Set<Role> roles;
    private Set<User> users;
    //user profile information
    private Image avatar;
    private String discription;
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    
    public boolean getOnline() {
        return online;
    }

    public void setOnline(boolean online) {
        this.online = online;
    }
    
    public boolean getBanned() {
        return banned;
    }
    
    public void setBanned(boolean banned) {
        
        this.banned = banned;
    }


    @Transient
    public String getPasswordConfirm() {
        return passwordConfirm;
    }

    public void setPasswordConfirm(String passwordConfirm) {
        this.passwordConfirm = passwordConfirm;
    }
    
    

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_user", joinColumns = @JoinColumn(name = "user_id1"), inverseJoinColumns = @JoinColumn(name = "user_id2"))
    public Set<User> getUsers() {
        return users;
    }
    
    public void setUsers(Set<User> users) {
        this.users = users;
    }
   
    public boolean isFriend(Long userId){
        Iterator<User> iterator = users.iterator();
        while (iterator.hasNext()) {
        User element = iterator.next();
        if (Objects.equals(element.getId(), userId)) {
        return true;
        }
        }
        return false;
    }
    @OneToOne(mappedBy = "user")
    public VerificationToken getToken() {
        return token;
    }

    public void setToken(VerificationToken token) {
        this.token = token;
    }
    
    @OneToOne(mappedBy = "userId")
    public Image getAvatar() {
        return avatar;
    }

    public void setAvatar(Image avatar) {
        this.avatar = avatar;
    }
    
    public String getDiscription() {
        return discription;
    }

    public void setDiscription(String discription) {
        this.discription = discription;
    }

}
